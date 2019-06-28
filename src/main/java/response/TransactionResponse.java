package response;

import lombok.Getter;
import lombok.Setter;

import java.math.BigDecimal;
import java.time.LocalDateTime;

@Getter
@Setter
public class TransactionResponse {

    private String type;

    private long accountNumber;

    private double amount;

    private LocalDateTime time;

    private String details;
}
