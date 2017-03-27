package com.ubqsys.station.service;

import android.bluetooth.BluetoothAdapter;
import android.content.Intent;

/**
 * Created by Julian on 3/16/2017.
 *
 */
public class BluetoothHelper {


    public void test() {

        // Get the BluetoothAdapter
        BluetoothAdapter mBluetoothAdapter = BluetoothAdapter.getDefaultAdapter();
        if (mBluetoothAdapter == null) {
            // Device does not support Bluetooth

        } else {

            if (!mBluetoothAdapter.isEnabled()) {
                Intent enableBtIntent = new Intent(BluetoothAdapter.ACTION_REQUEST_ENABLE);
                //startActivityForResult(enableBtIntent, REQUEST_ENABLE_BT);


            }
        }
    }


}
