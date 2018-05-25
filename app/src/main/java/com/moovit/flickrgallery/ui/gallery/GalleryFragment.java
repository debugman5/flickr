package com.moovit.flickrgallery.ui.gallery;

import android.app.SearchManager;
import android.content.Context;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.SearchView;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;

import com.mindorks.placeholderview.InfinitePlaceHolderView;
import com.moovit.flickrgallery.R;
import com.moovit.flickrgallery.di.component.ActivityComponent;
import com.moovit.flickrgallery.ui.base.BaseFragment;
import com.moovit.flickrgallery.ui.base.LoadMoreView;
import com.moovit.flickrgallery.ui.page.PhotoPageActivity;
import com.moovit.flickrgallery.utils.AppLogger;

import java.util.List;

import javax.inject.Inject;

public class GalleryFragment extends BaseFragment implements GalleryMvpView, GalleryPhotoItem.GalleryPhotoListener, LoadMoreView.LoadMoreListener {


    private static final int GALLERY_SPAN_COUNT = 3;
    private static final int VIEW_CACHE_SIZE = 15;

    @Inject
    GalleryMvpPresenter<GalleryMvpView> mPresenter;

    private InfinitePlaceHolderView mGalleryView;

    private SearchView mSearchView;
    private MenuItem mPollingToggleView;

    public static GalleryFragment newInstance() {
        GalleryFragment fragment = new GalleryFragment();
        return fragment;
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_photos_gallery, container, false);
        setHasOptionsMenu(true);
        ActivityComponent component = getActivityComponent();
        if ( component != null ) {
            component.inject(this);
        }
        return view;
    }

    @Override
    protected void setUp(View view) {
        mGalleryView = view.findViewById(R.id.galleryView);
        mGalleryView.setLoadMoreResolver(new LoadMoreView(this));
        mGalleryView.getBuilder()
                .setHasFixedSize(true)
                .setItemViewCacheSize(VIEW_CACHE_SIZE)
                .setLayoutManager(new GridLayoutManager(getContext(), GALLERY_SPAN_COUNT));
    }

    @Override
    public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {
        super.onCreateOptionsMenu(menu, inflater);
        inflater.inflate(R.menu.gallery_menu, menu);

        // Associate searchable configuration with the SearchView
        mSearchView = (SearchView)menu.findItem(R.id.search).getActionView();
        mPollingToggleView = menu.findItem(R.id.menu_item_toggle_polling);
        String lastSearchText = mPresenter.getLastSearchText();
        if ( !TextUtils.isEmpty(lastSearchText) ) {
            mSearchView.setIconified(false);
            mSearchView.setQuery(lastSearchText, false);
            mSearchView.clearFocus();
        }

        SearchManager searchManager = (SearchManager)getActivity().getSystemService(Context.SEARCH_SERVICE);
        mSearchView.setSearchableInfo(searchManager.getSearchableInfo(getActivity().getComponentName()));

        mSearchView.setOnCloseListener(new SearchView.OnCloseListener() {
            @Override
            public boolean onClose() {
                mPollingToggleView.setVisible(false);
                onSearchQuery(null);
                return false;
            }
        });


        mPresenter.onAttach(this);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch ( item.getItemId() ) {
            case R.id.menu_item_clear:
                if ( !TextUtils.isEmpty(mSearchView.getQuery()) )
                    clearSearch();
                return true;
            case R.id.menu_item_toggle_polling:
                mPresenter.onPollingToggleClick();
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }

    private void clearSearch() {
        mSearchView.setQuery(null, false);
        mSearchView.clearFocus();
        mSearchView.setIconified(true);
        mPollingToggleView.setVisible(false);
        onSearchQuery(null);
    }

    @Override
    public void showLoading() {
        super.showLoading();
    }

    @Override
    public void hideLoading() {
        super.hideLoading();
        mGalleryView.loadingDone();
    }

    @Override
    public void addPhotos(List<GalleryPhotoViewModel> photos, boolean noMoreToLoad) {
        AppLogger.d("addPhotos photos size: " + photos.size() + " noMoreToLoad: " + noMoreToLoad);
        for ( GalleryPhotoViewModel photo : photos ) {
            mGalleryView.addView(new GalleryPhotoItem(photo.getId(), photo.getImageUrl(), this));
        }
        if ( noMoreToLoad ) {
            mGalleryView.noMoreToLoad();
        }
    }

    @Override
    public void showPhotoPage(String url) {
        startActivity(PhotoPageActivity.getStartingIntent(getContext(), url));
    }

    @Override
    public void clearPhotos() {
        mGalleryView.removeAllViews();
        mGalleryView.setLoadMoreResolver(new LoadMoreView(this));
    }

    @Override
    public void setPollingToggle(boolean toggleIsOn) {
        mPollingToggleView.setTitle(toggleIsOn ? R.string.stop_polling : R.string.start_polling);
    }

    @Override
    public void onGalleryPhotoClick(String id) {
        mPresenter.onGalleryPhotoClick(id);
    }

    public void onSearchQuery(String query) {
        mSearchView.clearFocus();
        mPresenter.onSearchQuery(query);
    }

    @Override
    public void onLoadMore() {
        String text = mSearchView != null && mSearchView.getQuery() != null ? mSearchView.getQuery().toString() : null;
        mPresenter.loadMorePhotos(text);
    }
}
