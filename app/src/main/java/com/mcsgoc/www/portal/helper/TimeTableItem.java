package com.mcsgoc.www.portal.helper;

/**
 * Created by user on 06-01-2016.
 */
public class TimeTableItem {
    private String branch, day, semister, place, section;
    private String teacher, subject, code, schedule;
    private String period;
    private int colorCode;

    public TimeTableItem(String teacher, String subject, String code, String period, String schedule, String branch, String day, String semister, String place, String section) {
        this.teacher = teacher;
        this.subject = subject;
        this.code = code;
        this.period = period;
        this.schedule = schedule;
        this.branch = branch;
        this.day = day;
        this.semister = semister;
        this.place = place;
        this.section = section;
    }

    public TimeTableItem(String teacherName, String subjectName, String subjectCode, String place, String schedule) {
        this.teacher = teacherName;
        this.subject = subjectName;
        this.code = subjectCode;
        this.place = place;
        this.schedule = schedule;


    }

    public int getColorCode() {
        return colorCode;
    }

    public void setColorCode(int colorCode) {
        this.colorCode = colorCode;
    }

    public String getSchedule() {
        return schedule;
    }

    public void setSchedule(String schedule) {
        this.schedule = schedule;
    }

    public String getTeacher() {
        return teacher;
    }

    public void setTeacher(String teacher) {
        this.teacher = teacher;
    }

    public String getSubject() {
        return subject;
    }

    public void setSubject(String subject) {
        this.subject = subject;
    }

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public String getPeriod() {
        return period;
    }

    public void setPeriod(String period) {
        this.period = period;
    }

    public String getBranch() {
        return branch;
    }

    public void setBranch(String branch) {
        this.branch = branch;
    }

    public String getDay() {
        return day;
    }

    public void setDay(String day) {
        this.day = day;
    }

    public String getSemister() {
        return semister;
    }

    public void setSemister(String semister) {
        this.semister = semister;
    }

    public String getPlace() {
        return place;
    }

    public void setPlace(String place) {
        this.place = place;
    }

    public String getSection() {
        return section;
    }

    public void setSection(String section) {
        this.section = section;
    }
}
