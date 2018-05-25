

package com.moovit.flickrgallery.data.network;

import android.text.TextUtils;

import com.androidnetworking.AndroidNetworking;
import com.androidnetworking.gsonparserfactory.GsonParserFactory;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.moovit.flickrgallery.data.network.model.GetPhotosResponse;
import com.moovit.flickrgallery.di.ApiInfo;
import com.rx2androidnetworking.Rx2ANRequest;
import com.rx2androidnetworking.Rx2AndroidNetworking;

import javax.inject.Inject;
import javax.inject.Singleton;

import io.reactivex.Single;

@Singleton
public class AppApiHelper implements ApiHelper {

    private static final String API_KEY_PATH_KEY = "api_key";
    private static final String PER_PAGE_QUERY_KEY = "per_page";
    private static final String PAGE_NUMBER_QUERY_KEY = "page";
    private static final String SEARCH_QUERY_KEY = "text";

    private final String mApiKey;

    @Inject
    public AppApiHelper(@ApiInfo String apiKey) {
        mApiKey = apiKey;
        Gson gson = new GsonBuilder().setLenient().create();
        AndroidNetworking.setParserFactory(new GsonParserFactory(gson));
    }

    @Override
    public Single<GetPhotosResponse> getRecentPhotos(int perPage, int pageNumber) {
        Rx2ANRequest.GetRequestBuilder builder = Rx2AndroidNetworking.get(ApiEndPoint.GET_RECENT_PHOTOS_URL)
            .addQueryParameter(API_KEY_PATH_KEY, mApiKey);
        if ( perPage > 0 ) {
            builder.addQueryParameter(PER_PAGE_QUERY_KEY, String.valueOf(perPage));
        }
        if ( pageNumber > 0 ) {
            builder.addQueryParameter(PAGE_NUMBER_QUERY_KEY, String.valueOf(pageNumber));
        }
        return builder.build().getObjectSingle(GetPhotosResponse.class);
    }

    @Override
    public Single<GetPhotosResponse> searchPhotos(int perPage, int pageNumber, String searchText) {
        Rx2ANRequest.GetRequestBuilder builder = Rx2AndroidNetworking.get(ApiEndPoint.SEARCH_PHOTOS_URL)
                .addQueryParameter(API_KEY_PATH_KEY, mApiKey);
        if ( perPage > 0 ) {
            builder.addQueryParameter(PER_PAGE_QUERY_KEY, String.valueOf(perPage));
        }
        if ( pageNumber > 0 ) {
            builder.addQueryParameter(PAGE_NUMBER_QUERY_KEY, String.valueOf(pageNumber));
        }
        if ( !TextUtils.isEmpty(searchText) ) {
            builder.addQueryParameter(SEARCH_QUERY_KEY, searchText);
        }
        return builder.build().getObjectSingle(GetPhotosResponse.class);
    }
}

