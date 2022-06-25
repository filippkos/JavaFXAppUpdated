package com.example.javafxapp.models;

import java.time.LocalDateTime;
import java.time.temporal.ChronoUnit;

public class DateTimeHandler {

    public String getRemainingTime(LocalDateTime deadline) {
        LocalDateTime currentTime = LocalDateTime.now();
        LocalDateTime tempDateTime = LocalDateTime.from( currentTime );

        long years = tempDateTime.until(deadline, ChronoUnit.YEARS);
        tempDateTime = tempDateTime.plusYears( years );
        String yearsString = years + " y, ";
        if (years == 0) {
            yearsString = "";
        }
        long months = tempDateTime.until(deadline, ChronoUnit.MONTHS);
        tempDateTime = tempDateTime.plusMonths( months );
        String monthString = months + " m, ";
        if (months == 0) {
            monthString = "";
        }
        long days = tempDateTime.until(deadline, ChronoUnit.DAYS);
        tempDateTime = tempDateTime.plusDays( days );
        String daysString = days + " d, ";
        if (days == 0) {
            daysString = "";
        }
        long hours = tempDateTime.until(deadline, ChronoUnit.HOURS);
        tempDateTime = tempDateTime.plusHours( hours );
        String hoursString = hours + " h, ";
        if (hours == 0) {
            hoursString = "";
        }
        long minutes = tempDateTime.until(deadline, ChronoUnit.MINUTES);
        tempDateTime = tempDateTime.plusMinutes(minutes);
        String minutesString = minutes + " min, ";
        if (minutes == 0) {
            minutesString = "";
        }
        long seconds = tempDateTime.until( deadline, ChronoUnit.SECONDS);
        String secondsString = seconds + " s";
        if (seconds < 0) {
            return "time is over";
        }
        return yearsString + monthString + daysString + hoursString + minutesString + secondsString;
    }
}
