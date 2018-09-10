package com.album.janez.data.repository;

import com.album.janez.data.model.presentation.Album;

import java.util.List;

import io.reactivex.Single;

public interface IAlbumRepository {

    Single<List<Album>> getAlbums();
}
