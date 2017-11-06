package com.gve.testapplication.core.data;

import android.support.annotation.NonNull;
import android.support.v4.util.Pair;

import java.util.List;

import io.reactivex.Flowable;

public interface ReactiveStoreAll<Value> {

    void storeAll(@NonNull List<Value> modelList) throws Exception;

    void replaceAll(@NonNull List<Value> modelList) throws Exception;

    Flowable<List<Pair<Long, Value>>> getAll();
}
