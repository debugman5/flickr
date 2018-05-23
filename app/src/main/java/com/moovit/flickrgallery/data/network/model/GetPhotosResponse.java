package com.moovit.flickrgallery.data.network.model;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.List;

public class GetPhotosResponse {

    @Expose
    @SerializedName("photos")
    private MetaData mMetaData;



    public static class MetaData {

        @Expose
        @SerializedName("page")
        private int mPage;

        @Expose
        @SerializedName("pages")
        private int mPages;

        @Expose
        @SerializedName("photo")
        private List<Photo> mPhotos;

        public int getPage() {
            return mPage;
        }

        public void setPage(int page) {
            mPage = page;
        }
    }
}
