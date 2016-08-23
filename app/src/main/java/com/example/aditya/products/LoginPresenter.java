package com.example.aditya.products;

import android.content.Context;
import android.widget.EditText;

import com.example.aditya.products.misc.Presenter;
import com.jakewharton.rxbinding.view.RxView;

import rx.Observable;
import rx.android.schedulers.AndroidSchedulers;
import rx.functions.Action1;
import rx.subscriptions.CompositeSubscription;

/**
 * Created by trust on 8/1/2016.
 */
public abstract class LoginPresenter extends Presenter<LoginModel> {
    LoginView view;
    Context context;
    private CompositeSubscription running;

    private EditText userField;
    private EditText passField;

    public LoginPresenter(LoginView view, Context context){
        this.view = view;
        this.context = context;
        userField = (EditText) view.findViewById(R.id.username_field);
        passField = (EditText) view.findViewById(R.id.password_field);

    }


        @Override
    protected void attach(final LoginModel model) {
            running = new CompositeSubscription();
            running.add(RxView.clicks(view.findViewById(R.id.register_button))
                    .observeOn(AndroidSchedulers.mainThread())
                    .subscribe(new Action1<Void>() {
                        @Override
                        public void call(Void aVoid) {
                            registerClicked();
                        }
                    })

            );
            running.add(RxView.clicks(view.findViewById(R.id.login_button))
            .observeOn(AndroidSchedulers.mainThread())
                    .subscribe(new Action1<Void>() {
                        @Override
                        public void call(Void aVoid) {
                            if (!(model.login(userField.getText().toString(), passField.getText().toString()))){
                                failure();
                            }
                            else{
                                success(userField.getText().toString());
                            }
                        }
                    })
            );

            running.add(RxView.clicks(view.findViewById(R.id.map_button))
            .observeOn(AndroidSchedulers.mainThread())
            .subscribe(new Action1<Void>() {
                @Override
                public void call(Void aVoid) {
                    gotoMap();
                }
            })
            );


    }

    abstract void registerClicked();
    abstract void failure();
    abstract void success(String username);
    abstract void gotoMap();

    @Override
    protected void drop(LoginModel model) {

    }
}
