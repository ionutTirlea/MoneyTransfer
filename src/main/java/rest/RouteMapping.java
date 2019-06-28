package rest;

import data.account.AccountRepository;
import data.transaction.TransactionRepository;
import rest.controller.AccountController;
import rest.controller.TransactionController;
import service.AccountService;
import service.TransactionService;

import static rest.util.JsonTransformer.asJson;
import static spark.Spark.after;
import static spark.Spark.get;
import static spark.Spark.path;
import static spark.Spark.post;

class RouteMapping {

    private RouteMapping() {
    }

    static void configure() {

        AccountRepository accountRepository = new AccountRepository();
        AccountService accountService = new AccountService(accountRepository);
        AccountController accountController = new AccountController(accountService);

        TransactionRepository transactionRepository = new TransactionRepository();
        TransactionService transactionService = new TransactionService(accountService, transactionRepository);
        TransactionController transactionController = new TransactionController(accountService, transactionService);

        /* q = request, r = response */

        path("/api", () -> {

            post("/accounts/:accountHolder", (q, r) -> accountController.createAccount(q.params("accountHolder")), asJson());
            get("/accounts", (q, r) -> accountController.getAllAccounts(), asJson());
            get("/accounts/:accountNumber", (q, r) -> accountController.getAccount(q.params("accountNumber")), asJson());

            get("/transactions/:accountNumber", (q, r) -> transactionController.getTransactions(q.params("accountNumber")), asJson());
            get("/transactions/:accountNumber/filter", (q, r) -> {
                String accountNumber = "accountNumber";
                String begin = q.queryParams("begin");
                String end = q.queryParams("end");
                return transactionController.getTransactionsByAccountNumberAndPeriod(accountNumber, begin, end);
            }, asJson());
            post("/transactions/:accountNumber/:balance/deposit", (q, r) -> {
                String accountNumber = q.params("accountNumber");
                String amount = q.params("balance");
                return transactionController.deposit(accountNumber, amount);
            }, asJson());
            post("/transactions/:accountNumber/:balance/withdraw", (q, r) -> {
                String accountNumber = q.params("accountNumber");
                String amount = q.params("balance");
                return transactionController.withdraw(accountNumber, amount);
            }, asJson());
            post("/transactions/:fromAccountNumber/:toAccountNumber/:balance/transfer", (q, r) -> {
                String from = q.params("fromAccountNumber");
                String to = q.params("toAccountNumber");
                String amount = q.params("balance");
                return transactionController.transfer(from, to, amount);
            }, asJson());
        });

        after((q, r) -> r.type("application/json"));
    }

}