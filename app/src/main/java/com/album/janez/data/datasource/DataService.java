package com.album.janez.data.datasource;

import com.album.janez.data.model.entity.AlbumEntity;
import com.album.janez.data.model.entity.PhotoEntity;

import java.util.List;

import io.reactivex.Single;
import retrofit2.http.GET;

public interface DataService {

    @GET("/albums")
    Single<List<AlbumEntity>> getAlbums();

    @GET("/photos")
    Single<List<PhotoEntity>> getPhotos();
}
