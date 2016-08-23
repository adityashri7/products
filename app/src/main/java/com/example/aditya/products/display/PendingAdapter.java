package com.example.aditya.products.display;

import android.database.sqlite.SQLiteDatabase;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.support.v7.widget.RecyclerView;
import android.util.Base64;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.example.aditya.products.R;

import java.util.List;


public class PendingAdapter extends RecyclerView.Adapter<PendingAdapter.ViewHolder> {

    private final List<String> mValues;
    private final List<String> mImages;
    private final PendingFragment.OKListener mListener;

    public PendingAdapter(List<String> items, List<String> images, PendingFragment.OKListener listener) {
        mValues = items;
        mListener = listener;
        mImages = images;
    }



    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.pending_item, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(final ViewHolder holder, int position) {
        holder.mContentView.setText(mValues.get(position));
        if (!(mImages.get(position).equals(""))){
            byte[] decodedString = Base64.decode(mImages.get(position), Base64.DEFAULT);
            Bitmap decodedByte = BitmapFactory.decodeByteArray(decodedString, 0, decodedString.length);
            holder.mImageView.setImageBitmap(decodedByte);

        }

        holder.mOKButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (null != mListener) {
                    // Notify the active callbacks interface (the activity, if the
                    // fragment is attached to one) that an item has been selected.
                    mListener.okPressed(holder.mContentView.getText().toString());
                }
            }
        });
    }


    @Override
    public int getItemCount() {
        if (mValues != null) {
            return mValues.size();
        }
        else{
            return 0;
        }
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        public final TextView mContentView;
        public final Button mOKButton;
        public final ImageView mImageView;

        public ViewHolder(View view) {
            super(view);
            mContentView = (TextView) view.findViewById(R.id.content);
            mOKButton = (Button) view.findViewById(R.id.ok_button);
            mImageView = (ImageView) view.findViewById(R.id.imageView);
        }

        @Override
        public String toString() {
            return super.toString() + " '" + mContentView.getText() + "'";
        }
    }

}
