package com.vitacard.finsvc.commons;

import com.vitacard.finsvc.commons.unit.UnitDateFormat;
import org.junit.jupiter.api.Test;

class UnitDateFormatTest {
    @Test
    public void testParse() {
        UnitDateFormat.parse("2023-02-15T05:03:28.803Z");
    }
}