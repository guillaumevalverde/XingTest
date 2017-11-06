package com.gve.testapplication.core.data;

import android.support.annotation.NonNull;
import android.support.v4.util.Pair;

import java.util.List;

import io.reactivex.Flowable;

public interface ReactiveStoreSingular<Value> {

    void storeSingular(@NonNull Value model) throws Exception;

    Flowable<Pair<Long, Value>> getSingular(@NonNull String key);
}
