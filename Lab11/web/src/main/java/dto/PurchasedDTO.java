package dto;

import lombok.*;

import java.io.Serializable;
import java.util.Date;

@NoArgsConstructor
@AllArgsConstructor
@Data
@Builder
public class PurchasedDTO implements Serializable {
    private Long id;
    private BookDTO book;
    private ClientDTO client;
    private Date date;
}
