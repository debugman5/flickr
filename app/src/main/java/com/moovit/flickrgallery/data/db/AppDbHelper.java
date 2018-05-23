

package com.moovit.flickrgallery.data.db;

import android.content.Context;

import com.moovit.flickrgallery.di.ApplicationContext;

import javax.inject.Inject;
import javax.inject.Singleton;

@Singleton
public class AppDbHelper implements DbHelper {

    @Inject
    public AppDbHelper(@ApplicationContext Context context) {
    }
}
