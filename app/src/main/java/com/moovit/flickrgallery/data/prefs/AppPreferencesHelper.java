

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
}
