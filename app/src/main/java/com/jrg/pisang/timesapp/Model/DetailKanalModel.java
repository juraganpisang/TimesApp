package com.jrg.pisang.timesapp.Model;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.List;

public class DetailKanalModel {
    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    @SerializedName("status")
    @Expose
    private String status;

    public DataDetailKanalModel getData() {
        return data;
    }

    public void setData(DataDetailKanalModel data) {
        this.data = data;
    }

    @SerializedName("data")
    @Expose
    private DataDetailKanalModel data;


}
