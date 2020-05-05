package converter;


import dto.BaseDTO;
import lab10.core.domain.BaseEntity;

public interface Converter<Model extends BaseEntity<Long>, Dto extends BaseDTO> {
    Model convertDtoToModel(Dto dto);

    Dto convertModelToDto(Model model);
}
