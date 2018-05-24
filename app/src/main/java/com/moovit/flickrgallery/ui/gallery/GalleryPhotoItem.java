package com.moovit.flickrgallery.ui.gallery;

import android.widget.ImageView;

import com.bumptech.glide.Glide;
import com.mindorks.placeholderview.annotations.Click;
import com.mindorks.placeholderview.annotations.Layout;
import com.mindorks.placeholderview.annotations.Position;
import com.mindorks.placeholderview.annotations.Resolve;
import com.mindorks.placeholderview.annotations.View;
import com.moovit.flickrgallery.R;

@Layout(R.layout.gallery_photo_item_layout)
public class GalleryPhotoItem {

    interface GalleryPhotoListener {
        void onGalleryPhotoClick(int position);
    }

    @View(R.id.gallery_photo_image_view)
    ImageView mPhotoImageView;

    @Position
    int mPosition;

    private String mImageUrl;

    private GalleryPhotoListener mListener;

    public GalleryPhotoItem(String imageUrl, GalleryPhotoListener listener) {
        mImageUrl = imageUrl;
        mListener = listener;
    }

    @Resolve
    public void onResolve() {
        Glide.with(mPhotoImageView).load(mImageUrl).into(mPhotoImageView);
    }

    @Click(R.id.gallery_photo_image_view)
    public void onClick() {
        if ( mListener != null ) {
            mListener.onGalleryPhotoClick(mPosition);
        }
    }
}
