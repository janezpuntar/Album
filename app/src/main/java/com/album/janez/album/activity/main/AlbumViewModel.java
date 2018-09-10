package com.album.janez.album.activity.main;

import android.app.Application;
import android.arch.lifecycle.AndroidViewModel;
import android.arch.lifecycle.LiveData;
import android.arch.lifecycle.MutableLiveData;
import android.support.annotation.NonNull;

import com.album.janez.application.AlbumApplication;
import com.album.janez.data.model.presentation.Album;
import com.album.janez.data.model.presentation.Photo;
import com.album.janez.response.Response;

import java.util.List;

import io.reactivex.SingleObserver;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.Disposable;
import io.reactivex.schedulers.Schedulers;

public class AlbumViewModel extends AndroidViewModel {

    private Disposable disposable;
    private MutableLiveData<Response<List<Album>>> data = new MutableLiveData<>();
    private MutableLiveData<Album> selectedAlbum = new MutableLiveData<>();
    private MutableLiveData<Photo> selectedPhoto = new MutableLiveData<>();

    public AlbumViewModel(@NonNull Application application) {
        super(application);

        AlbumApplication.get(getApplication()).getAlbumComponent()
                .getAlbumRepository().getAlbums()
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new SingleObserver<List<Album>>() {
                    @Override
                    public void onSubscribe(Disposable d) {
                        disposable = d;
                        data.postValue(new Response<List<Album>>());
                    }

                    @Override
                    public void onSuccess(List<Album> albums) {
                        data.postValue(new Response<>(albums));
                    }

                    @Override
                    public void onError(Throwable e) {
                        data.postValue(new Response<List<Album>>(e));
                    }
                });
    }

    public MutableLiveData<Response<List<Album>>> getData() {
        return data;
    }

    public void setSelectedAlbum(Album selectedAlbum) {
        this.selectedAlbum.setValue(selectedAlbum);
    }

    public MutableLiveData<Album> getSelectedAlbum() {
        return selectedAlbum;
    }

    public MutableLiveData<Photo> getSelectedPhoto() {
        return selectedPhoto;
    }

    public void setSelectedPhoto(Photo selectedPhoto) {
        this.selectedPhoto.setValue(selectedPhoto);
    }

    @Override
    protected void onCleared() {
        super.onCleared();

        if (disposable != null && !disposable.isDisposed()) {
            disposable.dispose();
            disposable = null;
        }
    }
}
