package com.example.testing.bitcoincoursenavigation.Fragments.ConvertFragment;

import android.graphics.BitmapFactory;

import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;


import com.example.testing.bitcoincoursenavigation.ObjectsPojo.CryptoWallet;
import com.example.testing.bitcoincoursenavigation.R;


import java.util.List;

/**
 * {@link RecyclerView.Adapter} that can display a {@link CryptoWallet} and makes a call to the
 * specified {@link ConvertCryptoListFragment.OnListConvertListener}.
 * TODO: Replace the implementation with code for your data type.
 */
public class MyConvertCryptoListRecyclerViewAdapter extends RecyclerView.Adapter<MyConvertCryptoListRecyclerViewAdapter.ViewHolder> {

    private final List<CryptoWallet> mValues;
    private final ConvertCryptoListFragment.OnListConvertListener mListener;

    public MyConvertCryptoListRecyclerViewAdapter(List<CryptoWallet> items, ConvertCryptoListFragment.OnListConvertListener listener) {
        mValues = items;
        mListener = listener;
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.fragment_convertcryptolist, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(final ViewHolder holder, int position) {
        holder.mItem = mValues.get(position);
        holder.CText.setText(holder.mItem.getShortName());
        holder.CImage.setImageBitmap(BitmapFactory.decodeResource(holder.mView.getResources(), holder.mItem.getCryptoImgid()));

        holder.mView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (null != mListener) {
                    // Notify the active callbacks interface (the activity, if the
                    // fragment is attached to one) that an item has been selected.
                    mListener.OnListConvertListener(holder.mItem.getShortName());

                }
            }
        });
    }

    @Override
    public int getItemCount() {
        return mValues.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        public final View mView;
        public final TextView CText;
        public final ImageView CImage;
        public CryptoWallet mItem;

        public ViewHolder(View view) {
            super(view);
            mView = view;
            CText = (TextView)view.findViewById(R.id.key_crypto);
            CImage = (ImageView)view.findViewById(R.id.img_crypro_key);
        }
        /*
        @Override
        public String toString() {
            return super.toString() + " '" + mContentView.getText() + "'";
        }
        */
    }
}
