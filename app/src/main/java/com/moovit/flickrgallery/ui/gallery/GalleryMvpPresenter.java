package com.moovit.flickrgallery.ui.gallery;

import com.moovit.flickrgallery.ui.base.MvpPresenter;

public interface GalleryMvpPresenter<V extends GalleryMvpView> extends MvpPresenter<V> {

    void loadMorePhotos();
}
