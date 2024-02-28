package com.server.service;

public interface CustomSpringService<S, T> {
    S execute(T t);
}
