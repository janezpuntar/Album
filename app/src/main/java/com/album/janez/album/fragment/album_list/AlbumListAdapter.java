package com.album.janez.album.fragment.album_list;

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

import java.util.ArrayList;
import java.util.List;

public class AlbumListAdapter extends RecyclerView.Adapter<AlbumItemViewHolder> implements Filterable {

    private List<Album> filteredAlbums;
    private List<Album> originalAlbums;
    private OnAlbumClickListener listener;

    public AlbumListAdapter(OnAlbumClickListener listener) {
        this.filteredAlbums = new ArrayList<>();
        this.originalAlbums = new ArrayList<>();
        this.listener = listener;
    }

    public void setAlbums(List<Album> albums) {
        this.filteredAlbums.clear();
        this.originalAlbums.clear();

        this.filteredAlbums = albums;
        this.originalAlbums = albums;
        notifyDataSetChanged();
    }

    public void setFilteredAlbums(List<Album> albums) {
        this.filteredAlbums = albums;
        notifyDataSetChanged();
    }

    @NonNull
    @Override
    public AlbumItemViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.album_list_item, parent, false);
        return new AlbumItemViewHolder(view, listener);
    }

    @Override
    public void onBindViewHolder(@NonNull AlbumItemViewHolder holder, int position) {
        holder.bind(filteredAlbums.get(position));
    }

    public Album getAlbum(int position) {
        return filteredAlbums.get(position);
    }

    @Override
    public int getItemCount() {
        return filteredAlbums.size();
    }

    @Override
    public Filter getFilter() {
        return new Filter() {
            @Override
            protected FilterResults performFiltering(CharSequence constraint) {
                FilterResults results = new FilterResults();

                if (TextUtils.isEmpty(constraint)) {
                    results.values = originalAlbums;
                    results.count = originalAlbums.size();
                } else {
                    List<Album> filterList = new ArrayList<>();

                    for (Album album : originalAlbums) {

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
                setFilteredAlbums((List<Album>) results.values);
            }
        };
    }
}
