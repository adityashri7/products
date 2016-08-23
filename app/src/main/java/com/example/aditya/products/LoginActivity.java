package com.example.aditya.products;

import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.location.LocationManager;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Toast;

import com.example.aditya.products.display.DisplayActivity;
import com.example.aditya.products.map.MapsActivity;
import com.example.aditya.products.register.RegisterActivity;
import com.facebook.CallbackManager;
import com.facebook.FacebookCallback;
import com.facebook.FacebookException;
import com.facebook.FacebookSdk;
import com.facebook.GraphRequest;
import com.facebook.GraphResponse;
import com.facebook.login.LoginResult;
import com.facebook.login.widget.LoginButton;

import org.json.JSONException;
import org.json.JSONObject;

public class LoginActivity extends AppCompatActivity {

    LoginModel model;
    LoginPresenter presenter;
    LoginView view;
    private Context context;
    private CallbackManager callbackManager;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        FacebookSdk.sdkInitialize(getApplicationContext());
        setContentView(R.layout.activity_login);
        context = this;
    }

    public void onFacebookSSO(final LoginResult loginResult) {

        final String[] email = {null};

        GraphRequest request = GraphRequest.newMeRequest(
                loginResult.getAccessToken(),
                new GraphRequest.GraphJSONObjectCallback() {
                    @Override
                    public void onCompleted(JSONObject object, GraphResponse response) {
                        Log.v("LoginActivity", response.toString());


                        // Application code
                        try {
                            email[0] = object.getString("email");
                            logIn(email[0]);
                            Log.e("Facebook Login:", "Success getting name");

                        } catch (JSONException e) {
                            e.printStackTrace();
                        }


                    }
                });
        Bundle parameters = new Bundle();
        parameters.putString("fields", "email");
        request.setParameters(parameters);
        request.executeAsync();


    }
    @Override
    protected void onStart(){
        super.onStart();
    }

    @Override
    protected void onResume(){
        super.onResume();

        view = (LoginView)findViewById(R.id.login_view);
        model = new LoginModel(this);
        presenter = new LoginPresenter(view, this) {
            @Override
            void registerClicked() {
                regClicked();
            }

            @Override
            void failure() {
                Toast.makeText(context, "Error logging in", Toast.LENGTH_SHORT).show();
            }

            @Override
            void success(String username) {
                Toast.makeText(context, "Success logging in", Toast.LENGTH_SHORT).show();
                logIn(username);
            }

            @Override
            void gotoMap() {
                statusCheck();
            }
        };

        presenter.attach();
        presenter.bind(model);


        LoginButton loginButton = (LoginButton) view.findViewById(R.id.fb_login_button);
        loginButton.setReadPermissions("email");
        // Other app specific specialization

        callbackManager = CallbackManager.Factory.create();


        // Callback registration
        loginButton.registerCallback(callbackManager, new FacebookCallback<LoginResult>() {
            @Override
            public void onSuccess(LoginResult loginResult) {
                Log.e("Facebook Login", "Success logging in");
                onFacebookSSO(loginResult);
            }

            @Override
            public void onCancel() {
                // App code
            }

            @Override
            public void onError(FacebookException exception) {
                Log.e("Facebook Login", "Error logging in");
            }
        });

    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        callbackManager.onActivityResult(requestCode, resultCode, data);
    }

    private void logIn(String username) {
        SharedPreferences pref = context.getApplicationContext().getSharedPreferences("user", Context.MODE_PRIVATE);
        pref.edit().putString("currentuser", username).apply();
        Intent intent = new Intent(this, DisplayActivity.class);
        startActivity(intent);
    }

    private void regClicked() {
        Intent intent = new Intent(this, RegisterActivity.class);
        startActivity(intent);

    }
    public void statusCheck()
    {
        final LocationManager manager = (LocationManager) getSystemService( Context.LOCATION_SERVICE );

        if ( !manager.isProviderEnabled( LocationManager.GPS_PROVIDER ) ) {
            buildAlertMessageNoGps();

        }
        else{
            startMaps();
        }


    }

    private void startMaps() {
        Intent intent = new Intent(context, MapsActivity.class);
        startActivity(intent);
    }

    private void buildAlertMessageNoGps() {
        final AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setMessage("Your GPS seems to be disabled, do you want to enable it?")
                .setCancelable(false)
                .setPositiveButton("Yes", new DialogInterface.OnClickListener() {
                    public void onClick(final DialogInterface dialog,  final int id) {
                        startActivity(new Intent(android.provider.Settings.ACTION_LOCATION_SOURCE_SETTINGS));
                    }
                })
                .setNegativeButton("No", new DialogInterface.OnClickListener() {
                    public void onClick(final DialogInterface dialog, final int id) {
                        dialog.cancel();
                    }
                });
        final AlertDialog alert = builder.create();
        alert.show();

    }

}
