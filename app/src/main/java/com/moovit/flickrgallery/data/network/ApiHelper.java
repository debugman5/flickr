

package com.moovit.flickrgallery.data.network;


import com.moovit.flickrgallery.data.network.model.GetPhotosResponse;

import io.reactivex.Single;

public interface ApiHelper {

    Single<GetPhotosResponse> getRecentPhotos(int perPage, String searchText);
}
