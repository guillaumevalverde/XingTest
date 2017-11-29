package com.gve.testapplication.core.app;

import com.gve.testapplication.ListOfRepoFeature.presentation.injection.ListRepoActivityComponent;
import dagger.Binds;
import dagger.Module;
import dagger.multibindings.IntoMap;

/**
 * Created by gve.
 */

@Module(subcomponents = {
        ListRepoActivityComponent.class
})

public abstract class ActivityBindingModule {

    @Binds @IntoMap
    @DaggerUtil.SubcomponentKey(ListRepoActivityComponent.Builder.class)
    public abstract SubcomponentBuilder listRepoActivity(ListRepoActivityComponent.Builder impl);

}
