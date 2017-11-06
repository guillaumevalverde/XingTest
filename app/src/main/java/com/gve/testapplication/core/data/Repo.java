package com.gve.testapplication.core.data;

import io.reactivex.Completable;
import io.reactivex.Flowable;
import io.reactivex.Single;

public interface Repo<Value> {

    Completable fetch(int id);

    Flowable<Value> getStream(int id);

    Single<Value> getSingle(int id);

    void store(Value v);

    void delete(Value v);
}
