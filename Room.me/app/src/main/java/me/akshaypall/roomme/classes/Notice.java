package me.akshaypall.roomme.classes;

import org.joda.time.DateTime;

/**
 * Created by Akshay on 2016-09-30.
 */

public class Notice {
    private String mTitle;          //Title of notice
    private String mDetails;        //Body text of notice
    private DateTime mPostedTime;   //Date and time when notice was posted
    private DateTime mTargetTime;   //Target date for notice (used to create event in
                                    //  other roommates calendars.

    public Notice(String title, String details, DateTime targetTime){
        mTitle = title;
        mDetails = details;
        mPostedTime = DateTime.now();
        mTargetTime = targetTime;
    }


    public String getmTitle() {
        return mTitle;
    }

    public String getmDetails() {
        return mDetails;
    }

    public DateTime getmPostedTime() {
        return mPostedTime;
    }

    public DateTime getmTargetTime() {
        return mTargetTime;
    }
}
