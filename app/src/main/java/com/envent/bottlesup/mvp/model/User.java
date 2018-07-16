package com.envent.bottlesup.mvp.model;

import com.activeandroid.Model;
import com.activeandroid.annotation.Column;
import com.activeandroid.annotation.Table;
import com.activeandroid.query.Delete;
import com.activeandroid.query.Select;

/**
 * Created by ronem on 3/23/18.
 */

@Table(name = "user")
public class User extends Model {
    @Column(name = "user_id")
    private int userId;

    public void setFullName(String fullName) {
        this.fullName = fullName;
    }

    @Column(name = "user_full_name")
    private String fullName;

    @Column(name = "user_name")
    private String userName;

    @Column(name = "profile_image")
    private String profileImage;

    @Column(name = "user_email")
    private String userEmail;

    @Column(name = "user_address")
    private String userAddress;

    @Column(name = "access_token")
    private String access_token;

    @Column(name = "refresh_token")
    private String refreshToken;

    @Column(name = "balance")
    private double balance;

    @Column(name = "verified")
    private boolean verified;

    @Column(name = "activated")
    private boolean activated;

    @Column(name = "blocked")
    private boolean blocked;

    @Column(name = "referral_code")
    private String referralCode;

    @Column(name = "allowedReferralCode")
    private int allowedReferralCode;

    @Column(name = "date_of_birth")
    private String dateOfBirth;

    @Column(name = "gender")
    private String gender;

    public String getGender() {
        return gender;
    }

    public void setGender(String gender) {
        this.gender = gender;
    }

    public User() {
        super();
    }

    public User(int userId, String fullName, String userName, String profileImage, String userEmail, String userAddress,
                String access_token, String refreshToken, double balance, boolean verified, boolean activated, boolean blocked
            , String referralCode, int allowedReferralCode, String dob, String gender) {
        this.userId = userId;
        this.fullName = fullName;
        this.userName = userName;
        this.profileImage = profileImage;
        this.userEmail = userEmail;
        this.userAddress = userAddress;
        this.access_token = access_token;
        this.refreshToken = refreshToken;
        this.balance = balance;
        this.verified = verified;
        this.activated = activated;
        this.blocked = blocked;
        this.referralCode = referralCode;
        this.allowedReferralCode = allowedReferralCode;
        this.dateOfBirth = dob;
        this.gender = gender;
    }

    public int getUserId() {
        return userId;
    }


    public String getFullName() {
        return fullName;
    }

    public String getDateOfBirth() {
        return dateOfBirth;
    }

    public void setDateOfBirth(String dateOfBirth) {
        this.dateOfBirth = dateOfBirth;
    }

    public String getUserName() {
        return userName;
    }

    public String getProfileImage() {
        return profileImage;
    }

    public String getUserEmail() {
        return userEmail;
    }

    public void setProfileImage(String profileImage) {
        this.profileImage = profileImage;
    }

    public String getUserAddress() {
        return userAddress;
    }


    public String getAccess_token() {
        return access_token;
    }


    public String getRefreshToken() {
        return refreshToken;
    }


    public double getBalance() {
        return balance;
    }

    public void setBalance(double balance) {
        this.balance = balance;
    }

    public boolean isVerified() {
        return verified;
    }

    public void setVerified(boolean verified) {
        this.verified = verified;
    }

    public boolean isActivated() {
        return activated;
    }

    public void setActivated(boolean activated) {
        this.activated = activated;
    }

    public boolean isBlocked() {
        return blocked;
    }

    public void setBlocked(boolean blocked) {
        this.blocked = blocked;
    }

    public String getReferralCode() {
        return referralCode;
    }

    public void setReferralCode(String referralCode) {
        this.referralCode = referralCode;
    }

    public int getAllowedReferralCode() {
        return allowedReferralCode;
    }

    public void setAllowedReferralCode(int allowedReferralCode) {
        this.allowedReferralCode = allowedReferralCode;
    }

    public void setUserEmail(String userEmail) {
        this.userEmail = userEmail;
    }

    @Override
    public String toString() {
        return "User{" +
                "userId=" + userId +
                ", fullName='" + fullName + '\'' +
                ", userName='" + userName + '\'' +
                ", profileImage='" + profileImage + '\'' +
                ", userEmail='" + userEmail + '\'' +
                ", userAddress='" + userAddress + '\'' +
                ", access_token='" + access_token + '\'' +
                ", refreshToken='" + refreshToken + '\'' +
                ", balance=" + balance +
                ", verified=" + verified +
                ", activated=" + activated +
                ", blocked=" + blocked +
                ", referralCode='" + referralCode + '\'' +
                ", allowedReferralCode=" + allowedReferralCode +
                ", dateOfBirth='" + dateOfBirth + '\'' +
                ", gender='" + gender + '\'' +
                '}';
    }

    public static User getUser() {
        return new Select().from(User.class).executeSingle();
    }

    public static boolean isUserVerified() {
        User u = new Select().from(User.class).executeSingle();
        return u.isVerified();
    }

    public static boolean isUserActivated() {
        User u = new Select().from(User.class).executeSingle();
        return u.isActivated();
    }

    public static boolean isUserBlocked() {
        User u = new Select().from(User.class).executeSingle();
        return u.isBlocked();
    }

    public static void logOut() {
        new Delete().from(User.class).execute();
    }
}
