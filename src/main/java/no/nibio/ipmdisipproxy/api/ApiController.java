package no.nibio.ipmdisipproxy.api;

import no.nibio.ipmdisipproxy.exception.BadRequestException;
import no.nibio.ipmdisipproxy.exception.UnauthorizedException;
import no.nibio.ipmdisipproxy.model.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpHeaders;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

import static no.nibio.ipmdisipproxy.api.RequestConverter.ipmToIsipRequest;
import static no.nibio.ipmdisipproxy.api.ResponseConverter.isipToImpdResponse;

@RestController
public class ApiController {

    private final IsipService isipService;
    private final TimeService timeService;

    @Value("${model.base.url}")
    private String baseUrl;

    @Autowired
    public ApiController(IsipService isipService, TimeService timeService) {
        this.isipService = isipService;
        this.timeService = timeService;
    }

    private static void validateParameters(Crop crop, Disease disease, IpmdWeatherData weatherData) {
        if (!crop.isCropDisease(disease)) {
            throw new BadRequestException(String.format("Given crop/disease combination (%s/%s) is not valid", crop.getEppoCode(), disease.getEppoCode()));
        }
        if (weatherData == null) {
            throw new BadRequestException("Weather data missing from request");
        }
        List<IpmdLocationWeatherData> locationWeatherDataList = weatherData.getLocationWeatherData();
        if (locationWeatherDataList.size() != 1) {
            throw new BadRequestException("Unexpected number of location weather data in request: " + locationWeatherDataList.size());
        }
    }

    @GetMapping("/")
    public ResponseEntity<String> home() {
        return ResponseEntity.ok("Velkommen til isip-ipm-proxy");
    }

    @PostMapping("/siggetreide")
    public ResponseEntity<IpmdResponse> triggerSiggetreide(
            @RequestBody IpmdRequest ipmdRequest,
            @RequestHeader(HttpHeaders.AUTHORIZATION) String authorizationHeader) {

        String token = extractTokenFromHeader(authorizationHeader);
        String simulationDate = timeService.getCurrentDate().toString();

        Crop crop = Crop.fromEppoCode(ipmdRequest.getCrop());
        Disease disease = Disease.fromEppoCode(ipmdRequest.getModelId());
        IpmdWeatherData weatherData = ipmdRequest.getWeatherData();
        validateParameters(crop, disease, weatherData);

        IpmdLocationWeatherData locationWeatherData = weatherData.getLocationWeatherData().get(0);
        Double longitude = locationWeatherData.getLongitude();
        Double latitude = locationWeatherData.getLatitude();

        IsipRequest isipRequest = ipmToIsipRequest(
                crop,
                disease,
                longitude,
                latitude,
                simulationDate,
                ipmdRequest.getWeatherData()
        );
        return ResponseEntity.ok(
                isipToImpdResponse(
                        ipmdRequest.getTimeZone(),
                        longitude,
                        latitude,
                        disease,
                        isipService.triggerSiggetreide(
                                isipRequest,
                                token
                        )
                ));
    }

    private String extractTokenFromHeader(String authorizationHeader) {
        if (authorizationHeader != null && authorizationHeader.startsWith("Bearer ")) {
            return authorizationHeader.substring(7);
        } else {
            throw new UnauthorizedException("Invalid Authorization header");
        }
    }


}