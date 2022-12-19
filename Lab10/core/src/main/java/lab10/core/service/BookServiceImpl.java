package lab10.core.service;


import lab10.core.domain.Book;
import lab10.core.repo.BookRepository;
import lab10.core.repo.PurchasedRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import java.util.List;


@Service
public class BookServiceImpl implements BookService {
    public static final Logger log = LoggerFactory.getLogger(BookServiceImpl.class);
    @Autowired
    private BookRepository bookRepository;

    @Autowired
    private PurchasedRepository purchasedRepository;

    @Override
    public List<Book> getAll() {
        log.trace("getAll - method entered");

        List<Book> books = bookRepository.findAll();

        log.trace("getAll: books={}", books);

        return books;
    }

    @Override
    public Book save(Book book) {
        log.trace("save - method entered: book={}", book);
        Book save= bookRepository.save(book);
        log.trace("save - method finished");
        return save;
    }

    @Override
    public Book update(Long id, Book book) {
        log.trace("update - method entered: book={}", book);
        Book update = bookRepository.findById(id).orElse(book);
        update.setTitle(book.getTitle());
        update.setAuthor(book.getAuthor());
        update.setPrice(book.getPrice());
        log.trace("update - book updated: book={}", update);
        bookRepository.saveAndFlush(update);
        log.trace("update - method finished");
        return update;
    }

    @Override
    public void delete(Long id) {
        log.trace("delete - method entered: id={}", id);

        purchasedRepository.findAll().forEach(purchased -> {
            if(purchased.getBookID()==id){
                purchasedRepository.deleteById(purchased.getId());
            }
        });

        bookRepository.deleteById(id);
        log.trace("delete - method finished");
    }

    @Override
    public Book getOne(Long id) {
        log.trace("getOne - method entered: id={}", id);
        Book found= bookRepository.findById(id).orElse(null);
        log.trace("getOne- book found: book{}", found);
        log.trace("getOne - method finished");
        return found;
    }

    @Override
    public List<Book> sortAll() {
        log.trace("sortAll - method entered!");
        List<Book> sorted = bookRepository.findAll(Sort.by(Sort.Direction.DESC, "price"));
        log.trace("sortAll - books: {}", sorted);
        log.trace("sortAll - method finished!");
        return sorted;
    }
}
