package com.jrg.pisang.timesapp.Model;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

class DataKanalModel {
    @SerializedName("id")
    @Expose
    private String id;

    @SerializedName("name")
    @Expose
    private String name;

    @SerializedName("slug")
    @Expose
    private String slug;

    @SerializedName("url")
    @Expose
    private String url;

    public String get_Id() {
        return id;
    }

    public void set_Id(String id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setSlug(String slug) {
        this.slug = slug;
    }
    public String get_Url() {
        return url;
    }

    public void set_Url(String url) {
        this.url = url;
    }
}
