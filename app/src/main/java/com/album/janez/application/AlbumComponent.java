package com.album.janez.application;

import com.album.janez.data.repository.IAlbumRepository;

import javax.inject.Singleton;
import dagger.Component;

@Singleton
@Component(modules = {AlbumModule.class})
public interface AlbumComponent {

    void inject(AlbumApplication app);

    IAlbumRepository getAlbumRepository();
}

