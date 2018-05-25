package com.moovit.flickrgallery.ui.gallery;

import com.moovit.flickrgallery.ui.base.MvpPresenter;

public interface GalleryMvpPresenter<V extends GalleryMvpView> extends MvpPresenter<V> {

    /**
     *
     * @return last saved search text
     */
    String getLastSearchText();

    /**
     * request to load the next page of photos
     * @param searchText if not null or empty, searches for pictures with this text. Otherwise, load recent photos
     */
    void loadMorePhotos(String searchText);

    /**
     * Call when a photo was clicked
     * @param id the id of the photo
     */
    void onGalleryPhotoClick(String id);

    /**
     * Call when a search query as been requested
     * @param query the text of the search
     */
    void onSearchQuery(String query);

    /**
     * Call when the polling button was clicked
     */
    void onPollingToggleClick();
}
