package lab10.core.domain.validators;

import lab10.core.domain.Client;

public class ClientValidator implements Validator<Client> {

    @Override
    public void validate(Client client) throws ValidatorException {
        String errors="";
        if(client.getFirstName().length()<=0)
            errors+="Client's first name can't be null\n";
        if(client.getLastName().length()<=0)
            errors+="Client's last name can't be null\n";
        if(client.getAge()<0)
            errors+="Client's age can't be negative\n";

        if(!errors.equals(""))
            throw new ValidatorException(errors);

    }
}
