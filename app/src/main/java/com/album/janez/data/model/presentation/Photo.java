package com.album.janez.data.model.presentation;

import com.google.gson.annotations.SerializedName;

public class Photo {

    private int albumId;

    private int id;

    private String title;

    private String url;

    private String thumbnailUrl;

    public Photo(int albumId, int id, String title, String url, String thumbnailUrl) {
        this.albumId = albumId;
        this.id = id;
        this.title = title;
        this.url = url;
        this.thumbnailUrl = thumbnailUrl;
    }

    public int getAlbumId() {
        return albumId;
    }
}
