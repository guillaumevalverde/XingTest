package com.gve.testapplication.apium.albumlist.data;

import java.util.List;

import javax.inject.Inject;

import io.reactivex.Single;
import io.reactivex.schedulers.Schedulers;

/**
 * Created by gve on 01/11/2017.
 */

public class ItunesNetworkService {

    private RetrofitItunesApiService service;
    private static final String ARTIST_ID = "909253";

    @Inject
    public ItunesNetworkService(RetrofitItunesApiService service) {
        this.service = service;
     }

    public Single<List<DataRaw>> fetchRawListData() {
        return service.getListAlbum(ARTIST_ID)
                .subscribeOn(Schedulers.io())
                .map(file -> file.getDataRawList());
    }

    public Single<List<DataRaw>> fetchSongRawListData(long id) {
        return service.getSongs(id)
                .subscribeOn(Schedulers.io())
                .map(file -> file.getDataRawList());
    }
}
