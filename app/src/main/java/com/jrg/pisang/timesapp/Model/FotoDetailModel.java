package com.jrg.pisang.timesapp.Model;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class FotoDetailModel {

    @SerializedName("status")
    @Expose
    private String status;

    @SerializedName("data")
    @Expose
    private DataFotoModel data;

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public DataFotoModel getData() {
        return data;
    }

    public void setData(DataFotoModel data) {
        this.data = data;
    }
}
