package com.envent.bottlesup.mvp.presenter;

import android.app.Activity;
import android.content.Context;
import android.database.Cursor;
import android.net.Uri;
import android.provider.ContactsContract;
import android.text.TextUtils;
import android.view.View;
import android.widget.EditText;
import android.widget.LinearLayout;

import com.envent.bottlesup.R;
import com.envent.bottlesup.mvp.MVPView;
import com.envent.bottlesup.mvp.model.ContactsToInvite;
import com.envent.bottlesup.mvp.model.InvitedContact;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by ronem on 5/3/18.
 */

public class InviteFriendFriendPresenterImpl implements MyPresenter.InviteFriendPresenter {
    private MVPView.InviteFriendView view;

    @Override
    public void addView(MVPView.InviteFriendView view) {
        this.view = view;
    }

    @Override
    public void getFriendToInvite(LinearLayout parent, List<InvitedContact> invitedContacts) {
        List<ContactsToInvite> contactToBeInvitedInBulk = new ArrayList<>();
        //first find the invite contacts
        for (int a = 0; a < parent.getChildCount(); a++) {
            if (invitedContacts.size() != 0 && a < invitedContacts.size()) {
                //todo
            } else {
                View v = parent.getChildAt(a);
                EditText ed = v.findViewById(R.id.edit_contact_number);
                String contact = ed.getText().toString();
                if (!TextUtils.isEmpty(contact)) {
                    ContactsToInvite c = new ContactsToInvite(contact, false);
                    contactToBeInvitedInBulk.add(c);
                }
            }
        }

        if (contactToBeInvitedInBulk.size() == 0) {
            view.showMessage("Please enter at least one contact to invite");
        } else {
            view.onContactsToBeInvited(contactToBeInvitedInBulk);
        }
    }

    @Override
    public void readContactNumber(Context context, Uri contactData) {
        Cursor c = ((Activity) context).managedQuery(contactData, null, null, null, null);
        if (c.moveToFirst()) {
            String id = c.getString(c.getColumnIndexOrThrow(ContactsContract.Contacts._ID));
            String hasPhone = c.getString(c.getColumnIndex(ContactsContract.Contacts.HAS_PHONE_NUMBER));
            if (hasPhone.equalsIgnoreCase("1")) {
                Cursor phones = context.getContentResolver().query(ContactsContract.CommonDataKinds.Phone.CONTENT_URI, null,
                        ContactsContract.CommonDataKinds.Phone.CONTACT_ID + " = " + id, null, null);
                phones.moveToFirst();
                String cNumber = phones.getString(phones.getColumnIndex(ContactsContract.CommonDataKinds.Phone.NUMBER));

                String COUNTRY_CODE_WITH_SPACE = "+977 ";
                String COUNTRY_CODE = "+977";

                /** check if the number contains the country code*/
                if (cNumber.startsWith(COUNTRY_CODE_WITH_SPACE)) {
                    cNumber = cNumber.substring(COUNTRY_CODE_WITH_SPACE.length());
                } else if (cNumber.startsWith(COUNTRY_CODE)) {
                    cNumber = cNumber.substring(COUNTRY_CODE.length());
                }

                /**check if the mobile number contains special character like -*/
                if (cNumber.contains("-")) {
                    cNumber = cNumber.replaceAll("-", "");
                }
                phones.close();
                view.onContactNumberReceivedFromStartActivityForResult(cNumber);
            }
        }
    }


    @Override
    public void cancelCall() {

    }
}
