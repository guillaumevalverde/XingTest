package com.gve.testapplication.apium.albumlist.presentation;

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

/**
 * Created by gve on 26/10/2017.
 */

@Module
public abstract class RecyclerViewActivityModule {

    @Provides
    static RecyclerViewAdapter provideRecyclerAdapter(ItemComparator itemComparator,
                                                      Map<Integer, ViewHolderFactory> factoryMap,
                                                      Map<Integer, ViewHolderBinder> binderMap,
                                                      AndroidPreconditions androidPreconditions) {
        return new RecyclerViewAdapter(itemComparator, factoryMap, binderMap, androidPreconditions);
    }

    @Provides
    static ItemComparator provideComparator() {
        return new AlbumItemComparator();
    }

    @Binds
    @IntoMap
    @IntKey(ALBUM_CARD_TYPE)
    abstract ViewHolderFactory
        provideUserCardHolderFactory(AlbumViewHolder.AlbumCardHolderFactory factory);

    @Binds
    @IntoMap
    @IntKey(ALBUM_CARD_TYPE)
    abstract ViewHolderBinder
        provideUserCardHolderBinder(AlbumViewHolder.AlbumCardHolderBinder binder);
}
