package serviceclient;

import Service.BookService;
import domain.Book;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.List;


public class BookServiceClient extends ServiceClient<Long, Book> implements BookService {
    public BookServiceClient() {
        super();
    }
    @Autowired
    private BookService bookService;

    @Override
    public List<Book> filterBooksByAuthor(String author) {
        return bookService.filterBooksByAuthor(author);
    }

    @Override
    public List<Book> sortBooks() {
        return bookService.sortBooks();
    }
}
