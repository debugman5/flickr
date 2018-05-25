

package com.moovit.flickrgallery.data.prefs;

import android.content.Context;
import android.content.SharedPreferences;

import com.moovit.flickrgallery.di.ApplicationContext;
import com.moovit.flickrgallery.di.PreferenceInfo;

import javax.inject.Inject;
import javax.inject.Singleton;



@Singleton
public class AppPreferencesHelper implements PreferencesHelper {

    private static final String LAST_SEARCH_TEXT_KEY = "LAST_SEARCH_TEXT_KEY";
    private static final String LAST_SEARCH_RESULT_ID = "LAST_SEARCH_RESULT_ID";
    private static final String POLLING_ENABLED_KEY = "POLLING_ENABLED_KEY";

    private final SharedPreferences mPrefs;

    @Inject
    public AppPreferencesHelper(@ApplicationContext Context context,
                                @PreferenceInfo String prefFileName) {
        mPrefs = context.getSharedPreferences(prefFileName, Context.MODE_PRIVATE);
    }

    @Override
    public void saveLastSearchText(String text) {
        mPrefs.edit().putString(LAST_SEARCH_TEXT_KEY, text).apply();
    }

    @Override
    public String getLastSearchText() {
        return mPrefs.getString(LAST_SEARCH_TEXT_KEY, "");
    }

    @Override
    public void saveLastSearchResultId(String id) {
        mPrefs.edit().putString(LAST_SEARCH_RESULT_ID, id).apply();
    }

    @Override
    public String getLastSearchResultId() {
        return mPrefs.getString(LAST_SEARCH_RESULT_ID, null);
    }

    @Override
    public boolean isPollingEnabled() {
        return mPrefs.getBoolean(POLLING_ENABLED_KEY, false);
    }

    @Override
    public void setPollingEnabled(boolean enabled) {
        mPrefs.edit().putBoolean(POLLING_ENABLED_KEY, enabled).apply();
    }
}
