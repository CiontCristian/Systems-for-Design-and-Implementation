package converter;

import dto.PurchasedDTO;
import lab10.core.domain.Purchased;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class PurchasedConverter extends BaseConverter<Purchased, PurchasedDTO> {
    @Autowired
    private BookConverter bookConverter;
    @Autowired
    private ClientConverter clientConverter;

    @Override
    public Purchased convertDtoToModel(PurchasedDTO dto) {
        return Purchased.builder()
                .id(dto.getId())
                .book((dto.getBook() == null) ? null : bookConverter.convertDtoToModel(dto.getBook()))
                .client((dto.getClient() == null) ? null : clientConverter.convertDtoToModel(dto.getClient()))
                .date(dto.getDate())
                .build();
    }

    @Override
    public PurchasedDTO convertModelToDto(Purchased purchased) {
        return PurchasedDTO.builder()
                .id(purchased.getId())
                .book((purchased.getBook() == null) ? null : bookConverter.convertModelToDto(purchased.getBook()))
                .client((purchased.getClient() == null) ? null : clientConverter.convertModelToDto(purchased.getClient()))
                .date(purchased.getDate())
                .build();
    }
}
