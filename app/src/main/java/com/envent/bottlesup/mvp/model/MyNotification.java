package com.envent.bottlesup.mvp.model;

import com.activeandroid.Model;
import com.activeandroid.annotation.Column;
import com.activeandroid.annotation.Table;
import com.activeandroid.query.Delete;
import com.activeandroid.query.Select;

import java.util.List;

/**
 * Created by ronem on 4/19/18.
 */

@Table(name = "notification")
public class MyNotification extends Model {
    @Column(name = "notification_type")
    private String notificationType;
    @Column(name = "title")
    private String title;
    @Column(name = "image")
    private String image;
    @Column(name = "body")
    private String body;
    @Column(name = "created_on")
    private String createdOn;

    public MyNotification() {
        super();
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getImage() {
        return image;
    }

    public void setImage(String image) {
        this.image = image;
    }

    public String getBody() {
        return body;
    }

    public void setBody(String body) {
        this.body = body;
    }

    public String getNotificationType() {
        return notificationType;
    }

    public void setNotificationType(String notificationType) {
        this.notificationType = notificationType;
    }

    public String getCreatedOn() {
        return createdOn;
    }

    public void setCreatedOn(String createdOn) {
        this.createdOn = createdOn;
    }

    public static List<MyNotification> getNotifications() {
        return new Select().from(MyNotification.class).execute();
    }

    public static void clearAllNotifications() {
        new Delete().from(MyNotification.class).execute();
    }
}
