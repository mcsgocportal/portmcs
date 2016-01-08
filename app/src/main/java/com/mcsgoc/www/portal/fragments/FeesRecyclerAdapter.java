package com.mcsgoc.www.portal.fragments;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.mcsgoc.www.portal.R;
import com.mcsgoc.www.portal.helper.FeesItem;

import java.util.ArrayList;

/**
 * Created by user on 06-01-2016.
 */
public class FeesRecyclerAdapter extends RecyclerView.Adapter<FeesRecyclerAdapter.FeesHolder> {


    private final ArrayList<FeesItem> items;


    public class FeesHolder extends RecyclerView.ViewHolder {
        TextView course;
        TextView firstInst;
        TextView secondInst;

        public FeesHolder(View itemView) {
            super(itemView);
            course = (TextView) itemView.findViewById(R.id.textCourse);
            firstInst = (TextView) itemView.findViewById(R.id.textFirstInst);
            secondInst = (TextView) itemView.findViewById(R.id.textSecondInst);
        }
    }

    public FeesRecyclerAdapter(Context context, ArrayList<FeesItem> items) {
        this.items = items;
    }


    @Override
    public FeesRecyclerAdapter.FeesHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.fees_item_layout, parent, false);
        return new FeesHolder(v);
    }

    @Override
    public void onBindViewHolder(FeesRecyclerAdapter.FeesHolder holder, int position) {

        String course = items.get(position).getCourse();

        int secondInstallment = items.get(position).getSecondInstallment();
        int firstInstallment = items.get(position).getFirstInstallment();
        holder.course.setText(course!=null?course:"");
        holder.firstInst.setText((firstInstallment> 0) ? ""+firstInstallment : "0");
        holder.secondInst.setText((secondInstallment> 0) ? ""+secondInstallment : "0");
    }

    @Override
    public int getItemCount() {
        return items.size();
    }
}
