package data.account;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ConcurrentHashMap;

public class AccountRepository {

    private ConcurrentHashMap<Long, Account> accounts = new ConcurrentHashMap<>();

    public List<Account> getAll() {
        return new ArrayList<>(accounts.values());
    }

    public Account getByAccountNumber(long accountNumber) {
        return accounts.get(accountNumber);
    }

    public Account create(String accountHolder) {
        long accountNumber = AccountNumberGenerator.nextId();
        Account account = new Account(accountNumber, accountHolder);
        accounts.put(accountNumber, account);
        return account;
    }
}
