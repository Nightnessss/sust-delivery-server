package com.fehead.model;

import javax.validation.constraints.NotBlank;

public class UserModel {

    private Integer id;
    private String telphone;
    private String email;
    private String registerMode;
    private String thirdPartyId;
    private String avatar;
    private String displayName;

    public UserModel(String telphone, String email, String registerMode, String thirdPartyId, String avatar, String displayName) {
        this.telphone = telphone;
        this.email = email;
        this.registerMode = registerMode;
        this.thirdPartyId = thirdPartyId;
        this.avatar = avatar;
        this.displayName = displayName;
    }

    public UserModel(String registerMode, String thirdPartyId, String avatar, String displayName) {
        this.registerMode = registerMode;
        this.thirdPartyId = thirdPartyId;
        this.avatar = avatar;
        this.displayName = displayName;
    }

    public UserModel() {

    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getTelphone() {
        return telphone;
    }

    public void setTelphone(String telphone) {
        this.telphone = telphone;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getRegisterMode() {
        return registerMode;
    }

    public void setRegisterMode(String registerMode) {
        this.registerMode = registerMode;
    }

    public String getThirdPartyId() {
        return thirdPartyId;
    }

    public void setThirdPartyId(String thirdPartyId) {
        this.thirdPartyId = thirdPartyId;
    }

    public String getAvatar() {
        return avatar;
    }

    public void setAvatar(String avatar) {
        this.avatar = avatar;
    }

    public String getDisplayName() {
        return displayName;
    }

    public void setDisplayName(String displayName) {
        this.displayName = displayName;
    }

    @Override
    public String toString() {
        return "UserModel{" +
                "id=" + id +
                ", telphone='" + telphone + '\'' +
                ", email='" + email + '\'' +
                ", registerMode='" + registerMode + '\'' +
                ", thirdPartyId='" + thirdPartyId + '\'' +
                ", avatar='" + avatar + '\'' +
                ", displayName='" + displayName + '\'' +
                '}';
    }
}
