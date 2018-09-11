package com.album.janez.data.repository;

import com.album.janez.data.model.presentation.Album;

import java.util.List;

import io.reactivex.Single;

public interface IAlbumRepository {

    /**
     * Get list of albums and photos for each of the album.
     *
     * @return List of {@link Album} with {@link com.album.janez.data.model.presentation.Photo}s
     * connected to the Album.
     */
    Single<List<Album>> getAlbums();
}
