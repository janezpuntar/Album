package com.album.janez.album.fragment.album_list;

import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.TextView;

import com.album.janez.R;
import com.album.janez.data.model.presentation.Album;

import butterknife.BindView;
import butterknife.ButterKnife;

public class AlbumItemViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {

    private OnAlbumClickListener listener;

    @BindView(R.id.title)
    TextView title;

    public AlbumItemViewHolder(View itemView, OnAlbumClickListener listener) {
        super(itemView);
        ButterKnife.bind(this, itemView);
        this.listener = listener;

        itemView.setOnClickListener(this);

    }

    public void bind(Album album) {
        title.setText(album.getTitle());
    }

    @Override
    public void onClick(View v) {
        listener.selected(getAdapterPosition());
    }
}
