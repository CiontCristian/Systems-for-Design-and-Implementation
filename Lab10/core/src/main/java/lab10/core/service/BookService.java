package lab10.core.service;


import lab10.core.domain.Book;

import java.util.List;
import java.util.Optional;

public interface BookService {
    List<Book> getAll();

    Book save(Book book);

    Book update(Long id, Book book);

    void delete(Long id);

    Book getOne(Long id);

    List<Book> sortAll();
}
