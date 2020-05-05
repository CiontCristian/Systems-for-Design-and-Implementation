package converter;


import dto.BookDTO;
import lab10.core.domain.Book;
import org.springframework.stereotype.Component;

@Component
public class BookConverter extends BaseConverter<Book, BookDTO> {
    @Override
    public Book convertDtoToModel(BookDTO dto) {
        Book book = Book.builder()
                .title(dto.getTitle())
                .author(dto.getAuthor())
                .price(dto.getPrice())
                .build();
        book.setId(dto.getId());
        return book;
    }

    @Override
    public BookDTO convertModelToDto(Book book) {
        BookDTO bookDTO = BookDTO.builder()
                .title(book.getTitle())
                .author(book.getAuthor())
                .price(book.getPrice())
                .build();
        bookDTO.setId(book.getId());
        return bookDTO;
    }
}
