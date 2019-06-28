package service;

import data.account.Account;
import data.transaction.Transaction;
import data.transaction.TransactionRepository;
import exceptions.BadRequestException;
import exceptions.InsufficientFundsException;
import exceptions.ResourceNotFoundException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.List;

import static data.transaction.TransactionType.CREDIT;
import static data.transaction.TransactionType.DEBIT;

public class TransactionService {

    private static final Logger LOG = LoggerFactory.getLogger(AccountService.class);

    private AccountService accountService;
    private TransactionRepository transactionRepository;

    public TransactionService(AccountService accountService, TransactionRepository transactionRepository) {
        this.accountService = accountService;
        this.transactionRepository = transactionRepository;
    }

    public synchronized void deposit(long accountNumber, BigDecimal amount) {
        checkAmount(amount);
        LOG.info("Deposit {} to account {}", amount.doubleValue(), accountNumber);

        Account account = accountService.getAccount(accountNumber);
        synchronized (account) {
            account.setBalance(account.getBalance().add(amount));
        }

        transactionRepository.create(accountNumber, CREDIT, amount);
    }

    public void withdraw(long accountNumber, BigDecimal amount) {
        checkAmount(amount);
        LOG.info("Withdraw {} from account {}", amount.doubleValue(), accountNumber);

        Account account = accountService.getAccount(accountNumber);
        checkBalanceAfterWithdrawal(amount, account);
        synchronized (account) {
            account.setBalance(account.getBalance().subtract(amount));
        }

        transactionRepository.create(accountNumber, DEBIT, amount);
    }


    public void transfer(long fromAccountNumber, long toAccountNumber, BigDecimal amount) {
        checkAmount(amount);
        LOG.info("Transfer {} from account {} to account {}", amount.doubleValue(), fromAccountNumber, toAccountNumber);

        checkAccount(fromAccountNumber, toAccountNumber);
        Account from = accountService.getAccount(fromAccountNumber);
        Account to = accountService.getAccount(toAccountNumber);

        checkBalanceAfterWithdrawal(amount, from);
        transfer(to, from, amount);

        transactionRepository.create(fromAccountNumber, DEBIT, amount);
        transactionRepository.create(toAccountNumber, CREDIT, amount);

    }

    private void transfer(Account to, Account from, BigDecimal amount) {

        Account lock1 = from.getAccountNumber() > to.getAccountNumber() ? from : to;
        Account lock2 = from.getAccountNumber() < to.getAccountNumber() ? from : to;

        synchronized (lock1) {
            synchronized (lock2) {
                from.setBalance(from.getBalance().subtract(amount));
                to.setBalance(to.getBalance().add(amount));
            }
        }
    }

    public List<Transaction> getTransactions(long accountNumber) {
        accountService.getAccount(accountNumber);
        List<Transaction> transactions = this.transactionRepository.getByAccountNumber(accountNumber);
        checkAnyTransactionIsFound(accountNumber, transactions);
        return transactions;
    }

    public List<Transaction> getTransactionsByAccountNumberAndPeriod(long accountNumber, LocalDate begin, LocalDate end) {
        accountService.getAccount(accountNumber);
        List<Transaction> transactions = this.transactionRepository.getByAccountNumberAndPeriod(accountNumber, begin, end);
        checkAnyTransactionIsFound(accountNumber, transactions);
        return transactions;
    }

    private void checkBalanceAfterWithdrawal(BigDecimal amount, Account account) {
        BigDecimal currentBalance = account.getBalance();
        if (currentBalance.subtract(amount).signum() < 0) {
            throw new InsufficientFundsException();
        }
    }

    private void checkAmount(BigDecimal amount) {
        if (amount == null) {
            throw new BadRequestException("Amount must be set!");
        }
        if (amount.signum() < 0) {
            throw new BadRequestException("Amount can't be negative");
        }
        if (amount.equals(BigDecimal.ZERO)) {
            throw new BadRequestException("Amount must be > 0!");
        }
    }

    private void checkAccount(long fromAccountNumber, long toAccountNumber) {
        if (fromAccountNumber == toAccountNumber) {
            throw new BadRequestException("Transfer must be done within different accounts!");
        }
    }

    private void checkAnyTransactionIsFound(long accountNumber, List<Transaction> transactions) {
        if (transactions == null || transactions.isEmpty()) {
            LOG.info("No transaction was found for accountNumber: {}", accountNumber);
            throw new ResourceNotFoundException("No transaction was found for accountNumber: " + accountNumber);
        }
    }

}