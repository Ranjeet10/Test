package com.envent.bottlesup.mvp.model.server_response.user_profile.response;

/**
 * Created by ronem on 5/11/18.
 */

public class ProfileEditResponseModelHead {

    public String success;
    public String message;
    public ProfileEditResponseModelMain data;

    public String getSuccess() {
        return success;
    }

    public void setSuccess(String success) {
        this.success = success;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public ProfileEditResponseModelMain getData() {
        return data;
    }

    public void setData(ProfileEditResponseModelMain data) {
        this.data = data;
    }
}
