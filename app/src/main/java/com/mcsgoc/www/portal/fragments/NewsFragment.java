package com.mcsgoc.www.portal.fragments;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.mcsgoc.www.portal.NoticeContentActivity;
import com.mcsgoc.www.portal.R;
import com.mcsgoc.www.portal.helper.Constants;
import com.mcsgoc.www.portal.helper.NewsItem;
import com.mcsgoc.www.portal.helper.RecyclerItemClickListener;

import java.util.ArrayList;


public class NewsFragment extends Fragment {

    public ArrayList<NewsItem> data;
    private SwipeRefreshLayout refreshLayout;
    private RefreshNotice refreshNotice;


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
        final NewsRecyclerAdapter adapter = new NewsRecyclerAdapter(getActivity(), data);
        refreshLayout = (SwipeRefreshLayout) layout.findViewById(R.id.swipeRefreshLayout);
        list.setAdapter(adapter);
        list.addOnItemTouchListener(new RecyclerItemClickListener(getActivity(), new RecyclerItemClickListener.OnItemClickListener() {
            @Override
            public void onItemClick(View view, final int position) {
                String objectID = data.get(position).getObjectID();
                Intent i = new Intent(getActivity(), NoticeContentActivity.class);
                i.putExtra(Constants.EXTRA_NOTICE_ID, objectID);
                startActivity(i);
            }
        }));

        refreshLayout.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                refreshItems();
            }
        });
        return layout;
    }

    private void refreshItems() {
        refreshNotice.refreshNoticeList();
        onItemsLoadComplete();
    }

    void onItemsLoadComplete() {
        // Update the adapter and notify data set changed
        // ...

        // Stop refresh animation
        refreshLayout.setRefreshing(false);
    }


    @Override
    public void onAttach(Activity activity) {
        super.onAttach(activity);
        refreshNotice = (RefreshNotice) activity;
    }

    public interface RefreshNotice {
        void refreshNoticeList();
    }
}
