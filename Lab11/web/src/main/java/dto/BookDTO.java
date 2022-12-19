package dto;

import lombok.*;

import java.io.Serializable;

@NoArgsConstructor
@AllArgsConstructor
@Data
@Builder
public class BookDTO implements Serializable {
    private Long id;
    private String title;
    private String author;
    private int price;
}
