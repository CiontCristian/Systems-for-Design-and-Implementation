package domain.validators;
public class ValidatorException extends RuntimeException {
    public ValidatorException(String error){
        super(error);
    }
}
