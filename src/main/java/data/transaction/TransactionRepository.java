package data.transaction;

import java.math.BigDecimal;
import java.time.Instant;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;
import java.util.concurrent.ConcurrentHashMap;
import java.util.stream.Collectors;

public class TransactionRepository {

    private ConcurrentHashMap<Long, List<Transaction>> transactions = new ConcurrentHashMap<>();

    public List<Transaction> getByAccountNumberAndPeriod(long accountNumber, LocalDate begin, LocalDate end) {
        List<Transaction> transactionsByAccountNumber = getByAccountNumber(accountNumber);
        return transactionsByAccountNumber.stream()
                .filter(t -> t.getTime().isAfter(Instant.from(begin)))
                .filter(t -> t.getTime().isBefore(Instant.from(end)))
                .collect(Collectors.toList());
    }

    public List<Transaction> getByAccountNumber(long accountNumber) {
        return transactions.get(accountNumber);
    }

    public Transaction create(long accountNumber, TransactionType type, BigDecimal amount) {
        Transaction transaction = build(accountNumber, type, amount);
        persist(transaction);
        return transaction;
    }

    private Transaction build(long accountNumber, TransactionType type, BigDecimal amount) {
        Transaction transaction = new Transaction();
        transaction.setId(UUID.randomUUID());
        transaction.setAccountNumber(accountNumber);
        transaction.setType(type);
        transaction.setAmount(amount);
        transaction.setTime(Instant.now());
        transaction.setDetails("dummy");
        return transaction;
    }

    private void persist(Transaction transaction) {
        long accountNumber = transaction.getAccountNumber();
        List<Transaction> transactionsByAccount = transactions.get(accountNumber);
        if (transactionsByAccount == null) {
            transactionsByAccount = new ArrayList<>();
            transactionsByAccount.add(transaction);
            transactions.put(accountNumber, transactionsByAccount);
        } else {
            transactionsByAccount.add(transaction);
        }
    }
}
