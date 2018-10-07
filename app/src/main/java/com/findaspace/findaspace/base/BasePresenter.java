package com.findaspace.findaspace.base;

public interface BasePresenter<V extends BaseView> {
    public void attachView(V view);

    public void detachView();
}
