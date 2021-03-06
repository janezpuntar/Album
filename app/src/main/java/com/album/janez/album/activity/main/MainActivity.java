package com.album.janez.album.activity.main;

import android.app.SearchManager;
import android.arch.lifecycle.Observer;
import android.arch.lifecycle.ViewModelProviders;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.SearchView;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.album.janez.R;
import com.album.janez.album.fragment.album_list.AlbumListFragment;
import com.album.janez.album.fragment.photo_grid.PhotoGridFragment;
import com.album.janez.data.model.presentation.Album;
import com.album.janez.response.Response;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class MainActivity extends AppCompatActivity implements ActionBarEventListener {

    private AlbumViewModel albumViewModel;
    private MenuItem search;
    private SearchView searchView;

    @BindView(R.id.loader_screen_layout)
    ViewGroup loaderLayout;
    @BindView(R.id.error_screen_layout)
    ViewGroup errorLayout;
    @BindView(R.id.tv_error_details)
    TextView tvErrorDetails;

    @BindView(R.id.toolbar)
    Toolbar toolbar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        ButterKnife.bind(this);

        setSupportActionBar(toolbar);

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
                            break;
                        case ERROR:
                            loaderLayout.setVisibility(View.GONE);
                            errorLayout.setVisibility(View.VISIBLE);
                            tvErrorDetails.setText(response.getErrorMessage());
                            break;
                    }
                }

            }
        });

        albumViewModel.getSelectedAlbum().observe(this, new Observer<Album>() {
            @Override
            public void onChanged(@Nullable Album album) {

                FragmentTransaction transaction = getSupportFragmentManager().beginTransaction();
                transaction.replace(R.id.fragment_container, PhotoGridFragment.getInstance());
                transaction.addToBackStack(null);
                transaction.commit();
            }
        });
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.main_menu, menu);

        search = menu.findItem(R.id.action_search);
        searchView = (SearchView) search.getActionView();
        SearchManager searchManager = (SearchManager) getSystemService(SEARCH_SERVICE);
        searchView.setSearchableInfo(searchManager.getSearchableInfo(getComponentName()));

        searchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String query) {
                return false;
            }

            @Override
            public boolean onQueryTextChange(String newText) {
                albumViewModel.setSearchQuery(newText);
                return false;
            }
        });
        return true;
    }

    @OnClick(R.id.btn_retry)
    public void retry() {
        albumViewModel.retry();
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case android.R.id.home:
                onBackPressed();
                return true;
        }

        return super.onOptionsItemSelected(item);
    }

    @Override
    public void setTitle(String title) {
        if (getSupportActionBar() != null) {
            getSupportActionBar().setTitle(title);
        }
    }

    @Override
    public void showBackButton() {
        if (getSupportActionBar() != null) {
            getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        }
    }

    @Override
    public void hideBackButton() {
        if (getSupportActionBar() != null) {
            getSupportActionBar().setDisplayHomeAsUpEnabled(false);
        }
    }

    @Override
    public void closeSearch() {
        if (search != null) {
            search.collapseActionView();
        }

        if (searchView != null) {
            searchView.setQuery("", true);
        }
    }
}
