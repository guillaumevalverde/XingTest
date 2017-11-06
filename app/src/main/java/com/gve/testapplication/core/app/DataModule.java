package com.gve.testapplication.core.app;

import android.content.Context;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.gve.testapplication.BuildConfig;
import com.gve.testapplication.apium.albumdetail.data.Song;
import com.gve.testapplication.apium.albumdetail.data.SongRepo;
import com.gve.testapplication.apium.albumlist.data.Album;
import com.gve.testapplication.apium.albumlist.data.AlbumRepo;
import com.gve.testapplication.core.data.ReactiveStoreSingular;
import com.gve.testapplication.core.data.roomjsonstore.RoomJsonStore;
import com.gve.testapplication.core.data.AppDataBase;
import com.gve.testapplication.core.data.ReactiveStore;
import com.gve.testapplication.core.injection.qualifiers.ForApplication;
import com.squareup.picasso.Picasso;

import java.util.List;

import javax.inject.Singleton;

import dagger.Module;
import dagger.Provides;

/**
 * Created by gve.
 */

@Module
final class DataModule {

    //Picasso library handle the caching of the image in the disk or in the memory.
    @Provides
    @Singleton
    Picasso providePicasso(@ForApplication Context context) {
        Picasso picasso = new Picasso.Builder(context)
                .indicatorsEnabled(BuildConfig.DEBUG)
                .loggingEnabled(BuildConfig.DEBUG)
                .listener((picasso1, uri, exception) -> exception.printStackTrace())
                .build();
        Picasso.setSingletonInstance(picasso);
        return picasso;
    }

    @Provides
    @Singleton
    ReactiveStoreSingular<List<Album>> provideRoomStore(@ForApplication Context context,
                                                        Gson gson) {
        return new RoomJsonStore<List<Album>>(
                AppDataBase.getDatabase(context),
                AlbumRepo.getKeyFunction(),
                json -> gson.fromJson(json, new TypeToken<List<Album>>(){ }.getType()),
                gson::toJson,
                () -> "[]");
    }

    @Provides
    @Singleton
    ReactiveStoreSingular<List<Song>> provideRoomSongStore(@ForApplication Context context,
                                                   Gson gson) {
        return new RoomJsonStore<List<Song>>(
                AppDataBase.getDatabase(context),
                SongRepo.getKeyFunction(),
                json -> gson.fromJson(json, new TypeToken<List<Song>>(){ }.getType()),
                gson::toJson,
                () -> "[]");
    }

}
