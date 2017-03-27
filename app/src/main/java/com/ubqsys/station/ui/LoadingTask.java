package com.ubqsys.station.ui;

import android.content.Context;
import android.content.Intent;
import android.os.AsyncTask;
import android.util.Log;

import com.ubqsys.station.data.DBTest;
import com.ubqsys.station.service.CloudSyncService;


/**
 * Created by julianzhu on 11/15/15.
 */
public class LoadingTask extends AsyncTask<String, Integer, Integer> {

    public interface LoadingTaskFinishedListener {
        void onTaskFinished();
    }

    private final LoadingTaskFinishedListener finishedListener;

    private Context context = null;

    public LoadingTask(Context context, LoadingTaskFinishedListener finishedListener) {

        this.context = context;
        this.finishedListener = finishedListener;
    }

    @Override
    protected Integer doInBackground(String... params) {

        // do initialization here ...

        try {

            //ActivityHelper.init(context);
            Log.i("Test", "Loading config ...");

            //Config config = Config.getInstance(context);

            // Initializing TextToSpeech Engine
            //Voice.getInstance(context);

            // Start Cloud Sync Service if it is not running
            if(!CloudSyncService.isRunning()) {
                Intent intent = new Intent(context, CloudSyncService.class);
                context.startService(intent);
            }

            DBTest.demo(this.context);
            Thread.sleep(800);


        } catch (InterruptedException ignore) {

        }

        // could also update progress status here.
        return 0;
    }

    @Override
    protected void onProgressUpdate(Integer... values) {

        super.onProgressUpdate(values);
        // Could add a progress bar to update status ....
    }

    @Override
    protected void onPostExecute(Integer integer) {

        super.onPostExecute(integer);
        // Notifiy whoever needs to be notified.
        finishedListener.onTaskFinished();
    }
}