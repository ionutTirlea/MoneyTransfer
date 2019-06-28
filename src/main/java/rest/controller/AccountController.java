package rest.controller;

import data.account.Account;
import response.AccountResponse;
import response.mapping.AccountMapper;
import service.AccountService;

import java.util.List;
import java.util.stream.Collectors;

import static response.mapping.AccountMapper.toResponse;
import static rest.controller.RequestParametersValidator.validateAndParseAccountNumber;

public class AccountController {

    private AccountService accountService = new AccountService();

    public AccountResponse createAccount(String name) {
        return toResponse(accountService.createAccount(name));
    }

    public List<AccountResponse> getAllAccounts() {
        return accountService.getAllAccounts().stream()
                .map(AccountMapper::toResponse)
                .collect(Collectors.toList());
    }

    public AccountResponse getAccount(String accountNumber) {
        long accountId = validateAndParseAccountNumber(accountNumber);
        Account account = accountService.getAccount(accountId);
        return toResponse(account);
    }
}