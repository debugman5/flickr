

package com.moovit.flickrgallery.di.module;

import android.content.Context;
import android.support.v7.app.AppCompatActivity;

import com.moovit.flickrgallery.di.ActivityContext;
import com.moovit.flickrgallery.ui.gallery.GalleryMvpPresenter;
import com.moovit.flickrgallery.ui.gallery.GalleryMvpView;
import com.moovit.flickrgallery.ui.gallery.GalleryPresenter;
import com.moovit.flickrgallery.utils.rx.AppSchedulerProvider;
import com.moovit.flickrgallery.utils.rx.SchedulerProvider;

import dagger.Module;
import dagger.Provides;
import io.reactivex.disposables.CompositeDisposable;



@Module
public class ActivityModule {

    private AppCompatActivity mActivity;

    public ActivityModule(AppCompatActivity activity) {
        this.mActivity = activity;
    }

    @Provides
    @ActivityContext
    Context provideContext() {
        return mActivity;
    }

    @Provides
    AppCompatActivity provideActivity() {
        return mActivity;
    }

    @Provides
    CompositeDisposable provideCompositeDisposable() {
        return new CompositeDisposable();
    }

    @Provides
    SchedulerProvider provideSchedulerProvider() {
        return new AppSchedulerProvider();
    }

    @Provides
    GalleryMvpPresenter<GalleryMvpView> provideGalleryPhotosMvpPresenter(GalleryPresenter<GalleryMvpView> presenter) {
        return presenter;
    }
}
