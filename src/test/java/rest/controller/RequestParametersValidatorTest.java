package rest.controller;

import exceptions.BadRequestException;
import org.junit.Test;

import java.math.BigDecimal;
import java.time.LocalDate;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;

public class RequestParametersValidatorTest {

    @Test
    public void testValidateAndAccountNumber() {
        long accountNumber = RequestParametersValidator.validateAndParseAccountNumber("101020");
        assertEquals(101020, accountNumber);
    }

    @Test(expected = BadRequestException.class)
    public void testValidateAndParseAccountNumber_WrongValue() {
        RequestParametersValidator.validateAndParseAccountNumber("123RX");
    }

    @Test
    public void testValidateAndParseDate() {
        LocalDate date = RequestParametersValidator.validateAndParseDate("01121993");
        assertNotNull(date);
        assertEquals(1, date.getDayOfMonth());
        assertEquals(12, date.getMonthValue());
        assertEquals(1993, date.getYear());
    }

    @Test(expected = BadRequestException.class)
    public void testValidateAndParseDate_WrongValue() {
        RequestParametersValidator.validateAndParseDate("01901993");
    }


    @Test
    public void testValidateAmount() {
        BigDecimal amount = RequestParametersValidator.validateAndParseAmount("123.50");
        assertEquals(123.50, amount.doubleValue(), 0);
    }

    @Test(expected = BadRequestException.class)
    public void testValidateAmount_WrongValue() {
        RequestParametersValidator.validateAndParseAmount("123RX");
    }

}
