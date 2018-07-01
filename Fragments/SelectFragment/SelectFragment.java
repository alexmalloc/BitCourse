package com.example.testing.bitcoincoursenavigation.Fragments.SelectFragment;

import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.SparseBooleanArray;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.testing.bitcoincoursenavigation.ObjectsPojo.CryptoWallet;
import com.example.testing.bitcoincoursenavigation.R;


/**
 * A fragment representing a list of Items.
 * <p/>
 * Activities containing this fragment MUST implement the {@link OnListFragmentInteractionListener}
 * interface.
 */
public class SelectFragment extends Fragment {

    // TODO: Customize parameter argument names
    private static final String ARG_COLUMN_COUNT = "column-count";
    // TODO: Customize parameters
    private int mColumnCount = 1;
    private OnListFragmentInteractionListener mListener;
    private MySelectRecyclerViewAdapter MyAdapter;
    private SharedPreferences sharedPreferences;
    /**
     * Mandatory empty constructor for the fragment manager to instantiate the
     * fragment (e.g. upon screen orientation changes).
     */

    public SelectFragment() {
        setRetainInstance(true);
    }
    // TODO: Customize parameter initialization
    @SuppressWarnings("unused")
    public static SelectFragment newInstance(int columnCount) {
        SelectFragment fragment = new SelectFragment();
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
        View view = inflater.inflate(R.layout.fragment_select_list, container, false);

        // Set the adapter
        RecyclerInit(view);
        return view;
    }
    private void RecyclerInit(View view)
    {
        // Set the adapter
        if (view instanceof RecyclerView) {
            Context context = view.getContext();
            RecyclerView recyclerView = (RecyclerView) view;
            recyclerView.setHasFixedSize(true);
            if (mColumnCount <= 1) {
                recyclerView.setLayoutManager(new LinearLayoutManager(context));
            } else {
                recyclerView.setLayoutManager(new GridLayoutManager(context, mColumnCount));
            }
            MyAdapter = new MySelectRecyclerViewAdapter(CryptoWallet.GetCryptos(), mListener);
            recyclerView.setAdapter(MyAdapter);

        }
    }

    @Override
    public void onPause()
    {
        super.onPause();
        saveChecks();
        if(MyAdapter.getmCheckInstance() != null) {
            mListener.onListFragmentInteraction(MyAdapter.getmCheckInstance());
        }
    }

    @Override
    public void onResume()
    {
        super.onResume();
        loadChecks();
        if(MyAdapter != null) {
            if (MyAdapter.getmCheckInstance() != null) {
                mListener.onListFragmentInteraction(MyAdapter.getmCheckInstance());
            }
        }
    }

    public void loadChecks()
    {
        sharedPreferences = getActivity().getSharedPreferences("Fragment", Context.MODE_PRIVATE);
        if(sharedPreferences != null && MyAdapter != null) {
            SparseBooleanArray mValues = new SparseBooleanArray(CryptoWallet.GetCryptos().size());
            for (int i = 0; i < CryptoWallet.GetCryptos().size(); i++) {
                mValues.put(i, sharedPreferences.getBoolean(Integer.toString(i), true));
            }
            MyAdapter.setmCheckInstance(mValues);
        }
    }
    public void saveChecks()
    {
        sharedPreferences = getActivity().getSharedPreferences("Fragment", Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedPreferences.edit();
        SparseBooleanArray myValues = MyAdapter.getmCheckInstance();
        if(myValues != null) {
            for (int i = 0; i < CryptoWallet.GetCryptos().size(); i++) {
                editor.putBoolean(Integer.toString(i), myValues.get(i));
            }
            editor.apply();
        }
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        if (context instanceof OnListFragmentInteractionListener) {
            mListener = (OnListFragmentInteractionListener) context;
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
    public interface OnListFragmentInteractionListener
    {
        // TODO: Update argument type and name
        void onListFragmentInteraction(SparseBooleanArray checkStates);
        // TODO make an interface of adding cryptowallets

    }
}
