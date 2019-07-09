package service;

import data.account.Account;
import data.transaction.Transaction;
import data.transaction.TransactionRepository;
import exceptions.BadRequestException;
import exceptions.InsufficientFundsException;
import exceptions.ResourceNotFoundException;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.runners.MockitoJUnitRunner;

import java.math.BigDecimal;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;

import static data.transaction.TransactionType.CREDIT;
import static data.transaction.TransactionType.DEBIT;
import static junit.framework.TestCase.assertNotNull;
import static org.junit.Assert.assertEquals;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@RunWith(MockitoJUnitRunner.class)
public class TransactionServiceTest {

    private static long ACCOUNT_NUMBER = 100;

    private static long FROM_ACCOUNT_NUMBER = 200200;
    private static long TO_ACCOUNT_NUMBER = 300300;

    @InjectMocks
    private TransactionService transactionService;

    @Mock
    private AccountService accountService;
    @Mock
    private TransactionRepository transactionRepository;

    @Test(expected = BadRequestException.class)
    public void testDeposit_NegativeAmount() {
        transactionService.deposit(ACCOUNT_NUMBER, BigDecimal.ONE.negate());
    }

    @Test(expected = BadRequestException.class)
    public void testDeposit_NullAmount() {
        transactionService.deposit(ACCOUNT_NUMBER, null);
    }

    @Test(expected = BadRequestException.class)
    public void testDeposit_Zero() {
        transactionService.deposit(ACCOUNT_NUMBER, BigDecimal.ZERO);
    }

    @Test
    public void testDeposit() {
        Account account = new Account();
        account.setBalance(BigDecimal.valueOf(3500));

        when(accountService.getAccount(ACCOUNT_NUMBER)).thenReturn(account);
        transactionService.deposit(ACCOUNT_NUMBER, BigDecimal.TEN);

        verify(transactionRepository, times(1)).create(ACCOUNT_NUMBER, CREDIT, BigDecimal.TEN);

        assertEquals(3510, account.getBalance().doubleValue(), 0);
    }

    @Test(expected = BadRequestException.class)
    public void testWithdraw_NegativeAmount() {
        transactionService.withdraw(ACCOUNT_NUMBER, BigDecimal.ONE.negate());
    }

    @Test(expected = BadRequestException.class)
    public void testWithdraw_NullAmount() {
        transactionService.withdraw(ACCOUNT_NUMBER, null);
    }

    @Test(expected = BadRequestException.class)
    public void testWithdraw_Zero() {
        transactionService.withdraw(ACCOUNT_NUMBER, BigDecimal.ZERO);
    }

    @Test
    public void testWithdraw() {
        Account account = new Account();
        account.setBalance(BigDecimal.valueOf(3500));

        when(accountService.getAccount(ACCOUNT_NUMBER)).thenReturn(account);
        transactionService.withdraw(ACCOUNT_NUMBER, BigDecimal.TEN);

        verify(transactionRepository, times(1)).create(ACCOUNT_NUMBER, DEBIT, BigDecimal.TEN);

        assertEquals(3490, account.getBalance().doubleValue(), 0);
    }

    @Test(expected = InsufficientFundsException.class)
    public void testWithdraw_InsufficientFundsException() {
        Account account = new Account();
        account.setBalance(BigDecimal.ONE);

        when(accountService.getAccount(ACCOUNT_NUMBER)).thenReturn(account);
        transactionService.withdraw(ACCOUNT_NUMBER, BigDecimal.TEN);
    }


    @Test(expected = BadRequestException.class)
    public void testTransfer_NegativeAmount() {
        transactionService.transfer(FROM_ACCOUNT_NUMBER, TO_ACCOUNT_NUMBER, BigDecimal.ONE.negate());
    }

    @Test(expected = BadRequestException.class)
    public void testTransfer_NullAmount() {
        transactionService.transfer(FROM_ACCOUNT_NUMBER, TO_ACCOUNT_NUMBER, null);
    }

    @Test(expected = BadRequestException.class)
    public void testTransfer_Zero() {
        transactionService.transfer(FROM_ACCOUNT_NUMBER, TO_ACCOUNT_NUMBER, BigDecimal.ZERO);
    }

    @Test(expected = InsufficientFundsException.class)
    public void testTransfer_InsufficientFundsException() {
        Account fromAccount = new Account();
        fromAccount.setBalance(BigDecimal.ONE);

        Account toAccount = new Account();

        when(accountService.getAccount(FROM_ACCOUNT_NUMBER)).thenReturn(fromAccount);
        when(accountService.getAccount(TO_ACCOUNT_NUMBER)).thenReturn(toAccount);
        transactionService.transfer(FROM_ACCOUNT_NUMBER, TO_ACCOUNT_NUMBER, BigDecimal.TEN);
    }

    @Test
    public void testTransfer() {
        Account fromAccount = new Account();
        fromAccount.setBalance(BigDecimal.valueOf(3500));

        Account toAccount = new Account();
        toAccount.setBalance(BigDecimal.valueOf(1200));

        when(accountService.getAccount(FROM_ACCOUNT_NUMBER)).thenReturn(fromAccount);
        when(accountService.getAccount(TO_ACCOUNT_NUMBER)).thenReturn(toAccount);

        transactionService.transfer(FROM_ACCOUNT_NUMBER, TO_ACCOUNT_NUMBER, BigDecimal.TEN);

        verify(transactionRepository, times(1)).create(FROM_ACCOUNT_NUMBER, DEBIT, BigDecimal.TEN);
        verify(transactionRepository, times(1)).create(TO_ACCOUNT_NUMBER, CREDIT, BigDecimal.TEN);

        assertEquals(3490, fromAccount.getBalance().doubleValue(), 0);
        assertEquals(1210, toAccount.getBalance().doubleValue(), 0);
    }

    @Test
    public void testGetAllTransactions() {
        when(transactionRepository.getByAccountNumber(ACCOUNT_NUMBER)).thenReturn(Arrays.asList(Mockito.mock(Transaction.class), Mockito.mock(Transaction.class)));

        List<Transaction> transactions = transactionService.getTransactions(ACCOUNT_NUMBER);
        verify(transactionRepository, times(1)).getByAccountNumber(ACCOUNT_NUMBER);
        assertNotNull(transactions);
        assertEquals(2, transactions.size());
    }

    @Test(expected = ResourceNotFoundException.class)
    public void testGetAllTransactions_NoTransactionFound() {
        when(transactionRepository.getByAccountNumber(ACCOUNT_NUMBER)).thenReturn(Collections.emptyList());

        transactionService.getTransactions(ACCOUNT_NUMBER);
        verify(transactionRepository, times(1)).getByAccountNumber(ACCOUNT_NUMBER);
    }
}