package converter;

import dto.PurchasedDTO;
import lab10.core.domain.Purchased;
import org.springframework.stereotype.Component;

@Component
public class PurchasedConverter extends BaseConverter<Purchased, PurchasedDTO> {
    @Override
    public Purchased convertDtoToModel(PurchasedDTO dto) {
        Purchased purchased = Purchased.builder()
                .bookID(dto.getBookID())
                .clientID(dto.getClientID())
                .build();
        purchased.setId(dto.getId());
        return purchased;
    }

    @Override
    public PurchasedDTO convertModelToDto(Purchased purchased) {
        PurchasedDTO purchasedDTO = PurchasedDTO.builder()
                .bookID(purchased.getBookID())
                .clientID(purchased.getClientID())
                .build();
        purchasedDTO.setId(purchased.getId());
        return purchasedDTO;
    }
}
