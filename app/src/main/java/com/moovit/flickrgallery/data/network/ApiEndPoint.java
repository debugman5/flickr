

package com.moovit.flickrgallery.data.network;

import com.moovit.flickrgallery.BuildConfig;

public final class ApiEndPoint {

    public static final String API_KEY_PATH_KEY = "api_key";

    public static final String GET_RECENT_PHOTOS_URL = BuildConfig.BASE_URL +
            "/?method=flickr.photos.getRecent&url_s&api_key={" + API_KEY_PATH_KEY + " }";

    private ApiEndPoint() {
        // This class is not publicly instantiable
    }

}
