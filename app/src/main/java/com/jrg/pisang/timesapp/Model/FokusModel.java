package com.jrg.pisang.timesapp.Model;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.List;

public class FokusModel {
    @SerializedName("status")
    @Expose
    private String status;

    @SerializedName("data")
    @Expose
    private List<DataFokusModel> data;

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public List<DataFokusModel> getData() {
        return data;
    }

    public void setData(List<DataFokusModel> data) {
        this.data = data;
    }
}
