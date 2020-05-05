package dto;

import lab10.core.domain.BaseEntity;
import lombok.*;

import javax.persistence.Entity;
import javax.persistence.Table;

@NoArgsConstructor
@AllArgsConstructor
@Data
@EqualsAndHashCode(callSuper = true)
@ToString(callSuper = true)
@Builder
public class ClientDTO extends BaseDTO {
    private String FirstName;
    private String LastName;
    private int age;
}
