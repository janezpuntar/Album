package com.album.janez.data.datasource;

import com.album.janez.data.model.entity.AlbumEntity;
import com.album.janez.data.model.entity.PhotoEntity;
import com.album.janez.data.model.presentation.Album;
import com.album.janez.data.model.presentation.Photo;

import org.junit.Before;
import org.junit.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import io.reactivex.Single;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

public class AlbumDataSourceTest {

    @Mock
    DataService dataService;

    IAlbumDataSource dataSource;

    @Before
    public void setUp() throws Exception {
        MockitoAnnotations.initMocks(this);

        List<AlbumEntity> albumEntities = new ArrayList<>();
        albumEntities.add(getAlbumEntityMock(1, "title1"));
        when(dataService.getAlbums()).thenReturn(Single.just(albumEntities));

        List<PhotoEntity> photoEntities = new ArrayList<>();
        photoEntities.add(getPhotoEntityMock(1, 1, "photoTitle", "url", "thumbnail"));
        when(dataService.getPhotos()).thenReturn(Single.just(photoEntities));

        dataSource = new AlbumDataSource(dataService);
    }

    @Test
    public void getAlbums() {
        Map<Integer, Album> values = dataSource.getAlbums().test().assertComplete().values().get(0);

        // map has one element
        assertEquals(1, values.size());
        // map contains key with Integer value 1
        assertTrue(values.containsKey(1));
        // title of album is "title1"
        assertEquals("title1", values.get(1).getTitle());
    }

    @Test
    public void getPhotos() {
        List<Photo> photos = dataSource.getPhotos().test().assertComplete().values().get(0);

        assertEquals(1, photos.size());

        Photo photo = photos.get(0);
        assertEquals(1, photo.getAlbumId());
        assertEquals("photoTitle", photo.getTitle());
        assertEquals("url", photo.getUrl());
        assertEquals("thumbnail", photo.getThumbnailUrl());

    }

    private AlbumEntity getAlbumEntityMock(int id, String title) {
        AlbumEntity entity = mock(AlbumEntity.class);
        when(entity.getId()).thenReturn(id);
        when(entity.getTitle()).thenReturn(title);
        return entity;
    }

    private PhotoEntity getPhotoEntityMock(int id, int albumId, String title, String url, String thumbnail) {
        PhotoEntity entity = mock(PhotoEntity.class);
        when(entity.getId()).thenReturn(id);
        when(entity.getAlbumId()).thenReturn(albumId);
        when(entity.getTitle()).thenReturn(title);
        when(entity.getUrl()).thenReturn(url);
        when(entity.getThumbnailUrl()).thenReturn(thumbnail);

        return entity;
    }
}


