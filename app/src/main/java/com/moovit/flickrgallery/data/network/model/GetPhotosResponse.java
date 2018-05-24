package com.moovit.flickrgallery.data.network.model;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.List;

public class GetPhotosResponse {

    @Expose
    @SerializedName("photos")
    private MetaData mMetaData;

    public MetaData getMetaData() {
        return mMetaData;
    }

    public void setMetaData(MetaData metaData) {
        mMetaData = metaData;
    }

    @Override
    public String toString() {
        return "GetPhotosResponse{" +
                "mMetaData=" + mMetaData +
                '}';
    }

    public static class MetaData {

        @Expose
        @SerializedName("pages")
        private int mNumberOfPages;

        @Expose
        @SerializedName("photo")
        private List<Photo> mPhotos;

        public int getNumberOfPages() {
            return mNumberOfPages;
        }

        public void setNumberOfPages(int numberOfPages) {
            mNumberOfPages = numberOfPages;
        }

        public List<Photo> getPhotos() {
            return mPhotos;
        }

        public void setPhotos(List<Photo> photos) {
            mPhotos = photos;
        }

        @Override
        public String toString() {
            return "MetaData{" +
                    "mNumberOfPages=" + mNumberOfPages +
                    ", mPhotos=" + mPhotos +
                    '}';
        }
    }
}
