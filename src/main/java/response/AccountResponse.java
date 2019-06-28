package response;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@ToString
public class AccountResponse {

    private long accountNumber;

    private String accountHolder;

    private double balance;
}