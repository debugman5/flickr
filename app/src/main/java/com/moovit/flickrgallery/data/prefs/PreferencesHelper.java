
package com.moovit.flickrgallery.data.prefs;


public interface PreferencesHelper {

    void saveLastSearchText(String text);

    String getLastSearchText();
}
