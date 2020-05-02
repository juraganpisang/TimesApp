package com.jrg.pisang.timesapp.Model;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class DataDetailKanalModel {
    @SerializedName("catnews_id")
    @Expose
    private String catnews_id;

    @SerializedName("catnews_order")
    @Expose
    private String catnews_order;

    @SerializedName("catnews_title")
    @Expose
    private String catnews_title;

    @SerializedName("catnews_slug")
    @Expose
    private String catnews_slug;

    @SerializedName("catnews_description")
    @Expose
    private String catnews_description;

    @SerializedName("catnews_keyword")
    @Expose
    private String catnews_keyword;

    @SerializedName("catnews_status")
    @Expose
    private String catnews_status;

    @SerializedName("created_by")
    @Expose
    private String created_by;

    @SerializedName("created")
    @Expose
    private String created;

    @SerializedName("modified_by")
    @Expose
    private String modified_by;

    @SerializedName("modified")
    @Expose
    private String modified;
    public String getCatnews_id() {
        return catnews_id;
    }

    public void setCatnews_id(String catnews_id) {
        this.catnews_id = catnews_id;
    }

    public String getCatnews_order() {
        return catnews_order;
    }

    public void setCatnews_order(String catnews_order) {
        this.catnews_order = catnews_order;
    }

    public String getCatnews_title() {
        return catnews_title;
    }

    public void setCatnews_title(String catnews_title) {
        this.catnews_title = catnews_title;
    }

    public String getCatnews_slug() {
        return catnews_slug;
    }

    public void setCatnews_slug(String catnews_slug) {
        this.catnews_slug = catnews_slug;
    }

    public String getCatnews_description() {
        return catnews_description;
    }

    public void setCatnews_description(String catnews_description) {
        this.catnews_description = catnews_description;
    }

    public String getCatnews_keyword() {
        return catnews_keyword;
    }

    public void setCatnews_keyword(String catnews_keyword) {
        this.catnews_keyword = catnews_keyword;
    }

    public String getCatnews_status() {
        return catnews_status;
    }

    public void setCatnews_status(String catnews_status) {
        this.catnews_status = catnews_status;
    }

    public String getCreated_by() {
        return created_by;
    }

    public void setCreated_by(String created_by) {
        this.created_by = created_by;
    }

    public String getCreated() {
        return created;
    }

    public void setCreated(String created) {
        this.created = created;
    }

    public String getModified_by() {
        return modified_by;
    }

    public void setModified_by(String modified_by) {
        this.modified_by = modified_by;
    }

    public String getModified() {
        return modified;
    }

    public void setModified(String modified) {
        this.modified = modified;
    }


}
