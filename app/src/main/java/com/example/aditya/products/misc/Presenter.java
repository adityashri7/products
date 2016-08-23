package com.example.aditya.products.misc;

/**
 * Created by trust on 8/1/2016.
 */
public abstract class Presenter<T> {
    private boolean isAttached;
    private T model;

    public void bind(T model) {
        if (!isAttached) {
            // We can assume the previous model
            // doesn't need to be dropped and
            // we do no need to attach this one
            this.model = model;
            return;
        }

        if (this.model != null) {
            drop(this.model);
        }

        this.model = model;
        if (model != null) {
            attach(model);
        }
    }

    public void attach() {
        isAttached = true;
        if (model != null) {
            attach(model);
        }
    }

    public void detach() {
        if (model != null) {
            drop(model);
        }
        isAttached = false;
    }


    protected abstract void attach(T model);

    protected abstract void drop(T model);

    public T model() {
        return model;
    }
}
