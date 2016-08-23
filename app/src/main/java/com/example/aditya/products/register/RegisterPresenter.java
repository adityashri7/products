package com.example.aditya.products.register;

import android.content.Context;
import android.util.Log;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.example.aditya.products.R;
import com.example.aditya.products.misc.Presenter;
import com.jakewharton.rxbinding.view.RxView;

import rx.android.schedulers.AndroidSchedulers;
import rx.functions.Action1;
import rx.subscriptions.CompositeSubscription;

/**
 * Created by trust on 8/1/2016.
 */
public abstract class RegisterPresenter extends Presenter<RegisterModel>{

    private RegisterModel model;
    private RegisterView view;
    private Context context;
    private CompositeSubscription running;

    private EditText userField;
    private EditText passField;
    private EditText confirmPassField;


    public RegisterPresenter(RegisterView view, Context context){
        this.view = view;
        this.context = context;

        userField = (EditText) view.findViewById(R.id.username_field);
        passField = (EditText) view.findViewById(R.id.password_field);
        confirmPassField = (EditText) view.findViewById(R.id.confirm_password_field);
    }

    @Override
    protected void attach(final RegisterModel model) {
        running = new CompositeSubscription();

        running.add(RxView.clicks(view.findViewById(R.id.register_button))
        .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Action1<Void>() {
                    @Override
                    public void call(Void aVoid) {
                        String username = userField.getText().toString();
                        String pass = passField.getText().toString();
                        String confirmPass = confirmPassField.getText().toString();
                        if (!(confirmPass.equals(pass))){
                            Toast.makeText(context, "Password don't match. Try again", Toast.LENGTH_SHORT).show();
                            return;
                        }
                        if (model.registerUser(username, pass)){
                            registerSuccess();
                        }
                        else{
                            failure();
                        }
                    }
                })
        );

        running.add(RxView.clicks(view.findViewById(R.id.login_button))
        .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Action1<Void>() {
                    @Override
                    public void call(Void aVoid) {
                        loginClicked();
                    }
                })
        );

    }

    abstract void registerSuccess();
    abstract void failure();
    abstract void loginClicked();

    @Override
    protected void drop(RegisterModel model) {

    }
}
