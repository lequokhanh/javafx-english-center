package com.utilities;

import javafx.util.StringConverter;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;

public class DateFormat extends StringConverter<LocalDate> {
    final String pattern = "dd-LLL-yy";
    final DateTimeFormatter dateFormatter = DateTimeFormatter.ofPattern(pattern);


    @Override
    public String toString(LocalDate date) {
        if (date != null) {
            return dateFormatter.format(date);
        } else {
            return "";
        }
    }

    @Override
    public LocalDate fromString(String string) {
        if (string != null && !string.isEmpty()) {
            return LocalDate.parse(string, dateFormatter);
        } else {
            return null;
        }
    }

    public LocalDate now() {
        // return date now in dd-LLL-yy format
        return LocalDate.now();
    }
}