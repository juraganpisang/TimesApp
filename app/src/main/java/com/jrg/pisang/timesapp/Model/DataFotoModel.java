package com.jrg.pisang.timesapp.Model;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class DataFotoModel {

    @SerializedName("gal_id")
    @Expose
    private String gal_id;

    @SerializedName("gal_title")
    @Expose
    private String gal_title;

    @SerializedName("gal_datepub")
    @Expose
    private String gal_datepub;

    @SerializedName("gal_description")
    @Expose
    private String gal_description;

    @SerializedName("gal_view")
    @Expose
    private String gal_view;

    @SerializedName("gal_cover")
    @Expose
    private String gal_cover;

    @SerializedName("galcat_title")
    @Expose
    private String galcat_title;

    @SerializedName("galcat_slug")
    @Expose
    private String galcat_slug;

    @SerializedName("urlPath")
    @Expose
    private String urlPath;

    public String getGal_id() {
        return gal_id;
    }

    public void setGal_id(String gal_id) {
        this.gal_id = gal_id;
    }

    public String getGal_title() {
        return gal_title;
    }

    public void setGal_title(String gal_title) {
        this.gal_title = gal_title;
    }

    public String getGal_datepub() {
        return gal_datepub;
    }

    public void setGal_datepub(String gal_datepub) {
        this.gal_datepub = gal_datepub;
    }

    public String getGal_description() {
        return gal_description;
    }

    public void setGal_description(String gal_description) {
        this.gal_description = gal_description;
    }

    public String getGal_view() {
        return gal_view;
    }

    public void setGal_view(String gal_view) {
        this.gal_view = gal_view;
    }

    public String getGal_cover() {
        return gal_cover;
    }

    public void setGal_cover(String gal_cover) {
        this.gal_cover = gal_cover;
    }

    public String getGalcat_title() {
        return galcat_title;
    }

    public void setGalcat_title(String galcat_title) {
        this.galcat_title = galcat_title;
    }

    public String getGalcat_slug() {
        return galcat_slug;
    }

    public void setGalcat_slug(String galcat_slug) {
        this.galcat_slug = galcat_slug;
    }

    public String getUrlPath() {
        return urlPath;
    }

    public void setUrlPath(String urlPath) {
        this.urlPath = urlPath;
    }
}
