package no.nibio.ipmdisipproxy.controller;

import no.nibio.ipmdisipproxy.exception.BadRequestException;
import no.nibio.ipmdisipproxy.exception.UnauthorizedException;
import no.nibio.ipmdisipproxy.model.*;
import no.nibio.ipmdisipproxy.service.GISService;
import no.nibio.ipmdisipproxy.service.IsipService;
import no.nibio.ipmdisipproxy.service.TimeService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

import static no.nibio.ipmdisipproxy.service.IpmdResponseFactory.createImpdResponse;
import static no.nibio.ipmdisipproxy.service.IsipRequestFactory.appendWeatherData;
import static no.nibio.ipmdisipproxy.service.IsipRequestFactory.createIsipRequest;

/**
 * ApiController is a REST controller which provides an endpoint for triggering the siggetreide
 * model at ISIP. It converts requests and responses between the IPMD and ISIP formats.
 *
 * @since 0.0.1
 */
@RestController
public class ApiController {
    private static final Logger LOGGER = LoggerFactory.getLogger(ApiController.class);

    private final IsipService isipService;
    private final TimeService timeService;
    private final GISService gisService;

    @Autowired
    public ApiController(IsipService isipService, TimeService timeService, GISService gisService) {
        this.isipService = isipService;
        this.timeService = timeService;
        this.gisService = gisService;
    }

    @GetMapping("/")
    public ResponseEntity<String> home() {
        return ResponseEntity.ok("The ipmd-isip-proxy api is up and running!");
    }

    @PostMapping("/siggetreide")
    public ResponseEntity<IpmdResponse> triggerSiggetreide(
            @RequestBody IpmdRequest ipmdRequest,
            @RequestHeader(value = HttpHeaders.AUTHORIZATION, required = false)
            String authorizationHeader) {

        String token = extractTokenFromHeader(authorizationHeader);
        String simulationDate = timeService.getCurrentDate().toString();

        Crop crop = Crop.fromEppoCode(ipmdRequest.getCrop());
        Disease disease = Disease.fromEppoCode(ipmdRequest.getModelId());
        IpmdWeatherData weatherData = ipmdRequest.getWeatherData();
        validateParameters(crop, disease, weatherData);

        IpmdLocationWeatherData locationWeatherData = weatherData.getLocationWeatherData().get(0);
        Double latitude = locationWeatherData.getLatitude();
        Double longitude = locationWeatherData.getLongitude();

        IsipRequest isipRequest = createIsipRequest(crop, disease, latitude, longitude, simulationDate);
        if (!gisService.isLocationInGermany(latitude, longitude)) {
            LOGGER.info(
                    "Given location ({}, {}) is not in Germany, add weather data to request",
                    latitude,
                    longitude);
            appendWeatherData(isipRequest, ipmdRequest.getWeatherData());
        }

        LOGGER.info(
                "Trigger siggetreide with crop={} and disease={}",
                crop.getIsipName(),
                disease.getIsipName());
        return ResponseEntity.ok(
                createImpdResponse(
                        ipmdRequest.getTimeZone(),
                        latitude, longitude,
                        disease,
                        isipService.triggerSiggetreide(isipRequest, token)));
    }

    /**
     * Extracts token from given authorization header
     *
     * @param authorizationHeader The authorization header which contains the token
     * @return The token as it should be forwarded to ISIP
     */
    private String extractTokenFromHeader(String authorizationHeader) {
        if (authorizationHeader != null && authorizationHeader.startsWith("Bearer ")) {
            return authorizationHeader.substring(7);
        } else {
            throw new UnauthorizedException("Missing or invalid header 'Authorization'");
        }
    }

    /**
     * Validates input data received, and throws exception if errors are found
     *
     * @param crop        The crop to run the model for
     * @param disease     The disease to run the model for
     * @param weatherData The weather data on which the model should be run
     */
    private void validateParameters(Crop crop, Disease disease, IpmdWeatherData weatherData) {
        if (!crop.isCropDisease(disease)) {
            throw new BadRequestException(
                    String.format(
                            "Given crop/disease combination (%s/%s) is not valid",
                            crop.getEppoCode(), disease.getEppoCode()));
        }
        if (weatherData == null) {
            throw new BadRequestException("Weather data missing from request");
        }
        List<IpmdLocationWeatherData> locationWeatherDataList = weatherData.getLocationWeatherData();
        if (locationWeatherDataList.size() != 1) {
            throw new BadRequestException(
                    "Unexpected number of location weather data in request: "
                            + locationWeatherDataList.size());
        }
    }
}
