package com.mcsgoc.www.portal;

import android.support.v7.widget.CardView;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.mcsgoc.www.portal.helper.Person;

import java.util.ArrayList;

/**
 * Created by Lab3 on 10/18/2015.
 */
public class MyAdapter extends RecyclerView.Adapter<MyAdapter.PersonViewHolder> {

    private final ArrayList<Person> items;


    public MyAdapter(ArrayList<Person> items) {
        this.items = items;
    }

    @Override
    public PersonViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.card_layout, parent, false);
        return new PersonViewHolder(v);

    }

    @Override
    public void onBindViewHolder(PersonViewHolder holder, int position) {
        String name = items.get(position).name;
        holder.personName.setText(name!=null?name:"");
        String designation = items.get(position).designation;
        holder.personDesignation.setText(designation!=null?designation:"");
        String college = items.get(position).college;
        holder.personCollege.setText(college!=null?college:"");
    }

    @Override
    public int getItemCount() {
        return items.size();
    }

    public static class PersonViewHolder extends RecyclerView.ViewHolder {
        CardView cv;
        TextView personName;
        TextView personDesignation;
        TextView personCollege;

        PersonViewHolder(View itemView) {
            super(itemView);
            cv = (CardView) itemView.findViewById(R.id.card);
            personName = (TextView) itemView.findViewById(R.id.person_name);
            personDesignation = (TextView) itemView.findViewById(R.id.person_designation);
            personCollege = (TextView) itemView.findViewById(R.id.person_college);

        }
    }
}
