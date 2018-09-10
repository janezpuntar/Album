package com.album.janez.data.model.entity;

import com.google.gson.annotations.SerializedName;

public class PhotoEntity {

    @SerializedName("albumId")
    private int albumId;

    @SerializedName("id")
    private int id;

    @SerializedName("title")
    private String title;

    @SerializedName("url")
    private String url;

    @SerializedName("thumbnailUrl")
    private String thumbnailUrl;
}
