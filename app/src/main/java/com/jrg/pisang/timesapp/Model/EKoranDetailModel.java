package com.jrg.pisang.timesapp.Model;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class EKoranDetailModel {

    @SerializedName("status")
    @Expose
    private String status;

    @SerializedName("data")
    @Expose
    private DataKoranModel data;

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public DataKoranModel getData() {
        return data;
    }

    public void setData(DataKoranModel data) {
        this.data = data;
    }
}
