package me.akshaypall.roomme.classes;

import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;

import me.akshaypall.roomme.R;

/**
 * Created by Akshay on 2016-10-01.
 */

public class NoticeAdapter extends RecyclerView.Adapter<NoticeAdapter.ViewHolder> {

    //List of notices saved here
    ArrayList<Notice> mNotices = new ArrayList<>();

    public static class ViewHolder extends RecyclerView.ViewHolder{
        public TextView title;
        public ViewHolder (TextView v){
            super(v);
            title = v;
        }
    }

    public NoticeAdapter(ArrayList<Notice> notices){
        //Arguments required for adapter
        mNotices = notices;
    }

    @Override
    public NoticeAdapter.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        //create v
        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.notice_item, parent, false);
        //can set viewholders size, margins, padding, etc. here


        return new ViewHolder((TextView) v);
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {
        //changing the contents of a view
        holder.title.setText("Test22222");
    }

    @Override
    public int getItemCount() {
        return mNotices.size();
    }
}
