package com.gve.testapplication.core.app;

import android.app.Application;
import android.content.Context;
import android.support.annotation.NonNull;

import com.facebook.stetho.Stetho;
import com.gve.testapplication.BuildConfig;

/**
 * Created by gve.
 */

public class BootCampApp extends Application {

    protected BootCampComponent applicationComponent;

    public static BootCampComponent get(Context context) {
        return (BootCampComponent) context.getApplicationContext();
    }

    @NonNull
    public BootCampComponent getComponent() {
        if (applicationComponent == null) {
            applicationComponent = DaggerBootCampComponent.builder().application(this).build();
        }
        return applicationComponent;
    }

    @Override
    public void onCreate() {
        getComponent().inject(this);
        super.onCreate();
        if (BuildConfig.DEBUG)
            Stetho.initializeWithDefaults(this);
    }
}
