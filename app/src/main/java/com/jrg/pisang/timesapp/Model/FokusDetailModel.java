package com.jrg.pisang.timesapp.Model;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class FokusDetailModel {

    @SerializedName("status")
    @Expose
    private String status;

    @SerializedName("data")
    @Expose
    private DataFokusModel data;

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public DataFokusModel getData() {
        return data;
    }

    public void setData(DataFokusModel data) {
        this.data = data;
    }
}
