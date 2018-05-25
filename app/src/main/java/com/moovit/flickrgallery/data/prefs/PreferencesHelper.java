
package com.moovit.flickrgallery.data.prefs;


public interface PreferencesHelper {

    /**
     * Saves the last search text
     * @param text the text to save
     */
    void saveLastSearchText(String text);

    /**
     *
     * @return the last saved search text or null if there is none
     */
    String getLastSearchText();

    /**
     * Saves the last search result photo id
     * @param id
     */
    void saveLastSearchResultId(String id);

    /**
     *
     * @return the last saved search result photo id
     */
    String getLastSearchResultId();

    /**
     *
     * @return the saved boolean indicating if polling is enabled or not
     */
    boolean isPollingEnabled();

    /**
     * Saves that polling is enabled or not
     * @param enabled true if polling is enabled
     */
    void setPollingEnabled(boolean enabled);
}
