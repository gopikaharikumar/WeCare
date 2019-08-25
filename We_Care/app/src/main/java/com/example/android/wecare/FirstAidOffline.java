package com.example.android.wecare;

import android.content.Context;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.widget.SwipeRefreshLayout;


import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;



import java.util.List;



/**
 * A simple {@link Fragment} subclass.
 * Activities that contain this fragment must implement the
 * {@link OnFragmentInteractionListener} interface
 * to handle interaction events.
 * Use the {@link FirstAidOffline#newInstance} factory method to
 * create an instance of this fragment.
 */
public class FirstAidOffline extends Fragment implements SwipeRefreshLayout.OnRefreshListener{
    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;
    SwipeRefreshLayout swipeRefreshLayout;
    private OnFragmentInteractionListener mListener;
    RecyclerView rv;
    CardView cardView;
    List<FirstAidCard> firstAidCardList;

    public FirstAidOffline() {
        // Required empty public constructor
    }

  \
    // TODO: Rename and change types and number of parameters
    public static FirstAidOffline newInstance(String param1, String param2) {
        FirstAidOffline fragment = new FirstAidOffline();
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
        View v =inflater.inflate(R.layout.fragment_first_aid_tips, container, false);
        rv = (RecyclerView)v.findViewById(R.id.rv);
        cardView = (CardView) v.findViewById(R.id.card);
        // Setting layout manager for recycler view
        LinearLayoutManager llm = new LinearLayoutManager(getActivity());
        rv.setLayoutManager(llm);
        swipeRefreshLayout = (SwipeRefreshLayout)v.findViewById(R.id.tips_swipe_layout);
        swipeRefreshLayout.setOnRefreshListener(this);
        refresh();
        return v;
    }




    // TODO: Rename method, update argument and hook method into UI event
    public void onButtonPressed(Uri uri) {
        if (mListener != null) {
            mListener.onFragmentInteraction(uri);
        }
    }

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

    @Override
    public void onDetach() {
        super.onDetach();
        mListener = null;
    }

    @Override
    public void onRefresh() {
        refresh();
    }

    public void refresh()
    {
        DatabaseHandler dh = new DatabaseHandler(getActivity());
        firstAidCardList = dh.getAllTips(getActivity().getExternalFilesDir(null)+"");
        FirstAidRVAdapter adapter = new FirstAidRVAdapter(firstAidCardList,getActivity(),true);
        rv.setAdapter(adapter);
        swipeRefreshLayout.setRefreshing(false);
    }
    @Override
    public void setUserVisibleHint(boolean isVisibleToUser) {
        super.setUserVisibleHint(isVisibleToUser);
        if(isVisibleToUser){
            refresh();
        }
    }



    public interface OnFragmentInteractionListener {

        void onFragmentInteraction(Uri uri);
    }
}

