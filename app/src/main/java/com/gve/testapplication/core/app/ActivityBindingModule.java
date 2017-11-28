package com.gve.testapplication.core.app;

import com.gve.testapplication.ListOfRepoFeature.presentation.injection.ListRepoActivityComponent;
import com.gve.testapplication.apium.albumdetail.presentation.ListSongActivityComponent;
import com.gve.testapplication.apium.albumlist.presentation.ListAlbumActivityComponent;
import dagger.Binds;
import dagger.Module;
import dagger.multibindings.IntoMap;

/**
 * Created by gve.
 */

@Module(subcomponents = {
        ListAlbumActivityComponent.class,
        ListSongActivityComponent.class,
        ListRepoActivityComponent.class
})

public abstract class ActivityBindingModule {

    @Binds @IntoMap
    @DaggerUtil.SubcomponentKey(ListAlbumActivityComponent.Builder.class)
    public abstract SubcomponentBuilder listAlbumActivity(ListAlbumActivityComponent.Builder impl);

    @Binds @IntoMap
    @DaggerUtil.SubcomponentKey(ListSongActivityComponent.Builder.class)
    public abstract SubcomponentBuilder listSongActivity(ListSongActivityComponent.Builder impl);

    @Binds @IntoMap
    @DaggerUtil.SubcomponentKey(ListRepoActivityComponent.Builder.class)
    public abstract SubcomponentBuilder listRepoActivity(ListRepoActivityComponent.Builder impl);

}
