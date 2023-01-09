package com.vitacard.finsvc.infrastructure;

import org.springframework.stereotype.Component;

import java.text.SimpleDateFormat;
import java.util.Date;

@Component
public class UnitDateFormat {
    private final SimpleDateFormat simpleDateFormat;
    public UnitDateFormat() {
        simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd");
    }

    public String format(Date date) {
        return simpleDateFormat.format(date);
    }
}
