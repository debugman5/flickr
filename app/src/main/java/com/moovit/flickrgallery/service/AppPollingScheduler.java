package com.moovit.flickrgallery.service;

import com.firebase.jobdispatcher.FirebaseJobDispatcher;
import com.firebase.jobdispatcher.Job;
import com.firebase.jobdispatcher.Lifetime;
import com.firebase.jobdispatcher.Trigger;
import com.moovit.flickrgallery.R;
import com.moovit.flickrgallery.data.DataManager;
import com.moovit.flickrgallery.utils.AppLogger;

import java.util.concurrent.TimeUnit;

import javax.inject.Inject;
import javax.inject.Singleton;

/**
 * This class responsible to start and stop the polling.
 */
@Singleton
public class AppPollingScheduler implements PollingScheduler {

    private static final String POLLING_JOB_TAG = String.valueOf(R.id.polling_job_tag);
    private static final int PERIODICITY = (int)TimeUnit.MINUTES.toSeconds(15);
    private static final int TOLERANCE = (int)TimeUnit.MINUTES.toSeconds(1);

    private final DataManager mDataManager;
    private final FirebaseJobDispatcher mJobDispatcher;

    @Inject
    public AppPollingScheduler(DataManager dataManager, FirebaseJobDispatcher jobDispatcher) {
        mDataManager = dataManager;
        mJobDispatcher = jobDispatcher;
    }

    @Override
    public boolean isPollingEnabled() {
        return mDataManager.isPollingEnabled();
    }

    @Override
    public void setPollingEnabled(boolean enabled) {
        AppLogger.i("setPollingEnabled " + enabled);
        if ( enabled ) {
            startPolling();
        } else {
            stopPolling();
        }
    }

    private void stopPolling() {
        if ( isPollingEnabled() ) {
            mJobDispatcher.cancel(POLLING_JOB_TAG);
        }
    }

    private void startPolling() {
        Job pollingJob = mJobDispatcher.newJobBuilder()
                .setService(PollingJobService.class)
                .setTag(POLLING_JOB_TAG)
                .setRecurring(true)
                .setLifetime(Lifetime.FOREVER)
                .setReplaceCurrent(true)
                .setTrigger(Trigger.executionWindow(PERIODICITY, PERIODICITY + TOLERANCE))
                .build();
        mJobDispatcher.mustSchedule(pollingJob);
        AppLogger.i("startPolling");
    }
}
