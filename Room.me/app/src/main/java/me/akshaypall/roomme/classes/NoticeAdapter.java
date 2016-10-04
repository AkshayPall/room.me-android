package me.akshaypall.roomme.classes;

import android.content.Context;
import android.support.v7.widget.ButtonBarLayout;
import android.support.v7.widget.CardView;
import android.support.v7.widget.RecyclerView;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import java.util.ArrayList;

import me.akshaypall.roomme.R;

import static android.view.View.GONE;

/**
 * Created by Akshay on 2016-10-01.
 */

public class NoticeAdapter extends RecyclerView.Adapter<NoticeAdapter.ViewHolder> {

    //List of notices saved here
    private ArrayList<Notice> mNotices = new ArrayList<>();
    private boolean mIsOnMainActivity;
    private Context mContext;

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

    public NoticeAdapter(ArrayList<Notice> notices, boolean isOnMainActivity, Context context){
        //Arguments required for adapter
        mNotices = notices;

        //if viewing notice list from Main Activity, do not show notify nor edit buttons
        mIsOnMainActivity = isOnMainActivity;

        mContext = context;
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

        //TODO: format postdate
        holder.mPostDate.setText(mNotices.get(position).getTargetDate());

        if (mIsOnMainActivity){
            //remove bottom margin for cardview
            ViewGroup.MarginLayoutParams cardLayoutParams =
                    (ViewGroup.MarginLayoutParams) holder.mCardView.getLayoutParams();
            cardLayoutParams.setMargins(
                    mContext.getResources().getDimensionPixelSize(R.dimen.notice_card_margin), 0,
                    mContext.getResources().getDimensionPixelSize(R.dimen.notice_card_margin), 0);
            holder.mCardView.setLayoutParams(cardLayoutParams);

            //make button invisible
            holder.mEditButton.setVisibility(GONE);
            holder.mNotifyButton.setVisibility(GONE);

            //truncate details text
            holder.mDetails.setEllipsize(TextUtils.TruncateAt.END);
            holder.mDetails.setSingleLine(true);
        }
    }

    @Override
    public int getItemCount() {
        return mNotices.size();
    }
}
