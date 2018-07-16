package com.envent.bottlesup.mvp.model;

import com.activeandroid.Model;
import com.activeandroid.annotation.Column;
import com.activeandroid.annotation.Table;
import com.activeandroid.query.Delete;
import com.activeandroid.query.Select;

import java.util.List;

/**
 * Created by ronem on 5/3/18.
 */

@Table(name = "invited_contact")
public class InvitedContact extends Model {

    @Column(name = "contact")
    private String contact;

    public InvitedContact() {
        super();
    }

    public InvitedContact(String contact) {
        this.contact = contact;
    }

    public String getContact() {
        return contact;
    }

    public void setContact(String contact) {
        this.contact = contact;
    }

    public static List<InvitedContact> getContacts() {
        return new Select().from(InvitedContact.class).execute();
    }

    public static void clearAll() {
        new Delete().from(InvitedContact.class).execute();
    }

}
