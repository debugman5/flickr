package com.moovit.flickrgallery.ui.page;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;

import com.moovit.flickrgallery.R;
import com.moovit.flickrgallery.ui.base.BaseActivity;
import com.moovit.flickrgallery.ui.gallery.GalleryFragment;

public class PhotoPageActivity extends BaseActivity {

    private static final String PHOTO_PAGE_URL_ARG = "PHOTO_PAGE_URL_ARG";

    public static Intent getStartingIntent(Context context, String photoPageUrl) {
        Intent intent = new Intent(context, PhotoPageActivity.class);
        intent.putExtra(PHOTO_PAGE_URL_ARG, photoPageUrl);
        return intent;
    }

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_photo_page);
        PhotoPageFragment fragment = (PhotoPageFragment)getSupportFragmentManager().findFragmentByTag(GalleryFragment.class.getName());
        if ( fragment == null ) {
            String url = getIntent().getStringExtra(PHOTO_PAGE_URL_ARG);
            getSupportFragmentManager()
                    .beginTransaction()
                    .add(R.id.photo_page__container, PhotoPageFragment.newInstance(url), PhotoPageFragment.class.getName())
                    .commit();
        }
    }
}
