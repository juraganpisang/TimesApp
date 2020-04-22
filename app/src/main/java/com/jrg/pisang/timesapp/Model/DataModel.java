package com.jrg.pisang.timesapp.Model;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class DataModel {

    @SerializedName("news_id")
    @Expose
    private String news_id;

    @SerializedName("catnews_id")
    @Expose
    private String catnews_id;

    @SerializedName("news_datepub")
    @Expose
    private String news_datepub;

    @SerializedName("news_headline")
    @Expose
    private String news_headline;

    @SerializedName("news_title")
    @Expose
    private String news_title;

    //caption gambar
    @SerializedName("news_caption")
    @Expose
    private String news_caption;

    @SerializedName("news_image_new")
    @Expose
    private String news_image_new;

    @SerializedName("news_description")
    @Expose
    private String news_description;

    @SerializedName("news_content")
    @Expose
    private String news_content;

    @SerializedName("news_tags")
    @Expose
    private String news_tags;

    @SerializedName("news_view")
    @Expose
    private String news_view;

    @SerializedName("url_ci")
    @Expose
    private String url_ci;

    @SerializedName("news_city")
    @Expose
    private String news_city;

    @SerializedName("modified")
    @Expose
    private String modified;

    @SerializedName("news_writer")
    @Expose
    private String news_writer;

    @SerializedName("editor_name")
    @Expose
    private String editor_name;

    @SerializedName("source_site_name")
    @Expose
    private String source_site_name;

    @SerializedName("publisher_name")
    @Expose
    private String publisher_name;

    public String getNews_id() {
        return news_id;
    }

    public void setNews_id(String news_id) {
        this.news_id = news_id;
    }

    public String getCatnews_id() {
        return catnews_id;
    }

    public void setCatnews_id(String catnews_id) {
        this.catnews_id = catnews_id;
    }

    public String getNews_datepub() {
        return news_datepub;
    }

    public void setNews_datepub(String news_datepub) {
        this.news_datepub = news_datepub;
    }

    public String getNews_headline() {
        return news_headline;
    }

    public void setNews_headline(String news_headline) {
        this.news_headline = news_headline;
    }

    public String getNews_title() {
        return news_title;
    }

    public void setNews_title(String news_title) {
        this.news_title = news_title;
    }

    public String getNews_caption() {
        return news_caption;
    }

    public void setNews_caption(String news_caption) {
        this.news_caption = news_caption;
    }

    public String getNews_image_new() {
        return news_image_new;
    }

    public void setNews_image_new(String news_image_new) {
        this.news_image_new = news_image_new;
    }

    public String getNews_description() {
        return news_description;
    }

    public void setNews_description(String news_description) {
        this.news_description = news_description;
    }

    public String getNews_content() {
        return news_content;
    }

    public void setNews_content(String news_content) {
        this.news_content = news_content;
    }

    public String getNews_tags() {
        return news_tags;
    }

    public void setNews_tags(String news_tags) {
        this.news_tags = news_tags;
    }

    public String getNews_view() {
        return news_view;
    }

    public void setNews_view(String news_view) {
        this.news_view = news_view;
    }

    public String getUrl_ci() {
        return url_ci;
    }

    public void setUrl_ci(String url_ci) {
        this.url_ci = url_ci;
    }

    public String getNews_city() {
        return news_city;
    }

    public void setNews_city(String news_city) {
        this.news_city = news_city;
    }

    public String getModified() {
        return modified;
    }

    public void setModified(String modified) {
        this.modified = modified;
    }

    public String getNews_writer() {
        return news_writer;
    }

    public void setNews_writer(String news_writer) {
        this.news_writer = news_writer;
    }

    public String getEditor_name() {
        return editor_name;
    }

    public void setEditor_name(String editor_name) {
        this.editor_name = editor_name;
    }

    public String getSource_site_name() {
        return source_site_name;
    }

    public void setSource_site_name(String source_site_name) {
        this.source_site_name = source_site_name;
    }

    public String getPublisher_name() {
        return publisher_name;
    }

    public void setPublisher_name(String publisher_name) {
        this.publisher_name = publisher_name;
    }
}
