package service;

import data.account.Account;
import data.account.AccountRepository;
import exceptions.ResourceNotFoundException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.List;

public class AccountService {

    private static final Logger LOG = LoggerFactory.getLogger(AccountService.class);

    private AccountRepository accountRepository;

    public AccountService(AccountRepository accountRepository) {
        this.accountRepository = accountRepository;
    }

    public Account createAccount(String accountHolder) {
        LOG.info("Creating new account for accountHolder {}", accountHolder);
        return accountRepository.create(accountHolder);
    }

    public List<Account> getAllAccounts() {
        LOG.info("Reading all account.");
        List<Account> accounts = accountRepository.getAll();
        if (accounts.isEmpty()) {
            LOG.info("No account was found");
            throw new ResourceNotFoundException("No account was found.");
        }
        return accounts;
    }

    public Account getAccount(long accountNumber) {
        LOG.info("Reading account with accountNumber {}.", accountNumber);
        Account account = accountRepository.getByAccountNumber(accountNumber);
        if (account == null) {
            LOG.info("Account with accountNumber: {} was not found.", accountNumber);
            throw new ResourceNotFoundException("Account with accountNumber: " + accountNumber + " was not found.");
        }
        return account;
    }
}