package com.example.aditya.products.register;

import android.app.Activity;
import android.content.Context;
import android.os.Bundle;
import android.widget.Toast;

import com.example.aditya.products.R;

/**
 * Created by trust on 8/1/2016.
 */
public class RegisterActivity extends Activity{

    private RegisterModel model;
    private RegisterView view;
    private RegisterPresenter presenter;
    private Context context;

    @Override
    protected void onCreate(Bundle savedInstanceState){
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);
        context = this;
    }

    @Override
    protected void onResume(){
        super.onResume();
        model = new RegisterModel(this);
        view = (RegisterView) findViewById(R.id.register_view);

        presenter = new RegisterPresenter(view, this) {
            @Override
            void registerSuccess() {
                Toast.makeText(context, "Registered user", Toast.LENGTH_SHORT).show();
            }

            @Override
            void failure() {
                Toast.makeText(context, "Failed to register user. Please try a different username", Toast.LENGTH_SHORT).show();
            }

            @Override
            void loginClicked() {
                goBack();
            }
        };

        presenter.attach();
        presenter.bind(model);

    }

    private void goBack() {
        finish();
    }

}
