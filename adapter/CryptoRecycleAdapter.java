package com.example.testing.bitcoincoursenavigation.adapter;

import android.graphics.BitmapFactory;
import android.graphics.Color;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;


import com.example.testing.bitcoincoursenavigation.ObjectsPojo.CryptoWallet;
import com.example.testing.bitcoincoursenavigation.R;

import java.util.List;

public class CryptoRecycleAdapter extends RecyclerView.Adapter<CryptoRecycleAdapter.ViewHolder>{
    private List<CryptoWallet> mDataset;
    public static class ViewHolder extends RecyclerView.ViewHolder {
        private TextView name;
        private ImageView image;
        private TextView ShortName;
        private TextView price;
        private TextView cap;
        private TextView HourChange;
        private TextView DayChange;
        private TextView WeekChange;
        public ViewHolder(View v) {
            super(v);
            name = (TextView)v.findViewById(R.id.course_name);
            image = (ImageView)v.findViewById(R.id.course_image);
            ShortName = (TextView)v.findViewById(R.id.course_short_name);
            price = (TextView)v.findViewById(R.id.price);
            cap = (TextView)v.findViewById(R.id.cap);
            HourChange = (TextView)v.findViewById(R.id.hour_change);
            DayChange = (TextView)v.findViewById(R.id.day_change);
            WeekChange = (TextView)v.findViewById(R.id.week_change);
        }
        public void bind(CryptoWallet cryptoWallet) {
            image.setImageBitmap(BitmapFactory.decodeResource(itemView.getResources(), cryptoWallet.getCryptoImgid()));

            name.setText(cryptoWallet.getName());
            ShortName.setText(cryptoWallet.getShortName());
            String priceText = cryptoWallet.getPrice();
            if(priceText != null && priceText.length() > 6) {
                priceText = priceText.substring(0, 5);
                int last = priceText.length() - 1;
                if (priceText.charAt(last) == '.') {
                    priceText = priceText.substring(0, 4);
                }
            }
            price.setText("$" + priceText);
            cap.setText("$" + cryptoWallet.getCap());
            if(cryptoWallet.getHourChange() < 0) {
                HourChange.setTextColor(Color.RED);
                HourChange.setText(Float.toString(cryptoWallet.getHourChange()) + "%");
            }
            else {
                HourChange.setTextColor(Color.GREEN);
                HourChange.setText("+" + Float.toString(cryptoWallet.getHourChange()) + "%");
            }
            if(cryptoWallet.getDayChange() < 0) {
                DayChange.setTextColor(Color.RED);
                DayChange.setText(Float.toString(cryptoWallet.getDayChange()) + "%");
            }
            else {
                DayChange.setTextColor(Color.GREEN);
                DayChange.setText( "+" + Float.toString(cryptoWallet.getDayChange()) + "%");
            }
            if(cryptoWallet.getWeekChange() < 0) {
                WeekChange.setTextColor(Color.RED);
                WeekChange.setText(Float.toString(cryptoWallet.getWeekChange()) + "%");
            }
            else {
                WeekChange.setTextColor(Color.GREEN);
                WeekChange.setText("+" + Float.toString(cryptoWallet.getWeekChange()) + "%");
            }

        }
    }

    public void setmDataset(List<CryptoWallet> mDataset) {
        this.mDataset = mDataset;
    }

    public CryptoRecycleAdapter(List<CryptoWallet> myDataSet) {
        mDataset = myDataSet;
    }


    @Override
    public CryptoRecycleAdapter.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.crypto_course, parent, false);
        ViewHolder vh = new ViewHolder(view);
        return vh;
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {
        holder.bind(mDataset.get(position));
    }

    @Override
    public int getItemCount() {
        return mDataset.size();
    }

}
