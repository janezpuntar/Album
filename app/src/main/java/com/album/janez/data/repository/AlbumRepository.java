package com.album.janez.data.repository;

import com.album.janez.data.datasource.IAlbumDataSource;
import com.album.janez.data.model.presentation.Album;
import com.album.janez.data.model.presentation.Photo;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import io.reactivex.Single;
import io.reactivex.functions.BiFunction;

public class AlbumRepository implements IAlbumRepository {

    private final IAlbumDataSource albumDataSource;

    public AlbumRepository(IAlbumDataSource albumDataSource) {
        this.albumDataSource = albumDataSource;
    }


    @Override
    public Single<List<Album>> getAlbums() {
        return Single.zip(albumDataSource.getAlbums(), albumDataSource.getPhotos(), new BiFunction<Map<Integer, Album>, List<Photo>, List<Album>>() {
            @Override
            public List<Album> apply(Map<Integer, Album> albumMap, List<Photo> photos) throws Exception {

                for (Photo photoItem : photos) {
                    if (albumMap.containsKey(photoItem.getAlbumId())) {
                        albumMap.get(photoItem.getAlbumId()).addPhoto(photoItem);
                    }
                }

                return new ArrayList<>(albumMap.values());
            }
        });
    }
}
