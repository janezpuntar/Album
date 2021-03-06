package com.album.janez.application;

import android.content.Context;

import com.album.janez.network.INetworkManager;
import com.album.janez.network.NetworkManager;

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
    INetworkManager provideNetworkManager(Context context) {
        return new NetworkManager(context);
    }
}