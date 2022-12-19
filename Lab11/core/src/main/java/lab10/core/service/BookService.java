package lab10.core.service;


import lab10.core.domain.Book;
import lab10.core.domain.Purchased;

import java.util.List;

public interface BookService {
    List<Book> getAll();

    Book save(Book book);

    Book update(Long id, Book book);

    void delete(Long id);

    Book getOne(Long id);

    List<Book> sort(Object attributeName);

    List<Book> filter(Object attributeName, String value);

    Purchased saveTransaction(Purchased tran);

    List<Purchased> getTransactions();
}
