package com.mcsgoc.www.portal.helper;

/**
 * Created by user on 06-01-2016.
 */
public class FeesItem {
    private String course;
    private int firstInstallment, secondInstallment;

    public FeesItem(String course, int firstInstallment, int secondInstallment) {
        this.course = course;
        this.firstInstallment = firstInstallment;
        this.secondInstallment = secondInstallment;
    }

    public int getFirstInstallment() {
        return firstInstallment;
    }

    public void setFirstInstallment(int firstInstallment) {
        this.firstInstallment = firstInstallment;
    }

    public int getSecondInstallment() {
        return secondInstallment;
    }

    public void setSecondInstallment(int secondInstallment) {
        this.secondInstallment = secondInstallment;
    }

    public String getCourse() {
        return course;
    }

    public void setCourse(String course) {
        this.course = course;
    }
}