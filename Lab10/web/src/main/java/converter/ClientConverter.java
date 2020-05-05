package converter;

import dto.ClientDTO;
import lab10.core.domain.Client;
import org.springframework.stereotype.Component;

@Component
public class ClientConverter extends BaseConverter<Client, ClientDTO> {
    @Override
    public Client convertDtoToModel(ClientDTO dto) {
        Client client = Client.builder()
                .FirstName(dto.getFirstName())
                .LastName(dto.getLastName())
                .age(dto.getAge())
                .build();
        client.setId(dto.getId());

        return client;
    }

    @Override
    public ClientDTO convertModelToDto(Client client) {
        ClientDTO clientDTO = ClientDTO.builder()
                .FirstName(client.getFirstName())
                .LastName(client.getLastName())
                .age(client.getAge())
                .build();
        clientDTO.setId(client.getId());
        return clientDTO;
    }
}
