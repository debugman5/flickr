package com.moovit.flickrgallery.ui.gallery;

import com.moovit.flickrgallery.ui.base.MvpView;

import java.util.List;

public interface GalleryMvpView extends MvpView {

    void showPhotos(List<GalleryPhotoViewModel> photos);

    void showPhotoPage();
}