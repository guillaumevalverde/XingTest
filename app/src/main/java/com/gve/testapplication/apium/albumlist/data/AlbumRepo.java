package com.gve.testapplication.apium.albumlist.data;

import android.support.annotation.NonNull;

import com.gve.testapplication.core.data.ReactiveStore;
import com.gve.testapplication.core.data.ReactiveStoreSingular;
import com.gve.testapplication.core.data.RepoList;

import java.util.Date;
import java.util.List;

import javax.inject.Inject;

import io.reactivex.Completable;
import io.reactivex.Flowable;
import io.reactivex.disposables.CompositeDisposable;
import io.reactivex.functions.Function;

/**
 * Created by gve on 01/11/2017.
 */

public class AlbumRepo implements RepoList<List<Album>> {

    private ItunesNetworkService fetcher;
    private ReactiveStoreSingular<List<Album>> reactiveStore;
    private static final long TIME_AMOUNT_VALIDATE = 1 * 60 * 60 * 1000;
    private CompositeDisposable disposable = new CompositeDisposable();

    @Inject
    public AlbumRepo(@NonNull ItunesNetworkService fetcher,
                     @NonNull ReactiveStoreSingular<List<Album>> reactiveStore) {
        this.fetcher = fetcher;
        this.reactiveStore = reactiveStore;
    }

    @Override
    public Completable fetch() {
        return fetcher.fetchRawListData()
                .flatMap(MapperAlbum.INSTANCE.getMapperRawToAlbumList())
                // put mapped objects in store
                .doOnSuccess(reactiveStore::storeSingular)
                .toCompletable();
    }

    @Override
    public Flowable<List<Album>> getStream() {

            return Flowable.just("start")
                    .map(d -> getKeyFunction().apply(null))
                    .flatMap(key -> reactiveStore.<Long, List<Album>>getSingular(key))
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

    public static boolean isDataDeprecated(long time) {
        return ((new Date()).getTime() - time) > TIME_AMOUNT_VALIDATE;
    }

    public static Function<List<Album>, String> getKeyFunction() {
        return albums -> "ALBUM_LIST";
    }
}
