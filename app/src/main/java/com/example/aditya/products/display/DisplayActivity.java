package com.example.aditya.products.display;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.database.sqlite.SQLiteDatabase;
import android.graphics.Bitmap;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.util.Log;
import android.view.View;
import android.support.design.widget.NavigationView;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.Button;
import android.widget.TextView;

import com.example.aditya.products.R;

public class DisplayActivity extends AppCompatActivity
        implements NavigationView.OnNavigationItemSelectedListener, AddFragment.OKListener, PendingFragment.OKListener{

    private static final int REQUEST_CODE = 0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_display);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Snackbar.make(view, "Replace with your own action", Snackbar.LENGTH_LONG)
                        .setAction("Action", null).show();
            }
        });

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer.setDrawerListener(toggle);
        toggle.syncState();

        NavigationView navigationView = (NavigationView) findViewById(R.id.nav_view);
        navigationView.setNavigationItemSelectedListener(this);

        findViewById(R.id.purchased_button).setOnClickListener(purchasedClicked);
        findViewById(R.id.add_button).setOnClickListener(addClicked);
        findViewById(R.id.pending_button).setOnClickListener(pendingClicked);

        SharedPreferences pref = this.getApplicationContext().getSharedPreferences("user", Context.MODE_PRIVATE);
        String currentUser = pref.getString("currentuser", "");



        if (savedInstanceState == null) {
            getSupportFragmentManager().beginTransaction()
                    .add(R.id.fragment_container, new PurchasedFragment())
                    .commit();
        }
    }

    android.view.View.OnClickListener purchasedClicked = new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            getSupportFragmentManager().beginTransaction()
                    .replace(R.id.fragment_container, new PurchasedFragment())
                    .commit();

        }
    };
    android.view.View.OnClickListener addClicked = new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            getSupportFragmentManager().beginTransaction()
                    .replace(R.id.fragment_container, new AddFragment())
                    .commit();

        }
    };

    android.view.View.OnClickListener pendingClicked = new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            getSupportFragmentManager().beginTransaction()
                    .replace(R.id.fragment_container, new PendingFragment())
                    .commit();

        }
    };

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (requestCode == REQUEST_CODE && resultCode == Activity.RESULT_OK) {
            Bitmap photo = (Bitmap) data.getExtras().get("data");
            imageReceived(photo);
        }
    }

    private void imageReceived(Bitmap photo) {
        AddFragment fragment = (AddFragment) getSupportFragmentManager().findFragmentById(R.id.fragment_container);
        if (fragment!= null){
            Log.e("OKPressed: ", "Fragment not null");
            fragment.imageReceived(photo);
        }
    }

    @Override
    public void onBackPressed() {
        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        if (drawer.isDrawerOpen(GravityCompat.START)) {
            drawer.closeDrawer(GravityCompat.START);
        } else {
            super.onBackPressed();
        }
    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.display, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    @SuppressWarnings("StatementWithEmptyBody")
    @Override
    public boolean onNavigationItemSelected(MenuItem item) {
        // Handle navigation view item clicks here.
        int id = item.getItemId();

        if (id == R.id.logout_button) {
            logOut();
        }

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        drawer.closeDrawer(GravityCompat.START);
        return true;
    }

    private void logOut() {
        finish();
    }

    @Override
    public void okPressed() {
        getSupportFragmentManager().beginTransaction()
                .replace(R.id.fragment_container, new PurchasedFragment())
                .commit();
    }

    @Override
    public void getImage() {
        Intent intent = new Intent();
        //intent.setType("image/*");
        intent.setAction(android.provider.MediaStore.ACTION_IMAGE_CAPTURE);
        //intent.addCategory(Intent.CATEGORY_OPENABLE);
        startActivityForResult(intent, REQUEST_CODE);
    }

    @Override
    public void okPressed(String item) {
        PendingFragment fragment = (PendingFragment) getSupportFragmentManager().findFragmentById(R.id.fragment_container);
        if (fragment!= null){
            Log.e("OKPressed: ", "Fragment not null");
            fragment.updateAdapterRemoveItem(item);
        }

    }

}
