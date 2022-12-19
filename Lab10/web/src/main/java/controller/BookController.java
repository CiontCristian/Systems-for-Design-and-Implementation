package controller;

import converter.BookConverter;
import dto.BookDTO;
import dto.BooksDTO;
import lab10.core.domain.Book;
import lab10.core.service.BookService;
import lab10.core.service.BookServiceImpl;
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

    @RequestMapping(value = "/books", method = RequestMethod.GET)
    BooksDTO getBooks(){
        logger.trace("getBooks - method entered");
        BooksDTO booksDTO = new BooksDTO(bookConverter.convertModelsToDtos(bookService.getAll()));
        logger.trace("getBooks - books:{}", booksDTO);
        logger.trace("getBooks - method finished!");
        return booksDTO;
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

    @RequestMapping(value = "/books/sorted", method = RequestMethod.GET)
    List<BookDTO> getSortedBooks(){
        logger.trace("getSortedBooks - method entered");
        List<Book> books = bookService.sortAll();
        logger.trace("getSortedBooks - books:{}", books);
        logger.trace("getSortedBooks - method finished!");
        return new ArrayList<>(bookConverter.convertModelsToDtos(books));
    }
}
