package me.akshaypall.roomme.classes;

import android.os.Parcel;
import android.os.Parcelable;

import org.joda.time.DateTime;

import me.akshaypall.roomme.App;
import me.akshaypall.roomme.R;

/**
 * Created by Akshay on 2016-09-30.
 */

public class Notice implements Parcelable {
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

    //constructor of Notice from Parcel
    public Notice (Parcel parcel){
        mTitle = parcel.readString();
        mDetails = parcel.readString();
        mPostedTime = new DateTime(parcel.readLong());
        mTargetTime = new DateTime(parcel.readLong());
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


    //the following are implementations of parcelable to allow passing of Notice objects between
    //activities
    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(mTitle);
        dest.writeString(mDetails);
        dest.writeLong(mPostedTime.getMillis());
        dest.writeLong(mTargetTime.getMillis());
    }

    public static final Parcelable.Creator<Notice> CREATOR
            = new Parcelable.Creator<Notice>() {
        public Notice createFromParcel(Parcel in) {
            return new Notice(in);
        }
        public Notice[] newArray(int size) {
            return new Notice[size];
        }
    };
}
