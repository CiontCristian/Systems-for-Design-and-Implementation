package domain.validators;

import domain.Book;

public class BookValidator implements Validator<Book> {

    @Override
    public void validate(Book book) throws ValidatorException{
        String errors="";
        if(book.getTitle().length()<=0)
            errors+="Book name can't be null\n";
        if(book.getAuthor().length()<=0)
            errors+="Author name can't be null\n";
        if(book.getPrice()<0)
            errors+="Book price can't be negative\n";

        if(!errors.equals(""))
            throw new ValidatorException(errors);
    }
}
