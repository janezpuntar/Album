package com.album.janez.album.fragment.photo_grid;

import android.arch.lifecycle.Observer;
import android.arch.lifecycle.ViewModelProviders;
import android.content.Context;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.DividerItemDecoration;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;

import com.album.janez.R;
import com.album.janez.album.activity.main.ActionBarEventListener;
import com.album.janez.album.activity.main.AlbumViewModel;
import com.album.janez.album.dialog.photo_detail.PhotoDetailDialog;
import com.album.janez.data.model.presentation.Album;

import butterknife.BindView;
import butterknife.ButterKnife;

public class PhotoGridFragment extends Fragment implements OnPhotoClickListener {

    @BindView(R.id.photo_list) RecyclerView photoListView;

    private PhotoGridAdapter photoGridAdapter;
    private AlbumViewModel albumViewModel;
    private ActionBarEventListener listener;

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        try {
            listener = (ActionBarEventListener) context;
        } catch (ClassCastException e) {
            throw new ClassCastException(context.toString()
                    + " must implement OnHeadlineSelectedListener");
        }
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        photoGridAdapter = new PhotoGridAdapter(this);
        albumViewModel = ViewModelProviders.of(requireActivity()).get(AlbumViewModel.class);
        albumViewModel.getSelectedAlbum().observe(this, new Observer<Album>() {
            @Override
            public void onChanged(@Nullable Album album) {
                if (album != null) {
                    photoGridAdapter.setPhotos(album.getPhotos());
                }
            }
        });
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.photo_grid_fragment, container, false);
        ButterKnife.bind(this, view);
        return view;
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        photoListView.setLayoutManager(new GridLayoutManager(requireContext(), 2));
        photoListView.setItemAnimator(new DefaultItemAnimator());
        photoListView.addItemDecoration(new DividerItemDecoration(requireContext(), LinearLayout.VERTICAL));
        photoListView.setAdapter(photoGridAdapter);
    }

    @Override
    public void onStart() {
        super.onStart();
        listener.showBackButton();
    }

    @Override
    public void onStop() {
        super.onStop();
        listener.hideBackButton();
    }

    @Override
    public void selected(int position) {
        albumViewModel.getSelectedPhoto().setValue(photoGridAdapter.getPhoto(position));

        PhotoDetailDialog dialog = new PhotoDetailDialog();
        dialog.show(getChildFragmentManager(), PhotoDetailDialog.TAG);
    }
}
