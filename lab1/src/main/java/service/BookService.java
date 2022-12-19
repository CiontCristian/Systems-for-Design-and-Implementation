package service;

import domain.Book;
import repo.Repository;
import repo.Sort;
import repo.SortingRepository;

import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;
import java.util.stream.StreamSupport;

public class BookService extends Service<Long, Book>{

    public BookService(SortingRepository<Long, Book> repository) {
        super(repository);
    }

    public Set<Book> filterBooksByAuthor(String author){
        Iterable<Book> allBooks=super.getAll();

        return StreamSupport.stream(allBooks.spliterator(), false)
                .filter(book -> book.getAuthor().contains(author)).collect(Collectors.toSet());
    }

    public List<Book> sortBooks(){
        Sort<Book> sort1=new Sort<>("author");
        Sort<Book> sort2=new Sort<>(Sort.SortType.DESC,"author", "price");

        return StreamSupport.stream(repository.findAll(sort2).spliterator(), false).collect(Collectors.toList());
    }

}
