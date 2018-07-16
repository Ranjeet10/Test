package com.envent.bottlesup.mvp.model.server_response.user_profile.response;

/**
 * Created by ronem on 5/11/18.
 */

public class ProfileEditResponseModelMain {

      int id;
      String name;
      String username;
      String email;
      String profileImage;
      String token;
      String mobile;
      boolean verified;
      boolean active;
      boolean blocked;
      double balance;
      String dob;
      String gender;
      String referralCode;
      int allowedReferralCount;

      public int getId() {
            return id;
      }

      public void setId(int id) {
            this.id = id;
      }

      public String getName() {
            return name;
      }

      public void setName(String name) {
            this.name = name;
      }

      public String getUsername() {
            return username;
      }

      public void setUsername(String username) {
            this.username = username;
      }

      public String getEmail() {
            return email;
      }

      public void setEmail(String email) {
            this.email = email;
      }

      public String getProfileImage() {
            return profileImage;
      }

      public void setProfileImage(String profileImage) {
            this.profileImage = profileImage;
      }

      public String getToken() {
            return token;
      }

      public void setToken(String token) {
            this.token = token;
      }

      public String getMobile() {
            return mobile;
      }

      public void setMobile(String mobile) {
            this.mobile = mobile;
      }

      public boolean isVerified() {
            return verified;
      }

      public void setVerified(boolean verified) {
            this.verified = verified;
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

      public double getBalance() {
            return balance;
      }

      public void setBalance(double balance) {
            this.balance = balance;
      }

      public String getDob() {
            return dob;
      }

      public void setDob(String dob) {
            this.dob = dob;
      }

      public String getGender() {
            return gender;
      }

      public void setGender(String gender) {
            this.gender = gender;
      }

      public String getReferralCode() {
            return referralCode;
      }

      public void setReferralCode(String referralCode) {
            this.referralCode = referralCode;
      }

      public int getAllowedReferralCount() {
            return allowedReferralCount;
      }

      public void setAllowedReferralCount(int allowedReferralCount) {
            this.allowedReferralCount = allowedReferralCount;
      }
}
