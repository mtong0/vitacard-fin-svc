package com.vitacard.finsvc.commons;

import org.junit.jupiter.api.Test;

import java.text.SimpleDateFormat;

import static org.junit.jupiter.api.Assertions.*;

class UnitDateFormatTest {
    @Test
    public void testParse() {
        UnitDateFormat.parse("2023-02-15T05:03:28.803Z");
    }
}