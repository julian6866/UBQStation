package com.ubqsys.station.service;

import android.content.Context;
import android.os.AsyncTask;
import android.util.Log;

/**
 * Created by julianzhu on 12/2/15.
 */
public class SyncTask extends AsyncTask<String, Integer, Integer> {

    private final SyncTaskListener listener;

    private Context context = null;

    public SyncTask(Context context, SyncTaskListener listener) {

        this.context = context;
        this.listener = listener;

    }

    @Override
    protected Integer doInBackground(String... params) {

        // do initialization here ...

        try {

            //Ticket.loadTickets(context);

        } catch(Exception ioe) {
            Log.e("Error", ioe.getMessage());
            ioe.printStackTrace();
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
    protected void onPreExecute() {
        super.onPreExecute();

        listener.onTaskStarted();
        //ProgressBar pbHeaderProgress = (ProgressBar) context.get.findViewById(R.id.progressBarArticleLoading);

        //pbHeaderProgress.setVisibility(View.VISIBLE);
    }

    @Override
    protected void onPostExecute(Integer integer) {

        super.onPostExecute(integer);
        // Notifiy whoever needs to be notified.
        listener.onTaskFinished();

    }
}
