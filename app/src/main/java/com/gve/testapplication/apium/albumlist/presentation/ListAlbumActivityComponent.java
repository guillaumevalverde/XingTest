package com.gve.testapplication.apium.albumlist.presentation;

import com.gve.testapplication.core.app.SubcomponentBuilder;
import com.gve.testapplication.core.injection.scopes.ActivityScope;

import dagger.Subcomponent;

/**
 * Created by gve on 26/10/2017.
 */

@ActivityScope
@Subcomponent(modules = {ListAlbumActivityModule.class, RecyclerViewActivityModule.class})
public interface ListAlbumActivityComponent {

    @Subcomponent.Builder
    interface Builder extends SubcomponentBuilder<ListAlbumActivityComponent> {
        Builder activityModule(ListAlbumActivityModule module);
    }

    void inject(ListAlbumActivity activity);
}
