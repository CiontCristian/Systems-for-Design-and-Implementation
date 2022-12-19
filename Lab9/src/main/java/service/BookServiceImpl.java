package service;

import domain.Book;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import repo.BookRepository;

import javax.transaction.Transactional;
import java.util.List;
import java.util.Optional;

@Service
public class BookServiceImpl implements BookService {
    public static final Logger log = LoggerFactory.getLogger(BookServiceImpl.class);
    @Autowired
    private BookRepository bookRepository;

    @Override
    public List<Book> getAll() {
        log.trace("getAll - method entered");
        return bookRepository.findAll();
    }

    @Override
    @Transactional
    public void save(Book book) {
        log.trace("save - method entered: book={}", book);
        bookRepository.save(book);
        log.trace("save - method finished");
    }

    @Override
    @Transactional
    public void update(Book book) {
        log.trace("update - method entered: book={}", book);
        bookRepository.findById(book.getId())
                .ifPresent(book1 -> {
                    book1.setTitle(book.getTitle());
                    book1.setAuthor(book.getAuthor());
                    book1.setPrice(book.getPrice());
                    //bookRepository.saveAndFlush(book1);
                    log.trace("update - book update: book={}", book1);
                });
        log.trace("update - method finished");
    }

    @Override
    public void delete(Long id) {
        log.trace("delete - method entered: id={}", id);
        bookRepository.deleteById(id);
        log.trace("delete - method finished");
    }

    @Override
    public Optional<Book> getOne(Long id) {
        log.trace("getOne - method entered: id={}", id);
        return bookRepository.findById(id);
    }

    @Override
    public List<Book> sortAll() {
        return bookRepository.findAll(Sort.by(Sort.Direction.DESC, "author", "price"));
    }
}
