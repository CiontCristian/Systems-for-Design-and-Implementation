package Service;

import domain.Book;

import java.util.List;
import java.util.concurrent.Future;

public interface BookService extends Service<Long, Book> {
    List<Book> filterBooksByAuthor(String author);
    List<Book> sortBooks();
}
