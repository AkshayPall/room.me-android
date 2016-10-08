package me.akshaypall.roomme.classes;

import net.danlew.android.joda.DateUtils;

import org.joda.time.DateTime;
import org.joda.time.DateTimeUtils;
import org.joda.time.ReadableInstant;

import java.util.Date;

import me.akshaypall.roomme.App;
import me.akshaypall.roomme.R;

/**
 * Created by Akshay on 2016-09-30.
 */

public class Notice {
    private static final long WEEKS_IN_MONTH = 4; //to set date transition period to 1 month
    public static final int MINUTES_IN_DAY = 24 * 60;
    //(shows "September 5" instead of "4 weeks ago")
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

    public String formatDateTime(DateTime dateTime) {
        if (Math.abs(dateTime.minus(DateTime.now().getMillis()).getMillis())
                < android.text.format.DateUtils.MINUTE_IN_MILLIS) //is within the last minute
            return App.getAppContext().getResources().getString(R.string.notice_datetime_now);
        return (String)android.text.format.DateUtils.getRelativeDateTimeString(App.getAppContext(),
                dateTime.getMillis(), android.text.format.DateUtils.MINUTE_IN_MILLIS,
                WEEKS_IN_MONTH*android.text.format.DateUtils.WEEK_IN_MILLIS,
                android.text.format.DateUtils.FORMAT_ABBREV_RELATIVE);

    }

    public String getTargetDate() {
        return formatDateTime(mTargetTime);
    }
}
