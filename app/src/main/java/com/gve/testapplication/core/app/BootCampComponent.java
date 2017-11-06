package com.gve.testapplication.core.app;

import android.app.Application;

import java.util.Map;

import javax.inject.Provider;
import javax.inject.Singleton;

import dagger.BindsInstance;
import dagger.Component;

/**
 * Created by gve.
 */

@Singleton
@Component(modules = {BootCampModule.class, NetworkModule.class,
        ActivityBindingModule.class, DataModule.class})
public interface BootCampComponent {

    @Component.Builder
    interface Builder {

        // @BindsInstance methods should be preferred to writing a @Module with constructor
        // arguments and immediately providing those values.
        @BindsInstance
        Builder application(Application application);
        BootCampComponent build();
    }

    Map<Class<?>, Provider<SubcomponentBuilder>> subComponentBuilders();

    void inject(BootCampApp app);

}
