package com.gve.testapplication.apium.albumlist.presentation;

import android.content.Context;
import android.os.Bundle;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.widget.TextView;

import com.gve.testapplication.R;
import com.gve.testapplication.apium.albumlist.domain.ListAlbumViewModel;
import com.gve.testapplication.core.app.BootCampApp;
import com.gve.testapplication.core.injection.qualifiers.ForActivity;
import com.gve.testapplication.core.recyclerview.RecyclerViewAdapter;

import javax.inject.Inject;

import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.CompositeDisposable;
import io.reactivex.schedulers.Schedulers;

/**
 * Created by gve on 31/10/2017.
 */

public class ListAlbumActivity extends AppCompatActivity {
    public static final String TAG = ListAlbumActivity.class.getSimpleName();

    @Inject
    ListAlbumViewModel listViewModel;

    @Inject
    RecyclerViewAdapter adapter;

    private CompositeDisposable disposable = new CompositeDisposable();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        ListAlbumActivityComponent.Builder builder = (ListAlbumActivityComponent.Builder)
                (((BootCampApp) getApplication()).getComponent())
                        .subComponentBuilders()
                        .get(ListAlbumActivityComponent.Builder.class)
                        .get();
        builder.activityModule(new ListAlbumActivityModule(this)).build().inject(this);

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_list_article);
        SwipeRefreshLayout swipeRefreshLayout = findViewById(R.id.list_article_pull_to_refresh);
        swipeRefreshLayout.setOnRefreshListener(() -> {
                    disposable.add(
                            listViewModel.fetch()
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
        titleToolBar.setText("APIUM");

        RecyclerView recyclerView = findViewById(R.id.recycler_view);

        RecyclerView.LayoutManager mLayoutManager
                = new LinearLayoutManager(getApplicationContext());
        recyclerView.setLayoutManager(mLayoutManager);
        recyclerView.setItemAnimator(new DefaultItemAnimator());
        recyclerView.setAdapter(adapter);
        disposable.add(
                listViewModel.getDisplayable()
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
