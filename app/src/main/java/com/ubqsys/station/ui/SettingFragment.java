package com.ubqsys.station.ui;

import android.Manifest;
import android.app.Activity;
import android.bluetooth.BluetoothAdapter;
import android.bluetooth.BluetoothDevice;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.graphics.Color;
import android.os.Bundle;
import android.os.ParcelUuid;
import android.os.Parcelable;
import android.support.v4.app.Fragment;
import android.support.v4.content.LocalBroadcastManager;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CompoundButton;
import android.widget.ImageButton;
import android.widget.TextView;
import android.widget.Toast;
import android.widget.ToggleButton;

import com.ubqsys.station.R;
import com.ubqsys.station.service.UBQReceiver;

//import com.pureiconsulting.tt.service.Ticket;

/**
 * A placeholder fragment containing a simple view.
 */
public class SettingFragment extends Fragment {

    private static SettingFragment instance = null;

    // Get the BluetoothAdapter
    final BluetoothAdapter mBluetoothAdapter = BluetoothAdapter.getDefaultAdapter();

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @return A new instance of fragment SyncTaskFragment.
     */
    public static SettingFragment getInstance() {
        if(instance == null)
            instance = new SettingFragment();

        Bundle args = new Bundle();
        //args.putString(instance.getString(R.string.WEBVIEW_URL_KEY), url);
        instance.setArguments(args);
        return instance;
    }

    public SettingFragment() {
    }

    // Create a BroadcastReceiver for ACTION_FOUND.
    // This receiver is subscribed by this fragement's activity and will be called
    // when bluetooth event is broadcasted.
    protected final BroadcastReceiver mReceiver = new BroadcastReceiver() {

        public void onReceive(Context context, Intent intent) {

            String action = intent.getAction();
            Toast.makeText(getContext(), "OnReceived ..." + action, Toast.LENGTH_SHORT).show();

            if (BluetoothDevice.ACTION_FOUND.equals(action)) {
                // Discovery has found a device. Get the BluetoothDevice
                // object and its info from the Intent.
                BluetoothDevice device = intent.getParcelableExtra(BluetoothDevice.EXTRA_DEVICE);
                String deviceName = device.getName();
                String deviceHardwareAddress = device.getAddress(); // MAC address
                Parcelable[] uuidExtra = intent.getParcelableArrayExtra(BluetoothDevice.EXTRA_UUID);
                //Log.i("uuiExtra-length", "Test " + uuidExtra.length);

                Toast.makeText(getContext(), "Bluetooth Device Found " + deviceName + ":" + deviceHardwareAddress, Toast.LENGTH_SHORT).show();

                if(mBluetoothAdapter != null) {

                    //BluetoothDevice device = mBluetoothAdapter.getRemoteDevice(deviceHardwareAddress);

                    UBQReceiver receiver = new UBQReceiver(device);
                    receiver.connect();

                }


            } else if(BluetoothAdapter.ACTION_STATE_CHANGED.equals(action)) {

            } else if(BluetoothAdapter.ACTION_DISCOVERY_STARTED.equals(action)) {

            } else if(BluetoothAdapter.ACTION_DISCOVERY_FINISHED.equals(action)) {

            }
        }
    };

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {


        // Register for broadcasts when a device is discovered.
        Toast.makeText(getContext(), "register receiver", Toast.LENGTH_SHORT).show();
        IntentFilter filter = new IntentFilter();
        filter.addAction(BluetoothAdapter.ACTION_STATE_CHANGED);
        filter.addAction(BluetoothDevice.ACTION_FOUND);
        filter.addAction(BluetoothAdapter.ACTION_DISCOVERY_STARTED);
        filter.addAction(BluetoothAdapter.ACTION_DISCOVERY_FINISHED);
        getActivity().registerReceiver(mReceiver, filter);

        return inflater.inflate(R.layout.fragment_setting, container, false);

    }


    @Override
    public void onResume() {

        super.onResume();
        View view = getView();

        final ToggleButton bnEnableBT = (ToggleButton) view.findViewById(R.id.bnEnableBT);


        if (mBluetoothAdapter == null) {
            // Device does not support Bluetooth
            Toast.makeText(getContext(), "Bluetooth is not supported on this device", Toast.LENGTH_SHORT).show();
            bnEnableBT.setEnabled(false);
            bnEnableBT.setChecked(false);
        }

        bnEnableBT.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {

            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {

                if(isChecked)
                {
                    // Bluetooth is not enabled
                    if (!mBluetoothAdapter.isEnabled()) {

                        Toast.makeText(getContext(), "Bluetooth is not enabled on this device", Toast.LENGTH_SHORT).show();

                        // enable discoverability (discovery mode so that local device can be discovered by other devices)
                        Intent enableBtIntent = new Intent(BluetoothAdapter.ACTION_REQUEST_ENABLE);
                        startActivityForResult(enableBtIntent, 0);

                    }

                }
                else
                {

                    // Bluetooth is not enabled
                    if (mBluetoothAdapter.isEnabled()) {

                        Toast.makeText(getContext(), "Bluetooth is enabled on this device", Toast.LENGTH_SHORT).show();

                        // enable discoverability (discovery mode so that local device can be discovered by other devices)
                        mBluetoothAdapter.disable();

                    }
                }
            }
        });

        // this button to start discover bluetooth device (UBQ Receiver)
        final ImageButton bnAddReceiver = (ImageButton) view.findViewById(R.id.bnAddReceiver);
        bnAddReceiver.setOnClickListener(new CompoundButton.OnClickListener() {
            @Override
            public void onClick(View v) {

                //Toast.makeText(getContext(), "Bluetooth is enabled on this device", Toast.LENGTH_SHORT).show();

                if(mBluetoothAdapter != null) {

                    int MY_PERMISSIONS_REQUEST_ACCESS_COARSE_LOCATION = 1;
                    requestPermissions(new String[]{Manifest.permission.ACCESS_COARSE_LOCATION}, 1);

                    Toast.makeText(getContext(), "Bluetooth start discovery ...", Toast.LENGTH_SHORT).show();
                    if (mBluetoothAdapter.isDiscovering()) {
                        mBluetoothAdapter.cancelDiscovery();
                    }
                    mBluetoothAdapter.startDiscovery();
                }

            }
        });

        final TextView tvBT1 = (TextView) view.findViewById(R.id.tvBT1);
        tvBT1.setOnClickListener(new TextView.OnClickListener() {
            @Override
            public void onClick(View view) {

            }
        });

    }

    @Override
    public void onPause() {
        super.onPause();
     }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        // Don't forget to unregister the ACTION_FOUND receiver.
        Toast.makeText(getContext(), "de-register receiver", Toast.LENGTH_SHORT).show();
        getActivity().unregisterReceiver(mReceiver);

    }

    // handle Bluetooth enabling activity
    public void onActivityResult(int requestCode, int resultCode, Intent intent) {

        Toast.makeText(getContext(), "onActivityResult called", Toast.LENGTH_SHORT).show();

        if (requestCode == 0) {

            if (resultCode == Activity.RESULT_OK) {
                //String contents = intent.getStringExtra("SCAN_RESULT"); // This will contain your scan result
                //String format = intent.getStringExtra("SCAN_RESULT_FORMAT");
                Toast.makeText(getContext(), "Bluetooth is now enabled on this device", Toast.LENGTH_SHORT).show();

            }
        } else {

            ;
        }
    }


}
