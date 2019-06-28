package exceptions;

public class InsufficientFundsException extends RuntimeException {

    public InsufficientFundsException() {
        super("Insufficient Funds! Can not withdraw the requested amount!");
    }
}
