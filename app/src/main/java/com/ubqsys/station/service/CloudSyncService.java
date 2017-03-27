package com.ubqsys.station.service;

import android.app.IntentService;
import android.content.Intent;
import android.support.annotation.Nullable;
import android.support.v4.content.LocalBroadcastManager;
import android.util.Log;

//import com.pureiconsulting.tt.service.Ticket;

/**
 * Created by julianzhu on 12/1/15.
 *
 * This IntentService (service) is started.
 *
 * It runs all the time to sync local base system with the cloud (bi-directional) as configured by user.
 *
 *
 */
public class CloudSyncService extends IntentService {

    public static final String BROADCAST_SYNC_START = "com.ubqsys.station.SYNC_START";
    public static final String BROADCAST_SYNC_FINISH = "com.ubqsys.station.SYNC_FINISH";

    private static boolean autoSync = false;

    private static boolean isRunning = false;

    private static int sleepTime = 5000; // default 0 seconds (disabled)

    public CloudSyncService() {
        super(CloudSyncService.class.getName());
    }

    public static boolean isAutoSync() {
        return autoSync;
    }

    public static void setAutoSync(boolean theAutoSync) {
        autoSync = theAutoSync;
    }

    public static int getSleepTime() {
        return sleepTime;
    }

    public static void setSleepTime(int theTime) {
        sleepTime = theTime; // in miliseconds;
    }

    public static boolean isRunning() {
        return isRunning;
    }

    @Override
    protected void onHandleIntent(Intent workIntent) {

        // Gets data from the incoming Intent
        //String dataString = workIntent.getDataString();

        // do work here ..
        isRunning = true;

        while(true) {

            try {

                Log.i("Service", "in service ...");

                Thread.sleep(sleepTime);

                if(autoSync) {
                    Intent localIntent = new Intent(BROADCAST_SYNC_START);
                    // Puts the status into the Intent
                    // .putExtra(Constants.EXTENDED_DATA_STATUS, status);
                    // Broadcasts the Intent to receivers in this app.
                    LocalBroadcastManager.getInstance(this).sendBroadcast(localIntent);

                    // First of all, let's send all scanned ticket to the cloud:
                    //Ticket.uploadTickets(getApplicationContext());
                    //Ticket.loadTickets(getApplicationContext());

                    Intent localIntent2 = new Intent(BROADCAST_SYNC_FINISH);
                    // Puts the status into the Intent
                    // .putExtra(Constants.EXTENDED_DATA_STATUS, status);
                    // Broadcasts the Intent to receivers in this app.
                    LocalBroadcastManager.getInstance(this).sendBroadcast(localIntent2);

                    // now let's reload the tickets from the cloud
                    //Ticket.loadTickets(getApplicationContext());
                    //ArrayList<Ticket> tickets = loadTickets(getApplicationContext());
                }
            } catch(InterruptedException ire) {


            }

        }

    }

    @Override
    public int onStartCommand(@Nullable Intent intent, int flags, int startId) {

        return super.onStartCommand(intent, flags, startId);
    }
}
