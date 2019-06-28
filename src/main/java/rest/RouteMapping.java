package rest;

import rest.controller.AccountController;
import rest.controller.TransactionController;

import static rest.util.JsonTransformer.asJson;
import static spark.Spark.after;
import static spark.Spark.get;
import static spark.Spark.path;
import static spark.Spark.post;

class RouteMapping {

    private RouteMapping() {
    }

    static void configure() {

        AccountController accountController = new AccountController();
        TransactionController transactionController = new TransactionController();

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
            post("/transactions/:accountNumber/:amount/deposit", (q, r) -> {
                String accountNumber = q.params("accountNumber");
                String amount = q.params("amount");
                return transactionController.deposit(accountNumber, amount);
            }, asJson());
            post("/transactions/:accountNumber/:amount/withdraw", (q, r) -> {
                String accountNumber = q.params("accountNumber");
                String amount = q.params("amount");
                return transactionController.withdraw(accountNumber, amount);
            }, asJson());
            post("/transactions/:fromAccountNumber/:toAccountNumber/:amount/transfer", (q, r) -> {
                String from = q.params("fromAccountNumber");
                String to = q.params("toAccountNumber");
                String amount = q.params("amount");
                return transactionController.transfer(from, to, amount);
            }, asJson());
        });

        after((q, r) -> r.type("application/json"));
    }

}