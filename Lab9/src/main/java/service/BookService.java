package service;

import domain.Book;

import java.util.List;
import java.util.Optional;

public interface BookService {
    List<Book> getAll();

    void save(Book book);

    void update(Book book);

    void delete(Long id);

    Optional<Book> getOne(Long id);

    List<Book> sortAll();
}
