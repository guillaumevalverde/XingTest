package com.gve.testapplication.apium.albumdetail.data;

import android.support.annotation.NonNull;

import com.gve.testapplication.apium.albumlist.data.ItunesNetworkService;
import com.gve.testapplication.core.data.ReactiveStoreSingular;

import java.util.Date;
import java.util.List;

import javax.inject.Inject;

import io.reactivex.Completable;
import io.reactivex.Flowable;
import io.reactivex.disposables.CompositeDisposable;
import io.reactivex.functions.BiFunction;


/**
 * Created by gve on 01/11/2017.
 */

public class SongRepo {

    private static final long TIME_AMOUNT_VALIDATE = 1 * 60 * 60 * 1000;
    private ItunesNetworkService fetcher;
    private ReactiveStoreSingular<List<Song>> reactiveStore;
    private CompositeDisposable disposable = new CompositeDisposable();

    @Inject
    public SongRepo(@NonNull ItunesNetworkService fetcher,
                    @NonNull ReactiveStoreSingular<List<Song>> reactiveStore) {
        this.fetcher = fetcher;
        this.reactiveStore = reactiveStore;
    }

    public static boolean isDataDeprecated(long time) {
        return ((new Date()).getTime() - time) > TIME_AMOUNT_VALIDATE;
    }

    public static BiFunction<List<Song>, Long, String> getKeyFunction() {
        return (songs, id) -> getKeyFromAlbum(songs.get(0).getAlbumId());
    }

    public static String getKeyFromAlbum(long albumId) {
        return "SongAlbum: " + albumId;
    }

    public Completable fetch(long id) {
        return fetcher.fetchSongRawListData(id)
                .flatMap(MapperSong.INSTANCE.getMapperRawToSongList())
                // put mapped objects in store
                .doOnSuccess(list -> reactiveStore.storeSingular(list, id))
                .toCompletable();
    }

    public Flowable<List<Song>> getStream(long key) {
        return reactiveStore.<Long, List<Song>>getSingularStream(getKeyFromAlbum(key))
                .doOnNext(pair -> {
                    if (isDataDeprecated(pair.first)) {
                        disposable.add(fetch(key)
                                .subscribe(() -> System.out.println("fetch ListArticle"),
                                        error -> System.out.println("error fetch ListArticle"
                                                + error.getMessage())));
                    }
                })
                .map(p -> p.second);

    }

}
