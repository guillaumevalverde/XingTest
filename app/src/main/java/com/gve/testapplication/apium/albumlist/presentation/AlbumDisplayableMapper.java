package com.gve.testapplication.apium.albumlist.presentation;

import android.support.annotation.NonNull;

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

public class AlbumDisplayableMapper implements Function<List<Album>, List<DisplayableItem>> {

    @Inject
    AlbumDisplayableMapper() { }

    @Override
    public List<DisplayableItem> apply(@NonNull final List<Album> albums) throws Exception {
        return Observable.fromIterable(albums)
                .map(this::wrapInDisplayableItem)
                .toList()
                .blockingGet();
    }

    private DisplayableItem wrapInDisplayableItem(Album viewEntity) {
        return toDisplayableItem(viewEntity, RecyclerViewConstant.ALBUM_CARD_TYPE);
    }
}
