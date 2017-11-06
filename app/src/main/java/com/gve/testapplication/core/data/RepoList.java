package com.gve.testapplication.core.data;

import io.reactivex.Completable;
import io.reactivex.Flowable;

public interface RepoList<Value> {

    Completable fetch();

    Flowable<Value> getStream() throws Exception;

}
