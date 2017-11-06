package com.gve.testapplication.core.app;

import com.google.gson.Gson;
import com.gve.testapplication.InstrumentationModule;
import com.gve.testapplication.apium.albumlist.data.RetrofitItunesApiService;
import com.jakewharton.retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory;

import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.util.Set;

import javax.inject.Named;
import javax.inject.Qualifier;
import javax.inject.Singleton;

import dagger.Module;
import dagger.Provides;
import okhttp3.Interceptor;
import okhttp3.OkHttpClient;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

@Module(includes = {GsonModule.class, InstrumentationModule.class})
public final class NetworkModule {

    private static final String API_ITUNES_URL = "API_ITUNES_URL";

    @Qualifier
    @Retention(RetentionPolicy.RUNTIME)
    public @interface AppInterceptor {
    }

    @Qualifier
    @Retention(RetentionPolicy.RUNTIME)
    public @interface NetworkInterceptor {
    }

    @Qualifier
    @Retention(RetentionPolicy.RUNTIME)
    public @interface FutureWorkshop {
    }

    @Qualifier
    @Retention(RetentionPolicy.RUNTIME)
    public @interface Itunes {
    }

    @Provides
    @Singleton
    @Itunes
    static Retrofit provideItunesApi(@Named(API_ITUNES_URL) String baseUrl, Gson gson,
                                     OkHttpClient client) {
        return new Retrofit.Builder().addCallAdapterFactory(RxJava2CallAdapterFactory.create())
                .addConverterFactory(GsonConverterFactory.create(gson))
                .client(client)
                .baseUrl(baseUrl)
                .build();
    }

    @Provides
    @Named(API_ITUNES_URL)
    static String provideItunesUrl() {
        return AppConstUtils.ITUNES_API_URL;
    }

    @Provides
    @Singleton
    static OkHttpClient provideApiOkHttpClient(@AppInterceptor Set<Interceptor> appInterceptor,
                                               @NetworkInterceptor Set<Interceptor>
                                                       networkInterceptor) {
        OkHttpClient.Builder okBuilder = new OkHttpClient.Builder();
        okBuilder.interceptors().addAll(appInterceptor);
        okBuilder.networkInterceptors().addAll(networkInterceptor);

        return okBuilder.build();
    }

    @Provides
    @Singleton
    static RetrofitItunesApiService provideItunesApiNetworkService(@Itunes Retrofit retrofit) {
        return retrofit.create(RetrofitItunesApiService.class);
    }

}
