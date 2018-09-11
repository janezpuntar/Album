package com.album.janez.application;

import com.album.janez.Constants;
import com.album.janez.NetworkManager;
import com.album.janez.data.datasource.AlbumDataSource;
import com.album.janez.data.datasource.DataService;
import com.album.janez.data.datasource.IAlbumDataSource;
import com.album.janez.data.repository.AlbumRepository;
import com.album.janez.data.repository.IAlbumRepository;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import javax.inject.Singleton;

import dagger.Module;
import dagger.Provides;
import retrofit2.Retrofit;
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory;
import retrofit2.converter.gson.GsonConverterFactory;

@Module
public class UtilsModule {

    @Provides
    @Singleton
    Gson provideGson() {
        return new GsonBuilder().create();
    }

    @Provides
    @Singleton
    Retrofit provideRetrofit(Gson gson) {
        return new Retrofit.Builder()
                .baseUrl(Constants.SERVICE_ENDPOINT)
                .addConverterFactory(GsonConverterFactory.create(gson))
                .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
                .build();
    }

    @Provides
    @Singleton
    DataService provideDataService(Retrofit retrofit) {
        return retrofit.create(DataService.class);
    }

    @Provides
    @Singleton
    IAlbumDataSource provideAlbumDataSource(DataService dataService) {
        return new AlbumDataSource(dataService);
    }

    @Provides
    IAlbumRepository provideWeatherRepository(IAlbumDataSource albumDataSource, NetworkManager networkManager) {
        return new AlbumRepository(albumDataSource, networkManager);
    }

}