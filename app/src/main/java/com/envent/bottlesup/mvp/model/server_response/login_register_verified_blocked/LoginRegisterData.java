package com.envent.bottlesup.mvp.model.server_response.login_register_verified_blocked;

import com.google.gson.annotations.SerializedName;

public class LoginRegisterData {

    @SerializedName("balance")
    private Double balance;

    @SerializedName("name")
    private String name;

    @SerializedName("profileImage")
    private String profileImage;

    @SerializedName("mobile")
    private String mobile;

    @SerializedName("verified")
    private boolean verified;

    @SerializedName("active")
    private boolean active;

    @SerializedName("blocked")
    private boolean blocked;

    @SerializedName("id")
    private int userId;

    @SerializedName("email")
    private String email;

    @SerializedName("username")
    private String username;

    @SerializedName("token")
    private String token;

    @SerializedName("referralCode")
    private String referalCode;

    @SerializedName("allowedReferralCount")
    private int allowedReferralCount;
    @SerializedName("gender")
    private String gender;

    public String getGender() {
        return gender;
    }

    public void setGender(String gender) {
        this.gender = gender;
    }

    @SerializedName("dob")
    private String dob;

    public String getDob() {
        return dob;
    }

    public void setDob(String dob) {
        this.dob = dob;
    }

    public void setBalance(Double balance) {
        this.balance = balance;
    }

    public Double getBalance() {
        return balance;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getName() {
        return name;
    }

    public String getProfileImage() {
        return profileImage;
    }

    public void setProfileImage(String profileImage) {
        this.profileImage = profileImage;
    }

    public void setMobile(String mobile) {
        this.mobile = mobile;
    }

    public String getMobile() {
        return mobile;
    }

    public void setVerified(boolean verified) {
        this.verified = verified;
    }

    public boolean isVerified() {
        return verified;
    }

    public boolean isActive() {
        return active;
    }

    public void setActive(boolean active) {
        this.active = active;
    }

    public boolean isBlocked() {
        return blocked;
    }

    public void setBlocked(boolean blocked) {
        this.blocked = blocked;
    }

    public int getUserId() {
        return userId;
    }

    public void setUserId(int userId) {
        this.userId = userId;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getEmail() {
        return email;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getUsername() {
        return username;
    }

    public void setToken(String token) {
        this.token = token;
    }

    public String getToken() {
        return token;
    }

    public String getReferalCode() {
        return referalCode;
    }

    public void setReferalCode(String referalCode) {
        this.referalCode = referalCode;
    }

    public int getAllowedReferralCount() {
        return allowedReferralCount;
    }

    public void setAllowedReferralCount(int allowedReferralCount) {
        this.allowedReferralCount = allowedReferralCount;
    }

    @Override
    public String toString() {
        return
                "LoginRegisterData{" +
                        "balance = '" + balance + '\'' +
                        ",name = '" + name + '\'' +
                        ",profileImage = '" + profileImage + '\'' +
                        ",mobile = '" + mobile + '\'' +
                        ",verified = '" + verified + '\'' +
                        ",id = '" + userId + '\'' +
                        ",email = '" + email + '\'' +
                        ",username = '" + username + '\'' +
                        ",token = '" + token + '\'' +
                        ",referalCode = '" + referalCode + '\'' +
                        ",allowedReferralCount = '" + allowedReferralCount + '\'' +
                        "}";
    }
}