package me.akshaypall.roomme.classes;

import android.content.Context;
import android.content.Intent;
import android.support.design.widget.SwipeDismissBehavior;
import android.support.v7.widget.ButtonBarLayout;
import android.support.v7.widget.CardView;
import android.support.v7.widget.RecyclerView;
import android.text.TextUtils;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.OvershootInterpolator;
import android.view.animation.ScaleAnimation;
import android.widget.Button;
import android.widget.TextView;

import java.util.ArrayList;

import me.akshaypall.roomme.App;
import me.akshaypall.roomme.R;

import static android.view.View.GONE;

/**
 * Created by Akshay on 2016-10-01.
 */

public class NoticeAdapter extends RecyclerView.Adapter<NoticeAdapter.ViewHolder> {

    private static final String TOUCH = "touched";
    //List of notices saved here
    private ArrayList<Notice> mNotices = new ArrayList<>();
    private boolean mIsOnMainActivity;
    private NoticeCardListener mListener;

    //for animations:
    private float mCardX;
    private float mOrigX;
    private float mLastX;

    public static class ViewHolder extends RecyclerView.ViewHolder{
        private final CardView mCardView;
        //All views in notice card
        private final TextView mTitle;
        private final TextView mPostDate;
        private final TextView mDetails;
        //if in notices view
        private final Button mNotifyButton;
        private final Button mEditButton;

        public ViewHolder (View v){
            super(v);
            mCardView = (CardView)v.findViewById(R.id.item_notice_cardview);
            mTitle = (TextView)v.findViewById(R.id.item_notice_title);
            mPostDate = (TextView)v.findViewById(R.id.item_notice_pdate);
            mDetails = (TextView)v.findViewById(R.id.item_notice_details);
            mNotifyButton = (Button)v.findViewById(R.id.item_notice_notify_button);
            mEditButton = (Button)v.findViewById(R.id.item_notice_edit_button);
        }
    }

    public NoticeAdapter(ArrayList<Notice> notices, boolean isOnMainActivity, NoticeCardListener listener){
        //Arguments required for adapter
        mNotices = notices;

        //if viewing notice list from Main Activity, do not show notify nor edit buttons
        mIsOnMainActivity = isOnMainActivity;

        //to watch for Notice card clicks
        mListener = listener;
    }

    @Override
    public NoticeAdapter.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        //create v
        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.notice_item, parent, false);
        //can set viewholders size, margins, padding, etc. here

        ViewHolder vh = new ViewHolder(v);
        return vh;
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {
        //changing the contents of a view
        holder.mTitle.setText(mNotices.get(position).getmTitle());
        holder.mDetails.setText(mNotices.get(position).getmDetails());

        //set the date for the Notice to the relative date string
        holder.mPostDate.setText(mNotices.get(position).getTargetDate());

        if (mIsOnMainActivity){
            //remove bottom margin for cardview if in the truncated card view (for main activity)
            ViewGroup.MarginLayoutParams cardLayoutParams =
                    (ViewGroup.MarginLayoutParams) holder.mCardView.getLayoutParams();
            cardLayoutParams.setMargins(
                    App.getAppContext().getResources().getDimensionPixelSize(R.dimen.notice_card_margin), 0,
                    App.getAppContext().getResources().getDimensionPixelSize(R.dimen.notice_card_margin), 0);
            holder.mCardView.setLayoutParams(cardLayoutParams);

            //make button invisible
            holder.mEditButton.setVisibility(GONE);
            holder.mNotifyButton.setVisibility(GONE);

            //truncate details text
            holder.mDetails.setEllipsize(TextUtils.TruncateAt.END);
            holder.mDetails.setSingleLine(true);

            holder.mCardView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    mListener.clickedCard(v);
                }
            });
        } else { //for animation on notice activity only
            //float xOr = holder.mCardView.getX();
            //float yOr = holder.mCardView.getY();
            holder.mCardView.setOnTouchListener(new View.OnTouchListener() {
                @Override
                public boolean onTouch(View v, MotionEvent event) {
                    switch (event.getAction()){
                        case MotionEvent.ACTION_DOWN:
                            Log.d(TOUCH, "pressed down view");
                            mCardX = v.getX();
                            mOrigX = v.getX();
                            mLastX = event.getX();
                            break;
                        case MotionEvent.ACTION_CANCEL:
                        case MotionEvent.ACTION_UP:
                            Log.d(TOUCH, "let go of card");
                            resetCard(v, mOrigX);
                            break;
                        case MotionEvent.ACTION_MOVE:
                            Log.d(TOUCH, "moved card");
                            mCardX += event.getX()-mLastX;
                            v.setX(mCardX);
                            break;

                    }
                    return true;
                }
            });
            //holder.mCardView.startAnimation(new ScaleAnimation(xOr, xOr+100, yOr, yOr+100));
        }
    }

    private void resetCard(View v, float origX) {
        mCardX = origX;
        v.animate()
                .setDuration(200)
                .setInterpolator(new OvershootInterpolator())
                .x(mCardX);
    }

    @Override
    public int getItemCount() {
        return mNotices.size();
    }
}
