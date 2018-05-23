

package com.moovit.flickrgallery.di.component;

import com.moovit.flickrgallery.di.PerService;
import com.moovit.flickrgallery.di.module.ServiceModule;

import dagger.Component;



@PerService
@Component(dependencies = ApplicationComponent.class, modules = ServiceModule.class)
public interface ServiceComponent {

}
