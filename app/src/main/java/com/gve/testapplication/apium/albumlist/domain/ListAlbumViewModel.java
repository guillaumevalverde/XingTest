package com.gve.testapplication.apium.albumlist.domain;


import android.support.annotation.NonNull;

import com.gve.testapplication.apium.albumlist.data.AlbumRepo;
import com.gve.testapplication.apium.albumlist.presentation.AlbumDisplayableMapper;
import com.gve.testapplication.core.recyclerview.DisplayableItem;

import java.util.List;

import javax.inject.Inject;

import io.reactivex.Completable;
import io.reactivex.Flowable;
import io.reactivex.schedulers.Schedulers;


/**
 * Created by gve on 26/10/2017.
 */

public class ListAlbumViewModel {

    private AlbumDisplayableMapper albumDisplayableMapper;
    private AlbumRepo albumRepo;


    @Inject
    public ListAlbumViewModel(@NonNull AlbumDisplayableMapper albumDisplayableMapper,
                              @NonNull AlbumRepo albumRepo) {

        this.albumDisplayableMapper = albumDisplayableMapper;
        this.albumRepo = albumRepo;
    }

    public Flowable<List<DisplayableItem>> getDisplayable() {
        return albumRepo.getStream()
                .map(albumDisplayableMapper);
    }

    public Completable fetch() {
        return albumRepo.fetch().subscribeOn(Schedulers.io());
    }
}
