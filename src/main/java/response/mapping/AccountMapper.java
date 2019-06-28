package response.mapping;

import data.account.Account;
import response.AccountResponse;

public class AccountMapper {

    private AccountMapper() {}

    public static AccountResponse toResponse(Account account) {
        AccountResponse response = new AccountResponse();
        response.setAccountNumber(account.getAccountNumber());
        response.setAmount(account.getBalance().doubleValue());
        response.setName(account.getAccountHolder());
        return response;
    }
}