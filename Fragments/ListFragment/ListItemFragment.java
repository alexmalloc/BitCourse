package com.example.testing.bitcoincoursenavigation.Fragments.ListFragment;

import android.content.Context;
import android.os.Bundle;
import android.os.Handler;
import android.support.v4.app.Fragment;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;
import com.example.testing.bitcoincoursenavigation.MainActivity;
import com.example.testing.bitcoincoursenavigation.ObjectsPojo.CryptoData;
import com.example.testing.bitcoincoursenavigation.ObjectsPojo.CryptoWallet;
import com.example.testing.bitcoincoursenavigation.R;


import org.json.JSONException;
import org.json.JSONObject;

import java.util.List;

/**
 * A fragment representing a list of Items.
 * <p/>
 * Activities containing this fragment MUST implement the {@link OnListFragmentInteractionListener}
 * interface.
 */
public class ListItemFragment extends Fragment {

    // TODO: Customize parameter argument names
    private static final String ARG_COLUMN_COUNT = "column-count";
    // TODO: Customize parameters
    private int mColumnCount = 1;
    private OnListFragmentInteractionListener mListener;
    private List<CryptoWallet> WholeList;
    private MyListItemRecyclerViewAdapter ListAdapter;

    /**
     * Mandatory empty constructor for the fragment manager to instantiate the
     * fragment (e.g. upon screen orientation changes).
     */
    public ListItemFragment() {
    }

    // TODO: Customize parameter initialization
    @SuppressWarnings("unused")
    public static ListItemFragment newInstance(int columnCount) {
        ListItemFragment fragment = new ListItemFragment();
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
        View view = inflater.inflate(R.layout.fragment_listitem_list, container, false);
        WholeList = CryptoWallet.GetCryptos();
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
            ListAdapter = new MyListItemRecyclerViewAdapter(WholeList, mListener);
            recyclerView.setAdapter(ListAdapter);
            UpdatingThread();

            RecyclerView listLayout = (RecyclerView) view.findViewById(R.id.list_layout);

            if (listLayout != null && MainActivity.background != null) {
                listLayout.setBackground(MainActivity.background);

            }
        }
        return view;
    }

    public void UpdatingThread() {
        Handler mHandler = new Handler();
        Runnable runnable = new Runnable() {
            @Override
            public void run() {
                LoadDataList();
                Toast.makeText(getContext(), "UPDATED", Toast.LENGTH_SHORT).show();

            }
        };
        mHandler.postAtTime(runnable, System.currentTimeMillis() + 1000);
        mHandler.postDelayed(runnable, 0);

    }

    private void LoadDataList() {
        String sourceURL = "https://api.coinmarketcap.com/v2/ticker/";
        JsonObjectRequest DataRequest = new JsonObjectRequest(Request.Method.GET, sourceURL, null, new Response.Listener<JSONObject>() {
            @Override
            public void onResponse(JSONObject response) {
                try {
                    JSONObject mData = response.getJSONObject("data");
                    int Max = MainActivity.Courses.size();
                    List<CryptoWallet> ShortCourses = MainActivity.Courses;
                    for(int i = 0; i < Max; i++) {
                        switch (ShortCourses.get(i).getShortName()) {
                            case "BTC":
                                CryptoData.SetValues(WholeList.get(i), "1", mData);
                                break;
                            case "ETH":
                                CryptoData.SetValues(WholeList.get(i), "1027", mData);

                                break;
                            case "EOS":
                                CryptoData.SetValues(WholeList.get(i), "1765", mData);

                                break;
                            case "ADA":
                                CryptoData.SetValues(WholeList.get(i), "2010", mData);

                                break;
                            case "LTC":
                                CryptoData.SetValues(WholeList.get(i), "2", mData);
                                break;
                            case "IOTA":
                                CryptoData.SetValues(WholeList.get(i), "1720", mData);

                                break;
                            case "XEM":
                                CryptoData.SetValues(WholeList.get(i), "873", mData);

                                break;
                            case "XMR":
                                CryptoData.SetValues(WholeList.get(i), "328", mData);
                        }
                    }

                    if(ListAdapter != null) {
                        ListAdapter.notifyDataSetChanged();
                    }
                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {

            }
        });
        RequestQueue MQueue = Volley.newRequestQueue(getActivity());
        MQueue.add(DataRequest);

    }

    /*
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
    */
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
    public interface OnListFragmentInteractionListener {
        // TODO: Update argument type and name
        void onListFragmentInteraction(CryptoWallet item);
    }
}

