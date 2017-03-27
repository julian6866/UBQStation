package com.ubqsys.station.service;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;

/**
 * Created by Julian on 3/25/2017.
 *
 * This class impleements BroadcaseReeiver.
 *
 * Its onReceive() method is called when the device is booted (as defined in AndroidManifest.xml)
 * We implement this method to ensure services are initiated appropriately (according to user's configuration settings)
 *
 *
 */
public class DeviceBootReceiver extends BroadcastReceiver {

    @Override
    public void onReceive(Context context, Intent intent) {


        // TODO: ensure CloudSyncService is started if user sets so.
        // For Cloud Sync.
        // TODO: Note: We may NOT enable cloud sync service unless user explicitly trigger/start it from UI.


        // TODO: ensure UBQReceiver is started to collect data via bluetooth if user sets so.
        // For Bluetooth.
        // TODO: Note: We may NOT enable data collection unless user explicitly trigger/start it from UI.

    }
}
