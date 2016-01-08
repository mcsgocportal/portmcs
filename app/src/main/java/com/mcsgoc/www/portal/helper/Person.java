package com.mcsgoc.www.portal.helper;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Lab3 on 10/18/2015.
 */
public class Person {
    public String name;
    public String college;
    public String designation;
    public String dept;
    public String contact;
    public String email;


    public Person(String name, String college, String designation, String department, String contactNo, String email) {
        this.name = name;
        this.college = college;
        this.designation = designation;
        this.dept = department;
        this.contact = contactNo;
        this.email = email;
    }

    @Override
    public String toString() {
        return "Person{" +
                "name='" + name + '\'' +
                ", college='" + college + '\'' +
                ", designation='" + designation + '\'' +
                ", dept='" + dept + '\'' +
                ", contact='" + contact + '\'' +
                '}';
    }
}