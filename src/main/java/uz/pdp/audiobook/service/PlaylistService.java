package uz.pdp.audiobook.service;

import uz.pdp.audiobook.payload.ApiResult;
import uz.pdp.audiobook.payload.PlaylistDTO;

import java.util.List;

public interface PlaylistService {

    ApiResult<PlaylistDTO> createPlaylist(PlaylistDTO playlistDTO);

    ApiResult<PlaylistDTO> getPlaylist(Integer id);

    ApiResult<List<PlaylistDTO>> getAllPlaylists();

    ApiResult<PlaylistDTO> updatePlaylist(Integer id, PlaylistDTO playlistDTO);

    ApiResult<Object> deletePlaylist(Integer id);
}
