package com.ubqsys.station.service;

/**
 * Created by Julian on 3/11/2017.
 */

public interface SyncTaskListener {

    void onTaskStarted();
    void onTaskFinished();

}
