package me.akshaypall.roomme;

import android.content.Intent;
import android.os.Bundle;
import android.support.design.internal.ParcelableSparseArray;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.support.design.widget.NavigationView;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;

import org.joda.time.DateTime;

import java.util.ArrayList;

import me.akshaypall.roomme.classes.Notice;
import me.akshaypall.roomme.classes.NoticeAdapter;
import me.akshaypall.roomme.classes.NoticeCardListener;

public class MainActivity extends AppCompatActivity
        implements NavigationView.OnNavigationItemSelectedListener, NoticeCardListener {

    public static final String NOTICE_CARDS_MAIN = "notices_main_to_notice_A";
    private ArrayList<Notice> mNotices;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        /** Template setup for navigation drawer and toolbar **/
        setContentView(R.layout.activity_main);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer.setDrawerListener(toggle);
        toggle.syncState();

        NavigationView navigationView = (NavigationView) findViewById(R.id.nav_view);
        navigationView.setNavigationItemSelectedListener(this);

        //fake notice data (temporary)
        mNotices = new ArrayList<>();
        for (int i = -5; i < 5; i++){
            DateTime date = (i < 0) ? DateTime.now().minusDays((-i)*6) :
                    DateTime.now().plusMinutes(i*6*Notice.MINUTES_IN_DAY);
                    //to test relative datetime string (in past and future)

            mNotices.add(i+5, new Notice("Ayy"+i, "afsdsfdhjka j dhsakjdajk " +
                    "h dksahd kashdjas dkj sajdahskjdhsak kjkdhjkhdak---"+i,
                    date));
            }


        /** Setup for Notice recyclerview && adapter **/

        RecyclerView noticeView = (RecyclerView)findViewById(R.id.main_notices_recyclerview);
        RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(this);
        noticeView.setLayoutManager(layoutManager);
        //create and set adapter
        NoticeAdapter adapter = new NoticeAdapter(mNotices, true, this);
        noticeView.setAdapter(adapter);

    }

    @Override
    public void onBackPressed() {
        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        if (drawer.isDrawerOpen(GravityCompat.START)) {
            drawer.closeDrawer(GravityCompat.START);
        } else {
            super.onBackPressed();
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    @SuppressWarnings("StatementWithEmptyBody")
    @Override
    public boolean onNavigationItemSelected(MenuItem item) {
        // Handle navigation view item clicks here.
        int id = item.getItemId();

        if (id == R.id.nav_notices) {
            // TODO: go to notices
        } else if (id == R.id.nav_spending) {
            //TODO: go to spending
        } else if (id == R.id.nav_settings) {
            //TODO: go to settings
        }

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        drawer.closeDrawer(GravityCompat.START);
        return true;
    }

    @Override
    public void clickedCard(View card) {
        Intent i = new Intent(this, NoticeActivity.class);
        Bundle b = new Bundle();
        b.putParcelableArrayList(NOTICE_CARDS_MAIN, mNotices);
        i.putExtra(NOTICE_CARDS_MAIN, b);
        //TODO: start activity with transition of cards
        startActivity(i);
    }
}
