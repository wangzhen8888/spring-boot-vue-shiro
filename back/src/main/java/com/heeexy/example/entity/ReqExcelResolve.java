package com.heeexy.example.entity;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.io.Serializable;
import java.util.List;



public class ReqExcelResolve implements Serializable {
    /**
     * 存在用户
     */
    @SerializedName("existsClient")
    @Expose
    private List<ResImportClient> existsClient;
    /**
     * 失败用户
     */
    @SerializedName("failClients")
    @Expose
    private List<ResImportClient> failClients;
    /**
     * 成功用户
     */
    @SerializedName("successClients")
    @Expose
    private List<ResImportClient> successClients;

    public List<ResImportClient> getExistsClient() {
        return existsClient;
    }

    public List<ResImportClient> getFailClients() {
        return failClients;
    }

    public List<ResImportClient> getSuccessClients() {
        return successClients;
    }

    public void setExistsClient(List<ResImportClient> existsClient) {
        this.existsClient = existsClient;
    }

    public void setFailClients(List<ResImportClient> failClients) {
        this.failClients = failClients;
    }

    public void setSuccessClients(List<ResImportClient> successClients) {
        this.successClients = successClients;
    }
}
