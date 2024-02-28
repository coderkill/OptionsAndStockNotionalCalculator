package com.server.enrichers;

public interface Enricher<T> {

    T enrich(T t);
}
