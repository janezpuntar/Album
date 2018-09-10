package com.album.janez.application;

import android.app.Application;
import android.content.Context;

public class AlbumApplication extends Application {

    private AlbumComponent albumComponent;

    @Override
    public void onCreate() {
        super.onCreate();
        buildComponentAndInject();
    }

    private void buildComponentAndInject() {

        albumComponent = DaggerAlbumComponent.builder()
                .albumModule(new AlbumModule(this))
                .build();

        albumComponent.inject(this);
    }

    public AlbumComponent getAlbumComponent() {
        return albumComponent;
    }

    public static AlbumApplication get(Context context) {
        return (AlbumApplication) context.getApplicationContext();
    }
}
