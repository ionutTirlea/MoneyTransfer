package api;

import io.restassured.RestAssured;
import io.restassured.response.Response;

class RequestHelper {

    private static final String SLASH = "/";
    private static final String TRANSACTIONS = "/transactions/";
    private static final String ACCOUNTS = "/accounts/";

    static void configure() {
        RestAssured.baseURI = "http://localhost/api/";
        RestAssured.basePath = "";
        RestAssured.port = 4567;
    }

    static Response createAccount(String name) {
        return RestAssured.given()
                .post(ACCOUNTS + name);
    }

    static Response getAllAccounts() {
        return RestAssured.given()
                .get(ACCOUNTS);
    }

    static Response getByAccountNumber(String accountNumber) {
        return RestAssured.given()
                .get(ACCOUNTS + accountNumber);
    }

    static Response deposit(String accountNumber, String amount) {
        return RestAssured.given()
                .post(TRANSACTIONS + accountNumber + SLASH + amount + SLASH + "deposit");
    }

    static Response withdraw(String accountNumber, String amount) {
        return RestAssured.given()
                .post(TRANSACTIONS + accountNumber + SLASH + amount + SLASH + "withdraw");
    }

    static Response transfer(String from, String to, String amount) {
        return RestAssured.given()
                .get(TRANSACTIONS + from + SLASH + to + SLASH + amount + SLASH + "transfer");
    }

}