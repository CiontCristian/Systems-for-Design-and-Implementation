package Service;

import domain.Book;

import java.util.List;
import java.util.Set;
import java.util.concurrent.Future;

public interface BookService extends Service<Long, Book> {
    String sortBooks="sortBooks";
    String filterBooksByAuthor="filterBooksByAuthor";

    public Future<Object> filterBooksByAuthor(String author);
    public Future<Object> sortBooks();
}
