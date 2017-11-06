package com.gve.testapplication.core.data;

public interface ReactiveStore<Value> extends  ReactiveStoreAll<Value>,
        ReactiveStoreSingular<Value> { }
