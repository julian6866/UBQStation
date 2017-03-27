package com.ubqsys.station.ui;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.ubqsys.station.R;

//import com.pureiconsulting.tt.service.Ticket;

/**
 * A placeholder fragment containing a simple view.
 */
public class MonitorFragment extends Fragment {

    private static MonitorFragment instance = null;

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @return A new instance of fragment SyncTaskFragment.
     */
    public static MonitorFragment getInstance() {
        if(instance == null)
            instance = new MonitorFragment();

        Bundle args = new Bundle();
        //args.putString(instance.getString(R.string.WEBVIEW_URL_KEY), url);
        instance.setArguments(args);
        return instance;
    }

    public MonitorFragment() {
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_monitor, container, false);

    }


    @Override
    public void onResume() {

        super.onResume();
        View view = getView();

    }


}
