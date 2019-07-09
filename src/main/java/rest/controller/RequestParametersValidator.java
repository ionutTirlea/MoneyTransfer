package rest.controller;

import exceptions.BadRequestException;
import exceptions.MissingParameterException;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;

class RequestParametersValidator {

    private RequestParametersValidator() {

    }

    static long validateAndParseAccountNumber(String accountNumber) {
        if (accountNumber == null) {
            throw new MissingParameterException("accountNumber");
        }
        long accountId;
        try {
            accountId = Long.parseLong(accountNumber);
        } catch (NumberFormatException e) {
            throw new BadRequestException("The provided accountNumber " + accountNumber + " is not valid!");
        }
        return accountId;
    }

    static BigDecimal validateAndParseAmount(String amount) {
        if (amount == null) {
            throw new MissingParameterException("amount");
        }
        BigDecimal value;
        try {
            value = BigDecimal.valueOf(Double.parseDouble(amount));
        } catch (NumberFormatException e) {
            throw new BadRequestException("The provided ammount " + amount + " is not valid!");
        }
        return value;
    }

    static LocalDate validateAndParseDate(String date) {
        if (date == null) {
            throw new MissingParameterException("date");
        }
        DateTimeFormatter dateTimeFormatter = DateTimeFormatter.ofPattern("ddMMyyyy");
        try {
            return LocalDate.parse(date, dateTimeFormatter);
        } catch (DateTimeParseException e) {
            throw new BadRequestException("Invalid date! Correct format is ddMMyyyy!");
        }
    }

}