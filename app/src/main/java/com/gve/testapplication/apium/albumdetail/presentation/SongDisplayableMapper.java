package com.gve.testapplication.apium.albumdetail.presentation;

import android.support.annotation.NonNull;

import com.gve.testapplication.apium.albumdetail.data.Song;
import com.gve.testapplication.apium.albumlist.data.Album;
import com.gve.testapplication.core.recyclerview.DisplayableItem;
import com.gve.testapplication.core.recyclerview.RecyclerViewConstant;

import java.util.List;

import javax.inject.Inject;

import io.reactivex.Observable;
import io.reactivex.functions.Function;

import static com.gve.testapplication.core.recyclerview.DisplayableItem.toDisplayableItem;

/**
 * Created by gve on 27/10/2017.
 */

public class SongDisplayableMapper implements Function<List<Song>, List<DisplayableItem>> {

    @Inject
    SongDisplayableMapper() { }

    @Override
    public List<DisplayableItem> apply(@NonNull final List<Song> song) throws Exception {
        return Observable.fromIterable(song)
                .map(this::wrapInDisplayableItem)
                .toList()
                .blockingGet();
    }

    private DisplayableItem wrapInDisplayableItem(Song viewEntity) {
        return toDisplayableItem(viewEntity, RecyclerViewConstant.SONG_CARD_TYPE);
    }
}
