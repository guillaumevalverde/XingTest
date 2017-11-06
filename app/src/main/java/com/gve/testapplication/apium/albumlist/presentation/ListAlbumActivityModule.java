package com.gve.testapplication.apium.albumlist.presentation;

import android.app.Activity;
import com.gve.testapplication.core.injection.scopes.ActivityScope;

import dagger.Module;
import dagger.Provides;

/**
 * Created by gve on 26/10/2017.
 */

@Module
public class ListAlbumActivityModule {


    private Activity activity;

    public ListAlbumActivityModule(Activity activity) {
        this.activity = activity;
    }

    @Provides
    @ActivityScope
    public Activity getActivity() {
        return activity;
    }
}
