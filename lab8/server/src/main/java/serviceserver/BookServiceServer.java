package serviceserver;

import Service.BookService;
import domain.Book;
import repo.Sort;
import repo.SortingRepository;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;
import java.util.stream.StreamSupport;


public class BookServiceServer extends ServiceServer<Long, Book> implements BookService {

    public BookServiceServer(SortingRepository<Long, Book> repository) {
        super(repository);
    }

    @Override
    public List<Book> filterBooksByAuthor(String author) {
        Iterable<Book> books=repository.findAll();
        return StreamSupport.stream(books.spliterator(), false)
                .filter(book -> book.getAuthor().contains(author))
                .collect(Collectors.toList());
    }

    @Override
    public List<Book> sortBooks() {
        Sort<Book> sort1=new Sort<>("author");
        Sort<Book> sort2=new Sort<>(Sort.SortType.DESC,"author", "price");
        Sort<Book> sort3=new Sort<>(Sort.SortType.DESC,"author");
        sort3.and(new Sort<>(Sort.SortType.DESC, "price"));

        return StreamSupport.stream(repository.findAll(sort3).spliterator(), false).collect(Collectors.toList());
    }
}
