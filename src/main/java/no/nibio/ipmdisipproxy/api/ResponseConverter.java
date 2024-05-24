package no.nibio.ipmdisipproxy.api;

import no.nibio.ipmdisipproxy.model.*;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.List;

public class ResponseConverter {

    public static IpmdResponse isipToImpdResponse(String timeZone, double longitude, double latitude, Disease disease, IsipResponse isipResponse) {
        IsipResponse.Nodes.Result.Variable dateResult = isipResponse.getNodes().getResult().getVariables().get("date");
        String dateTimeStart = findDateTimeStart(timeZone, dateResult.getData());
        String dateTimeEnd = findDateTimeEnd(timeZone, dateResult.getData());

        IpmdResponse ipmdResponse = new IpmdResponse(dateTimeStart, dateTimeEnd, longitude, latitude);

        IsipResponse.Nodes.Result.Variable diseaseResult = isipResponse.getNodes().getResult().getVariables().get(disease.getIsipName());

        IpmdLocationResult ipmdLocationResult = ipmdResponse.getLocationResult().get(0);
        List<Integer> warningStatusList = ipmdLocationResult.getWarningStatus();
        for (String value : diseaseResult.getData()) {
            WarningStatus ws = WarningStatus.fromIsipCode(Integer.parseInt(value));
            warningStatusList.add(ws.getIpmCode());
        }
        return ipmdResponse;
    }

    static String findDateTimeStart(String timeZone, List<String> dates) {
        if (dates != null && !dates.isEmpty()) {
            LocalDate localDate = LocalDate.parse(dates.get(0));
            LocalDateTime midnightUTC = localDate.atStartOfDay();
            //ZonedDateTime zonedDateTimeUTC = midnightUTC.atZone(ZoneId.of("UTC"));
            //ZonedDateTime zonedDateTimeLocal = zonedDateTimeUTC.withZoneSameInstant(ZoneId.of(timeZone));
            DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd'T'HH:mm:ss'Z'");
            return midnightUTC.format(formatter);
        }
        return null;
    }

    static String findDateTimeEnd(String timeZone, List<String> dates) {
        if (dates != null && !dates.isEmpty()) {
            LocalDate localDate = LocalDate.parse(dates.get(dates.size() - 1));
            LocalDateTime lastHourBeforeMidnightUTC = localDate.atTime(23, 0, 0);
            //ZonedDateTime zonedDateTimeUTC = lastHourBeforeMidnightUTC.atZone(ZoneId.of("UTC"));
            //ZonedDateTime zonedDateTimeLocal = zonedDateTimeUTC.withZoneSameInstant(ZoneId.of(timeZone));
            DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd'T'HH:mm:ss'Z'");
            return lastHourBeforeMidnightUTC.format(formatter);
        }
        return null;
    }

}

