

package com.moovit.flickrgallery.di.component;

import com.moovit.flickrgallery.di.PerActivity;
import com.moovit.flickrgallery.di.module.ActivityModule;

import dagger.Component;



@PerActivity
@Component(dependencies = ApplicationComponent.class, modules = ActivityModule.class)
public interface ActivityComponent {

}
