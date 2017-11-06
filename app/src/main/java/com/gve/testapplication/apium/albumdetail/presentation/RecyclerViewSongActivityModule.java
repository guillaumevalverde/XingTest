package com.gve.testapplication.apium.albumdetail.presentation;

import com.gve.testapplication.apium.albumlist.presentation.AlbumItemComparator;
import com.gve.testapplication.apium.albumlist.presentation.AlbumViewHolder;
import com.gve.testapplication.core.preconditions.AndroidPreconditions;
import com.gve.testapplication.core.recyclerview.ItemComparator;
import com.gve.testapplication.core.recyclerview.RecyclerViewAdapter;
import com.gve.testapplication.core.recyclerview.ViewHolderBinder;
import com.gve.testapplication.core.recyclerview.ViewHolderFactory;

import java.util.Map;

import dagger.Binds;
import dagger.Module;
import dagger.Provides;
import dagger.multibindings.IntKey;
import dagger.multibindings.IntoMap;

import static com.gve.testapplication.core.recyclerview.RecyclerViewConstant.ALBUM_CARD_TYPE;
import static com.gve.testapplication.core.recyclerview.RecyclerViewConstant.SONG_CARD_TYPE;

/**
 * Created by gve on 26/10/2017.
 */

@Module
public abstract class RecyclerViewSongActivityModule {

    @Provides
    static RecyclerViewAdapter provideRecyclerAdapter(ItemComparator itemComparator,
                                                      Map<Integer, ViewHolderFactory> factoryMap,
                                                      Map<Integer, ViewHolderBinder> binderMap,
                                                      AndroidPreconditions androidPreconditions) {
        return new RecyclerViewAdapter(itemComparator, factoryMap, binderMap, androidPreconditions);
    }

    @Provides
    static ItemComparator provideComparator() {
        return new SongItemComparator();
    }

    @Binds
    @IntoMap
    @IntKey(SONG_CARD_TYPE)
    abstract ViewHolderFactory
        provideSongCardHolderFactory(SongViewHolder.SongCardHolderFactory factory);

    @Binds
    @IntoMap
    @IntKey(SONG_CARD_TYPE)
    abstract ViewHolderBinder
        provideSongCardHolderBinder(SongViewHolder.SongCardHolderBinder binder);
}
