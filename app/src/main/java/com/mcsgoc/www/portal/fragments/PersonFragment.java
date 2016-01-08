package com.mcsgoc.www.portal.fragments;


import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.mcsgoc.www.portal.R;
import com.mcsgoc.www.portal.helper.Person;

public class PersonFragment extends Fragment {

    private static Person person;
    private TextView personContact;
    private TextView personEmail;
    private TextView personDesignation;
    private TextView personDept;
    private TextView personName;
    private TextView personCollege;

    public static PersonFragment newInstance(Person person) {
        PersonFragment fragment = new PersonFragment();
        Bundle args = new Bundle();
        PersonFragment.person = person;

        return fragment;
    }

    public PersonFragment() {
        // Required empty public constructor
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        View itemView = inflater.inflate(R.layout.fragment_person, container, false);
        initView(itemView);
        setContentToViews();
        return itemView;
    }

    private void setContentToViews() {
        personContact.setText(person.contact);
        personCollege.setText(person.college);
        personDept.setText(person.dept);
        personDesignation.setText(person.designation);
        personName.setText(person.name);
        personEmail.setText(person.email);
    }

    private void initView(View itemView) {
        personContact = (TextView) itemView.findViewById(R.id.textPersonContact);
        personDesignation = (TextView) itemView.findViewById(R.id.textPersonDesignation);
        personEmail = (TextView) itemView.findViewById(R.id.textPersonEmail);
        personDept = (TextView) itemView.findViewById(R.id.textPersonDept);
        personCollege = (TextView) itemView.findViewById(R.id.textPersonCollege);
        personName = (TextView) itemView.findViewById(R.id.textPersonName);
    }


}
