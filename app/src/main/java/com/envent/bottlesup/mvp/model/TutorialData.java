package com.envent.bottlesup.mvp.model;

/**
 * Created by ronem on 4/9/18.
 */

public class TutorialData {
    private int img;
    private String description;

    public TutorialData(int img, String description) {
        this.img = img;
        this.description = description;
    }

    public int getImg() {
        return img;
    }

    public String getDescription() {
        return description;
    }
}
