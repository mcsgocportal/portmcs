package com.mcsgoc.www.portal.helper;

import java.util.Date;

/**
 * Created by user on 03-01-2016.
 */
public class NewsItem {
    private String noticeContent;
    private String noticeTitle;
    private String section;
    private String branch;
    private String department;
    private Date date;
    private String objectID;


    public NewsItem(String objectID, String noticeTitle, String department, Date date) {
        this.noticeContent = noticeContent;
        this.noticeTitle = noticeTitle;
        this.section = section;
        this.branch = branch;
        this.department = department;
        this.date = date;
        this.objectID = objectID;
    }

    public String getObjectID() {
        return objectID;
    }

    public void setObjectID(String objectID) {
        this.objectID = objectID;
    }

    public String getNoticeContent() {
        return noticeContent;
    }

    public void setNoticeContent(String noticeContent) {
        this.noticeContent = noticeContent;
    }

    public String getNoticeTitle() {
        return noticeTitle;
    }

    public void setNoticeTitle(String noticeTitle) {
        this.noticeTitle = noticeTitle;
    }

    public String getBranch() {
        return branch;
    }

    public void setBranch(String branch) {
        this.branch = branch;
    }

    public String getDepartment() {
        return department;
    }

    public void setDepartment(String department) {
        this.department = department;
    }

    public Date getDate() {
        return date;
    }

    public void setDate(Date date) {
        this.date = date;
    }
}
