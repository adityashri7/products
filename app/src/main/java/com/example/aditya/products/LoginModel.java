package com.example.aditya.products;

import android.content.Context;

import com.example.aditya.products.misc.LoginHelper;

/**
 * Created by trust on 8/1/2016.
 */
public class LoginModel {
    LoginHelper loginHelper;
    private Context context;

    public LoginModel(Context context){
        this.context = context;
        loginHelper = new LoginHelper(context, "loginDatabase.db", null, 1);
    }

    public boolean login(String username, String pass) {
        return loginHelper.getUser(username, pass);
    }
}
