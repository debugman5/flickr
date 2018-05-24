

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

    private static final String TAG = "AppDataManager";

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
    public Single<GetPhotosResponse> getRecentPhotos(int perPage, String searchText) {
        return mApiHelper.getRecentPhotos(perPage, searchText);
    }

    @Override
    public void saveLastSearchText(String text) {
        mPreferencesHelper.saveLastSearchText(text);
    }

    @Override
    public String getLastSearchText() {
        return mPreferencesHelper.getLastSearchText();
    }
}
