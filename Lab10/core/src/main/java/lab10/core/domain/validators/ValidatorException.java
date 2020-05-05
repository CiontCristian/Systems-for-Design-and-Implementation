package lab10.core.domain.validators;
public class ValidatorException extends RuntimeException {
    public ValidatorException(String error){
        super(error);
    }
}
