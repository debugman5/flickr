package com.moovit.flickrgallery.data.network.model;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class Photo {

    @Expose
    @SerializedName("id")
    private long mId;

    @Expose
    @SerializedName("owner")
    private long mOwner;

    @Expose
    @SerializedName("url_s")
    private String mUrl;

    public long getId() {
        return mId;
    }

    public void setId(long id) {
        mId = id;
    }

    public long getOwner() {
        return mOwner;
    }

    public void setOwner(long owner) {
        mOwner = owner;
    }

    public String getUrl() {
        return mUrl;
    }

    public void setUrl(String url) {
        mUrl = url;
    }
}
