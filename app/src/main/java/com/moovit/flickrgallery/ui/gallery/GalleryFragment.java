package com.moovit.flickrgallery.ui.gallery;

import android.app.SearchManager;
import android.content.Context;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.SearchView;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.View;
import android.view.ViewGroup;

import com.mindorks.placeholderview.InfinitePlaceHolderView;
import com.moovit.flickrgallery.R;
import com.moovit.flickrgallery.di.component.ActivityComponent;
import com.moovit.flickrgallery.ui.base.BaseFragment;
import com.moovit.flickrgallery.ui.base.LoadMoreView;

import java.util.List;

import javax.inject.Inject;

public class GalleryFragment extends BaseFragment implements GalleryMvpView, GalleryPhotoItem.GalleryPhotoListener, LoadMoreView.LoadMoreListener {


    private static final int GALLERY_SPAN_COUNT = 3;
    private static final int VIEW_CACHE_SIZE = 10;

    @Inject
    GalleryMvpPresenter<GalleryMvpView> mPresenter;

    private InfinitePlaceHolderView mGalleryView;

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
        if (component != null) {
            component.inject(this);
        }
        return view;
    }

    @Override
    protected void setUp(View view) {
        mGalleryView = view.findViewById(R.id.galleryView);
        mGalleryView.setLoadMoreResolver(new LoadMoreView(this));
        mGalleryView.getBuilder()
                .setLayoutManager(new GridLayoutManager(getContext(), GALLERY_SPAN_COUNT))
                .setItemViewCacheSize(VIEW_CACHE_SIZE);
        mPresenter.onAttach(this);
    }

    @Override
    public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {
        super.onCreateOptionsMenu(menu, inflater);
        inflater.inflate(R.menu.gallery_menu, menu);

        // Associate searchable configuration with the SearchView
        SearchManager searchManager = (SearchManager) getActivity().getSystemService(Context.SEARCH_SERVICE);
        SearchView searchView = (SearchView) menu.findItem(R.id.search).getActionView();
        searchView.setSearchableInfo(searchManager.getSearchableInfo(getActivity().getComponentName()));
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
        for ( GalleryPhotoViewModel photo : photos ) {
            mGalleryView.addView(new GalleryPhotoItem(photo.getImageUrl(), this));
        }
        if ( noMoreToLoad ) {
            mGalleryView.noMoreToLoad();
        }
    }

    @Override
    public void showPhotoPage() {

    }

    @Override
    public void clearPhotos() {
        mGalleryView.removeAllViews();
    }

    @Override
    public void onGalleryPhotoClick(int position) {
        mPresenter.onGalleryPhotoClick(position);
    }

    public void onSearchQuery(String query) {
        mPresenter.onSearchQuery(query);
    }

    @Override
    public void onLoadMore() {
        mPresenter.loadMorePhotos("");
    }
}
