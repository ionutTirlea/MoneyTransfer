package rest.controller;

@Test
public class AccountControllerTest {

    private AccountController accountController;

    testAccountCreation();

    testGetAllAccountsInvalid();

    testGetAccountByAccountNumber();

    testGetAccountByAccountNumberInvalid();

}