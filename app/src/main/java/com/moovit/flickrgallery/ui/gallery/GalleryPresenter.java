package com.moovit.flickrgallery.ui.gallery;

import android.text.TextUtils;

import com.androidnetworking.error.ANError;
import com.moovit.flickrgallery.R;
import com.moovit.flickrgallery.data.DataManager;
import com.moovit.flickrgallery.data.network.ApiEndPoint;
import com.moovit.flickrgallery.data.network.model.GetPhotosResponse;
import com.moovit.flickrgallery.data.network.model.Photo;
import com.moovit.flickrgallery.service.PollingScheduler;
import com.moovit.flickrgallery.ui.base.BasePresenter;
import com.moovit.flickrgallery.utils.AppLogger;
import com.moovit.flickrgallery.utils.rx.SchedulerProvider;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.inject.Inject;

import io.reactivex.Single;
import io.reactivex.disposables.CompositeDisposable;
import io.reactivex.disposables.Disposable;
import io.reactivex.functions.Consumer;
import io.reactivex.functions.Function;

public class GalleryPresenter<V extends GalleryMvpView> extends BasePresenter<V> implements GalleryMvpPresenter<V> {

    private static final int RESULTS_PER_PAGE = 30;
    private int mNumberOfPages;
    private Map<String, Photo> mPhotosMap;
    private int mCurrentPage;

    private final PollingScheduler mPollingScheduler;

    @Inject
    public GalleryPresenter(DataManager dataManager, SchedulerProvider schedulerProvider, CompositeDisposable compositeDisposable, PollingScheduler pollingScheduler) {
        super(dataManager, schedulerProvider, compositeDisposable);
        mPollingScheduler = pollingScheduler;
        mPhotosMap = new HashMap<>();
    }

    @Override
    public String getLastSearchText() {
        return getDataManager().getLastSearchText();
    }

    @Override
    public void onAttach(V mvpView) {
        super.onAttach(mvpView);
        mvpView.showLoading();
        loadMorePhotos(getLastSearchText());
        mvpView.setPollingToggle(mPollingScheduler.isPollingEnabled());
    }

    @Override
    public void loadMorePhotos(final String searchText) {
        AppLogger.i("loadMorePhotos - searchText: " + searchText);
        mCurrentPage++;
        if ( mCurrentPage > 1 && mCurrentPage > mNumberOfPages ) {
            return;
        }
        getDataManager().saveLastSearchText(searchText);
        Single<GetPhotosResponse> photosResponseSingle;
        if ( TextUtils.isEmpty(searchText) ) {
            photosResponseSingle = getDataManager().getRecentPhotos(RESULTS_PER_PAGE, mCurrentPage);
        } else {
            photosResponseSingle = getDataManager().searchPhotos(RESULTS_PER_PAGE, mCurrentPage, searchText);
        }
        Disposable disposable = photosResponseSingle
                .subscribeOn(getSchedulerProvider().io())
                .map(new Function<GetPhotosResponse, List<GalleryPhotoViewModel>>() {
                    @Override
                    public List<GalleryPhotoViewModel> apply(GetPhotosResponse getPhotosResponse) {
                        // save meta data and prepare list of GalleryPhotoViewModel for view
                        AppLogger.i("load more photos: " + getPhotosResponse);
                        List<GalleryPhotoViewModel> photosList = new ArrayList<>();
                        if ( getPhotosResponse != null ) {
                            GetPhotosResponse.MetaData metaData = getPhotosResponse.getMetaData();
                            if ( metaData != null ) {
                                mNumberOfPages = getPhotosResponse.getMetaData().getNumberOfPages();
                                List<Photo> photos = metaData.getPhotos();
                                if ( photos != null ) {
                                    if ( !TextUtils.isEmpty(searchText) && mCurrentPage == 1 && photos.size() > 0 ) {
                                        // in case we searching, save the id of the first item of page 1;
                                        getDataManager().saveLastSearchResultId(photos.get(0).getId());
                                    }
                                    for ( Photo photo : photos ) {
                                        mPhotosMap.put(photo.getId(), photo);
                                        photosList.add(new GalleryPhotoViewModel(photo.getId(), photo.getUrl()));
                                    }
                                }
                            }
                        }
                        return photosList;
                    }
                })
                .observeOn(getSchedulerProvider().ui())
                .subscribe(new Consumer<List<GalleryPhotoViewModel>>() {
                    @Override
                    public void accept(List<GalleryPhotoViewModel> galleryPhotoViewModels) {
                        if ( getMvpView() != null ) {
                            getMvpView().hideLoading();
                            getMvpView().addPhotos(galleryPhotoViewModels, mCurrentPage >= mNumberOfPages);
                        }
                    }
                }, new Consumer<Throwable>() {
                    @Override
                    public void accept(Throwable throwable) {
                        if ( getMvpView() != null ) {
                            getMvpView().hideLoading();
                            getMvpView().onError(R.string.some_error);
                        }
                        if ( throwable instanceof ANError ) {
                            AppLogger.w("failed to load more photos. error");
                            handleApiError((ANError)throwable);
                        } else {
                            AppLogger.w("failed to load more photos", throwable);
                        }
                    }
                });
        getCompositeDisposable().add(disposable);
    }

    @Override
    public void onGalleryPhotoClick(String id) {
        if ( mPhotosMap != null ) {
            Photo photo = mPhotosMap.get(id);
            if ( photo != null ) {
                String url = String.format(ApiEndPoint.FLICKR_PHOTO_PAGE_URL, photo.getOwner(), photo.getId());
                getMvpView().showPhotoPage(url);
            }
        }
    }

    @Override
    public void onSearchQuery(String query) {
        mCurrentPage = 0;
        if ( getMvpView() != null ) {
            getMvpView().clearPhotos();
            getMvpView().showLoading();
            loadMorePhotos(query);
        }

        if ( TextUtils.isEmpty(query) ) {
            mPollingScheduler.setPollingEnabled(false);
            getMvpView().setPollingToggle(false);
        }
    }

    @Override
    public void onPollingToggleClick() {
        if ( !TextUtils.isEmpty(getLastSearchText()) ) {
            if (mPollingScheduler.isPollingEnabled()) {
                getMvpView().setPollingToggle(false);
                mPollingScheduler.setPollingEnabled(false);
            } else {
                getMvpView().setPollingToggle(true);
                mPollingScheduler.setPollingEnabled(true);
            }
        }
    }
}
