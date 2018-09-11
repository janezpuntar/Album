package com.album.janez.album.fragment.photo_grid;

import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Filter;
import android.widget.Filterable;

import com.album.janez.R;
import com.album.janez.data.model.presentation.Album;
import com.album.janez.data.model.presentation.Photo;

import java.util.ArrayList;
import java.util.List;

public class PhotoGridAdapter extends RecyclerView.Adapter<PhotoItemViewHolder> implements Filterable {

    private List<Photo> filteredPhotos;
    private List<Photo> originalPhotos;
    private OnPhotoClickListener listener;

    public PhotoGridAdapter(OnPhotoClickListener listener) {
        this.originalPhotos = new ArrayList<>();
        this.filteredPhotos = new ArrayList<>();
        this.listener = listener;
    }

    public void setPhotos(List<Photo> photos) {
        this.originalPhotos.clear();
        this.filteredPhotos.clear();

        this.filteredPhotos = photos;
        this.originalPhotos = photos;
        notifyDataSetChanged();
    }

    private void setFilteredPhotos(List<Photo> photos) {
        this.filteredPhotos = photos;
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
        holder.bind(filteredPhotos.get(position));
    }

    public Photo getPhoto(int position) {
        return filteredPhotos.get(Math.abs(position) % filteredPhotos.size());
    }

    @Override
    public int getItemCount() {
        return filteredPhotos.size();
    }

    @Override
    public Filter getFilter() {
        return new Filter() {
            @Override
            protected FilterResults performFiltering(CharSequence constraint) {
                FilterResults results = new FilterResults();

                if (TextUtils.isEmpty(constraint)) {
                    results.values = originalPhotos;
                    results.count = originalPhotos.size();
                } else {
                    List<Photo> filterList = new ArrayList<>();

                    for (Photo album : originalPhotos) {

                        if (album.getTitle().toLowerCase().contains(String.valueOf(constraint).toLowerCase())) {
                            filterList.add(album);
                        }
                    }

                    results.values = filterList;
                    results.count = filterList.size();

                }

                return results;
            }

            @Override
            protected void publishResults(CharSequence constraint, FilterResults results) {
                setFilteredPhotos((List<Photo>) results.values);
            }
        };
    }
}
