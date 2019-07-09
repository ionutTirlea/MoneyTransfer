package api;

import org.junit.Before;
import org.junit.Test;
import response.AccountResponse;
import rest.Application;

import static org.junit.Assert.assertEquals;

/* API E2E Tests: Added test for the deposit / withdrawn endpoints */
/*TODO add tests for all endpoints */
public class MoneyTransferAPITest {

    @Before
    public void setup() {
        Application.main(null);
        RequestHelper.configure();
    }

    @Test
    public void testDeposit() {

        RequestHelper.deposit("invalidAccountNumber", "0")
                .then().statusCode(400);

        RequestHelper.deposit("0", "invalidAmount")
                .then().statusCode(400);

        RequestHelper.deposit("0", "-10")
                .then().statusCode(400);

        RequestHelper.deposit("0", "10")
                .then().statusCode(404);

        AccountResponse accountResponse = RequestHelper.createAccount("testDeposit").as(AccountResponse.class);
        long accountNumber = accountResponse.getAccountNumber();

        RequestHelper.deposit(String.valueOf(accountNumber), "10")
                .then().statusCode(200);

        accountResponse = RequestHelper.getByAccountNumber(String.valueOf(accountNumber)).as(AccountResponse.class);
        assertEquals(10, accountResponse.getBalance(), 0);
    }

    @Test
    public void testWithdrawal() {

        RequestHelper.withdraw("invalidAccountNumber", "0")
                .then().statusCode(400);

        RequestHelper.withdraw("0", "invalidAmount")
                .then().statusCode(400);

        RequestHelper.withdraw("0", "-10")
                .then().statusCode(400);

        RequestHelper.withdraw("0", "10")
                .then().statusCode(404);


        AccountResponse accountResponse = RequestHelper.createAccount("testWithdraw").as(AccountResponse.class);
        long accountNumber = accountResponse.getAccountNumber();

        RequestHelper.withdraw(String.valueOf(accountNumber), "10")
                .then().statusCode(400);

        RequestHelper.deposit(String.valueOf(accountNumber), "100")
                .then().statusCode(200);

        RequestHelper.withdraw(String.valueOf(accountNumber), "10")
                .then().statusCode(200);

        accountResponse = RequestHelper.getByAccountNumber(String.valueOf(accountNumber)).as(AccountResponse.class);
        assertEquals(90, accountResponse.getBalance(), 0);
    }

}