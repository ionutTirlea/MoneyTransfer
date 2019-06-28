package service;

import data.account.Account;
import data.account.AccountRepository;
import exceptions.ResourceNotFoundException;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.runners.MockitoJUnitRunner;

import java.util.Arrays;
import java.util.Collections;
import java.util.List;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@RunWith(MockitoJUnitRunner.class)
public class AccountServiceTest {

    @Mock
    AccountRepository accountRepository;
    @InjectMocks
    private AccountService accountService;

    @Test
    public void testAccountCreation() {
        String accountHolder = "ionut_tirlea";
        when(accountRepository.create(accountHolder)).thenReturn(Mockito.mock(Account.class));

        Account account = accountService.createAccount(accountHolder);
        verify(accountRepository, times(1)).create(accountHolder);
        assertNotNull(account);
    }

    @Test
    public void testGetAllAccounts() {
        when(accountRepository.getAll()).thenReturn(Arrays.asList(Mockito.mock(Account.class), Mockito.mock(Account.class)));

        List<Account> accounts = accountService.getAllAccounts();
        verify(accountRepository, times(1)).getAll();
        assertNotNull(accounts);
        assertEquals(2, accounts.size());
    }

    @Test(expected = ResourceNotFoundException.class)
    public void testGetAllAccounts_NoAccountFound() {
        when(accountRepository.getAll()).thenReturn(Collections.emptyList());

        accountService.getAllAccounts();
        verify(accountRepository, times(1)).getAll();
    }

    @Test
    public void testGetAccountByAccountNumber() {
        long accountNumber = 101010L;
        when(accountRepository.getByAccountNumber(accountNumber)).thenReturn(Mockito.mock(Account.class));

        Account account = accountService.getAccount(accountNumber);
        verify(accountRepository, times(1)).getByAccountNumber(accountNumber);
        assertNotNull(account);
    }

    @Test(expected = ResourceNotFoundException.class)
    public void testGetAccountByAccountNumber_NoAccountFound() {
        long accountNumber = 101010L;
        when(accountRepository.getByAccountNumber(accountNumber)).thenReturn(null);

        accountService.getAccount(accountNumber);
        verify(accountRepository, times(1)).getByAccountNumber(accountNumber);
    }

}