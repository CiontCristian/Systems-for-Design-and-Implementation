package dto;

import lombok.*;

import java.io.Serializable;

@NoArgsConstructor
@AllArgsConstructor
@Data
@Builder
public class ClientDTO implements Serializable {
    private Long id;
    private String firstName;
    private String lastName;
    private int age;
}
