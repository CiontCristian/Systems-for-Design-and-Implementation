package lab10.core.domain;

import lombok.*;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Table;
import java.io.Serializable;

@Entity
@NoArgsConstructor
@AllArgsConstructor
@Data
@Builder
public class Book implements Serializable {
    @Id
    @GeneratedValue
    private Long id;
    private String title;
    private String author;
    private int price;

}
