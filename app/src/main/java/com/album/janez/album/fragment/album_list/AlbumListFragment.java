package com.album.janez.album.fragment.album_list;

import android.arch.lifecycle.Observer;
import android.arch.lifecycle.ViewModelProviders;
import android.content.Context;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.DividerItemDecoration;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;

import com.album.janez.R;
import com.album.janez.album.activity.main.ActionBarEventListener;
import com.album.janez.album.activity.main.AlbumViewModel;
import com.album.janez.data.model.presentation.Album;
import com.album.janez.response.Response;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

public class AlbumListFragment extends Fragment implements OnAlbumClickListener {

    @BindView(R.id.album_list)
    RecyclerView albumListView;

    private AlbumListAdapter albumListAdapter;
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
        albumListAdapter = new AlbumListAdapter(this);

        albumViewModel = ViewModelProviders.of(requireActivity()).get(AlbumViewModel.class);
        albumViewModel.getData().observe(this, new Observer<Response<List<Album>>>() {
            @Override
            public void onChanged(@Nullable Response<List<Album>> response) {
                if (response != null) {
                    Response.State state = response.getState();

                    switch (state) {
                        case DATA:
                            albumListAdapter.setAlbums(response.getData());
                            break;
                    }
                }
            }
        });
    }

    @Override
    public void onStart() {
        super.onStart();
        listener.setTitle(getString(R.string.app_name));
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.album_list_fragment, container, false);
        ButterKnife.bind(this, view);
        return view;
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        albumListView.setLayoutManager(new LinearLayoutManager(requireContext()));
        albumListView.setItemAnimator(new DefaultItemAnimator());
        albumListView.addItemDecoration(new DividerItemDecoration(requireContext(), LinearLayout.VERTICAL));
        albumListView.setAdapter(albumListAdapter);
    }

    @Override
    public void selected(int position) {
        albumViewModel.setSelectedAlbum(albumListAdapter.getAlbum(position));
    }
}
