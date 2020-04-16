package com.jrg.pisang.timesapp.Model;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;


public class DataFokus {
    @SerializedName("focnews_id")
    @Expose
    private String focnews_id;

    @SerializedName("focnews_title")
    @Expose
    private String focnews_title;

    @SerializedName("urlPath")
    @Expose
    private String urlPath;

    public String getFocnews_id() {
        return focnews_id;
    }

    public void setFocnews_id(String focnews_id) {
        this.focnews_id = focnews_id;
    }

    public String getFocnews_title() {
        return focnews_title;
    }

    public void setFocnews_title(String focnews_title) {
        this.focnews_title = focnews_title;
    }

    public String getUrlPath() {
        return urlPath;
    }

    public void setUrlPath(String urlPath) {
        this.urlPath = urlPath;
    }
}
