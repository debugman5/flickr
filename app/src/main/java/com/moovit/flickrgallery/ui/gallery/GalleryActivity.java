package com.moovit.flickrgallery.ui.gallery;

import android.app.SearchManager;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

import com.moovit.flickrgallery.R;
import com.moovit.flickrgallery.ui.base.BaseActivity;

public class GalleryActivity extends BaseActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_gallery);
        getSupportActionBar().setDisplayShowHomeEnabled(true);
        getSupportActionBar().setLogo(R.mipmap.ic_launcher);
        getSupportActionBar().setDisplayUseLogoEnabled(true);
        getSupportActionBar().setDisplayShowTitleEnabled(false);
        GalleryFragment fragment = (GalleryFragment)getSupportFragmentManager().findFragmentByTag(GalleryFragment.class.getName());
        if ( fragment == null ) {
            getSupportFragmentManager()
                    .beginTransaction()
                    .add(R.id.gallery_container, GalleryFragment.newInstance(), GalleryFragment.class.getName())
                    .commit();
        }
    }

    @Override
    protected void onNewIntent(Intent intent) {
        if ( Intent.ACTION_SEARCH.equals(intent.getAction()) ) {
            String query = intent.getStringExtra(SearchManager.QUERY);
            GalleryFragment fragment = (GalleryFragment)getSupportFragmentManager().findFragmentByTag(GalleryFragment.class.getName());
            if ( fragment != null ) {
                fragment.onSearchQuery(query);
            }
        }
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
    }
}
