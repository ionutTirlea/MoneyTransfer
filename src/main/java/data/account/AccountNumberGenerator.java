package data.account;

class AccountNumberGenerator {

    private static volatile long id = 100010L;

    private AccountNumberGenerator() {

    }

    static synchronized long nextId() {
        return id++;
    }
}