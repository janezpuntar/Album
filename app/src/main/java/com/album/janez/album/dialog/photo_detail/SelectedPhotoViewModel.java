package com.album.janez.album.dialog.photo_detail;

import android.arch.lifecycle.MutableLiveData;
import android.arch.lifecycle.ViewModel;

public class SelectedPhotoViewModel extends ViewModel {

    private MutableLiveData<Integer> selectedPhotoPosition = new MutableLiveData<>();

    public MutableLiveData<Integer> getSelectedPhotoPosition() {
        return selectedPhotoPosition;
    }

    public void setSelectedPhotoPosition(Integer selectedPhotoPosition) {
        this.selectedPhotoPosition.setValue(selectedPhotoPosition);
    }
}
