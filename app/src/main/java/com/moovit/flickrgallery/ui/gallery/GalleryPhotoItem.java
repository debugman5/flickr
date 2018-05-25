package com.moovit.flickrgallery.ui.gallery;

import android.widget.ImageView;

import com.bumptech.glide.Glide;
import com.mindorks.placeholderview.annotations.Click;
import com.mindorks.placeholderview.annotations.Layout;
import com.mindorks.placeholderview.annotations.Position;
import com.mindorks.placeholderview.annotations.Resolve;
import com.mindorks.placeholderview.annotations.View;
import com.moovit.flickrgallery.R;

/**
 * This class represent a gallery photo item in the gallery view
 */
@Layout(R.layout.gallery_photo_item_layout)
public class GalleryPhotoItem {

    interface GalleryPhotoListener {
        void onGalleryPhotoClick(String id);
    }

    @View(R.id.gallery_photo_image_view)
    ImageView mPhotoImageView;

    private String mId;

    private String mImageUrl;

    private GalleryPhotoListener mListener;

    public GalleryPhotoItem(String id, String imageUrl, GalleryPhotoListener listener) {
        mId = id;
        mImageUrl = imageUrl;
        mListener = listener;
    }

    @Resolve
    public void onResolve() {
        Glide.with(mPhotoImageView.getContext()).load(mImageUrl).into(mPhotoImageView);
    }

    @Click(R.id.gallery_photo_image_view)
    public void onClick() {
        if ( mListener != null ) {
            mListener.onGalleryPhotoClick(mId);
        }
    }
}
