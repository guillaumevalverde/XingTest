package com.gve.testapplication.ListOfRepoFeature.presentation;

import android.content.Context;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.DividerItemDecoration;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.widget.TextView;

import com.gve.testapplication.ListOfRepoFeature.domain.ListRepoViewModel;
import com.gve.testapplication.ListOfRepoFeature.presentation.injection.ListRepoActivityComponent;
import com.gve.testapplication.ListOfRepoFeature.presentation.injection.ListRepoActivityModule;
import com.gve.testapplication.R;
import com.gve.testapplication.apium.albumlist.data.Album;
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

public class ListRepositoryActivity extends AppCompatActivity {
    public static final String TAG = ListRepositoryActivity.class.getSimpleName();

    @Inject
    ListRepoViewModel listViewModel;

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
        ListRepoActivityComponent.Builder builder = (ListRepoActivityComponent.Builder)
                (((BootCampApp) getApplication()).getComponent())
                        .subComponentBuilders()
                        .get(ListRepoActivityComponent.Builder.class)
                        .get();
        builder.activityModule(new ListRepoActivityModule(this)).build().inject(this);

        super.onCreate(savedInstanceState);
        setContentView(R.layout.repository_list);


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
                listViewModel.getDisplayableList(1)
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
