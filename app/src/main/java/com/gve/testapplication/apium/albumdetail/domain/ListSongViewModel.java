package com.gve.testapplication.apium.albumdetail.domain;


import android.support.annotation.NonNull;

import com.gve.testapplication.apium.albumdetail.data.SongRepo;
import com.gve.testapplication.apium.albumdetail.presentation.SongDisplayableMapper;
import com.gve.testapplication.core.recyclerview.DisplayableItem;

import java.util.List;

import javax.inject.Inject;

import io.reactivex.Completable;
import io.reactivex.Flowable;
import io.reactivex.schedulers.Schedulers;


/**
 * Created by gve on 26/10/2017.
 */

public class ListSongViewModel {

    private SongDisplayableMapper songDisplayableMapper;
    private SongRepo songRepo;


    @Inject
    public ListSongViewModel(@NonNull SongDisplayableMapper songDisplayableMapper,
                             @NonNull SongRepo songRepo) {

        this.songDisplayableMapper = songDisplayableMapper;
        this.songRepo = songRepo;
    }

    public Flowable<List<DisplayableItem>> getDisplayable(long key) {
        return songRepo.getStream(key)
                .map(songDisplayableMapper);
    }

    public Completable fetch(long key) {
        return songRepo.fetch(key).subscribeOn(Schedulers.io());
    }
}
