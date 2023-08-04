package com.example.firebaselab.ass;

import com.google.gson.annotations.SerializedName;

public class NasaResponse {
    @SerializedName("url")
    private String imageUrl;

    public String getImageUrl() {
        return imageUrl;
    }

    public void setImageUrl(String imageUrl) {
        this.imageUrl = imageUrl;
    }
}
