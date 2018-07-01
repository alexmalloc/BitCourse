package com.example.testing.bitcoincoursenavigation.Fragments.SortFragment;

import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;

import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;

import com.example.testing.bitcoincoursenavigation.R;


/**
 * A simple {@link Fragment} subclass.
 * Activities that contain this fragment must implement the
 * {@link Sort.OnSortInteraction} interface
 * to handle interaction events.
 * Use the {@link Sort#newInstance} factory method to
 * create an instance of this fragment.
 */
public class Sort extends Fragment {
    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;
    private SharedPreferences sharedPreferences;
    private OnSortInteraction mListener;
    private String LastSort;
    public Sort() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment Sort.
     */
    // TODO: Rename and change types and number of parameters
    public static Sort newInstance(String param1, String param2) {
        Sort fragment = new Sort();
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
        View view = inflater.inflate(R.layout.fragment_sort, container, false);

        ImageButton rankButton = (ImageButton)view.findViewById(R.id.rank_button);
        ImageButton priceButton = (ImageButton)view.findViewById(R.id.price_button);
        ImageButton hourButton = (ImageButton)view.findViewById(R.id.hour_button);
        ImageButton dayButton = (ImageButton)view.findViewById(R.id.day_button);
        ImageButton weekButton = (ImageButton)view.findViewById(R.id.week_button);

        onSelectListener(rankButton);
        onSelectListener(priceButton);
        onSelectListener(hourButton);
        onSelectListener(dayButton);
        onSelectListener(weekButton);

        return view;

    }
    // TODO: Rename method, update argument and hook method into UI event
    public void onSelectListener(ImageButton button) {
        int id = button.getId();
        if(id == R.id.rank_button) {
            button.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    LastSort = "Rank Button";
                    mListener.onFragmentInteraction("Rank Button");

                }
            });
        }
        else if(id == R.id.price_button) {
            button.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    LastSort = "Price Button";
                    mListener.onFragmentInteraction("Price Button");

                }
            });
        }
        else if(id == R.id.hour_button) {
            button.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    LastSort = "Hour Button";
                    mListener.onFragmentInteraction("Hour Button");

                }
            });
        }
        else if(id == R.id.day_button) {
            button.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    LastSort = "Day Button";
                    mListener.onFragmentInteraction("Day Button");

                }
            });
        }
        else if(id == R.id.week_button) {

            button.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    LastSort = "Week Button";
                    mListener.onFragmentInteraction("Week Button");

                }
            });
        }
    }

    @Override
    public void onPause() {
        super.onPause();
  //      saveSort();
    }

    @Override
    public void onResume() {

        super.onResume();
      //      loadSort();
    }

    public void saveSort() {
        sharedPreferences = getActivity().getSharedPreferences("Sort Fragment", Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedPreferences.edit();
        if(LastSort != null) {
            editor.putString("LAST_SORT", LastSort);
        }
        editor.apply();
    }
    public void loadSort() {
        sharedPreferences = getActivity().getSharedPreferences("Sort Fragment", Context.MODE_PRIVATE);
        if(sharedPreferences != null) {
            String myString = sharedPreferences.getString("LAST_SORT", "Rank Button");
            LastSort = myString;
            if(myString != null) {
                mListener.onFragmentInteraction(myString);
            }
            else {
                mListener.onFragmentInteraction("Rank Button");
            }
        }
    }
    public void onButtonPressed(String uri) {
        if (mListener != null) {
            mListener.onFragmentInteraction(uri);
        }

    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        if (context instanceof OnSortInteraction) {
            mListener = (OnSortInteraction) context;
        } else {
            throw new RuntimeException(context.toString()
                    + " must implement OnFragmentInteractionListener");
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
     * <p>
     * See the Android Training lesson <a href=
     * "http://developer.android.com/training/basics/fragments/communicating.html"
     * >Communicating with Other Fragments</a> for more information.
     */
    public interface OnSortInteraction {
        // TODO: Update argument type and name
        void onFragmentInteraction(String KeySelect);

    }
}
