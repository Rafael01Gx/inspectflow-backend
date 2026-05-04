package br.com.inspectflow.application.utils;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

public class FormatDateUtils {

    public static String format(LocalDate date) {
        return date != null
                ? date.format(DateTimeFormatter.ofPattern("dd/MM/yyyy"))
                : null;
    }

    public static String format(LocalDateTime dateTime) {
        return dateTime != null
                ? dateTime.format(DateTimeFormatter.ofPattern("dd/MM/yyyy HH:mm"))
                : null;
    }
}
