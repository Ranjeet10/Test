package com.envent.bottlesup.mvp.model;

/**
 * Created by ronem on 5/3/18.
 */

public class ContactsToInvite {
    private String contactNumber;
    private boolean isInvited;

    public ContactsToInvite(String contactNumber,boolean isInvited) {
        this.contactNumber = contactNumber;
        this.isInvited = isInvited;
    }

    public String getContactNumber() {
        return contactNumber;
    }

    public void setContactNumber(String contactNumber) {
        this.contactNumber = contactNumber;
    }

    public boolean isInvited() {
        return isInvited;
    }

    public void setInvited(boolean invited) {
        isInvited = invited;
    }
}
