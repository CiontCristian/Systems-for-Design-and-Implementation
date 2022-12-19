package serviceserver;

import Service.BookService;
import domain.Book;
import domain.Message;
import domain.validators.ValidatorException;
import repo.Sort;
import repo.SortingRepository;

import java.util.List;
import java.util.Set;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Future;
import java.util.stream.Collectors;
import java.util.stream.StreamSupport;

public class BookServiceServer extends ServiceServer<Long, Book> implements BookService {

    public BookServiceServer(SortingRepository<Long, Book> repository, ExecutorService executorService) {
        super(repository, executorService);
    }

    @Override
    public synchronized Future<Object> filterBooksByAuthor(String author) {
        Iterable<Book> books=repository.findAll();
        Set<Book> set= StreamSupport.stream(books.spliterator(), false)
                .filter(book -> book.getAuthor().contains(author))
                .collect(Collectors.toSet());

        return executorService.submit(() -> set);
    }

    @Override
    public synchronized Future<Object> sortBooks() {
        Sort<Book> sort1=new Sort<>("author");
        Sort<Book> sort2=new Sort<>(Sort.SortType.DESC,"author", "price");
        Sort<Book> sort3=new Sort<>(Sort.SortType.DESC,"author");
        sort3.and(new Sort<>(Sort.SortType.DESC, "price"));

        List<Book> list=StreamSupport.stream(repository.findAll(sort3).spliterator(), false).collect(Collectors.toList());

        return executorService.submit(()->list);
    }
}
