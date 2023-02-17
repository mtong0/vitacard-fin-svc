package com.vitacard.finsvc.commons;

import java.sql.Timestamp;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

public class UnitDateFormat {
    private static final SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss.SSS'Z'");
    private static final SimpleDateFormat simpleDateFormatDay = new SimpleDateFormat("yyyy-MM-dd");
    public static String format(Date date) {
        return simpleDateFormat.format(date);
    }

    public static String formatToDay(Date date) {
        return simpleDateFormatDay.format(date);
    }

    public static Timestamp parse(String timestamp) {
        try {
            return new Timestamp(simpleDateFormat.parse(timestamp).getTime());
        } catch (ParseException e) {
            throw new RuntimeException(e);
        }
    }
}
