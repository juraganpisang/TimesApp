package com.jrg.pisang.timesapp.Model;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class DataFokusDetail {

    @SerializedName("status")
    @Expose
    private String status;

    @SerializedName("data")
    @Expose
    private DataFokus data;

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public DataFokus getData() {
        return data;
    }

    public void setData(DataFokus data) {
        this.data = data;
    }
}
