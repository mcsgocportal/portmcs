package com.mcsgoc.www.portal.fragments;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.mcsgoc.www.portal.R;
import com.mcsgoc.www.portal.helper.NewsItem;
import com.mcsgoc.www.portal.helper.RecyclerItemClickListener;

import java.util.ArrayList;


public class NewsFragment extends Fragment {

    public ArrayList<NewsItem> data;


    public NewsFragment() {
        // Required empty public constructor
    }


    public static NewsFragment newInstance(ArrayList<NewsItem> data) {
        NewsFragment fragment = new NewsFragment();
        Bundle args = new Bundle();
        fragment.data = data;
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        // Inflate the layout for this fragment
        View layout = inflater.inflate(R.layout.fragment_news, container, false);

        final RecyclerView list = (RecyclerView) layout.findViewById(R.id.recycler);
        list.setLayoutManager(new LinearLayoutManager(getActivity()));
        list.setItemAnimator(new DefaultItemAnimator());
        NewsRecyclerAdapter adapter = new NewsRecyclerAdapter(getActivity(), data);
        list.addOnItemTouchListener(new RecyclerItemClickListener(getActivity(), new RecyclerItemClickListener.OnItemClickListener() {
            @Override
            public void onItemClick(View view, final int position) {
                String objectID = data.get(position).getObjectID();
              /*  if (view.getId() == R.id.getNotice) {
                    Intent i = new Intent(this, NoticeContentActivity.class);
                    startActivity(i);*/

                Toast.makeText(getActivity(), "Make an intent for the next Activity\n" + objectID, Toast.LENGTH_SHORT).show();
            }
        }));
        list.setAdapter(adapter);
        return layout;
    }


}
