package com.moovit.flickrgallery.ui.gallery;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.moovit.flickrgallery.R;
import com.moovit.flickrgallery.ui.base.BaseFragment;

import java.util.List;

public class PhotosGalleryFragment extends BaseFragment implements GalleryMvpView {

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_photos_gallery, container, false);
    }

    @Override
    protected void setUp(View view) {

    }

    @Override
    public void showPhotos(List<GalleryPhotoViewModel> photos) {

    }

    @Override
    public void showPhotoPage() {

    }
}
