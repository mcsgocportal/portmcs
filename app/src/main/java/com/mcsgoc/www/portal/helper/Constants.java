package com.mcsgoc.www.portal.helper;


public interface Constants {

    // schema for directory table
    String TABLE_DIRECTORY = "Directory";
    String DIR_COL_NAME="name";
    String DIR_COL_COLEGE="college";
    String DIR_COL_CONTACT="contactNo";
    String DIR_COL_DEP="department";
    String DIR_COL_DESIG="designation";
    String DIR_COL_EMAIL = "email";

    //schema for user table
    String TABLE_USER ="User";
    String DIR_COL_USER_NAME="name";
    String DIR_COL_USER_USERNAME="username";
    String DIR_COL_USER_PASS="password";
    String DIR_COL_USER_EMAIL_VER="emailVerified";
    String DIR_COL_USER_EMAIL="email";
    String DIR_COL_USER_ROLLNO="roll_no";
    String DIR_COL_USER_CONTACT="contact_no";
    String DIR_COL_USER_YEAR="year";
    String DIR_COL_USER_BRANCH="branch";
    String DIR_COL_USER_COLLEGE="college";
    String DIR_COL_USER_SUBJECT="subject";
    String DIR_COL_USER_FACULTY_ID="faculty_id";
    String DIR_COL_USER_FACULTY="faculty";
    String DIR_COL_USER_STUDENT="student";
    String DIR_COLC_USER_PIC="profilePic";

    //schema for admission
    String DIR_COL_ADMI_ADD="address";
    String DIR_COL_ADMI_COLLEGE="college";
    String DIR_COL_ADMI_CONTACT="contactNo";
    String DIR_COL_ADMI_COURSE="course";
    String DIR_COL_ADMI_INTER_PERCENTAGE="inter_per";
    String DIR_COL_ADMI_NAME="name";

    //schema for aplication
    String DIR_COL_APLI_SUBJECT="subject";
    String DIR_COL_APLI_LEAVE_MSG="leave_msg";
    String DIR_COL_APLI_TYPE_OF_LEAVE="type_of_leave";
    String DIR_COL_APLI_HOD="hod";
    String DIR_COL_APLI_DATE="date";

    //schema for chats
    String DIR_COL_CHAT_ATTACHMENT="attachment";
    String DIR_COL_CHAT_DATE="date";
    String DIR_COL_CHAT_MSG="msg";
    String DIR_COL_CHAT_RECEIVER_ID="receiverId";
    String DIR_COL_CHAT_TIME="time";
    String DIR_COL_CHAT_USER_ID="userId";

    //schema for course
    String DIR_COL_COURSE_COLLEGE="college";
    String DIR_COL_COURSE_NAME="courseName";
    String DIR_COL_COURSE_SEM="sem";
    String DIR_COL_COURSE_YEAR="year";

    //schema for news
    String DIR_COL_NEWS_REF_NO="Ref_no";
    String DIR_COL_NEWS_ATTACHMENT="attachment";
    String DIR_COL_NEWS="news";
    String DIR_COL_NEWS_SUB="subject";
    String DIR_COL_NEWS_COLLEGE="college";
    String DIR_COL_NEWS_DEP="department";
    String DATE="date";
    String DIR_COL_NEWS_NOTICE="notice";

    //schema for suggestion
    String DIR_COL_SUGGESTION_DATE="date";
    String DIR_COL_SUGGESTION_USER_ID="userId";
    String DIR_COL_SUGGESTION_SUGGESTION="suggestion";

    // timetable schema
    String TIMETABLE = "timetable";
    String SUBJECT_NAME = "subjectName";
    String SUBJECT_CODE = "subjectCode";
    String TEACHER_NAME = "teacherName";
    String PERIOD_NUM = "periodNum";
    String SCHEDULE = "schedule";
    String DAY = "day";
    String BRANCH = "branch";
    String SEMESTER = "semister";
    String PLACE = "place";
    String SECTION = "section";

    // fees schema
    String FEES = "fees";
    String COURSE = "course";
    String FIRST_INSTALLMENT = "firstInstallment";
    String SECOND_INSTALLMENT = "secondInstallment";
}
