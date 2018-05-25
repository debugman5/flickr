

package com.moovit.flickrgallery.data;


import android.content.Context;

import com.moovit.flickrgallery.data.db.DbHelper;
import com.moovit.flickrgallery.data.network.ApiHelper;
import com.moovit.flickrgallery.data.network.model.GetPhotosResponse;
import com.moovit.flickrgallery.data.prefs.PreferencesHelper;
import com.moovit.flickrgallery.di.ApplicationContext;

import javax.inject.Inject;
import javax.inject.Singleton;

import io.reactivex.Single;


@Singleton
public class AppDataManager implements DataManager {

    private final Context mContext;
    private final DbHelper mDbHelper;
    private final PreferencesHelper mPreferencesHelper;
    private final ApiHelper mApiHelper;

    @Inject
    public AppDataManager(@ApplicationContext Context context,
                          DbHelper dbHelper,
                          PreferencesHelper preferencesHelper,
                          ApiHelper apiHelper) {
        mContext = context;
        mDbHelper = dbHelper;
        mPreferencesHelper = preferencesHelper;
        mApiHelper = apiHelper;
    }

    @Override
    public Single<GetPhotosResponse> getRecentPhotos(int perPage, int pageNumber) {
        return mApiHelper.getRecentPhotos(perPage, pageNumber);
    }

    @Override
    public Single<GetPhotosResponse> searchPhotos(int perPage, int pageNumber, String searchText) {
        return mApiHelper.searchPhotos(perPage, pageNumber, searchText);
    }

    @Override
    public void saveLastSearchText(String text) {
        mPreferencesHelper.saveLastSearchText(text);
    }

    @Override
    public String getLastSearchText() {
        return mPreferencesHelper.getLastSearchText();
    }

    @Override
    public void saveLastSearchResultId(String id) {
        mPreferencesHelper.saveLastSearchResultId(id);
    }

    @Override
    public String getLastSearchResultId() {
        return mPreferencesHelper.getLastSearchResultId();
    }

    @Override
    public boolean isPollingEnabled() {
        return mPreferencesHelper.isPollingEnabled();
    }

    @Override
    public void setPollingEnabled(boolean enabled) {
        mPreferencesHelper.setPollingEnabled(enabled);
    }
}
