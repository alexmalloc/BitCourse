package com.example.testing.bitcoincoursenavigation.Fragments.ThemesFragment;

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

import com.example.testing.bitcoincoursenavigation.ObjectsPojo.CryptoWallet;
import com.example.testing.bitcoincoursenavigation.R;


/**
 * A fragment representing a list of Items.
 * <p/>
 * Activities containing this fragment MUST implement the {@link OnThemeListener}
 * interface.
 */
public class ThemesFragment extends Fragment {

    // TODO: Customize parameter argument names
    private static final String ARG_COLUMN_COUNT = "column-count";
    // TODO: Customize parameters
    private int mColumnCount = 2;
    private OnThemeListener mListener;
    private SharedPreferences sharedPreferences;
    private MyThemesRecyclerViewAdapter myAdapter;

    /**
     * Mandatory empty constructor for the fragment manager to instantiate the
     * fragment (e.g. upon screen orientation changes).
     */
    public ThemesFragment() {

    }

    // TODO: Customize parameter initialization
    @SuppressWarnings("unused")
    public static ThemesFragment newInstance(int columnCount) {
        ThemesFragment fragment = new ThemesFragment();
        Bundle args = new Bundle();
        args.putInt(ARG_COLUMN_COUNT, columnCount);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        if (getArguments() != null) {
            mColumnCount = getArguments().getInt(ARG_COLUMN_COUNT);
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_themes_list, container, false);

        // Set the adapter
        if (view instanceof RecyclerView) {
            Context context = view.getContext();
            RecyclerView recyclerView = (RecyclerView) view;
            if (mColumnCount <= 1) {
                recyclerView.setLayoutManager(new LinearLayoutManager(context));
            } else {
                recyclerView.setLayoutManager(new GridLayoutManager(context, mColumnCount));
            }
            myAdapter = new MyThemesRecyclerViewAdapter(CryptoWallet.getThemes(), mListener);
            recyclerView.setAdapter(myAdapter);
            recyclerView.setHasFixedSize(true);
        }
        return view;
    }
    public void loadChecks() {
        sharedPreferences = getActivity().getSharedPreferences("Theme", Context.MODE_PRIVATE);
        if(sharedPreferences != null && myAdapter != null) {
            int position = sharedPreferences.getInt("Position", 0);
            myAdapter.setCheckedPosition(position);

        }
    }
    public void saveChecks() {
        sharedPreferences = getActivity().getSharedPreferences("Theme", Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedPreferences.edit();
        int position = myAdapter.getChecedPosition();


            editor.putInt("Position", position);
            editor.apply();

    }

    @Override
    public void onPause() {
        super.onPause();
        saveChecks();
    }

    @Override
    public void onResume() {
        super.onResume();
        loadChecks();
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        if (context instanceof OnThemeListener) {
            mListener = (OnThemeListener) context;
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

    /**
     * This interface must be implemented by activities that contain this
     * fragment to allow an interaction in this fragment to be communicated
     * to the activity and potentially other fragments contained in that
     * activity.
     * <p/>
     * See the Android Training lesson <a href=
     * "http://developer.android.com/training/basics/fragments/communicating.html"
     * >Communicating with Other Fragments</a> for more information.
     */
    public interface OnThemeListener {
        // TODO: Update argument type and name
        void OnThemeSelectListener(Integer item);
    }
}
