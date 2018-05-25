package com.moovit.flickrgallery.service;

import android.app.Notification;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.support.v4.app.NotificationCompat;
import android.text.TextUtils;

import com.androidnetworking.error.ANError;
import com.firebase.jobdispatcher.JobParameters;
import com.firebase.jobdispatcher.JobService;
import com.moovit.flickrgallery.FlickrGalleryApp;
import com.moovit.flickrgallery.R;
import com.moovit.flickrgallery.data.DataManager;
import com.moovit.flickrgallery.data.network.model.GetPhotosResponse;
import com.moovit.flickrgallery.di.component.DaggerServiceComponent;
import com.moovit.flickrgallery.di.module.ServiceModule;
import com.moovit.flickrgallery.ui.gallery.GalleryActivity;
import com.moovit.flickrgallery.utils.AppLogger;
import com.moovit.flickrgallery.utils.rx.SchedulerProvider;

import javax.inject.Inject;

import io.reactivex.disposables.CompositeDisposable;
import io.reactivex.disposables.Disposable;
import io.reactivex.functions.Consumer;

/**
 * A {@link JobService} that will do the polling and show a notification if there is a new search result
 */
public class PollingJobService extends JobService {

    private static final String NOTIFICATION_CHANNEL_ID = String.valueOf(R.id.notification_channel_id);
    private static final int NOTIFICATION_ID = 49894129;

    @Inject
    DataManager mDataManager;

    @Inject
    CompositeDisposable mCompositeDisposable;

    @Inject
    SchedulerProvider mSchedulerProvider;

    @Override
    public void onCreate() {
        super.onCreate();
        DaggerServiceComponent.builder()
                .serviceModule(new ServiceModule(this))
                .applicationComponent(((FlickrGalleryApp) getApplication()).getComponent())
                .build().inject(this);
    }

    @Override
    public boolean onStartJob(final JobParameters job) {
        AppLogger.i("PollingService onStartJob");
        String lastSearchText = mDataManager.getLastSearchText();
        if (!TextUtils.isEmpty(lastSearchText)) {
            Disposable disposable = mDataManager.searchPhotos(1, 1, lastSearchText)
                    .subscribeOn(mSchedulerProvider.io())
                    .subscribe(new Consumer<GetPhotosResponse>() {
                        @Override
                        public void accept(GetPhotosResponse getPhotosResponse) throws Exception {
                            AppLogger.i("Received getPhotoResponse: " + getPhotosResponse);
                            GetPhotosResponse.MetaData metaData = getPhotosResponse.getMetaData();
                            if (metaData != null && metaData.getPhotos() != null && !metaData.getPhotos().isEmpty()) {
                                String id = metaData.getPhotos().get(0).getId();
                                String lastSearchResultId = mDataManager.getLastSearchResultId();
                                // check if the first result photo id is different that the last saved one
                                if (lastSearchResultId != null && !lastSearchResultId.equals(id)) {
                                    showNotification();
                                    mDataManager.saveLastSearchResultId(id);
                                }
                            }
                            jobFinished(job, false);
                        }
                    }, new Consumer<Throwable>() {
                        @Override
                        public void accept(Throwable throwable) throws Exception {
                            if (throwable instanceof ANError) {
                                AppLogger.w("failed to load more photos. error: " + ((ANError) throwable).getErrorBody(), throwable);
                            } else {
                                AppLogger.w("failed to load more photos", throwable);
                            }
                            jobFinished(job, false);
                        }
                    });
            mCompositeDisposable.add(disposable);
            return true;
        }

        return false;
    }

    /**
     * Shows notification to the user that there are new photos
     */
    private void showNotification() {
        Intent intent = new Intent(this, GalleryActivity.class);
        intent.setAction(Intent.ACTION_SEARCH);
        PendingIntent pi = PendingIntent.getActivity(this, 0, intent, 0);
        Notification notification = new NotificationCompat.Builder(this, NOTIFICATION_CHANNEL_ID)
                .setTicker(getString(R.string.new_pictures_title)).setSmallIcon(android.R.drawable.ic_menu_report_image)
                .setContentTitle(getString(R.string.new_pictures_title))
                .setContentText(getString(R.string.new_pictures_text))
                .setContentIntent(pi)
                .setAutoCancel(true)
                .build();

        NotificationManager notificationManager = (NotificationManager) getSystemService(Context.NOTIFICATION_SERVICE);
        if (notificationManager != null) {
            notificationManager.notify(NOTIFICATION_ID, notification);
        }
    }

    @Override
    public boolean onStopJob(JobParameters job) {
        mCompositeDisposable.dispose();
        return false;
    }
}
