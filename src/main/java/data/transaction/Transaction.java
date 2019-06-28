package data.transaction;

import lombok.Getter;
import lombok.Setter;

import java.math.BigDecimal;
import java.time.Instant;
import java.util.UUID;

@Getter
@Setter
public class Transaction {

    private UUID id;

    private TransactionType type;

    private long accountNumber;

    private BigDecimal amount;

    private Instant time;

    private String details;

}
