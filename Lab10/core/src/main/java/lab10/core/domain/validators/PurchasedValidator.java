package lab10.core.domain.validators;

import lab10.core.domain.Purchased;

public class PurchasedValidator implements Validator<Purchased> {

    @Override
    public void validate(Purchased purchased) throws ValidatorException {
        String errors="";
        if(purchased.getId()<0){
            errors+="Purchased item's id can't be negative";
        }

        if(!errors.equals(""))
            throw new ValidatorException(errors);
    }
}
