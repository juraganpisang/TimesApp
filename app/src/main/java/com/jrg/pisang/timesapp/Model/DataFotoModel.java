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

    @SerializedName("gal_description")
    @Expose
    private String gal_description;

    @SerializedName("gal_datepub")
    @Expose
    private String gal_datepub;

    @SerializedName("gal_content")
    @Expose
    private String gal_content;

    @SerializedName("gal_city")
    @Expose
    private String gal_city;

    @SerializedName("gal_pewarta")
    @Expose
    private String gal_pewarta;

    @SerializedName("gal_view")
    @Expose
    private String gal_view;

    @SerializedName("gal_cover")
    @Expose
    private String gal_cover;

    @SerializedName("editor_name")
    @Expose
    private String editor_name;

    @SerializedName("publisher_name")
    @Expose
    private String publisher_name;

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

    public String getGal_description() {
        return gal_description;
    }

    public void setGal_description(String gal_description) {
        this.gal_description = gal_description;
    }

    public String getGal_datepub() {
        return gal_datepub;
    }

    public void setGal_datepub(String gal_datepub) {
        this.gal_datepub = gal_datepub;
    }

    public String getGal_content() {
        return gal_content;
    }

    public void setGal_content(String gal_content) {
        this.gal_content = gal_content;
    }

    public String getGal_city() {
        return gal_city;
    }

    public void setGal_city(String gal_city) {
        this.gal_city = gal_city;
    }

    public String getGal_pewarta() {
        return gal_pewarta;
    }

    public void setGal_pewarta(String gal_pewarta) {
        this.gal_pewarta = gal_pewarta;
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

    public String getEditor_name() {
        return editor_name;
    }

    public void setEditor_name(String editor_name) {
        this.editor_name = editor_name;
    }

    public String getPublisher_name() {
        return publisher_name;
    }

    public void setPublisher_name(String publisher_name) {
        this.publisher_name = publisher_name;
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
