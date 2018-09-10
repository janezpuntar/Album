package com.album.janez.album.fragment.photo_grid;

import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.album.janez.R;
import com.album.janez.data.model.presentation.Photo;

import java.util.ArrayList;
import java.util.List;

public class PhotoGridAdapter extends RecyclerView.Adapter<PhotoItemViewHolder> {

    private List<Photo> photos;
    private OnPhotoClickListener listener;

    public PhotoGridAdapter(OnPhotoClickListener listener) {
        this.photos = new ArrayList<>();
        this.listener = listener;
    }

    public void setPhotos(List<Photo> photos) {
        this.photos.clear();
        this.photos = photos;
        notifyDataSetChanged();
    }

    @NonNull
    @Override
    public PhotoItemViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.photo_grid_item, parent, false);
        return new PhotoItemViewHolder(view, listener);
    }

    @Override
    public void onBindViewHolder(@NonNull PhotoItemViewHolder holder, int position) {
        holder.bind(photos.get(position));
    }

    public Photo getPhoto(int position) {
        return photos.get(position);
    }

    @Override
    public int getItemCount() {
        return photos.size();
    }
}
