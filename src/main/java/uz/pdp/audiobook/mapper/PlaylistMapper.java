package uz.pdp.audiobook.mapper;

import org.mapstruct.*;
import uz.pdp.audiobook.entity.Playlist;
import uz.pdp.audiobook.entity.PlaylistAudiobooks;
import uz.pdp.audiobook.payload.PlaylistDTO;

import java.util.ArrayList;
import java.util.List;

@Mapper(unmappedTargetPolicy = ReportingPolicy.IGNORE, componentModel = MappingConstants.ComponentModel.SPRING)
public interface PlaylistMapper {
    @Mapping(target = "userId", source = "user.id")
    @Mapping(target = "audiobooksId", expression = "java(mapAudiobooks(playlist.getAudiobooks()))")
    PlaylistDTO toDTO(Playlist playlist);

    @Mapping(target = "user", ignore = true)
    @Mapping(target = "audiobooks", ignore = true)
    Playlist toEntity(PlaylistDTO playlistDTO);

    @BeanMapping(nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE)
    @Mapping(target = "id", ignore = true)
    void partialUpdate(PlaylistDTO playlistDTO, @MappingTarget Playlist playlist);

    default List<Integer> mapAudiobooks(List<PlaylistAudiobooks> playlistAudiobooks) {
        if (playlistAudiobooks == null) {
            return new ArrayList<>();
        }

        return playlistAudiobooks.stream()
                .map(pa -> pa.getAudiobook().getId())
                .toList();
    }
}