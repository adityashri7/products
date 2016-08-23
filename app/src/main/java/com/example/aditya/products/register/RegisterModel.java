package com.example.aditya.products.register;

import android.content.Context;

import com.example.aditya.products.misc.LoginHelper;

/**
 * Created by trust on 8/1/2016.
 */
public class RegisterModel {
    LoginHelper loginHelper;
    private Context context;

    public RegisterModel(Context context){
        this.context = context;
        loginHelper = new LoginHelper(context, "loginDatabase.db", null, 1);
    }

    public boolean registerUser(String username, String pass) {
        return loginHelper.insertUser(username, pass);
    }
}
