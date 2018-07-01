package com.example.testing.bitcoincoursenavigation.Fragments.ThemesFragment;

import android.graphics.BitmapFactory;
import android.support.v7.widget.RecyclerView;
import android.util.SparseBooleanArray;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CheckBox;

import android.widget.ImageView;


import com.example.testing.bitcoincoursenavigation.R;


import java.util.List;

/**
 * {@link RecyclerView.Adapter} that can display a {@link com.example.testing.bitcoincoursenavigation.ObjectsPojo.CryptoWallet} and makes a call to the
 * specified {@link ThemesFragment.OnThemeListener}.
 * TODO: Replace the implementation with code for your data type.
 */
public class MyThemesRecyclerViewAdapter extends RecyclerView.Adapter<MyThemesRecyclerViewAdapter.ViewHolder> {

    private final List<Integer> mValues;
    private final ThemesFragment.OnThemeListener mListener;
    private SparseBooleanArray mCheckInstance;
    private int CheckedPosition = -1;
    public MyThemesRecyclerViewAdapter(List<Integer> items, ThemesFragment.OnThemeListener listener) {
        mValues = items;
        mListener = listener;
        mCheckInstance = new SparseBooleanArray(mValues.size());
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.fragment_themes, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(final ViewHolder holder, final int position) {
        holder.mItem = mValues.get(position);
        holder.ThemeImage.setImageBitmap(BitmapFactory.decodeResource(holder.mView.getResources(), holder.mItem));

        holder.mView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                if(holder.themeCheck != null) {
                    // при нажатии на элемент проверяем отмечен ли чекбокс в данной позиции
                    // если нет, то запоминаем позицию для отметки
                    // если отмечен, то сбрасываем отметку (ставим -1)
                    CheckedPosition = holder.themeCheck.isChecked() ? -1 : position;
                    // обновляем список, чтобы убрать прошлую! отметку и показать новую
                    notifyDataSetChanged();
                    holder.themeCheck.setChecked(!holder.themeCheck.isChecked());

                    if (null != mListener && holder.themeCheck.isChecked() == true) {
                        // Notify the active callbacks interface (the activity, if the
                        // fragment is attached to one) that an item has been selected.
                        mListener.OnThemeSelectListener(holder.mItem);
                    }
                }
            }
        });

        holder.themeCheck.setChecked(position == CheckedPosition);
    }
    public void setCheckedPosition (int position){
        CheckedPosition = position;
        notifyDataSetChanged();
    }

    public int getChecedPosition (){
        return CheckedPosition;
    }





    @Override
    public int getItemCount() {
        return mValues.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        public final View mView;
        public final CheckBox themeCheck;
        public final ImageView ThemeImage;
        public Integer mItem;

        public ViewHolder(View view) {
            super(view);
            mView = view;
            themeCheck = (CheckBox)view.findViewById(R.id.themeCheck);
            ThemeImage = (ImageView)view.findViewById(R.id.themeImage);

        }
        /*
        @Override
        public String toString() {
            return super.toString() + " '" + mContentView.getText() + "'";
        }
        */
    }
}
