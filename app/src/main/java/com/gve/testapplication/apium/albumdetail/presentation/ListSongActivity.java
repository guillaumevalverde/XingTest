package com.gve.testapplication.apium.albumdetail.presentation;

import android.content.Context;
import android.os.Bundle;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.DividerItemDecoration;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.widget.ImageView;
import android.widget.TextView;

import com.gve.testapplication.R;
import com.gve.testapplication.apium.albumdetail.domain.ListSongViewModel;
import com.gve.testapplication.apium.albumlist.data.Album;
import com.gve.testapplication.apium.albumlist.data.ConstItunes;
import com.gve.testapplication.core.PicassoUtils;
import com.gve.testapplication.core.app.BootCampApp;
import com.gve.testapplication.core.injection.qualifiers.ForActivity;
import com.gve.testapplication.core.recyclerview.RecyclerViewAdapter;
import com.squareup.picasso.Picasso;

import javax.inject.Inject;

import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.CompositeDisposable;
import io.reactivex.schedulers.Schedulers;

/**
 * Created by gve on 31/10/2017.
 */

public class ListSongActivity extends AppCompatActivity {
    public static final String TAG = ListSongActivity.class.getSimpleName();

    @Inject
    ListSongViewModel listViewModel;

    @Inject
    RecyclerViewAdapter adapter;

    @Inject
    @ForActivity
    Context context;

    @Inject
    Picasso picasso;

    private CompositeDisposable disposable = new CompositeDisposable();
    private Album album;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        ListSongActivityComponent.Builder builder = (ListSongActivityComponent.Builder)
                (((BootCampApp) getApplication()).getComponent())
                        .subComponentBuilders()
                        .get(ListSongActivityComponent.Builder.class)
                        .get();
        builder.activityModule(new ListSongActivityModule(this)).build().inject(this);

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_song_list);

        TextView albumNameTV = findViewById(R.id.songs_album_title);
        TextView artistNameTV = findViewById(R.id.songs_album_artist_name);
        TextView trackCountTV = findViewById(R.id.songs_album_count_track);
        ImageView imageIV = findViewById(R.id.songs_album_image);

        album  = new Album(
        getIntent().getLongExtra(ConstItunes.ALBUM_TYPE_ID, 0L),
        getIntent().getStringExtra(ConstItunes.ALBUM_TYPE_NAME),
        getIntent().getStringExtra(ConstItunes.ALBUM_TYPE_ARTIST_NAME),
        getIntent().getStringExtra(ConstItunes.ALBUM_TYPE_THUMBNAIL),
        getIntent().getIntExtra(ConstItunes.ALBUM_TYPE_TRACK, 0));



        albumNameTV.setText(album.getName());
        artistNameTV.setText(album.getArtistName());
        trackCountTV.setText("" + album.getTrackCount());
        PicassoUtils.showImageWithPicasso(picasso, imageIV, album.getThumbnail());

        SwipeRefreshLayout swipeRefreshLayout = findViewById(R.id.list_article_pull_to_refresh);
        swipeRefreshLayout.setOnRefreshListener(() -> {
                    disposable.add(
                            listViewModel.fetch(album.getId())
                                    .observeOn(AndroidSchedulers.mainThread())
                                    .subscribe(
                                            () -> {
                                                Log.v(TAG, "finish fetch");
                                                swipeRefreshLayout.setRefreshing(false);
                                            },
                                            error -> {
                                                Log.e(TAG, "error fetch");
                                                swipeRefreshLayout.setRefreshing(false);
                                            }));
        });

        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayShowTitleEnabled(false);

        TextView titleToolBar = toolbar.findViewById(R.id.toolbar_title);
        titleToolBar.setText("ALBUM");

        RecyclerView recyclerView = findViewById(R.id.recycler_view);

        RecyclerView.LayoutManager mLayoutManager
                = new LinearLayoutManager(getApplicationContext());

        DividerItemDecoration dividerItemDecoration = new DividerItemDecoration(recyclerView.getContext(),
                DividerItemDecoration.VERTICAL);

        recyclerView.addItemDecoration(dividerItemDecoration);
        recyclerView.setLayoutManager(mLayoutManager);
        recyclerView.setItemAnimator(new DefaultItemAnimator());
        recyclerView.setAdapter(adapter);
        disposable.add(
                listViewModel.getDisplayable(album.getId())
                        .subscribeOn(Schedulers.computation())
                        .observeOn(AndroidSchedulers.mainThread())
                        .subscribe(users -> {
                            Log.v(TAG, "update adapter");
                            adapter.update(users);
                        }, e -> Log.e(TAG, e.getMessage())));

    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        disposable.clear();
    }
}
