package com.moovit.flickrgallery.service;

public interface PollingScheduler {

    /**
     * returns whether polling is enabled or not
     */
    boolean isPollingEnabled();

    /**
     * If enabled is true, starts polling if there is a saved search text. If false, stops polling if it is currently enabled
     * @param enabled whether to start or stop polling
     */
    void setPollingEnabled(boolean enabled);
}
