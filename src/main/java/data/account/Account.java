package data.account;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import java.math.BigDecimal;
import java.util.Currency;
import java.util.UUID;

@Getter
@Setter
@ToString
/* To keep it simple let's assume we have only one currency accounts */
public class Account {

    private UUID id;

    private long accountNumber;

    private String accountHolder;

    private volatile BigDecimal balance;

    private Currency currency;

    public Account() {

    }

    Account(long accountNumber, String accountHolder) {
        this.id = UUID.randomUUID();
        this.accountNumber = accountNumber;
        this.accountHolder = accountHolder;
        this.balance = BigDecimal.ZERO;
    }

}