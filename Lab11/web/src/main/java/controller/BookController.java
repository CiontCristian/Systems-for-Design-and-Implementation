package controller;

import converter.BookConverter;
import converter.PurchasedConverter;
import dto.BookDTO;
import dto.PurchasedDTO;
import lab10.core.domain.Book;
import lab10.core.domain.Purchased;
import lab10.core.service.BookService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;


@RestController
public class BookController {
    public static final Logger logger = LoggerFactory.getLogger(BookController.class);

    @Autowired
    private BookService bookService;

    @Autowired
    private BookConverter bookConverter;

    @Autowired
    private PurchasedConverter purchasedConverter;

    @RequestMapping(value = "/books", method = RequestMethod.GET)
    List<BookDTO> getBooks(){
        logger.trace("getBooks - method entered");
        List<Book> books = bookService.getAll();
        logger.trace("getBooks - books:{}", books);
        logger.trace("getBooks - method finished!");
        return new ArrayList<>(bookConverter.convertModelsToDtos(books));
    }

    @RequestMapping(value = "/books", method = RequestMethod.POST)
    BookDTO saveBook(@RequestBody BookDTO bookDTO){
        return bookConverter.convertModelToDto(bookService.save(bookConverter.convertDtoToModel(bookDTO)));
    }

    @RequestMapping(value = "/books/{id}", method = RequestMethod.PUT)
    BookDTO updateBook(@PathVariable Long id, @RequestBody BookDTO bookDTO){
        return bookConverter.convertModelToDto(bookService.update(id, bookConverter.convertDtoToModel(bookDTO)));
    }

    @RequestMapping(value = "/books/{id}", method = RequestMethod.DELETE)
    ResponseEntity<?> deleteBook(@PathVariable Long id){
        bookService.delete(id);
        return new ResponseEntity<>(HttpStatus.OK);
    }

    @RequestMapping(value = "/books/sort", method = RequestMethod.POST)
    List<BookDTO> getSortedBooks(@RequestBody String attributeName){
        logger.trace("getSortedBooks - method entered");
        List<Book> sortedBooks = bookService.sort(attributeName);
        logger.trace("getSortedBooks - books:{}", sortedBooks);
        logger.trace("getSortedBooks - method finished!");
        return new ArrayList<>(bookConverter.convertModelsToDtos(sortedBooks));
    }

    @RequestMapping(value = "books/filter", method = RequestMethod.POST)
    List<BookDTO> filterBooks(@RequestBody String[] params){
        List<Book> filteredBooks = bookService.filter(params[0], params[1]);
        return new ArrayList<>(bookConverter.convertModelsToDtos(filteredBooks));
    }

    @RequestMapping(value = "transactions/saveTransaction", method = RequestMethod.POST)
    PurchasedDTO saveTransaction(@RequestBody PurchasedDTO purchasedDTO){
        return purchasedConverter.convertModelToDto(bookService.saveTransaction(purchasedConverter.convertDtoToModel(purchasedDTO)));
    }

    @RequestMapping(value = "transactions/getTransactions", method = RequestMethod.GET)
    List<PurchasedDTO> getTransactions(){
        List<Purchased> trans = bookService.getTransactions();
        return new ArrayList<>(purchasedConverter.convertModelsToDtos(trans));
    }
}
