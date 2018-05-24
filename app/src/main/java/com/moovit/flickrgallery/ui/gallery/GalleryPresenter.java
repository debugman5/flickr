package com.moovit.flickrgallery.ui.gallery;

import com.androidnetworking.error.ANError;
import com.moovit.flickrgallery.data.DataManager;
import com.moovit.flickrgallery.data.network.model.GetPhotosResponse;
import com.moovit.flickrgallery.data.network.model.Photo;
import com.moovit.flickrgallery.ui.base.BasePresenter;
import com.moovit.flickrgallery.utils.AppLogger;
import com.moovit.flickrgallery.utils.rx.SchedulerProvider;

import java.util.ArrayList;
import java.util.List;

import javax.inject.Inject;

import io.reactivex.disposables.CompositeDisposable;
import io.reactivex.disposables.Disposable;
import io.reactivex.functions.Consumer;
import io.reactivex.functions.Function;

public class GalleryPresenter<V extends GalleryMvpView> extends BasePresenter<V> implements GalleryMvpPresenter<V> {

    private static final int RESULTS_PER_PAGE = 15;
    private int mNumberOfPages;
    private List<Photo> mPhotos;
    private int mCurrentPage;

    @Inject
    public GalleryPresenter(DataManager dataManager, SchedulerProvider schedulerProvider, CompositeDisposable compositeDisposable) {
        super(dataManager, schedulerProvider, compositeDisposable);
        mPhotos = new ArrayList<>();
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
    }

    @Override
    public void loadMorePhotos(String searchText) {
        mCurrentPage++;
        if ( mCurrentPage > 1 && mCurrentPage > mNumberOfPages ) {
            return;
        }
        getDataManager().saveLastSearchText(searchText);
        Disposable disposable = getDataManager().getRecentPhotos(RESULTS_PER_PAGE, searchText)
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
                                    mPhotos.addAll(photos);
                                    for ( Photo photo : photos ) {
                                        photosList.add(new GalleryPhotoViewModel(photo.getUrl()));
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
                        if ( throwable instanceof ANError ) {
                            AppLogger.w("failed to load more photos. error: " + ((ANError)throwable).getErrorBody(), throwable);
                        } else {
                            AppLogger.w("failed to load more photos", throwable);
                        }
                    }
                });
        getCompositeDisposable().add(disposable);
    }

    @Override
    public void onGalleryPhotoClick(int position) {

    }

    @Override
    public void onSearchQuery(String query) {
        mCurrentPage = 0;
        if ( getMvpView() != null ) {
            getMvpView().clearPhotos();
            getMvpView().showLoading();
            loadMorePhotos(query);
        }
    }
}
