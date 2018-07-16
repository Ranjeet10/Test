package com.envent.bottlesup.mvp.model.server_response.alias_response;

import com.google.gson.annotations.SerializedName;

import java.io.Serializable;

public class AliasResponse implements Serializable {

    @SerializedName("data")
    private AliasData aliasData;

    @SerializedName("status")
    private String status;

    public void setAliasData(AliasData aliasData) {
        this.aliasData = aliasData;
    }

    public AliasData getAliasData() {
        return aliasData;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String getStatus() {
        return status;
    }

    @Override
    public String toString() {
        return
                "AliasResponse{" +
                        "aliasData = '" + aliasData + '\'' +
                        ",status = '" + status + '\'' +
                        "}";
    }
}