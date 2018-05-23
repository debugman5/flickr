

package com.moovit.flickrgallery.di.component;

import android.app.Application;
import android.content.Context;

import com.moovit.flickrgallery.FlickrGalleryApp;
import com.moovit.flickrgallery.data.DataManager;
import com.moovit.flickrgallery.di.ApplicationContext;
import com.moovit.flickrgallery.di.module.ApplicationModule;

import javax.inject.Singleton;

import dagger.Component;

@Singleton
@Component(modules = ApplicationModule.class)
public interface ApplicationComponent {

    void inject(FlickrGalleryApp app);

    @ApplicationContext
    Context context();

    Application application();

    DataManager getDataManager();
}