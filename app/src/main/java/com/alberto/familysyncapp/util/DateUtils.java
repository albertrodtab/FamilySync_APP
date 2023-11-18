package com.alberto.familysyncapp.util;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;

public class DateUtils {

    private static final String MY_DATE_FORMAT = "dd/MM/yyyy";


    public static LocalDate fromMyDateFormatStringToLocalDate(String dateString) {
        DateTimeFormatter formato = DateTimeFormatter.ofPattern(MY_DATE_FORMAT);
        return LocalDate. parse(dateString.trim(), formato);
    }

    public static String fromLocalDateToMyDateFormatString(LocalDate localDate) {
        DateTimeFormatter dateTimeFormatter = DateTimeFormatter.ofPattern(MY_DATE_FORMAT);
        return String.valueOf(localDate.format(dateTimeFormatter));
    }
}
