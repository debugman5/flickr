

package com.moovit.flickrgallery.data.network;


import com.moovit.flickrgallery.data.network.model.GetPhotosResponse;

import io.reactivex.Single;

public interface ApiHelper {

    /**
     * Retrieves the recent gallery photos from Flickr
     * @param perPage the number of results per page
     * @param pageNumber the number of page requested
     * @return a Single that will emit the {@link GetPhotosResponse}
     */
    Single<GetPhotosResponse> getRecentPhotos(int perPage, int pageNumber);

    /**
     * Searches Flickr for pictures with the given search text
     * @param perPage the number of results per page
     * @param pageNumber the number of page requested
     * @param searchText the text to search
     * @return a Single that will emit the {@link GetPhotosResponse}
     */
    Single<GetPhotosResponse> searchPhotos(int perPage, int pageNumber, String searchText);
}
