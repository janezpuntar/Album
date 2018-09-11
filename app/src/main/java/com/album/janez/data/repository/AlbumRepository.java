package com.album.janez.data.repository;

import com.album.janez.data.datasource.IAlbumDataSource;
import com.album.janez.data.model.presentation.Album;
import com.album.janez.data.model.presentation.Photo;
import com.album.janez.network.NetworkManager;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import io.reactivex.Single;
import io.reactivex.SingleSource;
import io.reactivex.functions.BiFunction;
import io.reactivex.functions.Function;

public class AlbumRepository implements IAlbumRepository {

    private final IAlbumDataSource albumDataSource;
    private final NetworkManager networkManager;

    public AlbumRepository(IAlbumDataSource albumDataSource, NetworkManager networkManager) {
        this.albumDataSource = albumDataSource;
        this.networkManager = networkManager;
    }


    @Override
    public Single<List<Album>> getAlbums() {

        return networkManager.isOnline().flatMap(new Function<Boolean, SingleSource<? extends List<Album>>>() {
            @Override
            public SingleSource<? extends List<Album>> apply(Boolean aBoolean) throws Exception {

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
        });
    }
}
