

package com.moovit.flickrgallery.di.component;

import com.moovit.flickrgallery.di.PerActivity;
import com.moovit.flickrgallery.di.module.ActivityModule;
import com.moovit.flickrgallery.ui.gallery.GalleryFragment;

import dagger.Component;



@PerActivity
@Component(dependencies = ApplicationComponent.class, modules = ActivityModule.class)
public interface ActivityComponent {

    void inject(GalleryFragment fragment);

}
