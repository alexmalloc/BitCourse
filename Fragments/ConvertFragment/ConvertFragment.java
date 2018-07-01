package com.example.testing.bitcoincoursenavigation.Fragments.ConvertFragment;


import android.graphics.Color;
import android.net.Uri;

import android.os.Bundle;

import android.support.constraint.ConstraintLayout;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
import android.text.Editable;
import android.text.TextWatcher;


import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.TextView;


import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;
import com.example.testing.bitcoincoursenavigation.Fragments.BlankFragment.BlankFragment;
import com.example.testing.bitcoincoursenavigation.MainActivity;
import com.example.testing.bitcoincoursenavigation.ObjectsPojo.CryptoData;
import com.example.testing.bitcoincoursenavigation.ObjectsPojo.CryptoWallet;
import com.example.testing.bitcoincoursenavigation.R;

import org.json.JSONException;
import org.json.JSONObject;


import java.math.RoundingMode;

import java.text.DecimalFormat;
import java.util.HashMap;
import java.util.List;
import java.util.Map;


/**
 * A simple {@link Fragment} subclass.
 * Activities that contain this fragment must implement the
 * {@link ConvertFragment.OnFragmentInteractionListener} interface
 * to handle interaction events.
 * Use the {@link ConvertFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class ConvertFragment extends Fragment {
    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;
    private OnFragmentInteractionListener mListener;
    private Map<String, Float> PriceMap;
    public String KeyPrice;
    Float price;
    public ConvertFragment() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment ConvertFragment.
     */
    // TODO: Rename and change types and number of parameters
    public static ConvertFragment newInstance(String param1, String param2) {
        ConvertFragment fragment = new ConvertFragment();
        Bundle args = new Bundle();
        args.putString(ARG_PARAM1, param1);
        args.putString(ARG_PARAM2, param2);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            mParam1 = getArguments().getString(ARG_PARAM1);
            mParam2 = getArguments().getString(ARG_PARAM2);
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_convert, container, false);
        KeyPrice = "BTC";
        LoadDataList();
        setOnEditText(view);
        ImageButton CButton = (ImageButton)view.findViewById(R.id.CButton);
        setOnClickListener(CButton);
        ConstraintLayout convertLayout = (ConstraintLayout)view.findViewById(R.id.convert_layout);
        if(convertLayout != null && MainActivity.background != null) {
            convertLayout.setBackground(MainActivity.background);
        }
        return view;
    }
    public void setOnClickListener(ImageButton mButton) {
        int id = mButton.getId();
        if(id == R.id.CButton) {
            mButton.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    openList();
                }
            });
        }
    }
    public void openList() {
        Fragment convertList = new ConvertCryptoListFragment();
        FragmentTransaction myFM = getActivity().getSupportFragmentManager().beginTransaction();
        myFM.replace(R.id.listFragment, convertList, "ConvertList");
        myFM.commit();
        convertList.setRetainInstance(true);
        myFM.addToBackStack(null);
    }
    public void closeList() {
        Fragment blank = new BlankFragment();
        FragmentTransaction myFM = getActivity().getSupportFragmentManager().beginTransaction();
        myFM.replace(R.id.listFragment, blank, "blanke");
        myFM.commit();
        blank.setRetainInstance(true);

    }
    private void LoadDataList() {
        PriceMap = new HashMap<String, Float>(CryptoWallet.GetCryptos().size());
        String sourceURL = "https://api.coinmarketcap.com/v2/ticker/";
        JsonObjectRequest DataRequest = new JsonObjectRequest(Request.Method.GET, sourceURL, null, new Response.Listener<JSONObject>() {
            @Override
            public void onResponse(JSONObject response) {
                try {
                    JSONObject mData = response.getJSONObject("data");

                                PriceMap.put("BTC", Float.parseFloat(CryptoData.getPrice("1", mData)));



                                PriceMap.put("ETH", Float.parseFloat(CryptoData.getPrice("1027", mData)));



                                PriceMap.put("EOS", Float.parseFloat(CryptoData.getPrice("1765", mData)));

                                PriceMap.put("ADA", Float.parseFloat(CryptoData.getPrice("2010", mData)));

                                PriceMap.put("LTC", Float.parseFloat(CryptoData.getPrice("2", mData)));


                                PriceMap.put("IOTA", Float.parseFloat(CryptoData.getPrice("1720", mData)));


                                PriceMap.put("XEM", Float.parseFloat(CryptoData.getPrice("873", mData)));

                                PriceMap.put("XMR", Float.parseFloat(CryptoData.getPrice("328", mData)));






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

    public void setOnEditText(View v) {
        final EditText CryptoEdit = (EditText)v.findViewById(R.id.cryptoEdit);

        final EditText FiatEdit = (EditText)v.findViewById(R.id.fiatEdit);
        if(MainActivity.Courses != null) {
            final List<CryptoWallet> dataList = MainActivity.Courses;

            if (CryptoEdit != null && FiatEdit != null) {
                CryptoEdit.addTextChangedListener(new TextWatcher() {
                    @Override
                    public void beforeTextChanged(CharSequence s, int start, int count, int after) {

                    }

                    @Override
                    public void onTextChanged(CharSequence s, int start, int before, int count) {
                        TextView TView = (TextView)getView().findViewById(R.id.CButton_text);
                        KeyPrice = TView.getText().toString();
                        price = PriceMap.get(KeyPrice);
                        DecimalFormat df = new DecimalFormat("#.######");
                        df.setRoundingMode(RoundingMode.CEILING);
                        if (price != null) {
                        //    Float cryptoValue;
                            if (s.length() > 0) {
                                 Float cryptoValue = Float.parseFloat(s.toString());
                            //    Double cryptoVal = Double.parseDouble(s.toString());
                                Float result = price * cryptoValue;
                                FiatEdit.setText("");
                                FiatEdit.setHint(df.format(result));
                                FiatEdit.setHintTextColor(Color.WHITE);
                            } else {

                                FiatEdit.setHint(df.format(price));
                                FiatEdit.setHintTextColor(Color.GRAY);
                            }
                        }
                    }

                    @Override
                    public void afterTextChanged(Editable s) {

                    }
                });
                FiatEdit.addTextChangedListener(new TextWatcher() {
                    @Override
                    public void beforeTextChanged(CharSequence s, int start, int count, int after) {

                    }

                    @Override
                    public void onTextChanged(CharSequence s, int start, int before, int count) {
                        TextView TView = (TextView)getView().findViewById(R.id.CButton_text);
                        KeyPrice = TView.getText().toString();
                        price = PriceMap.get(KeyPrice);
                        DecimalFormat df = new DecimalFormat("#.######");
                        df.setRoundingMode(RoundingMode.CEILING);

                        if (price != null) {
                            Float cryptoValue;
                            if (s.length() > 0) {
                                cryptoValue = Float.parseFloat(s.toString());
                                Float result = cryptoValue / price;
                                CryptoEdit.setText("");
                                CryptoEdit.setHint(df.format(result));
                                CryptoEdit.setHintTextColor(Color.WHITE);
                            } else {

                                CryptoEdit.setHint(df.format(1.0));
                                CryptoEdit.setHintTextColor(Color.GRAY);
                            }
                        }

                    }

                    @Override
                    public void afterTextChanged(Editable s) {

                    }
                });
            }
        }
    }

    // TODO: Rename method, update argument and hook method into UI event
    public void onButtonPressed(Uri uri) {
        if (mListener != null) {
            mListener.onFragmentInteraction(uri);
        }
    }
    /*
    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        if (context instanceof OnFragmentInteractionListener) {
            mListener = (OnFragmentInteractionListener) context;
        } else {
            throw new RuntimeException(context.toString()
                    + " must implement OnFragmentInteractionListener");
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
     * <p>
     * See the Android Training lesson <a href=
     * "http://developer.android.com/training/basics/fragments/communicating.html"
     * >Communicating with Other Fragments</a> for more information.
     */
    public interface OnFragmentInteractionListener {
        // TODO: Update argument type and name
        void onFragmentInteraction(Uri uri);
    }

}
