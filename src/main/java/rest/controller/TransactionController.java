package rest.controller;

import data.transaction.Transaction;
import response.AccountResponse;
import response.TransactionResponse;
import response.mapping.TransactionMapper;
import service.AccountService;
import service.TransactionService;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.List;
import java.util.stream.Collectors;

import static response.mapping.AccountMapper.toResponse;
import static rest.controller.RequestParametersValidator.validateAndParseAccountNumber;
import static rest.controller.RequestParametersValidator.validateAndParseAmount;
import static rest.controller.RequestParametersValidator.validateAndParseDate;

public class TransactionController {

    private AccountService accountService = new AccountService();
    private TransactionService transactionService = new TransactionService();

    public List<TransactionResponse> getTransactions(String accountNumber) {
        long accountId = validateAndParseAccountNumber(accountNumber);
        List<Transaction> transactions = transactionService.getTransactions(accountId);
        return transactions.stream()
                .map(TransactionMapper::toResponse)
                .collect(Collectors.toList());
    }

    public List<TransactionResponse> getTransactionsByAccountNumberAndPeriod(String accountNumber, String begin, String end) {
        long accountId = validateAndParseAccountNumber(accountNumber);
        LocalDate beginDate = validateAndParseDate(begin);
        LocalDate endDate = validateAndParseDate(end);
        List<Transaction> transactions = transactionService.getTransactionsByAccountNumberAndPeriod(accountId, beginDate, endDate);
        return transactions.stream()
                .map(TransactionMapper::toResponse)
                .collect(Collectors.toList());
    }

    public AccountResponse deposit(String accountNumber, String amount) {
        long accountId = validateAndParseAccountNumber(accountNumber);
        BigDecimal value = validateAndParseAmount(amount);
        transactionService.deposit(accountId, value);
        return toResponse(accountService.getAccount(accountId));
    }

    public AccountResponse withdraw(String accountNumber, String amount) {
        long accountId = validateAndParseAccountNumber(accountNumber);
        BigDecimal value = validateAndParseAmount(amount);
        transactionService.withdraw(accountId, value);
        return toResponse(accountService.getAccount(accountId));
    }

    public AccountResponse transfer(String fromAccountNumber, String toAccountNumber, String amount) {
        long from = validateAndParseAccountNumber(fromAccountNumber);
        long to = validateAndParseAccountNumber(toAccountNumber);
        BigDecimal value = validateAndParseAmount(amount);
        transactionService.transfer(from, to, value);
        return toResponse(accountService.getAccount(from));
    }

}
