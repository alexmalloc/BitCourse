package com.example.testing.bitcoincoursenavigation.Fragments.ListFragment;

import android.content.res.Resources;
import android.graphics.BitmapFactory;
import android.graphics.Color;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.testing.bitcoincoursenavigation.Fragments.ListFragment.ListItemFragment.OnListFragmentInteractionListener;
import com.example.testing.bitcoincoursenavigation.ObjectsPojo.CryptoWallet;
import com.example.testing.bitcoincoursenavigation.R;


import java.util.List;

/**
 * {@link RecyclerView.Adapter} that can display a {@link CryptoWallet} and makes a call to the
 * specified {@link OnListFragmentInteractionListener}.
 * TODO: Replace the implementation with code for your data type.
 */
public class MyListItemRecyclerViewAdapter extends RecyclerView.Adapter<MyListItemRecyclerViewAdapter.ViewHolder> {

    private final List<CryptoWallet> mValues;
    private final OnListFragmentInteractionListener mListener;

    public MyListItemRecyclerViewAdapter(List<CryptoWallet> items, OnListFragmentInteractionListener listener) {
        mValues = items;
        mListener = listener;
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.crypto_course, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(final ViewHolder holder, int position) {
        holder.bind(mValues.get(position));
    }

    @Override
    public int getItemCount() {
        return mValues.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        private  View mView;
        private  Resources mResources;
        private  TextView name;
        private  ImageView image;
        private  TextView ShortName;
        private  TextView price;
        private  TextView cap;
        private  TextView HourChange;
        private  TextView DayChange;
        private  TextView WeekChange;
        public CryptoWallet mItem;

        public ViewHolder(View view) {
            super(view);
            mView = view;
            mResources = mView.getResources();
            name = (TextView)view.findViewById(R.id.course_name);
            image = (ImageView)view.findViewById(R.id.course_image);
            ShortName = (TextView)view.findViewById(R.id.course_short_name);
            price = (TextView)view.findViewById(R.id.price);
            cap = (TextView)view.findViewById(R.id.cap);
            HourChange = (TextView)view.findViewById(R.id.hour_change);
            DayChange = (TextView)view.findViewById(R.id.day_change);
            WeekChange = (TextView)view.findViewById(R.id.week_change);
        }
        public void bind(CryptoWallet cryptoWallet) {
            mItem = cryptoWallet;

            if (image != null) {
                image.setImageBitmap(BitmapFactory.decodeResource(mResources, mItem.getCryptoImgid()));
            }
            name.setText(mItem.getName());
            ShortName.setText(mItem.getShortName());
            String priceText = mItem.getPrice();
            if (priceText.length() > 6) {
                priceText = priceText.substring(0, 5);
                int last = priceText.length() - 1;
                if (priceText.charAt(last) == '.') {
                    priceText = priceText.substring(0, 4);
                }
            }
            price.setText("$" + priceText);
            cap.setText("$" + mItem.getCap());
            if (mItem.getHourChange() < 0) {
                HourChange.setTextColor(Color.RED);
                HourChange.setText(Float.toString(mItem.getHourChange()) + "%");
            } else {
                HourChange.setTextColor(Color.GREEN);
                HourChange.setText("+" + Float.toString(mItem.getHourChange()) + "%");
            }
            if (mItem.getDayChange() < 0) {
                DayChange.setTextColor(Color.RED);
                DayChange.setText(Float.toString(mItem.getDayChange()) + "%");
            } else {
                DayChange.setTextColor(Color.GREEN);
                DayChange.setText("+" + Float.toString(mItem.getDayChange()) + "%");

                if (mItem.getWeekChange() < 0) {
                    WeekChange.setTextColor(Color.RED);
                    WeekChange.setText(Float.toString(mItem.getWeekChange()) + "%");
                } else {
                    WeekChange.setTextColor(Color.GREEN);
                    WeekChange.setText("+" + Float.toString(mItem.getWeekChange()) + "%");
                }
            }
        }
        @Override
        public String toString() {

            // return super.toString() + " '" + mContentView.getText() + "'";
            return super.toString();
        }
    }
}
