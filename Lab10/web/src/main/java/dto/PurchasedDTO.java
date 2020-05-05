package dto;

import lombok.*;

@NoArgsConstructor
@AllArgsConstructor
@Data
@EqualsAndHashCode(callSuper = true)
@ToString(callSuper = true)
@Builder
public class PurchasedDTO extends BaseDTO{
    private long bookID;
    private long clientID;
}
