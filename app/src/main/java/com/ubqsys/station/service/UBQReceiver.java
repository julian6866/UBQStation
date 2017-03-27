package com.ubqsys.station.service;

import android.bluetooth.BluetoothDevice;
import android.bluetooth.BluetoothSocket;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.os.ParcelUuid;
import android.provider.SyncStateContract;
import android.util.Log;

import com.ubqsys.station.R;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.util.UUID;

/**
 * Created by Julian on 3/19/2017.
 *
 * This is our UBQ Receiver (Bluetooth Device)
 */
public class UBQReceiver implements Runnable {

    private static final String TAG = "MY_APP_DEBUG_TAG";

    //private Handler mHandler; // handler that gets info from Bluetooth service

    private Thread thread = null;

    private boolean isEnabled = true;

    private BluetoothDevice mDevice;

    private BluetoothSocket socket;

    private InputStream mmInStream;

    private OutputStream mmOutStream;

    private byte[] mmBuffer; // mmBuffer store for the stream

    // Defines several constants used when transmitting messages between the
    // service and the UI.
    private interface MessageConstants {
        public static final int MESSAGE_READ = 0;
        public static final int MESSAGE_WRITE = 1;
        public static final int MESSAGE_TOAST = 2;

        // ... (Add other message types here as needed.)
    }

    /**
     * Constructor
     */
    public UBQReceiver(BluetoothDevice device) {

        //this.mHandler = handler;
        this.mDevice = device;

    }

    public synchronized void connect() {

        try {

            Log.i("test", "connect() ....");

            ParcelUuid[] uuids = mDevice.getUuids();
            for(int i=0; uuids != null && i < uuids.length; i++) {
                UUID uuid = uuids[i].getUuid();
                Log.i("uuid-" + i + ":", uuid.toString());
            }


            socket = mDevice.createInsecureRfcommSocketToServiceRecord(UUID.fromString("00001101-0000-1000-8000-00805F9B34FB")); //00000000-0000-1000-8000-00805F9B34FB
            socket.connect();

            mmInStream = socket.getInputStream();

        } catch (IOException e) {
            Log.e(TAG, "Error occurred when creating input stream", e);


            Log.i("test", "error" + e.toString());
        }

        /**/
        try {
            mmOutStream = socket.getOutputStream();
        } catch (IOException e) {
            Log.e(TAG, "Error occurred when creating output stream", e);
        }
         /**/

        isEnabled = true;
        thread = new Thread(this);
        thread.start();
    }

    public void run() {

        mmBuffer = new byte[1024];
        int numBytes; // bytes returned from read()

        // Keep listening to the InputStream until an exception occurs.
        while (isEnabled) {
            try {

                Log.i("test", "in while reading() ....");
                // Read from the InputStream.
                numBytes = mmInStream.read(mmBuffer);
                String readMessage = new String(mmBuffer, 0, numBytes);
                Log.i("data" , readMessage);
                Log.d(TAG, "Message :: " + readMessage);

                 /**
                // Send the obtained bytes to the UI activity.
                Message readMsg = mHandler.obtainMessage(
                        MessageConstants.MESSAGE_READ, numBytes, -1,
                        mmBuffer);

                readMsg.sendToTarget();
                 **/

            } catch (IOException e) {
                Log.d(TAG, "Input stream was disconnected", e);
                isEnabled = false;
                break;
            }
        }
    }


    // Call this from the main activity to send data to the remote device.
    public void write(byte[] bytes) {
        try {
            mmOutStream.write(bytes);

            // Share the sent message with the UI activity.
            Message writtenMsg = mHandler.obtainMessage(
                    MessageConstants.MESSAGE_WRITE, -1, -1, mmBuffer);
            writtenMsg.sendToTarget();
        } catch (IOException e) {
            Log.e(TAG, "Error occurred when sending data", e);

            // Send a failure message back to the activity.
            Message writeErrorMsg =
                    mHandler.obtainMessage(MessageConstants.MESSAGE_TOAST);
            Bundle bundle = new Bundle();
            bundle.putString("toast","Couldn't send data to the other device");
            writeErrorMsg.setData(bundle);
            mHandler.sendMessage(writeErrorMsg);
        }
    }

    // Call this method from the main activity to shut down the connection.
    public void stop() {
        try {
            socket.close();
            isEnabled = false;
            notify();
        } catch (IOException e) {
            Log.e(TAG, "Could not close the connect socket", e);
        }
    }

    /**
     * The Handler that gets information back from the BluetoothChatService
     */
    private final Handler mHandler = new Handler() {
        @Override
        public void handleMessage(Message msg) {

            Log.i("Test", msg.toString());
        }
    };

}
