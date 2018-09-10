package com.album.janez.album.fragment.album_list;

import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.album.janez.R;
import com.album.janez.data.model.presentation.Album;

import java.util.ArrayList;
import java.util.List;

public class AlbumListAdapter extends RecyclerView.Adapter<AlbumItemViewHolder> {

    private List<Album> albums;
    private OnAlbumClickListener listener;

    public AlbumListAdapter(OnAlbumClickListener listener) {
        this.albums = new ArrayList<>();
        this.listener = listener;
    }

    public void setAlbums(List<Album> albums) {
        this.albums.clear();
        this.albums = albums;
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
        holder.bind(albums.get(position));
    }

    public Album getAlbum(int position) {
        return albums.get(position);
    }

    @Override
    public int getItemCount() {
        return albums.size();
    }
}
