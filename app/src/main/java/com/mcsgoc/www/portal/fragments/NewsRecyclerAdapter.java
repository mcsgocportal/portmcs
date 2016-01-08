package com.mcsgoc.www.portal.fragments;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.CardView;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.mcsgoc.www.portal.R;
import com.mcsgoc.www.portal.helper.NewsItem;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.Locale;

/**
 * Created by user on 04-01-2016.
 */
public class NewsRecyclerAdapter extends RecyclerView.Adapter<NewsRecyclerAdapter.NewsHolder> {

    private final Context context;
    private final ArrayList<NewsItem> items;


    // viewholder implementation for recycler
    public class NewsHolder extends RecyclerView.ViewHolder {
        CardView cv;
        TextView noticeTitle;
        TextView date;

        public NewsHolder(View itemView) {
            super(itemView);
            cv = (CardView) itemView.findViewById(R.id.card_notice);
            noticeTitle = (TextView) itemView.findViewById(R.id.textNoticeTitle);
            date = (TextView) itemView.findViewById(R.id.textDate);

        }
    }

    public NewsRecyclerAdapter(Context context, ArrayList<NewsItem> items) {
        this.items = items;
        this.context = context;
        // initializeData();
    }

    @Override
    public NewsHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.custom_news, parent, false);
        return new NewsHolder(v);
    }

    @Override
    public void onBindViewHolder(NewsHolder holder, int position) {
        holder.noticeTitle.setText(items.get(position).getNoticeTitle());
        holder.date.setText(getFormattedDate(position));
    }

    @NonNull
    private String getFormattedDate(int position) {
        DateFormat dateFormat = new SimpleDateFormat("dd-mm-yyyy", Locale.ENGLISH);
        Date date = items.get(position).getDate();
        return dateFormat.format(date);
    }

    @Override
    public int getItemCount() {
        return items.size();
    }
}
