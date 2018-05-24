package com.moovit.flickrgallery.data.network.model;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class Photo {

    @Expose
    @SerializedName("id")
    private String mId;

    @Expose
    @SerializedName("owner")
    private String mOwner;

    @Expose
    @SerializedName("url_s")
    private String mUrl;

    public String getId() {
        return mId;
    }

    public void setId(String id) {
        mId = id;
    }

    public String getOwner() {
        return mOwner;
    }

    public void setOwner(String owner) {
        mOwner = owner;
    }

    public String getUrl() {
        return mUrl;
    }

    public void setUrl(String url) {
        mUrl = url;
    }

    @Override
    public String toString() {
        return "Photo{" +
                "mId=" + mId +
                ", mOwner=" + mOwner +
                ", mUrl='" + mUrl + '\'' +
                '}';
    }
}
