package me.akshaypall.roomme;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;

import java.util.ArrayList;

import me.akshaypall.roomme.classes.Notice;

public class NoticeActivity extends AppCompatActivity {

    private static final String TEST_TAG = "TEST INFO------";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_notice);

        ArrayList<Notice> notices = new ArrayList<>();

        //TODO: take input of notice cards from main activity, but check if empty to pull data from
        //      database (i.e. if go to notice activity from another, non-main activity without crashing)

        if (this.getIntent().getExtras() != null){
            Bundle b = this.getIntent().getExtras().getBundle(MainActivity.NOTICE_CARDS_MAIN);
            notices = b.getParcelableArrayList(MainActivity.NOTICE_CARDS_MAIN);
        }

        Log.d(TEST_TAG,
                notices.size() > 0 ? notices.get(0).getmTitle() : "NO NOTICES PASSED FROM MAIN");
    }
}
