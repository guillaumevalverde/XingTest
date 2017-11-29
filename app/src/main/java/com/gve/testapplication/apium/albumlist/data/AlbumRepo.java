package com.gve.testapplication.apium.albumlist.data;

import android.support.annotation.NonNull;

import com.gve.testapplication.core.data.ReactiveStoreSingular;
import com.gve.testapplication.core.data.RepoList;

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

public class AlbumRepo implements RepoList<List<Album>> {

    private static final long TIME_AMOUNT_VALIDATE = 60 * 60 * 1000;
    private ItunesNetworkService fetcher;
    private ReactiveStoreSingular<List<Album>> reactiveStore;
    private CompositeDisposable disposable = new CompositeDisposable();

    @Inject
    public AlbumRepo(@NonNull ItunesNetworkService fetcher,
                     @NonNull ReactiveStoreSingular<List<Album>> reactiveStore) {
        this.fetcher = fetcher;
        this.reactiveStore = reactiveStore;
    }

    public static boolean isDataDeprecated(long time) {
        return ((new Date()).getTime() - time) > TIME_AMOUNT_VALIDATE;
    }

    public static BiFunction<List<Album>, Long, String> getKeyFunction() {
        return (albums, id) -> "ALBUM_LIST";
    }

    @Override
    public Completable fetch() {
        return fetcher.fetchRawListData()
                .flatMap(MapperAlbum.INSTANCE.getMapperRawToAlbumList())
                // put mapped objects in store
                .doOnSuccess(list -> reactiveStore.storeSingular(list, 0))
                .toCompletable();
    }

    @Override
    public Flowable<List<Album>> getStream() {
        return Flowable.just("start")
                .map(d -> getKeyFunction().apply(null, 0L))
                .flatMap(key -> reactiveStore.<Long, List<Album>>getSingularStream(key))
                .doOnNext(pair -> {
                    if (isDataDeprecated(pair.first)) {
                        disposable.add(fetch()
                                .subscribe(() -> System.out.println("fetch ListArticle"),
                                        error -> System.out.println("error fetch ListArticle"
                                                + error.getMessage())));
                    }
                })
                .map(p -> p.second);

    }
}
