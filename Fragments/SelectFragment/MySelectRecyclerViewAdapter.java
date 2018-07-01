package com.example.testing.bitcoincoursenavigation.Fragments.SelectFragment;

import android.graphics.BitmapFactory;

import android.support.v7.widget.RecyclerView;
import android.util.SparseBooleanArray;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.testing.bitcoincoursenavigation.ObjectsPojo.CryptoWallet;
import com.example.testing.bitcoincoursenavigation.R;
import com.example.testing.bitcoincoursenavigation.Fragments.SelectFragment.SelectFragment.OnListFragmentInteractionListener;


import java.util.List;

/**
 * {@link RecyclerView.Adapter} that can display a {@link } and makes a call to the
 * specified {@link OnListFragmentInteractionListener}.
 * TODO: Replace the implementation with code for your data type.
 */
public class MySelectRecyclerViewAdapter extends RecyclerView.Adapter<MySelectRecyclerViewAdapter.ViewHolder>
{
    private SparseBooleanArray mCheckInstance;
    private final List<CryptoWallet> mValues;
    private final OnListFragmentInteractionListener mListener;

    public MySelectRecyclerViewAdapter(List<CryptoWallet> items, OnListFragmentInteractionListener listener)
    {
        mValues = items;
        mListener = listener;
        mCheckInstance = new SparseBooleanArray(mValues.size());
        for(int i = 0; i < mValues.size(); i++) {
            mCheckInstance.put(i, true);
        }
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType)
    {
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.crypto_for_sellect, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(final ViewHolder holder, int position)
    {
        holder.mItem = mValues.get(position);
        holder.ShortName.setText(mValues.get(position).getShortName());
        holder.name.setText(mValues.get(position).getName());
        if(holder.cryptoImg != null) {
            holder.cryptoImg.setImageBitmap(
                    BitmapFactory.decodeResource(holder.mView.getResources(),
                            mValues.get(position).getCryptoImgid()
                    ));
        }
        holder.mView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(holder.cryptoCheck != null) {
                    holder.cryptoCheck.setChecked(!holder.cryptoCheck.isChecked());
                }
            }
        });
        holder.cryptoCheck.setTag(position);
        holder.cryptoCheck.setChecked(mCheckInstance.get(position, false));
        holder.cryptoCheck.setOnCheckedChangeListener(
                new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked)
            {
                mCheckInstance.put((Integer)buttonView.getTag(), isChecked);
            }
        });
    }
    public void setmCheckInstance(SparseBooleanArray newChecks)
    {
        mCheckInstance = newChecks;
        notifyDataSetChanged();
    }

    public SparseBooleanArray getmCheckInstance() {
        return mCheckInstance;
    }

    @Override
    public int getItemCount() {
        return mValues.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        public final View mView;
        public final TextView ShortName;
        public final TextView name;
        public final ImageView cryptoImg;
        public final CheckBox cryptoCheck;
        public CryptoWallet mItem;

        public ViewHolder(View view) {
            super(view);
            mView = view;
            ShortName = (TextView)view.findViewById(R.id.shortName);
            name = (TextView)view.findViewById(R.id.name);
            cryptoImg = (ImageView)view.findViewById(R.id.crypto_img);
            cryptoCheck = (CheckBox)view.findViewById(R.id.check_select);
        }

        @Override
        public String toString() {
            return super.toString() + " '" + ShortName.getText() + "'";
        }
    }
}
