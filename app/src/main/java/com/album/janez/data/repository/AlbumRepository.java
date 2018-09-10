package com.album.janez.data.repository;

import com.album.janez.data.datasource.DataService;

public class AlbumRepository implements IAlbumRepository {

    private final DataService dataService;

    public AlbumRepository(DataService dataService) {
        this.dataService = dataService;
    }


}
