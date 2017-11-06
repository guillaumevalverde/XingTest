package com.gve.testapplication.core.app;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

import dagger.MapKey;

/**
 * Created by gve.
 */

public class DaggerUtil {

    // Needed only to create the above mapping
    @MapKey
    @Target({ElementType.METHOD}) @Retention(RetentionPolicy.RUNTIME)
    public @interface SubcomponentKey {
        Class<?> value();
    }
}
