package com.moovit.flickrgallery.ui.gallery;

import com.moovit.flickrgallery.ui.base.MvpView;

import java.util.List;

public interface GalleryMvpView extends MvpView {

    /**
     * Adds the photos to the list of photos and show them
     * @param photos the photos to add and show
     * @param noMoreToLoad indicates if there are more photos to load or not
     */
    void addPhotos(List<GalleryPhotoViewModel> photos, boolean noMoreToLoad);

    /**
     * Show or open the view that will show the photo page
     * @param url the url of the photo page
     */
    void showPhotoPage(String url);

    /**
     * Clears all of the current shown photos
     */
    void clearPhotos();

    /**
     * Set the polling toggle button to on/of
     * @param toggleIsOn true indicates that polling is enabled
     */
    void setPollingToggle(boolean toggleIsOn);
}
