package com.gve.testapplication.apium.albumdetail.presentation;
import com.gve.testapplication.core.app.SubcomponentBuilder;
import com.gve.testapplication.core.injection.scopes.ActivityScope;

import dagger.Subcomponent;

/**
 * Created by gve on 26/10/2017.
 */

@ActivityScope
@Subcomponent(modules = {ListSongActivityModule.class, RecyclerViewSongActivityModule.class})
public interface ListSongActivityComponent {

    @Subcomponent.Builder
    interface Builder extends SubcomponentBuilder<ListSongActivityComponent> {
        Builder activityModule(ListSongActivityModule module);
    }

    void inject(ListSongActivity activity);
}
