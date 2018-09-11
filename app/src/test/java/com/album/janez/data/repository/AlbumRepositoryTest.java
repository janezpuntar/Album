package com.album.janez.data.repository;

import com.album.janez.data.datasource.IAlbumDataSource;
import com.album.janez.data.model.presentation.Album;
import com.album.janez.data.model.presentation.Photo;
import com.album.janez.network.INetworkManager;

import junit.framework.Assert;

import org.junit.Before;
import org.junit.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import io.reactivex.Single;

import static org.mockito.Mockito.when;

public class AlbumRepositoryTest {


    @Mock
    IAlbumDataSource albumDataSource;
    @Mock
    INetworkManager networkManager;

    IAlbumRepository albumRepository;

    @Before
    public void setUp() throws Exception {
        MockitoAnnotations.initMocks(this);

        when(networkManager.isOnline()).thenReturn(Single.just(true));

        Map<Integer, Album> map = new HashMap<>();
        map.put(1, new Album(1, "album 1"));
        map.put(2, new Album(2, "album 2"));

        List<Photo> photos = new ArrayList<>();
        photos.add(new Photo(1, 1, "photo 1", "url 1", "thumbnail 1"));
        photos.add(new Photo(1, 2, "photo 2", "url 2", "thumbnail 2"));
        photos.add(new Photo(1, 3, "photo 3", "url 3", "thumbnail 3"));

        when(albumDataSource.getAlbums()).thenReturn(Single.just(map));
        when(albumDataSource.getPhotos()).thenReturn(Single.just(photos));

        albumRepository = new AlbumRepository(albumDataSource, networkManager);
    }

    @Test
    public void getAlbums() {

        List<Album> albums = albumRepository.getAlbums().test().assertComplete().values().get(0);

        Assert.assertEquals(2, albums.size());
        // first album has 3 photos
        Assert.assertEquals(3, albums.get(0).getPhotos().size());
        // second album does not have photos
        Assert.assertEquals(0, albums.get(1).getPhotos().size());
    }
}