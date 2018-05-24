package com.moovit.flickrgallery.ui.gallery;

public class GalleryPhotoViewModel {

    private String mImageUrl;

    public GalleryPhotoViewModel(String imageUrl) {
        mImageUrl = imageUrl;
    }

    public String getImageUrl() {
        return mImageUrl;
    }

    public void setImageUrl(String imageUrl) {
        mImageUrl = imageUrl;
    }
}
