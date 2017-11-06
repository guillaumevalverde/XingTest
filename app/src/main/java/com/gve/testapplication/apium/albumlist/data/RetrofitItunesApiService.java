package com.gve.testapplication.apium.albumlist.data;

import io.reactivex.Single;
import retrofit2.http.GET;
import retrofit2.http.Query;

/**
 * Created by gve on 02/11/2017.
 */

public interface RetrofitItunesApiService {

    @GET("lookup?id=&entity=album")
    Single<DataListRaw> getListAlbum(@Query("id") String id);

    @GET("lookup?&entity=song")
    Single<DataListRaw> getSongs(@Query("id") long id);
}
