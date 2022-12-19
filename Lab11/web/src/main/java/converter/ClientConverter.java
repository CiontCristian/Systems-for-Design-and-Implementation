package converter;

import dto.ClientDTO;
import lab10.core.domain.Client;
import org.springframework.stereotype.Component;

@Component
public class ClientConverter extends BaseConverter<Client, ClientDTO> {
    @Override
    public Client convertDtoToModel(ClientDTO dto) {
        return Client.builder()
                .id(dto.getId())
                .firstName(dto.getFirstName())
                .lastName(dto.getLastName())
                .age(dto.getAge())
                .build();
    }

    @Override
    public ClientDTO convertModelToDto(Client client) {
        return ClientDTO.builder()
                .id(client.getId())
                .firstName(client.getFirstName())
                .lastName(client.getLastName())
                .age(client.getAge())
                .build();
    }
}
