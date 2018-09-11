package com.album.janez.album.dialog.photo_detail;

import android.arch.lifecycle.Observer;
import android.arch.lifecycle.ViewModelProviders;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.DialogFragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;

import com.album.janez.R;
import com.album.janez.album.activity.main.AlbumViewModel;
import com.album.janez.data.model.presentation.Photo;
import com.squareup.picasso.Picasso;

import butterknife.BindView;
import butterknife.ButterKnife;

public class PhotoDetailDialog extends DialogFragment implements View.OnClickListener {

    public static final String TAG = PhotoDetailDialog.class.getSimpleName();

    @BindView(R.id.title)
    TextView title;

    @BindView(R.id.url)
    TextView url;

    @BindView(R.id.image)
    ImageView image;

    @BindView(R.id.btn_next)
    ImageButton btnNext;

    @BindView(R.id.btn_previous)
    ImageButton btnPrevious;

    private Integer currentPhotoPosition;
    private AlbumViewModel albumViewModel;
    private SelectedPhotoViewModel selectedPhotoViewModel;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setStyle(DialogFragment.STYLE_NO_TITLE, R.style.PhotoDialog);

        selectedPhotoViewModel = ViewModelProviders.of(requireActivity()).get(SelectedPhotoViewModel.class);
        selectedPhotoViewModel.getSelectedPhotoPosition().observe(this, new Observer<Integer>() {
            @Override
            public void onChanged(@Nullable Integer position) {
                currentPhotoPosition = position;
            }
        });
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.photo_detail_dialog, container, false);
        ButterKnife.bind(this, view);

        btnNext.setOnClickListener(this);
        btnPrevious.setOnClickListener(this);

        return view;
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        albumViewModel = ViewModelProviders.of(requireActivity()).get(AlbumViewModel.class);
        albumViewModel.getSelectedPhoto().observe(requireActivity(), new Observer<Photo>() {
            @Override
            public void onChanged(@Nullable Photo photo) {
                if (photo != null) {
                    Picasso.get().load(photo.getUrl()).into(image);
                    title.setText(photo.getTitle());
                    url.setText(photo.getUrl());
                }
            }
        });
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.btn_next:
                selectedPhotoViewModel.setSelectedPhotoPosition(--currentPhotoPosition);
                break;
            case R.id.btn_previous:
                selectedPhotoViewModel.setSelectedPhotoPosition(++currentPhotoPosition);
                break;
        }
    }
}
