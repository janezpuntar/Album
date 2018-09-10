package com.album.janez.data.datasource;

import com.album.janez.data.model.entity.AlbumEntity;
import com.album.janez.data.model.entity.PhotoEntity;
import com.album.janez.data.model.presentation.Album;
import com.album.janez.data.model.presentation.Photo;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import io.reactivex.Single;
import io.reactivex.functions.Function;

public class AlbumDataSource implements IAlbumDataSource {

    private final DataService dataService;

    public AlbumDataSource(DataService dataService) {
        this.dataService = dataService;
    }


    @Override
    public Single<Map<Integer, Album>> getAlbums() {
        return dataService.getAlbums().map(new Function<List<AlbumEntity>, Map<Integer, Album>>() {
            @Override
            public Map<Integer, Album> apply(List<AlbumEntity> albumEntities) throws Exception {
                HashMap<Integer, Album> albums = new HashMap<>();

                for (AlbumEntity entity : albumEntities) {
                    if (!albums.containsKey(entity.getId())) {
                        albums.put(entity.getId(), new Album(entity.getId(), entity.getTitle()));
                    }
                }

                return albums;
            }
        });
    }

    @Override
    public Single<List<Photo>> getPhotos() {
        return dataService.getPhotos().map(new Function<List<PhotoEntity>, List<Photo>>() {
            @Override
            public List<Photo> apply(List<PhotoEntity> photoEntities) throws Exception {
                List<Photo> photos = new ArrayList<>();

                for (PhotoEntity photoEntity : photoEntities) {
                    photos.add(new Photo(photoEntity.getAlbumId(), photoEntity.getId(), photoEntity.getTitle(), photoEntity.getUrl(), photoEntity.getThumbnailUrl()));
                }

                return photos;
            }
        });
    }
}
