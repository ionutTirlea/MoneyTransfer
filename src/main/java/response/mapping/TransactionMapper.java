package response.mapping;

import data.account.Account;
import data.transaction.Transaction;
import response.AccountResponse;
import response.TransactionResponse;

import java.time.LocalDateTime;
import java.time.ZoneId;

public class TransactionMapper {
    private TransactionMapper() {}

    public static TransactionResponse toResponse(Transaction transaction) {
        TransactionResponse response = new TransactionResponse();
        response.setAccountNumber(transaction.getAccountNumber());
        response.setAmount(transaction.getAmount().doubleValue());
        response.setType(transaction.getType().name());
        response.setTime(LocalDateTime.ofInstant(transaction.getTime(), ZoneId.systemDefault()));
        response.setDetails(transaction.getDetails());
        return response;
    }
}
