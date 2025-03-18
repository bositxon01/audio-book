package uz.pdp.audiobook.mapper;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingConstants;
import org.mapstruct.ReportingPolicy;
import uz.pdp.audiobook.entity.User;
import uz.pdp.audiobook.payload.RegisterDTO;

@Mapper(unmappedTargetPolicy = ReportingPolicy.IGNORE,
        componentModel = MappingConstants.ComponentModel.SPRING)
public interface UserMapper {

    RegisterDTO toDTO(User user);

    @Mapping(target = "id", ignore = true)
    User toEntity(RegisterDTO registerDTO);

}