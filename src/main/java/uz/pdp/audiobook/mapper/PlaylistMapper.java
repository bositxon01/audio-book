package uz.pdp.audiobook.mapper;

import org.mapstruct.*;
import uz.pdp.audiobook.entity.Playlist;
import uz.pdp.audiobook.payload.PlaylistDTO;

@Mapper(unmappedTargetPolicy = ReportingPolicy.IGNORE, componentModel = MappingConstants.ComponentModel.SPRING)
public interface PlaylistMapper {
    Playlist toEntity(PlaylistDTO playlistDTO);

    PlaylistDTO toDTO(Playlist playlist);

    @BeanMapping(nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE)
    Playlist partialUpdate(PlaylistDTO playlistDTO, @MappingTarget Playlist playlist);
}