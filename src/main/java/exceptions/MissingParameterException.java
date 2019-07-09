package exceptions;

public class MissingParameterException extends BadRequestException {

    public MissingParameterException(String paramName) {
        super("Parameter " + paramName +  " is mandatory and must not be null");
    }
}