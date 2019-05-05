package com.heeexy.example.entity;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;
import lombok.Data;


@Data
public class ReqImportClient {

    @SerializedName("userName")
    @Expose
    private String userName;

    @SerializedName("loginName")
    @Expose
    private String loginName;
    @SerializedName("password")
    @Expose
    private String password;
    @SerializedName("groupID")
    @Expose
    private String groupID;

}
