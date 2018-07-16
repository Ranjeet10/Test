package com.envent.bottlesup.mvp.view.activities;

import android.app.Activity;
import android.app.AlertDialog;
import android.app.PendingIntent;
import android.content.ActivityNotFoundException;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.provider.ContactsContract;
import android.support.annotation.Nullable;
import android.telephony.SmsManager;
import android.text.TextUtils;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.Toast;

import com.envent.bottlesup.R;
import com.envent.bottlesup.mvp.MVPView;
import com.envent.bottlesup.mvp.model.ContactsToInvite;
import com.envent.bottlesup.mvp.model.InvitedContact;
import com.envent.bottlesup.mvp.model.User;
import com.envent.bottlesup.mvp.presenter.InviteFriendFriendPresenterImpl;
import com.envent.bottlesup.mvp.presenter.MyPresenter;
import com.envent.bottlesup.mvp.view.activities.base_activity.SimpleBaseActivity;
import com.envent.bottlesup.mvp.view.customview.CustomToast;
import com.envent.bottlesup.mvp.view.customview.MyDialog;
import com.envent.bottlesup.utils.MarshMalloPermission;
import com.envent.bottlesup.utils.MetaData;

import java.util.List;

import butterknife.BindString;
import butterknife.BindView;
import butterknife.OnClick;

/**
 * Created by ronem on 5/3/18.
 */

public class InviteFriendActivity extends SimpleBaseActivity implements MVPView.InviteFriendView {
    @BindString(R.string.invite_a_friend)
    String toolbarTitle;
    @BindView(R.id.invitation_field_layout)
    LinearLayout inviteFieldLayout;

    List<InvitedContact> invitedContacts;


    private MyPresenter.InviteFriendPresenter presenter;
    private MarshMalloPermission permission;
    private LayoutInflater inflater;
    private int childPosition = 0;
    private BroadcastReceiver smsSentListener;
    private PendingIntent smsPendingIntent;
    private String selectedContact;


    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.invite_friend_layout);

        presenter = new InviteFriendFriendPresenterImpl();
        presenter.addView(this);
        inflater = LayoutInflater.from(this);


        permission = new MarshMalloPermission(this);
        if (!permission.isSMSSendPermissionGranted()) {
            permission.requestSMSSendPermission();
        }

        invitedContacts = InvitedContact.getContacts();

        inviteFieldLayout.removeAllViews();

        setUpInviteLayoutField();

        setUpSmsSentListener();

    }

    private void setUpSmsSentListener() {
        String smsIntentFilter = "SMS_SENT_FILTER";
        smsPendingIntent = PendingIntent.getBroadcast(this, 0, new Intent(smsIntentFilter), 0);
        smsSentListener = new BroadcastReceiver() {
            @Override
            public void onReceive(Context context, Intent intent) {
                if (getResultCode() == Activity.RESULT_OK) {
                    showMessage("Invited successfully");
                    InvitedContact i = new InvitedContact(selectedContact);
                    i.save();
                    InviteFriendActivity.this.finish();
                } else {
                    showMessage("Invitation not sent");
                }
            }
        };

        registerReceiver(smsSentListener, new IntentFilter(smsIntentFilter));
    }

    private void setUpInviteLayoutField() {
        int allowedReferralCount = User.getUser().getAllowedReferralCode();

        for (int i = 0; i < allowedReferralCount; i++) {

            View view = inflater.inflate(R.layout.single_item_invite_friend, null);
            EditText phoneField = (EditText) view.findViewById(R.id.edit_contact_number);
//            phoneField.setBackgroundResource(?attr/editTextBackground);
            ImageView contactPick = (ImageView) view.findViewById(R.id.pick_contact);
            ImageView inviteSingle = (ImageView) view.findViewById(R.id.invite_single);

            //enable disable the view
            if (invitedContacts.size() != 0 && i < invitedContacts.size()) {
                phoneField.setText(invitedContacts.get(i).getContact());
                enableViews(phoneField, contactPick, inviteSingle, false);
            } else {
                enableViews(phoneField, contactPick, inviteSingle, true);
            }

            inviteSingle.setOnClickListener((v) -> {
                String contact = phoneField.getText().toString();
                if (!TextUtils.isEmpty(contact)) {
                    if (!wasPersonInvited(contact)) {
                        inviteFriend(contact);
                    }

                } else {
                    showMessage(MetaData.MESSAGE.EMPTY);
                }
            });

            contactPick.setOnClickListener((v) -> {
                if (!permission.isContactReadPermissionGranted()) {
                    permission.requestReadContactPermission();
                } else {
                    childPosition = inviteFieldLayout.indexOfChild(view);
                    Log.i("ChildPos", " " + childPosition);
                    Intent intent = new Intent(Intent.ACTION_PICK, ContactsContract.Contacts.CONTENT_URI);
                    startActivityForResult(intent, MetaData.REQUEST_CODE.READ_CONTACT_REQUEST_CODE);
                }
            });

            inviteFieldLayout.addView(view);

        }
    }

    private void enableViews(EditText phoneField, ImageView contactPick, ImageView inviteSingle, boolean shouldEnable) {
        phoneField.setEnabled(shouldEnable);
        contactPick.setEnabled(shouldEnable);
        inviteSingle.setEnabled(shouldEnable);
    }


    @Override
    public void onActivityResult(int reqCode, int resultCode, Intent data) {
        super.onActivityResult(reqCode, resultCode, data);

        switch (reqCode) {
            case MetaData.REQUEST_CODE.READ_CONTACT_REQUEST_CODE:
                if (resultCode == Activity.RESULT_OK) {
                    Uri contactData = data.getData();
                    presenter.readContactNumber(this, contactData);
                }
        }
    }


    @Override
    protected void onStart() {
        super.onStart();
        getToolbarTitleTextView().setText(toolbarTitle);
    }

    @Override
    protected void updateBalance() {

    }

    @Override
    public void showMessage(String msg) {
        new CustomToast(this, msg, Toast.LENGTH_SHORT);
    }

    @Override
    public void unAuthorized() {
        logoutNow();
    }

    @Override
    public void showProgressDialog() {
        showMyProgressDialog();
    }

    @Override
    public void hideProgressDialog() {
        cancelProgressDialog();
    }

    @Override
    public void onContactNumberReceivedFromStartActivityForResult(String contactNumber) {
        if (!wasPersonInvited(contactNumber)) {
            View itemView = inviteFieldLayout.getChildAt(childPosition);
            EditText editText = (EditText) itemView.findViewById(R.id.edit_contact_number);
            editText.setText(contactNumber);
        }
    }

    private boolean wasPersonInvited(String contactNumber) {
        for (int i = 0; i < invitedContacts.size(); i++) {
            String invitedContact = invitedContacts.get(i).getContact();
            if (invitedContact.equals(contactNumber)) {
                showMessage("Cannot invite same person multiple times");
                return true;
            }
        }
        return false;
    }

    @Override
    public void onContactsToBeInvited(List<ContactsToInvite> newContacts) {
        String referralCode = getUser().getReferralCode();

        AlertDialog.Builder builder = new MyDialog(this).getDialogBuilder("Bottles Up would like to send a message. This may cause charges on your mobile account");
        builder.setPositiveButton("Invite", (dialog, which) -> {

            for (int i = 0; i < newContacts.size(); i++) {
                ContactsToInvite ci = newContacts.get(i);
                Log.i("CONTACTLoop", ci.getContactNumber());
                sendLoopInvitation(ci.getContactNumber(), referralCode);
            }
            InviteFriendActivity.this.finish();
            dialog.dismiss();
        });
        builder.setNegativeButton("Cancel", (dialog, which) -> dialog.dismiss());
        builder.create().show();
    }

    @OnClick(R.id.btn_invite_friend)
    public void oinBtnInviteCLicked() {
        presenter.getFriendToInvite(inviteFieldLayout, invitedContacts);
    }

    private void sendLoopInvitation(String toInviteContact, String referralCode) {
        SmsManager sms = SmsManager.getDefault();
        sms.sendTextMessage(toInviteContact, null, "Please use this referral code " + referralCode + " And get the free membership ", null, null);
        InvitedContact i = new InvitedContact(toInviteContact);
        i.save();
    }

    private void sendSingleInvitation(String contact, String referralCode) {

        AlertDialog.Builder builder = new MyDialog(this).getDialogBuilder("BottlesUp would like to send a message to " + contact + ". Applicable charges may apply.");
        builder.setPositiveButton("Invite", (dialog, which) -> {
            int apiLevel = Build.VERSION.SDK_INT;
            String releaseVersion = Build.VERSION.RELEASE;

            Log.i("OS", apiLevel + "(" + releaseVersion + ")");

            if (apiLevel == Build.VERSION_CODES.O && releaseVersion.equals("8.0.0")) {
                try {
                    Intent smsIntent = new Intent(Intent.ACTION_VIEW);
                    smsIntent.setType("vnd.android-dir/mms-sms");
                    smsIntent.putExtra("address", contact);
                    smsIntent.putExtra("sms_body", MetaData.MESSAGE.YOU_HAVE_BEEN_INVITED);
                    startActivity(smsIntent);
                } catch (Exception e) {
                    if (e instanceof ActivityNotFoundException) {
                        showMessage("No sms client found. Please try again later");
                    } else {
                        showMessage("Please try again later");
                    }
                }
            } else {
                selectedContact = contact;
                SmsManager sms = SmsManager.getDefault();
                sms.sendTextMessage(contact, null, MetaData.MESSAGE.YOU_HAVE_BEEN_INVITED, smsPendingIntent, null);
            }

            dialog.dismiss();
        });
        builder.setNegativeButton("Cancel", (dialog, which) -> dialog.dismiss());
        builder.create().show();
    }


    private void inviteFriend(String contact) {
        String referralCode = getUser().getReferralCode();
        sendSingleInvitation(contact, referralCode);
    }


    @Override
    protected void onDestroy() {
        super.onDestroy();
        unregisterReceiver(smsSentListener);
    }
}
