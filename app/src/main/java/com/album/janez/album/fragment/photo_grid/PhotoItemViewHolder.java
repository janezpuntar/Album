package com.album.janez.album.fragment.photo_grid;

import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.album.janez.R;
import com.album.janez.data.model.presentation.Photo;
import com.squareup.picasso.Picasso;

import butterknife.BindView;
import butterknife.ButterKnife;

public class PhotoItemViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {

    private OnPhotoClickListener listener;

    @BindView(R.id.thumbnail)
    ImageView thumbnail;
    @BindView(R.id.title)
    TextView title;

    public PhotoItemViewHolder(View itemView, OnPhotoClickListener listener) {
        super(itemView);
        ButterKnife.bind(this, itemView);
        this.listener = listener;

        itemView.setOnClickListener(this);
    }

    public void bind(Photo photo) {
        Picasso.get().load(photo.getThumbnailUrl())
                .resize(150, 150)
                .centerInside().into(thumbnail);

        title.setText(photo.getTitle());
    }

    @Override
    public void onClick(View v) {
        listener.selected(getAdapterPosition());
    }
}
