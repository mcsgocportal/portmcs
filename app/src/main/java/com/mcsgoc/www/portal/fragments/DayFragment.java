package com.mcsgoc.www.portal.fragments;

import android.content.Context;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.mcsgoc.www.portal.R;
import com.mcsgoc.www.portal.helper.RecyclerItemClickListener;
import com.mcsgoc.www.portal.helper.TimeTableItem;

import java.util.ArrayList;


public class DayFragment extends Fragment {
    public static ArrayList<TimeTableItem> data;

    public static DayFragment newInstance(ArrayList<TimeTableItem> data) {
        DayFragment fragment = new DayFragment();
        DayFragment.data = data;
        return fragment;
    }

    public DayFragment() {
        // Required empty public constructor
    }

    @Override

    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        View layout = inflater.inflate(R.layout.fragment_day, container, false);

        // fetch data from parse get list using fetch in background
        final RecyclerView list = (RecyclerView) layout.findViewById(R.id.recycler);
        list.setLayoutManager(new LinearLayoutManager(getActivity()));
        list.setItemAnimator(new DefaultItemAnimator());
        final MyRecyclerAdapter adapter = new MyRecyclerAdapter(getActivity(), data);

        list.setAdapter(adapter);
        list.addOnItemTouchListener(new RecyclerItemClickListener(getActivity(), new RecyclerItemClickListener.OnItemClickListener() {
            @Override
            public void onItemClick(View view, final int position) {

            }
        }));
        return layout;
    }

}
