package converter;


import dto.BookDTO;
import lab10.core.domain.Book;
import org.springframework.stereotype.Component;

@Component
public class BookConverter extends BaseConverter<Book, BookDTO> {
    @Override
    public Book convertDtoToModel(BookDTO dto) {
        return Book.builder()
                .id(dto.getId())
                .title(dto.getTitle())
                .author(dto.getAuthor())
                .price(dto.getPrice())
                .build();
    }

    @Override
    public BookDTO convertModelToDto(Book book) {
        return BookDTO.builder()
                .id(book.getId())
                .title(book.getTitle())
                .author(book.getAuthor())
                .price(book.getPrice())
                .build();
    }
}
