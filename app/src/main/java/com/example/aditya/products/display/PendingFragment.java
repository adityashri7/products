package com.example.aditya.products.display;

import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.aditya.products.R;
import com.example.aditya.products.misc.PurchaseHelper;

import java.util.ArrayList;

public class PendingFragment extends Fragment {

    private static final String ARG_COLUMN_COUNT = "column-count";
    private int mColumnCount = 1;
    private OKListener mListener;
    private RecyclerView recyclerView;

    private String currentUser;
    public PendingFragment() {
    }



    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        if (getArguments() != null) {
            mColumnCount = getArguments().getInt(ARG_COLUMN_COUNT);
        }
        SharedPreferences pref = getContext().getApplicationContext().getSharedPreferences("user", Context.MODE_PRIVATE);
        currentUser = pref.getString("currentuser", "");
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_purchased, container, false);

        PurchaseHelper purchaseHelper = new PurchaseHelper(this.getContext(), "purchaseDatabase.db", null, 1);
        ArrayList list = purchaseHelper.getPending(currentUser);
        ArrayList imagesList = purchaseHelper.getPendingImages(currentUser);
        // Set the adapter
        if (view instanceof RecyclerView) {
            Context context = view.getContext();
            recyclerView = (RecyclerView) view;
            if (mColumnCount <= 1) {
                recyclerView.setLayoutManager(new LinearLayoutManager(context));
            } else {
                recyclerView.setLayoutManager(new GridLayoutManager(context, mColumnCount));
            }

            recyclerView.setAdapter(new PendingAdapter(list, imagesList, mListener));
        }
        return view;
    }

    public void updateAdapterRemoveItem(String item){
        PurchaseHelper purchaseHelper = new PurchaseHelper(this.getContext(), "purchaseDatabase.db", null, 1);
        int result = purchaseHelper.updateItem(currentUser, item);
        ArrayList list = purchaseHelper.getPending(currentUser);
        ArrayList imagesList = purchaseHelper.getPendingImages(currentUser);
        if (result!= 0){
            recyclerView.setAdapter(new PendingAdapter(list, imagesList, mListener));
        }
    }


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

    public interface OKListener {
        void okPressed(String item);
    }

}
