package com.ubqsys.station.ui;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.content.LocalBroadcastManager;
import android.view.View;
import android.support.design.widget.NavigationView;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.ubqsys.station.R;
import com.ubqsys.station.service.CloudSyncService;
import com.ubqsys.station.service.SyncTaskListener;

//https://github.com/codepath/android_guides/wiki/Fragment-Navigation-Drawer

public class MainActivity extends AppCompatActivity
        implements NavigationView.OnNavigationItemSelectedListener, SyncTaskListener {

    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);

        setContentView(com.ubqsys.station.R.layout.activity_main);

        Toolbar toolbar = (Toolbar) findViewById(com.ubqsys.station.R.id.toolbar);
        setSupportActionBar(toolbar);

        FloatingActionButton fab = (FloatingActionButton) findViewById(com.ubqsys.station.R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Snackbar.make(view, "Replace with your own action", Snackbar.LENGTH_LONG)
                        .setAction("Action", null).show();
            }
        });

        DrawerLayout drawer = (DrawerLayout) findViewById(com.ubqsys.station.R.id.drawer_layout);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, drawer, toolbar, com.ubqsys.station.R.string.navigation_drawer_open, com.ubqsys.station.R.string.navigation_drawer_close);
        drawer.setDrawerListener(toggle);
        toggle.syncState();

        NavigationView navigationView = (NavigationView) findViewById(com.ubqsys.station.R.id.nav_view);
        navigationView.setNavigationItemSelectedListener(this);


        // Instantiates a new DownloadStateReceiver
        SyncStatusReceiver mDownloadStateReceiver = new SyncStatusReceiver();
        // The filter's action is BROADCAST_ACTION
        IntentFilter mStatusIntentFilter = new IntentFilter(CloudSyncService.BROADCAST_SYNC_START);
        // Registers the DownloadStateReceiver and its intent filters
        LocalBroadcastManager.getInstance(this).registerReceiver(mDownloadStateReceiver, mStatusIntentFilter);

        IntentFilter mStatusIntentFilter2 = new IntentFilter(CloudSyncService.BROADCAST_SYNC_FINISH);
        LocalBroadcastManager.getInstance(this).registerReceiver(mDownloadStateReceiver, mStatusIntentFilter2);



    }

    @Override
    public void onBackPressed() {
        DrawerLayout drawer = (DrawerLayout) findViewById(com.ubqsys.station.R.id.drawer_layout);
        if (drawer.isDrawerOpen(GravityCompat.START)) {
            drawer.closeDrawer(GravityCompat.START);
        } else {
            super.onBackPressed();
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(com.ubqsys.station.R.menu.main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == com.ubqsys.station.R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    @SuppressWarnings("StatementWithEmptyBody")
    @Override
    public boolean onNavigationItemSelected(MenuItem item) {
        // Handle navigation view item clicks here.
        int id = item.getItemId();

        Fragment fragment = null;


        if (id == com.ubqsys.station.R.id.nav_camera) {
            // Handle the camera action
            fragment = new WebViewFragment();
            Bundle args = new Bundle();
            args.putString(getString(R.string.WEBVIEW_URL_KEY), "file:///android_asset/about.html");
            fragment.setArguments(args);

        } else if (id == com.ubqsys.station.R.id.nav_gallery) {

            fragment = new SyncTaskFragment();
            Bundle args = new Bundle();
            //args.putString(getString(R.string.WEBVIEW_URL_KEY), "file:///android_asset/about.html");
            fragment.setArguments(args);

        } else if (id == com.ubqsys.station.R.id.nav_slideshow) {

            fragment = new MonitorFragment();
            Bundle args = new Bundle();
            //args.putString(getString(R.string.WEBVIEW_URL_KEY), "file:///android_asset/about.html");
            fragment.setArguments(args);

        } else if (id == com.ubqsys.station.R.id.nav_manage) {
            fragment = new SettingFragment();
            Bundle args = new Bundle();
            //args.putString(getString(R.string.WEBVIEW_URL_KEY), "file:///android_asset/about.html");
            fragment.setArguments(args);

        } else if (id == com.ubqsys.station.R.id.nav_share) {


        } else if (id == com.ubqsys.station.R.id.nav_send) {


        }

        if(fragment != null && !fragment.isAdded()) {
            FragmentManager fragmentManager = getSupportFragmentManager();
            fragmentManager.beginTransaction()
                    .replace(R.id.container, fragment, "My_Tag")
                    .commit();

        }

        DrawerLayout drawer = (DrawerLayout) findViewById(com.ubqsys.station.R.id.drawer_layout);
        drawer.closeDrawer(GravityCompat.START);
        return true;
    }


    @Override
    public void onTaskStarted() {

        ProgressBar pb = (ProgressBar) findViewById(R.id.pbTicketLoading);
        pb.setVisibility(View.VISIBLE);

    }

    @Override
    public void onTaskFinished() {


        ProgressBar pb = (ProgressBar) findViewById(R.id.pbTicketLoading);
        pb.setVisibility(View.GONE);

        TextView textViewUnScanned = (TextView) findViewById(R.id.textViewUnScanned);
        //textViewUnScanned.setText("" + Ticket.getUnscannedTickets().size());

        TextView textViewScanned = (TextView) findViewById(R.id.textViewScanned);
        //textViewScanned.setText("" + Ticket.getScannedTickets().size());

    }

    private class SyncStatusReceiver extends BroadcastReceiver
    {
        // Prevents instantiation
        private SyncStatusReceiver() {
        }
        // Called when the BroadcastReceiver gets an Intent it's registered to receive
        @Override
        public void onReceive(Context context, Intent intent) {


            String action = intent.getAction();
            //Toast.makeText(context, action, Toast.LENGTH_SHORT).show();
            ProgressBar pb = null;
            ProgressBar pb1 = null;
            switch (action) {
                case CloudSyncService.BROADCAST_SYNC_START:

                    pb = (ProgressBar) findViewById(R.id.pbTicketPulling);
                    if(pb != null) pb.setVisibility(View.VISIBLE);


                    pb1 = (ProgressBar) findViewById(R.id.pbTicketLoading);
                    if(pb1 != null) pb1.setVisibility(View.VISIBLE);

                    break;
                case CloudSyncService.BROADCAST_SYNC_FINISH:
                    pb = (ProgressBar) findViewById(R.id.pbTicketPulling);
                    if(pb != null) pb.setVisibility(View.GONE);

                    pb1 = (ProgressBar) findViewById(R.id.pbTicketLoading);
                    if(pb1 != null) pb1.setVisibility(View.GONE);

                    TextView textViewScanned = (TextView) findViewById(R.id.textViewScanned);
                    //if(textViewScanned != null) textViewScanned.setText("" + Ticket.getScannedTickets().size());


                    TextView textViewUnScanned = (TextView) findViewById(R.id.textViewUnScanned);
                    //if(textViewUnScanned !=null) textViewUnScanned.setText("" + Ticket.getUnscannedTickets().size());

                    break;
                default:
                    break;

            }
        }
    }
}
