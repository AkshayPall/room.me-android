package me.akshaypall.roomme;

import android.animation.Animator;
import android.animation.ValueAnimator;
import android.content.DialogInterface;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewAnimationUtils;
import android.view.animation.AccelerateInterpolator;

import java.util.ArrayList;

import me.akshaypall.roomme.classes.Notice;
import me.akshaypall.roomme.classes.NoticeAdapter;
import me.akshaypall.roomme.classes.NoticeCardListener;

public class NoticeActivity extends AppCompatActivity implements NoticeCardListener {

    private static final String TEST_TAG = "TEST INFO------";
    private static final String CLICKED_TAG = "Clicked_Notice_Card";
    public static final String DELETE_TAG = "Deleted card";


    private ArrayList<Notice> mNotices;
    private NoticeAdapter mAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_notice);

        mNotices = new ArrayList<>();

        //TODO: take input of notice cards from main activity, but check if empty to pull data from
        //      database (i.e. if go to notice activity from another, non-main activity without crashing)

        if (this.getIntent().getExtras() != null){
            Bundle b = this.getIntent().getExtras().getBundle(MainActivity.NOTICE_CARDS_MAIN);
            mNotices = b.getParcelableArrayList(MainActivity.NOTICE_CARDS_MAIN);
        }

        //print to console if notice cards passed
        Log.d(TEST_TAG,
                mNotices.size() > 0 ? mNotices.get(0).getmTitle() : "NO NOTICES PASSED FROM MAIN");

        RecyclerView recyclerView = (RecyclerView)findViewById(R.id.notice_activity_recyclerview);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        mAdapter = new NoticeAdapter(mNotices, false, this);
        recyclerView.setAdapter(mAdapter);
    }

    @Override
    public void clickedCard(View card) {
        Log.d(CLICKED_TAG, "Notice card clicked!");
    }

    @Override
    public void swipedCard(View v, int position) {
        final int cardPosition = position;
        final View view = v;
        //open dialog box asking to remove card
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setTitle(R.string.delete_notice_dialog_title)
                .setMessage(R.string.delete_notice_dialog_message)
                .setNegativeButton(R.string.delete_notice_dialog_no, new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        dialog.dismiss(); //do nothing
                    }
                })
                .setPositiveButton(R.string.delete_notice_dialog_yes, new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        //remove said card
                        final float oldX = view.getX();
                        view.postOnAnimationDelayed(new Runnable() {
                            @Override
                            public void run() {
                                mNotices.remove(cardPosition);
                                mAdapter.notifyDataSetChanged();
                                Log.wtf(DELETE_TAG,
                                        "Deleted: "+mNotices.get(cardPosition).getmTitle());
                            }
                        }, 450);
                        view.animate()
                                .setDuration(450)
                                .setInterpolator(new AccelerateInterpolator())
                                .x(oldX+1500)
                                /**.setListener(new Animator.AnimatorListener() {

                                    @Override
                                    public void onAnimationStart(Animator animation) {

                                    }

                                    @Override
                                    public void onAnimationEnd(Animator animation) {
                                        //move first view back to normal spot
                                        view.setVisibility(View.INVISIBLE);
                                        view.setX(oldX);
                                        view.setVisibility(View.VISIBLE);
                                        Log.wtf(DELETE_TAG,
                                                "Deleted: "+mNotices.get(cardPosition).getmTitle());
                                        mNotices.remove(cardPosition);
                                        mAdapter.notifyDataSetChanged();
                                    }

                                    @Override
                                    public void onAnimationCancel(Animator animation) {

                                    }

                                    @Override
                                    public void onAnimationRepeat(Animator animation) {

                                    }
                                })**/;
                        dialog.dismiss();
                    }
                })
                .show();
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()){
            case R.id.home:
                supportFinishAfterTransition();
                return true;
        }
        return super.onOptionsItemSelected(item);
    }
}
