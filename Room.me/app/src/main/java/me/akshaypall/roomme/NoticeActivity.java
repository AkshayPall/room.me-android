package me.akshaypall.roomme;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewAnimationUtils;

import java.util.ArrayList;

import me.akshaypall.roomme.classes.Notice;
import me.akshaypall.roomme.classes.NoticeAdapter;
import me.akshaypall.roomme.classes.NoticeCardListener;

public class NoticeActivity extends AppCompatActivity implements NoticeCardListener {

    private static final String TEST_TAG = "TEST INFO------";
    private static final String CLICKED_TAG = "Clicked_Notice_Card";

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

        //print to console if notice cards passed
        Log.d(TEST_TAG,
                notices.size() > 0 ? notices.get(0).getmTitle() : "NO NOTICES PASSED FROM MAIN");

        RecyclerView recyclerView = (RecyclerView)findViewById(R.id.notice_activity_recyclerview);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        recyclerView.setAdapter(new NoticeAdapter(notices, false, this));
    }

    @Override
    public void clickedCard(View card) {
        Log.d(CLICKED_TAG, "Notice card clicked!");
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
