

package com.moovit.flickrgallery.di.module;

import android.app.Application;
import android.content.Context;

import com.moovit.flickrgallery.BuildConfig;
import com.moovit.flickrgallery.data.AppDataManager;
import com.moovit.flickrgallery.data.DataManager;
import com.moovit.flickrgallery.data.db.AppDbHelper;
import com.moovit.flickrgallery.data.db.DbHelper;
import com.moovit.flickrgallery.data.network.ApiHelper;
import com.moovit.flickrgallery.data.network.AppApiHelper;
import com.moovit.flickrgallery.data.prefs.AppPreferencesHelper;
import com.moovit.flickrgallery.data.prefs.PreferencesHelper;
import com.moovit.flickrgallery.di.ApiInfo;
import com.moovit.flickrgallery.di.ApplicationContext;
import com.moovit.flickrgallery.di.PreferenceInfo;
import com.moovit.flickrgallery.utils.AppConstants;

import javax.inject.Singleton;

import dagger.Module;
import dagger.Provides;

@Module
public class ApplicationModule {

    private final Application mApplication;

    public ApplicationModule(Application application) {
        mApplication = application;
    }

    @Provides
    @ApplicationContext
    Context provideContext() {
        return mApplication;
    }

    @Provides
    Application provideApplication() {
        return mApplication;
    }

    @Provides
    @ApiInfo
    String provideApiKey() {
        return BuildConfig.API_KEY;
    }

    @Provides
    @PreferenceInfo
    String providePreferenceName() {
        return AppConstants.PREF_NAME;
    }

    @Provides
    @Singleton
    DataManager provideDataManager(AppDataManager appDataManager) {
        return appDataManager;
    }

    @Provides
    @Singleton
    PreferencesHelper providePreferencesHelper(AppPreferencesHelper appPreferencesHelper) {
        return appPreferencesHelper;
    }

    @Provides
    @Singleton
    ApiHelper provideApiHelper(AppApiHelper appApiHelper) {
        return appApiHelper;
    }

    @Provides
    @Singleton
    DbHelper provideDbHelper(AppDbHelper appDbHelper) {
        return appDbHelper;
    }
}
