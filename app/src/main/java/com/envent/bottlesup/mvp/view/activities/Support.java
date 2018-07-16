package com.envent.bottlesup.mvp.view.activities;

import android.content.ActivityNotFoundException;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.widget.TextView;
import android.widget.Toast;

import com.envent.bottlesup.R;
import com.envent.bottlesup.mvp.MVPView;
import com.envent.bottlesup.mvp.model.CheckedInVenue;
import com.envent.bottlesup.mvp.view.activities.base_activity.SimpleBaseActivity;
import com.envent.bottlesup.mvp.view.customview.CustomToast;
import com.envent.bottlesup.utils.MarshMalloPermission;

import butterknife.BindString;
import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

/**
 * Created by ronem on 3/16/18.
 */

public class Support extends SimpleBaseActivity implements MVPView.SupportView {
    @BindString(R.string.support)
    String toolbarTitle;
    @BindView(R.id.venue_name)
    TextView venueName;
    @BindView(R.id.sales_support_email)
    TextView salesSupportEmail;
    @BindView(R.id.technical_support_email)
    TextView technicalSupportEmail;


    private CheckedInVenue checkedInVenue;
    private MarshMalloPermission permission;

    @Override
    protected void onStart() {
        super.onStart();
        getToolbarTitleTextView().setText(toolbarTitle);
    }

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.support_layout);
        ButterKnife.bind(this);
        permission = new MarshMalloPermission(this);
        checkedInVenue = CheckedInVenue.getCheckedInVenue();

        if (checkedInVenue != null) {
            venueName.setText("Checked-In Venue: " + checkedInVenue.getVenueName());
        } else {
            venueName.setText("BottlesUp");
        }

    }

    @Override
    protected void updateBalance() {

    }

    @Override
    public void showMessage(String msg) {
        new CustomToast(getApplicationContext(), msg, Toast.LENGTH_SHORT);
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

    @OnClick(R.id.sales_support_email)
    public void onSalesSupportEmailClicked() {
        sendEmail(salesSupportEmail.getText().toString());
    }

    @OnClick(R.id.technical_support_email)
    public void onTechnicalSupportEmailClicked() {
        sendEmail(technicalSupportEmail.getText().toString());
    }


    private void sendEmail(String receiver) {
        String body = "Name : Type your Name\nMessage : Type Message Body here";
        String subject = "Subject";
//        Intent i = new Intent(Intent.ACTION_SEND);
//        i.setType("message/rfc822");
//        i.putExtra(Intent.EXTRA_EMAIL, receiver);
//        i.putExtra(Intent.EXTRA_SUBJECT, subject);
//        i.putExtra(Intent.EXTRA_TEXT, body);
//        try {
//            startActivity(Intent.createChooser(i, "Send mail..."));
//        } catch (ActivityNotFoundException ex) {
//            Toast.makeText(Support.this, "There are no email clients installed.", Toast.LENGTH_SHORT).show();
//        }

        Intent intent = new Intent(Intent.ACTION_VIEW);
        Uri data = Uri.parse("mailto:" + receiver + "?subject=" + subject + "&body=" + body);
        intent.setData(data);

        try {
            startActivity(intent);
        } catch (ActivityNotFoundException ex) {
            Toast.makeText(Support.this, "There are no email clients installed.", Toast.LENGTH_SHORT).show();
        }

    }
    /*

    @OnClick(R.id.venue_phone)
    public void onVenueContactClicked() {

        if (!permission.isPhoneCallPermissionGranted()) {
            if (ActivityCompat.shouldShowRequestPermissionRationale(this, permission.phoneCallPermissions[0])) {
                new MyDialog(this)
                        .getDialogBuilder(MetaData.MESSAGE.SHOULD_SHOW_PHONE_CALL_MESSAGE)
                        .setPositiveButton(MetaData.MESSAGE.GIVE_PERMISSION, (dialog, id) -> {
                            dialog.dismiss();
                            permission.requestPhoneCallPermission();
                        }).create().show();
            } else {
                permission.requestPhoneCallPermission();
            }
        } else {

            Intent callIntent = new Intent(Intent.ACTION_CALL);
            callIntent.setData(Uri.parse("tel:" + MetaData.CUSTOMER_CARE_NUMBER));
            if (ActivityCompat.checkSelfPermission(this, Manifest.permission.CALL_PHONE) != PackageManager.PERMISSION_GRANTED) {
                return;
            }
            startActivity(callIntent);
        }
    }

*/
}
