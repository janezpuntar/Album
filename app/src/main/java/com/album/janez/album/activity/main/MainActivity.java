package com.album.janez.album.activity.main;

import android.arch.lifecycle.Observer;
import android.arch.lifecycle.ViewModelProviders;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;

import com.album.janez.R;
import com.album.janez.album.fragment.album_list.AlbumListFragment;
import com.album.janez.album.fragment.photo_grid.PhotoGridFragment;
import com.album.janez.data.model.presentation.Album;
import com.album.janez.response.Response;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

public class MainActivity extends AppCompatActivity {

    private AlbumViewModel albumViewModel;

    @BindView(R.id.loader_screen_layout)
    ViewGroup loaderLayout;
    @BindView(R.id.error_screen_layout)
    ViewGroup errorLayout;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        ButterKnife.bind(this);

        albumViewModel = ViewModelProviders.of(this).get(AlbumViewModel.class);
        albumViewModel.getData().observe(this, new Observer<Response<List<Album>>>() {
            @Override
            public void onChanged(@Nullable Response<List<Album>> response) {
                if (response != null) {
                    Response.State state = response.getState();

                    switch (state) {
                        case DATA:
                            getSupportFragmentManager().beginTransaction()
                                    .replace(R.id.fragment_container, new AlbumListFragment())
                                    .commit();

                            loaderLayout.setVisibility(View.GONE);
                            errorLayout.setVisibility(View.GONE);

                            break;
                        case LOADING:
                            loaderLayout.setVisibility(View.VISIBLE);
                            errorLayout.setVisibility(View.GONE);
                            Log.e("dsa", "loading");
                            break;
                        case ERROR:
                            loaderLayout.setVisibility(View.GONE);
                            errorLayout.setVisibility(View.VISIBLE);
                            break;
                    }
                }

            }
        });

        albumViewModel.getSelectedAlbum().observe(this, new Observer<Album>() {
            @Override
            public void onChanged(@Nullable Album album) {

                FragmentTransaction transaction = getSupportFragmentManager().beginTransaction();
                transaction.replace(R.id.fragment_container, new PhotoGridFragment());
                transaction.addToBackStack(null);
                transaction.commit();
            }
        });
    }


}
