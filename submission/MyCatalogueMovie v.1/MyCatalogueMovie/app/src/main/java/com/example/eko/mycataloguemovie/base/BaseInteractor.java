package com.example.eko.mycataloguemovie.base;

public class BaseInteractor<T> implements MvpInteractor<T> {

    private T service;
    public BaseInteractor(T service){
        this.service = service;
    }

    @Override
    public T getService(){
        return service;
    }
}
