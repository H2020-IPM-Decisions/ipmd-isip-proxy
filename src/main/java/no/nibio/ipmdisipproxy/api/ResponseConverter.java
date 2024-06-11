package no.nibio.ipmdisipproxy.api;

import no.nibio.ipmdisipproxy.exception.ExternalApiException;
import no.nibio.ipmdisipproxy.model.*;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.List;

/** ResponseConverter contains methods for converting a given ISIP response to IPMD format */
public class ResponseConverter {

  /**
   * Convert ISIP response to IPMD format
   *
   * @param timeZone The timezone of the given dates - CURRENTLY NOT IN USE
   * @param longitude The longitude of the location
   * @param latitude The latitude of the location
   * @param disease The disease for which the model has been run
   * @param isipResponse The response from the ISIP endpoint
   * @return The response in IPMD format
   */
  public static IpmdResponse isipToImpdResponse(
      String timeZone,
      double longitude,
      double latitude,
      Disease disease,
      IsipResponse isipResponse) {
    IsipResponse.Nodes.Result.Variable dateResult =
        isipResponse.getNodes().getResult().getVariables().get("date");
    String dateTimeStart = findDateTimeStart(timeZone, dateResult.getData());
    String dateTimeEnd = findDateTimeEnd(timeZone, dateResult.getData());

    IpmdResponse ipmdResponse = new IpmdResponse(dateTimeStart, dateTimeEnd, longitude, latitude);

    IsipResponse.Nodes.Result.Variable diseaseResult =
        isipResponse.getNodes().getResult().getVariables().get(disease.getIsipName());
    if (diseaseResult == null || diseaseResult.getData() == null) {
      // Could be due to invalid weather data (not enough) being provided
      throw new ExternalApiException(
          "No data for " + disease.getEppoCode() + " in response from external API");
    }

    IpmdLocationResult ipmdLocationResult = ipmdResponse.getLocationResult().get(0);
    List<Integer> warningStatusList = ipmdLocationResult.getWarningStatus();
    for (String value : diseaseResult.getData()) {
      WarningStatus ws = WarningStatus.fromIsipCode(Integer.parseInt(value));
      warningStatusList.add(ws.getIpmCode());
    }
    return ipmdResponse;
  }

  /**
   * Find the very first date in the given list of dates, return as datetime at start of day
   *
   * @param timeZone CURRENTLY NOT IN USE
   * @param dates List of date strings 'yyyy-MM-dd'
   * @return datetime string in the format 'yyyy-MM-ddTHH:mm:ssZ'
   */
  static String findDateTimeStart(String timeZone, List<String> dates) {
    if (dates != null && !dates.isEmpty()) {
      LocalDate localDate = LocalDate.parse(dates.get(0));
      LocalDateTime midnightUTC = localDate.atStartOfDay();
      // ZonedDateTime zonedDateTimeUTC = midnightUTC.atZone(ZoneId.of("UTC"));
      // ZonedDateTime zonedDateTimeLocal =
      // zonedDateTimeUTC.withZoneSameInstant(ZoneId.of(timeZone));
      DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd'T'HH:mm:ss'Z'");
      return midnightUTC.format(formatter);
    }
    return null;
  }

  /**
   * Find the very last date in the given list of dates, return as datetime at hour 23
   *
   * @param timeZone CURRENTLY NOT IN USE
   * @param dates List of date strings 'yyyy-MM-dd'
   * @return datetime string in the format 'yyyy-MM-ddTHH:mm:ssZ'
   */
  static String findDateTimeEnd(String timeZone, List<String> dates) {
    if (dates != null && !dates.isEmpty()) {
      LocalDate localDate = LocalDate.parse(dates.get(dates.size() - 1));
      LocalDateTime lastHourBeforeMidnightUTC = localDate.atTime(23, 0, 0);
      // ZonedDateTime zonedDateTimeUTC = lastHourBeforeMidnightUTC.atZone(ZoneId.of("UTC"));
      // ZonedDateTime zonedDateTimeLocal =
      // zonedDateTimeUTC.withZoneSameInstant(ZoneId.of(timeZone));
      DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd'T'HH:mm:ss'Z'");
      return lastHourBeforeMidnightUTC.format(formatter);
    }
    return null;
  }
}
