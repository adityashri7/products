package com.example.aditya.products.display;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Base64;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.Toast;

import com.example.aditya.products.R;
import com.example.aditya.products.misc.PurchaseHelper;

import java.io.ByteArrayOutputStream;
import java.util.ArrayList;


public class AddFragment extends Fragment {

    private static final int REQUEST_CODE = 0;
    private OKListener mListener;
    private PurchaseHelper purchaseHelper;
    private EditText itemField;
    private EditText quantityField;
    private String currentUser;
    private String encodedImage = "";

    /**
     * Mandatory empty constructor for the fragment manager to instantiate the
     * fragment (e.g. upon screen orientation changes).
     */
    public AddFragment() {
    }


    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        purchaseHelper = new PurchaseHelper(this.getContext(), "purchaseDatabase.db", null, 1);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_add, container, false);
        view.findViewById(R.id.ok_button).setOnClickListener(okClicked);
        view.findViewById(R.id.camera_button).setOnClickListener(pictureClicked);
        itemField = (EditText) view.findViewById(R.id.item_field);
        quantityField = (EditText) view.findViewById(R.id.quantity_field);
        SharedPreferences pref = getContext().getApplicationContext().getSharedPreferences("user", Context.MODE_PRIVATE);
        currentUser = pref.getString("currentuser", "");
        return view;
    }

    android.view.View.OnClickListener pictureClicked = new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            mListener.getImage();
        }
    };

    android.view.View.OnClickListener okClicked = new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            String item = itemField.getText().toString();
            String quatity = quantityField.getText().toString();
            if (item.equals("")){
                Toast.makeText(getContext(), "Item cannot be empty", Toast.LENGTH_SHORT);
                return;
            }
            if (quatity.equals("")){
                Toast.makeText(getContext(), "Quantity cannot be empty", Toast.LENGTH_SHORT);
                return;
            }
            purchaseHelper.insertItem(currentUser, item, Integer.valueOf(quatity),encodedImage);
            mListener.okPressed();
        }
    };


    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        if (context instanceof OKListener) {
            mListener = (OKListener) context;
        } else {
            throw new RuntimeException(context.toString()
                    + " must implement OnListFragmentInteractionListener");
        }
    }

    @Override
    public void onDetach() {
        super.onDetach();
        mListener = null;
    }

    public void imageReceived(Bitmap photo) {
        ByteArrayOutputStream baos = new ByteArrayOutputStream();
        photo.compress(Bitmap.CompressFormat.JPEG, 100, baos);
        byte[] b = baos.toByteArray();

        encodedImage = Base64.encodeToString(b, Base64.DEFAULT);

    }


    public interface OKListener {
        void okPressed();
        void getImage();
    }
}
