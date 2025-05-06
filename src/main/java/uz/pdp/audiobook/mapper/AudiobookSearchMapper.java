package uz.pdp.audiobook.mapper;

import org.mapstruct.Mapper;
import uz.pdp.audiobook.entity.Audiobook;
import uz.pdp.audiobook.payload.AudiobookDTO;

@Mapper(componentModel = "spring")
public interface AudiobookSearchMapper {
    AudiobookDTO toDTO(Audiobook audiobook);
}
