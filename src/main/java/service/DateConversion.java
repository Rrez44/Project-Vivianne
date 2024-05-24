package service;

import ENUMS.TravelTime;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

public class DateConversion {

    private static final DateTimeFormatter dateTimeFormatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");

    public static LocalDateTime localDate(LocalDate date,String time) {
        if (date != null) {
            String formattedDate = date.toString() +" "+time;
            return LocalDateTime.parse(formattedDate, dateTimeFormatter);
        }
        return null;
    }


    public static LocalDateTime fromDateTimeComponents(LocalDate date, String hour, String minute) {
        if (date != null && hour != null && minute != null) {
            StringBuilder dateTime = new StringBuilder()
                    .append(date.toString()).append(" ")
                    .append(hour).append(":")
                    .append(minute).append(":00");
            return LocalDateTime.parse(dateTime.toString(), dateTimeFormatter);
        }
        return null;
    }

    public static LocalDateTime calculateEndDateTime(LocalDateTime startDateTime, String duration) {
//                TravelTime travelTime1= ;
//        String duration = travelTime1.getTime();
//



        if (startDateTime != null && duration != null) {
            String[] durationSplit = duration.split(":");
            int hours = Integer.parseInt(durationSplit[0]);
            int minutes = Integer.parseInt(durationSplit[1]);
            return startDateTime.plusHours(hours).plusMinutes(minutes);
        }
        return null;
    }
}
