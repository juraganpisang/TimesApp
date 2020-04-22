package com.jrg.pisang.timesapp.Model;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;


public class DataFokusModel {
    @SerializedName("focnews_id")
    @Expose
    private String focnews_id;

    @SerializedName("focnews_title")
    @Expose
    private String focnews_title;

    @SerializedName("focnews_description")
    @Expose
    private String focnews_description;

    @SerializedName("focnews_keyword")
    @Expose
    private String focnews_keyword;

    @SerializedName("focnews_image_body")
    @Expose
    private String focnews_image_body;

    @SerializedName("focnews_image_news")
    @Expose
    private String focnews_image_news;

    @SerializedName("focnews_image_mobile")
    @Expose
    private String focnews_image_mobile;

    @SerializedName("created")
    @Expose
    private String created;

    @SerializedName("modified")
    @Expose
    private String modified;

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

    public String getFocnews_description() {
        return focnews_description;
    }

    public void setFocnews_description(String focnews_description) {
        this.focnews_description = focnews_description;
    }

    public String getFocnews_keyword() {
        return focnews_keyword;
    }

    public void setFocnews_keyword(String focnews_keyword) {
        this.focnews_keyword = focnews_keyword;
    }

    public String getFocnews_image_body() {
        return focnews_image_body;
    }

    public void setFocnews_image_body(String focnews_image_body) {
        this.focnews_image_body = focnews_image_body;
    }

    public String getFocnews_image_news() {
        return focnews_image_news;
    }

    public void setFocnews_image_news(String focnews_image_news) {
        this.focnews_image_news = focnews_image_news;
    }

    public String getFocnews_image_mobile() {
        return focnews_image_mobile;
    }

    public void setFocnews_image_mobile(String focnews_image_mobile) {
        this.focnews_image_mobile = focnews_image_mobile;
    }

    public String getCreated() {
        return created;
    }

    public void setCreated(String created) {
        this.created = created;
    }

    public String getModified() {
        return modified;
    }

    public void setModified(String modified) {
        this.modified = modified;
    }

    public String getUrlPath() {
        return urlPath;
    }

    public void setUrlPath(String urlPath) {
        this.urlPath = urlPath;
    }
}
