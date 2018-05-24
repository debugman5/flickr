

package com.moovit.flickrgallery.data.network;

import com.moovit.flickrgallery.BuildConfig;

public final class ApiEndPoint {

    public static final String GET_RECENT_PHOTOS_URL = BuildConfig.BASE_URL +
            "/?method=flickr.photos.getRecent&extras=url_s&format=json&nojsoncallback=1";

    private ApiEndPoint() {
        // This class is not publicly instantiable
    }

}
