package com.ubqsys.station.ui;

import android.content.Context;
import android.os.AsyncTask;
import android.util.Log;
import android.view.View;

/**
 * Created by julianzhu on 11/16/15.
 */
public class WebViewLoading extends AsyncTask<String, Integer, Integer> {

    public interface LoadingTaskFinishedListener {
        void onTaskStarted();
        void onTaskFinished();
    }

    private final LoadingTaskFinishedListener finishedListener;

    private Context context = null;

    private View view = null;

    private String url = null;

    public WebViewLoading(Context context, LoadingTaskFinishedListener finishedListener, View view, String url) {

        this.context = context;
        this.finishedListener = finishedListener;

        this.view = view;
        this.url = url; // the news subject we will load articles for.
    }

    @Override
    protected Integer doInBackground(String... params) {

        // do initialization here ...

        try {
            //Log.i("Test", "Loading article ...");

            // do nothing here.


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

        finishedListener.onTaskStarted();
        //ProgressBar pbHeaderProgress = (ProgressBar) context.get.findViewById(R.id.progressBarArticleLoading);

        //pbHeaderProgress.setVisibility(View.VISIBLE);
    }

    @Override
    protected void onPostExecute(Integer integer) {

        super.onPostExecute(integer);
        // Notifiy whoever needs to be notified.
        finishedListener.onTaskFinished();


    }
}