

package com.moovit.flickrgallery.data.network;

import com.moovit.flickrgallery.BuildConfig;

public final class ApiEndPoint {

    public static final String GET_RECENT_PHOTOS_URL = BuildConfig.BASE_URL +
            "/?method=flickr.photos.getRecent&extras=url_s&format=json&nojsoncallback=1";

    public static final String SEARCH_PHOTOS_URL = BuildConfig.BASE_URL +
            "/?method=flickr.photos.search&extras=url_s&format=json&nojsoncallback=1";

    public static final String FLICKR_PHOTO_PAGE_URL = "https://www.flickr.com/photos/%s/%s/";

    private ApiEndPoint() {
        // This class is not publicly instantiable
    }

}
