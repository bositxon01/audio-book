package uz.pdp.audiobook.service;

import uz.pdp.audiobook.payload.ApiResult;
import uz.pdp.audiobook.payload.PlaylistDTO;
import uz.pdp.audiobook.payload.withoutId.PlaylistDto;

import java.util.List;

public interface PlaylistService {

    ApiResult<PlaylistDTO> createPlaylist(PlaylistDto playlistDto);

    ApiResult<PlaylistDTO> getPlaylist(Integer id);

    ApiResult<List<PlaylistDTO>> getAllPlaylists();

    ApiResult<PlaylistDTO> updatePlaylist(Integer id, PlaylistDto playlistDto);

    ApiResult<Object> deletePlaylist(Integer id);

}
