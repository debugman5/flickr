package com.moovit.flickrgallery.ui.gallery;

/**
 * A view model class to hold the required information to show a gallery photo
 */
public class GalleryPhotoViewModel {

    private String mId;
    private String mImageUrl;

    public GalleryPhotoViewModel(String id, String imageUrl) {
        mId = id;
        mImageUrl = imageUrl;
    }

    public String getId() {
        return mId;
    }

    public void setId(String id) {
        mId = id;
    }

    public String getImageUrl() {
        return mImageUrl;
    }

    public void setImageUrl(String imageUrl) {
        mImageUrl = imageUrl;
    }
}
