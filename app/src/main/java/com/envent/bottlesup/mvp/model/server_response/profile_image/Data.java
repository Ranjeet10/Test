package com.envent.bottlesup.mvp.model.server_response.profile_image;

import com.google.gson.annotations.SerializedName;

import java.io.Serializable;

public class Data implements Serializable {

	@SerializedName("gender")
	private String gender;

	@SerializedName("mobile")
	private String mobile;

	@SerializedName("verified")
	private boolean verified;

	@SerializedName("active")
	private boolean active;

	@SerializedName("profileImage")
	private String profileImage;

	@SerializedName("token")
	private String token;

	@SerializedName("allowedReferralCount")
	private int allowedReferralCount;

	@SerializedName("blocked")
	private boolean blocked;

	@SerializedName("balance")
	private double balance;

	@SerializedName("dob")
	private String dob;

	@SerializedName("referralCode")
	private String referralCode;

	@SerializedName("name")
	private String name;

	@SerializedName("id")
	private int id;

	@SerializedName("email")
	private String email;

	@SerializedName("username")
	private String username;

	public void setGender(String gender){
		this.gender = gender;
	}

	public void setMobile(String mobile){
		this.mobile = mobile;
	}

	public void setVerified(boolean verified){
		this.verified = verified;
	}

	public void setActive(boolean active){
		this.active = active;
	}

	public void setProfileImage(String profileImage){
		this.profileImage = profileImage;
	}

	public void setToken(String token){
		this.token = token;
	}

	public void setAllowedReferralCount(int allowedReferralCount){
		this.allowedReferralCount = allowedReferralCount;
	}

	public void setBlocked(boolean blocked){
		this.blocked = blocked;
	}

	public void setBalance(double balance){
		this.balance = balance;
	}

	public void setDob(String dob){
		this.dob = dob;
	}

	public void setReferralCode(String referralCode){
		this.referralCode = referralCode;
	}

	public void setName(String name){
		this.name = name;
	}

	public void setId(int id){
		this.id = id;
	}

	public void setEmail(String email){
		this.email = email;
	}

	public void setUsername(String username){
		this.username = username;
	}

	public String getGender() {
		return gender;
	}

	public String getMobile() {
		return mobile;
	}

	public boolean isVerified() {
		return verified;
	}

	public boolean isActive() {
		return active;
	}

	public String getProfileImage() {
		return profileImage;
	}

	public String getToken() {
		return token;
	}

	public int getAllowedReferralCount() {
		return allowedReferralCount;
	}

	public boolean isBlocked() {
		return blocked;
	}

	public double getBalance() {
		return balance;
	}

	public String getDob() {
		return dob;
	}

	public String getReferralCode() {
		return referralCode;
	}

	public String getName() {
		return name;
	}

	public int getId() {
		return id;
	}

	public String getEmail() {
		return email;
	}

	public String getUsername() {
		return username;
	}
}