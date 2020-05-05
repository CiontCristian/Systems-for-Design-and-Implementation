package lab10.core.domain.validators;


public interface Validator<T> {
    void validate(T entity) throws ValidatorException;
}
