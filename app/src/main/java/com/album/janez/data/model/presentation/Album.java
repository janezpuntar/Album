package com.album.janez.data.model.presentation;

import java.util.ArrayList;
import java.util.List;

public class Album {

    private int id;
    private String title;
    private List<Photo> photos;

    public Album(int id, String title) {
        this.id = id;
        this.title = title;
        this.photos = new ArrayList<>();
    }

    public void addPhoto(Photo photo) {
        photos.add(photo);
    }
}
