package com.album.janez.album.fragment.photo_grid;

public interface OnPhotoClickListener {

    /**
     * Get selected position.
     *
     * @param position of the selected item in the list
     */
    void selected(int position);
}
