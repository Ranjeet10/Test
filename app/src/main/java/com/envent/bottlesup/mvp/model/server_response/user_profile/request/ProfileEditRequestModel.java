package com.envent.bottlesup.mvp.model.server_response.user_profile.request;

/**
 * Created by ronem on 5/11/18.
 */

public class ProfileEditRequestModel {
    String name;
    String dob;
    String email;
    int gender;

    public ProfileEditRequestModel(String name, String dob, String email, int gender) {
        this.name = name;
        this.dob = dob;
        this.email = email;
        this.gender = gender;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getDob() {
        return dob;
    }

    public void setDob(String dob) {
        this.dob = dob;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public int getGender() {
        return gender;
    }

    public void setGender(int gender) {
        this.gender = gender;
    }
}
