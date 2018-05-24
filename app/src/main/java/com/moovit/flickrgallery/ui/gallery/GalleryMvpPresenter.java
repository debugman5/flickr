package com.moovit.flickrgallery.ui.gallery;

import com.moovit.flickrgallery.ui.base.MvpPresenter;

public interface GalleryMvpPresenter<V extends GalleryMvpView> extends MvpPresenter<V> {

    String getLastSearchText();

    void loadMorePhotos(String searchText);

    void onGalleryPhotoClick(int position);

    void onSearchQuery(String query);
}
