package me.akshaypall.roomme.classes;

import android.content.Context;
import android.support.v7.widget.CardView;

/**
 * Created by Akshay on 2016-10-26.
 */

public class NoticeCardView extends CardView {
    public NoticeCardView(Context context) {
        super(context);
    }

    @Override
    protected void onAnimationEnd() {
        //caution, only animation on notice should be
        super.onAnimationEnd();
    }
}
