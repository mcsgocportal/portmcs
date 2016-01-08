package com.mcsgoc.www.portal.fragments;

import android.content.Context;
import android.support.v7.widget.CardView;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.mcsgoc.www.portal.R;
import com.mcsgoc.www.portal.helper.TimeTableItem;

import java.util.ArrayList;

/**
 * Created by user on 06-01-2016.
 */
public class MyRecyclerAdapter extends RecyclerView.Adapter<MyRecyclerAdapter.TimeTableHolder> {

    private final Context context;
    private final ArrayList<TimeTableItem> items;

    // viewholder implementation for recycler
    public class TimeTableHolder extends RecyclerView.ViewHolder {
        CardView cv;
        TextView subjectName;
        TextView subjectCode;
        TextView teacherName;
        TextView place;

        TimeTableHolder(View itemView) {
            super(itemView);
            cv = (CardView) itemView.findViewById(R.id.card_time_table);
            subjectCode = (TextView) itemView.findViewById(R.id.textSubjectCode);
            subjectName = (TextView) itemView.findViewById(R.id.textSubjectName);
            teacherName = (TextView) itemView.findViewById(R.id.textTeacherName);
            place = (TextView) itemView.findViewById(R.id.textPlace);

        }

    }

    public MyRecyclerAdapter(Context context, ArrayList<TimeTableItem> items) {
        this.items = items;
        this.context = context;
        // initializeData();
    }

    @Override
    public TimeTableHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.custum_time_table_item, parent, false);
        return new TimeTableHolder(v);
    }

    @Override
    public void onBindViewHolder(TimeTableHolder holder, int position) {
        holder.place.setText(items.get(position).getPlace());
        holder.subjectCode.setText(items.get(position).getCode());
        holder.subjectName.setText(items.get(position).getSubject());
        holder.teacherName.setText(items.get(position).getTeacher());

    }

    @Override
    public int getItemCount() {
        return items.size();
    }
}
