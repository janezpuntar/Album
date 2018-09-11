package com.album.janez.application;

import android.content.Context;

import com.album.janez.NetworkManager;

import javax.inject.Singleton;

import dagger.Module;
import dagger.Provides;

@Module(
        includes = {
                UtilsModule.class
        }
)
public class AlbumModule {

    private final AlbumApplication app;

    public AlbumModule(AlbumApplication app) {
        this.app = app;
    }

    @Provides
    @Singleton
    AlbumApplication provideCoolApplication() {
        return this.app;
    }

    @Provides
    @Singleton
    Context provideContext(AlbumApplication app) {
        return app;
    }

    @Provides
    @Singleton
    NetworkManager provideNetworkManager(Context context) {
        return new NetworkManager(context);
    }
}