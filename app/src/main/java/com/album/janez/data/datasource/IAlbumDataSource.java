package com.album.janez.data.datasource;

import com.album.janez.data.model.presentation.Album;
import com.album.janez.data.model.presentation.Photo;

import java.util.List;
import java.util.Map;

import io.reactivex.Single;

public interface IAlbumDataSource {


    /**
     * Get list of albums from {@link DataService}, make transformation and pass it forward.
     *
     * @return map of transformed {@link com.album.janez.data.model.entity.AlbumEntity} to {@link Album}
     */
    Single<Map<Integer, Album>> getAlbums();


    /**
     * Get list of photos from {@link DataService}, make transformation and pass it forward.
     *
     * @return map of transformed {@link Photo} from {@link com.album.janez.data.model.entity.PhotoEntity}
     */
    Single<List<Photo>> getPhotos();
}
