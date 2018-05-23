

package com.moovit.flickrgallery.data.network;

import android.text.TextUtils;

import com.moovit.flickrgallery.data.network.model.GetPhotosResponse;
import com.moovit.flickrgallery.di.ApiInfo;
import com.rx2androidnetworking.Rx2ANRequest;
import com.rx2androidnetworking.Rx2AndroidNetworking;

import javax.inject.Inject;
import javax.inject.Singleton;

import io.reactivex.Single;

@Singleton
public class AppApiHelper implements ApiHelper {

    private static final String SEARCH_QUERY_KEY = "text";

    private final String mApiKey;

    @Inject
    public AppApiHelper(@ApiInfo String apiKey) {
        mApiKey = apiKey;
    }

    @Override
    public Single<GetPhotosResponse> getRecentPhotos(String searchText) {
        Rx2ANRequest.GetRequestBuilder builder = Rx2AndroidNetworking.get(ApiEndPoint.GET_RECENT_PHOTOS_URL)
            .addPathParameter(ApiEndPoint.API_KEY_PATH_KEY, mApiKey);
        if ( !TextUtils.isEmpty(searchText) ) {
            builder.addQueryParameter(SEARCH_QUERY_KEY, searchText);
        }
        return builder.build().getObjectSingle(GetPhotosResponse.class);
    }
}

