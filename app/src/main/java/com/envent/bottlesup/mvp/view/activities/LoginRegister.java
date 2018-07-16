package com.envent.bottlesup.mvp.view.activities;

import android.app.DatePickerDialog;
import android.app.ProgressDialog;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.LocalBroadcastManager;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.AppCompatCheckBox;
import android.text.TextUtils;
import android.util.Log;
import android.view.KeyEvent;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.RelativeLayout;
import android.widget.ScrollView;
import android.widget.TextView;
import android.widget.Toast;

import com.envent.bottlesup.BuildConfig;
import com.envent.bottlesup.R;
import com.envent.bottlesup.mvp.MVPView;
import com.envent.bottlesup.mvp.model.User;
import com.envent.bottlesup.mvp.model.server_response.login_register_verified_blocked.LoginRegisterData;
import com.envent.bottlesup.mvp.model.server_response.login_register_verified_blocked.LoginResponse;
import com.envent.bottlesup.mvp.model.server_response.login_register_verified_blocked.RegisterResponse;
import com.envent.bottlesup.mvp.presenter.LoginRegisterPresenterImpl;
import com.envent.bottlesup.mvp.presenter.MyPresenter;
import com.envent.bottlesup.mvp.view.customview.CustomToast;
import com.envent.bottlesup.mvp.view.customview.EditTextAutoFill;
import com.envent.bottlesup.mvp.view.customview.MyDialog;
import com.envent.bottlesup.services.DeviceRegistrationService;
import com.envent.bottlesup.utils.MarshMalloPermission;
import com.envent.bottlesup.utils.MetaData;
import com.envent.bottlesup.utils.MyLog;
import com.envent.bottlesup.utils.UtilityMethods;

import java.util.Calendar;

import butterknife.BindString;
import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import butterknife.Unbinder;

/**
 * Created by ronem on 3/23/18.
 */

public class LoginRegister extends AppCompatActivity implements MVPView.LoginRegisterView, TextView.OnEditorActionListener {

    @BindView(R.id.sign_in_parent_layout)
    ScrollView signInParentLayout;
    @BindView(R.id.edit_sign_in_user_name)
    EditText editSignInUserName;
    @BindView(R.id.edit_sign_in_password)
    EditText editSignInPassword;
    @BindView(R.id.btn_sign_in_proceed)
    Button btnSignInProceed;
    @BindView(R.id.btn_create_account)
    LinearLayout btnCreateAccount;
    @BindView(R.id.show_hide_password)
    ImageView showHidePassword;

    @BindView(R.id.register_parent_layout)
    ScrollView registerParentLayout;
    @BindView(R.id.edit_register_first_name)
    EditText editRegisterFirstName;
    @BindView(R.id.edit_register_last_name)
    EditText editRegisterLastName;
    @BindView(R.id.edit_register_email)
    EditText editRegisterEmail;
    @BindView(R.id.edit_register_password)
    EditText editRegisterPassword;
    @BindView(R.id.edit_register_confirm_password)
    EditText editRegisterConfirmPassword;
    @BindView(R.id.edit_register_referral_code)
    EditText edit_referral_code;


    @BindView(R.id.btn_register_proceed)
    Button btnRegisterProceed;
    @BindView(R.id.already_member)
    LinearLayout btnAlreadyMember;
    @BindView(R.id.edit_register_mobile)
    EditText editRegisterMobile;
    @BindView(R.id.radio_group_gender)
    RadioGroup radioGroupGender;
    @BindView(R.id.chk_box_terms_condition)
    AppCompatCheckBox termsCheckbox;
    @BindView(R.id.dob_picker)
    TextView dobPicker;

    @BindView(R.id.verify_un_verify_layout)
    LinearLayout verifyUnverifyLayout;
    @BindView(R.id.sms1)
    EditTextAutoFill edtSms1;
    @BindView(R.id.sms2)
    EditTextAutoFill edtSms2;
    @BindView(R.id.sms3)
    EditTextAutoFill edtSms3;
    @BindView(R.id.sms4)
    EditTextAutoFill edtSms4;
    @BindView(R.id.btn_verify)
    Button btnVerify;

    @BindView(R.id.active_inactive_layout_parent)
    RelativeLayout activeInActiveLayout;
    @BindView(R.id.edit_resend_referral_code)
    EditText edtResendReferralCode;

    @BindView(R.id.blocked_layout_parent)
    RelativeLayout blockedLayout;


    private Unbinder unbinder;
    private final int TOGGLE_SIGN_IN = 1;
    private final int TOGGLE_REGISTER = 2;
    private final int TOGGLE_VERIFY = 3;
    private final int TOGGLE_ACTIVATE = 4;
    private final int TOGGLE_BLOCKED = 5;

    private Intent callIntent;

    private MyPresenter.LoginRegisterPresenter presenter;
    private MarshMalloPermission permission;


    @BindString(R.string.male)
    String MALE;
    @BindString(R.string.female)
    String FEMALE;
    @BindString(R.string.other)
    String OTHER;

    private int gender;
    private String dob;
    private boolean isTermsAndConditionChecked = false;
    private ProgressDialog dialog;
    User user;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        permission = new MarshMalloPermission(this);

        dialog = new ProgressDialog(this);
        dialog.setMessage(MetaData.MESSAGE.PLEASE_WAIT);
        dialog.setCancelable(false);
        user = User.getUser();
        presenter = new LoginRegisterPresenterImpl();
        presenter.addView(this);

        UtilityMethods.getFullScreen(this);
        setContentView(R.layout.login_register_layout);
        unbinder = ButterKnife.bind(this);


        if (user == null) {
            toggleSignInRegister(TOGGLE_SIGN_IN);
        } else {
            //check if verified or active
            checkVerifiedActive(user);
        }

        setRadioAndCheckboxListener();
        setListenerToSMSEdt();
//
//        if (BuildConfig.FLAVOR_internal) {
//            editSignInUserName.setText("9808065961");
//            editSignInPassword.setText("123456");
//        }
    }

    private void setListenerToSMSEdt() {
        edtSms1.setOnEditorActionListener(this);
        edtSms2.setOnEditorActionListener(this);
        edtSms3.setOnEditorActionListener(this);
        edtSms4.setOnEditorActionListener(this);
    }

    private void checkVerifiedActive(User u) {
        MyLog.i(this, "User Verified:" + u.isVerified());
        MyLog.i(this, "User Activated:" + u.isActivated());
        MyLog.i(this, "User Blocked:" + u.isBlocked());
        if (!u.isBlocked()) {
            if (!u.isVerified()) {
                //show not verified window
                toggleSignInRegister(TOGGLE_VERIFY);
//                showMessage(MetaData.MESSAGE.NOT_VERIFIED);
            } else if (!u.isActivated()) {
                //show not activated window
                toggleSignInRegister(TOGGLE_ACTIVATE);
//                showMessage(MetaData.MESSAGE.NOT_ACTIVE);
            } else {
                //every thing is fine may visit the venue
                launchIntent(Dashboard.class);
            }
        } else {
            toggleSignInRegister(TOGGLE_BLOCKED);
        }
    }


    private void setRadioAndCheckboxListener() {

        radioGroupGender.setOnCheckedChangeListener((group, checkedId) -> {
            RadioButton rb = (RadioButton) findViewById(checkedId);
            String s = rb.getText().toString();
            if (s.equals(MALE)) {
                gender = 0;
            } else if (s.equals(FEMALE)) {
                gender = 1;
            } else {
                gender = 2;
            }
        });

        //check the default male as gender
        ((RadioButton) radioGroupGender.getChildAt(0)).setChecked(true);

        termsCheckbox.setOnCheckedChangeListener((group, isChecked) -> isTermsAndConditionChecked = isChecked);
    }

    private void toggleSignInRegister(int what) {
        switch (what) {
            case TOGGLE_SIGN_IN:
                signInParentLayout.setVisibility(View.VISIBLE);
                registerParentLayout.setVisibility(View.GONE);
                verifyUnverifyLayout.setVisibility(View.GONE);
                activeInActiveLayout.setVisibility(View.GONE);
                blockedLayout.setVisibility(View.GONE);
                break;
            case TOGGLE_REGISTER:
                signInParentLayout.setVisibility(View.GONE);
                registerParentLayout.setVisibility(View.VISIBLE);
                verifyUnverifyLayout.setVisibility(View.GONE);
                activeInActiveLayout.setVisibility(View.GONE);
                blockedLayout.setVisibility(View.GONE);
                break;

            case TOGGLE_VERIFY:
                registerParentLayout.setVisibility(View.GONE);
                signInParentLayout.setVisibility(View.GONE);
                verifyUnverifyLayout.setVisibility(View.VISIBLE);
                activeInActiveLayout.setVisibility(View.GONE);
                blockedLayout.setVisibility(View.GONE);
                break;

            case TOGGLE_ACTIVATE:
                registerParentLayout.setVisibility(View.GONE);
                signInParentLayout.setVisibility(View.GONE);
                verifyUnverifyLayout.setVisibility(View.GONE);
                activeInActiveLayout.setVisibility(View.VISIBLE);
                blockedLayout.setVisibility(View.GONE);
                break;
            case TOGGLE_BLOCKED:
                registerParentLayout.setVisibility(View.GONE);
                signInParentLayout.setVisibility(View.GONE);
                verifyUnverifyLayout.setVisibility(View.GONE);
                activeInActiveLayout.setVisibility(View.GONE);
                blockedLayout.setVisibility(View.VISIBLE);
                break;
        }
    }

    @OnClick(R.id.show_hide_password)
    public void onShowHidePassword() {
        UtilityMethods.togglePasswordField(showHidePassword, editSignInPassword);
    }

    @OnClick(R.id.terms_and_condition_action)
    public void onTermsAndConditionActionClicked() {
        startActivity(new Intent(this, TermsAndConditions.class));
    }

    @OnClick(R.id.btn_sign_in_proceed)
    public void onBtnSignInProceedClicked() {
        presenter.proceedLogin();
    }

    @OnClick(R.id.btn_create_account)
    public void onBtnSignInCancelClicked() {
        toggleSignInRegister(TOGGLE_REGISTER);
    }

    @OnClick(R.id.btn_register_proceed)
    public void onBtnRegisterProceedClicked() {

        if (Build.VERSION.SDK_INT < Build.VERSION_CODES.O) {
            if (!permission.isSMSPermissionGranted()) {
                if (ActivityCompat.shouldShowRequestPermissionRationale(this, permission.smsPermissionRead[0])) {
                    new MyDialog(this)
                            .getDialogBuilder(MetaData.MESSAGE.SHOULD_SHOW_SMS_MESSAGE)
                            .setPositiveButton(MetaData.MESSAGE.GIVE_PERMISSION, (dialog, id) -> {
                                dialog.dismiss();
                                permission.requestSMSPermission();
                            })
                            .create()
                            .show();
                } else {
                    permission.requestSMSPermission();
                }
            } else {
                presenter.proceedRegister();
            }
        } else {
            if (!permission.isSMSReceivePermissionGranted()) {
                if (ActivityCompat.shouldShowRequestPermissionRationale(this, permission.smsPermissionReceive[0])) {
                    new MyDialog(this)
                            .getDialogBuilder(MetaData.MESSAGE.SHOULD_SHOW_SMS_MESSAGE)
                            .setPositiveButton(MetaData.MESSAGE.GIVE_PERMISSION, (dialog, id) -> {
                                dialog.dismiss();
                                permission.requestSMSReceivePermission();
                            })
                            .create()
                            .show();
                } else {
                    permission.requestSMSReceivePermission();
                }
            } else {
                presenter.proceedRegister();
            }
        }
    }

    @OnClick(R.id.already_member)
    public void onBtnRegisterCancelClicked() {
        toggleSignInRegister(TOGGLE_SIGN_IN);
    }

    @OnClick(R.id.back_to_login)
    void onBackTOLoginCLicked() {
        toggleSignInRegister(TOGGLE_SIGN_IN);
    }

    @OnClick(R.id.dob_picker)
    public void oinEdtDobClicked() {
        DatePickerDialog.OnDateSetListener onDateSetListener = (datePicker, year, month, day) -> {
            Calendar cal = Calendar.getInstance();
            cal.set(Calendar.YEAR, year);
            cal.set(Calendar.MONTH, month);
            cal.set(Calendar.DAY_OF_MONTH, day);

            dob = UtilityMethods.getDateFormat().format(cal.getTime());
            dobPicker.setText(dob);
        };

        Calendar cl = Calendar.getInstance();
        cl.set(cl.get(Calendar.YEAR) - 18, cl.get(Calendar.MONTH), cl.get(Calendar.DAY_OF_MONTH));

        DatePickerDialog dialog = new DatePickerDialog(this,
                onDateSetListener,
                cl.get(Calendar.YEAR),
                cl.get(Calendar.MONTH),
                cl.get(Calendar.DAY_OF_MONTH));
        dialog.getDatePicker().setMaxDate(cl.getTimeInMillis());
        dialog.show();
    }

    @OnClick(R.id.btn_verify)
    public void onBtnVerifyClicked() {
        String s1 = edtSms1.getText().toString().trim();
        String s2 = edtSms2.getText().toString().trim();
        String s3 = edtSms3.getText().toString().trim();
        String s4 = edtSms4.getText().toString().trim();
        if (TextUtils.isEmpty(s1)
                || TextUtils.isEmpty(s2)
                || TextUtils.isEmpty(s3)
                || TextUtils.isEmpty(s4)) {
            showMessage(MetaData.MESSAGE.EMPTY);
        } else {
            //call verify api
            String code = s1 + s2 + s3 + s4;
            User u = User.getUser();
            MyLog.i(this, "TOKEN|" + u.getAccess_token() + "UserName|" + u.getUserName() + "Code|" + code);
            presenter.proceedVerification(u.getAccess_token(), u.getUserName(), code);
        }
    }

    @OnClick(R.id.btn_resend_referral_code)
    public void onResendReferralCodeClick() {
        String referral = edtResendReferralCode.getText().toString();
        if (TextUtils.isEmpty(referral)) {
            edtResendReferralCode.setError(MetaData.MESSAGE.EMPTY);
        } else {
            presenter.proceedResendReferralcode(User.getUser().getAccess_token(), referral);
        }
    }

    @OnClick(R.id.btn_resend_otp)
    public void onBtnResendOtpClicked() {
        User u = User.getUser();
        presenter.proceedResendOTP(u.getAccess_token(), u.getUserName());
    }

    @OnClick(R.id.btn_customer_care_call)
    public void onBtnCustomerCareCallClicked() {

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
            makeCall();
        }
    }

    @OnClick(R.id.forgot_password)
    public void onForgotPasswordClicked() {
        startActivity(new Intent(this, ForgotPassword.class));
    }

    private void makeCall() {
        callIntent = new Intent(Intent.ACTION_CALL);
        callIntent.setData(Uri.parse("tel:" + MetaData.CUSTOMER_CARE_NUMBER));
        startActivity(callIntent);
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        switch (requestCode) {
            case MetaData.REQUEST_CODE.PHONE_CALL_REQUEST_CODE:
                if (grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                    startActivity(callIntent);
                } else {
                    CustomToast.makeText(this, MetaData.MESSAGE.DENIED_PHONE_CALL_SERVICE, Toast.LENGTH_LONG).show();
                }
                break;
            case MetaData.REQUEST_CODE.SMS_REQUEST_CODE:
                if (grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                    presenter.proceedRegister();
                }
                break;
        }
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        unbinder.unbind();
        if (this.isFinishing()) {
            presenter.cancelCall();
        }
    }

    @Override
    public void showProgressDialog() {
        if (!dialog.isShowing()) dialog.show();
    }

    @Override
    public void hideProgressDialog() {
        if (dialog.isShowing()) dialog.dismiss();
    }

    @Override
    public void showMessage(String msg) {
        CustomToast.makeText(this, msg, Toast.LENGTH_SHORT).show();
    }

    @Override
    public void unAuthorized() {

    }

    @Override
    public void onRegisterResponseReceived(RegisterResponse response) {
        MyLog.i("UserRegister:", response.toString());
        userResponseManipulate(response.getRegisterData());
    }

    @Override
    public void onLoginResponseReceived(LoginResponse response) {
        userResponseManipulate(response.getLoginRegisterData());
        UtilityMethods.hideKeyboard(this, editSignInUserName);

        /**
         * starting the {@link DeviceRegistrationService} service
         * to register the newly logged in or registered user's apiKey
         * to the local server for the push notification
         */
        startService(new Intent(this, DeviceRegistrationService.class));
    }

    @Override
    public void onVerifyUserResponseReceived(RegisterResponse response) {
        userResponseManipulate(response.getRegisterData());
        UtilityMethods.hideKeyboard(this, editSignInUserName);
    }

    @Override
    public void onResendOTPResponseReceived(LoginResponse response) {

    }

    @Override
    public String getSignInUserName() {
        return editSignInUserName.getText().toString().trim();
    }

    @Override
    public String getSignInPassword() {
        return editSignInPassword.getText().toString().trim();
    }

    @Override
    public void onSignInUserNameError(String msg) {
        editSignInUserName.setError(msg);
    }

    @Override
    public void onSignInUserPasswordError(String msg) {
        editSignInPassword.setError(msg);
    }

    @Override
    public String getRegisterFirstName() {
        return editRegisterFirstName.getText().toString().trim();
    }

    @Override
    public String getRegisterLastName() {
        return editRegisterLastName.getText().toString().trim();
    }

    @Override
    public String getRegisterEmail() {
        return editRegisterEmail.getText().toString().trim();
    }

    @Override
    public String getRegisterMobile() {
        return editRegisterMobile.getText().toString().trim();
    }

    @Override
    public String getRegisterPassword() {
        return editRegisterPassword.getText().toString().trim();
    }

    @Override
    public String getRegisterConfirmPassword() {
        return editRegisterConfirmPassword.getText().toString().trim();
    }

    @Override
    public String getReferralCode() {
        return edit_referral_code.getText().toString().trim();
    }

    @Override
    public String getRegisterDOB() {
        return dob;
    }

    @Override
    public int getRegisterGender() {
        return gender;
    }

    @Override
    public boolean getTermsCondition() {
        return isTermsAndConditionChecked;
    }

    @Override
    public void onRegisterFirstNameError(String msg) {
        editRegisterFirstName.setError(msg);
    }

    @Override
    public void onRegisterLastNameError(String msg) {
        editRegisterLastName.setError(msg);
    }

    @Override
    public void onRegisterEmailError(String msg) {
        editRegisterEmail.setError(msg);
    }

    @Override
    public void onRegisterMobileError(String msg) {
        editRegisterMobile.setError(msg);
    }

    @Override
    public void onRegisterPasswordError(String msg) {
        editRegisterPassword.setError(msg);
    }

    @Override
    public void onRegisterConfirmPasswordError(String msg) {
        editRegisterConfirmPassword.setError(msg);
    }

    @Override
    public void onRegisterReferralCodeError(String msg) {
        edit_referral_code.setError(msg);
    }

    private void launchIntent(Class<?> a) {
        Intent i = new Intent(this, a);
        i.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK | Intent.FLAG_ACTIVITY_NEW_TASK);
        startActivity(i);
    }

    @Override
    public boolean onEditorAction(TextView v, int actionId, KeyEvent event) {
        if (actionId == edtSms1.getImeActionId() && v.getId() == edtSms1.getId()) {
            edtSms2.requestFocus();
            return true;
        } else if (actionId == edtSms2.getImeActionId() && v.getId() == edtSms2.getId()) {
            edtSms3.requestFocus();
            return true;
        } else if (actionId == edtSms3.getImeActionId() && v.getId() == edtSms3.getId()) {
            edtSms4.requestFocus();
            return true;
        } else if (actionId == edtSms4.getImeActionId() && v.getId() == edtSms4.getId()) {
            btnVerify.performClick();
            return true;
        }
        return false;
    }

    private void userResponseManipulate(LoginRegisterData d) {
        if (d != null) {
            double balance = (double) d.getBalance();
            if (BuildConfig.DEBUG) {
                balance = balance + MetaData.EXTRA_BALANCE;
            }

            User u = new User(d.getUserId(), d.getName(), d.getUsername(), d.getProfileImage(), d.getEmail(), "", d.getToken(),
                    d.getToken(), balance, d.isVerified(), d.isActive(), d.isBlocked(), d.getReferalCode(), d.getAllowedReferralCount(), d.getDob(), d.getGender());
            u.save();
            checkVerifiedActive(u);
        }
    }

    BroadcastReceiver smsBroadCastReceiver = new BroadcastReceiver() {
        @Override
        public void onReceive(Context context, Intent intent) {
            Log.i("SMS", "Broad cast received");
            if (!LoginRegister.this.isFinishing()) {
                String smsCode = intent.getStringExtra(MetaData.SMS_VERIFICATION_CODE);
                Log.i("SMS", "Broad cast received with " + smsCode);
                char[] verificationCode = smsCode.toCharArray();
                edtSms1.setIsAutoFill(true);
                edtSms1.setText(String.valueOf(verificationCode[0]));
                edtSms2.setIsAutoFill(true);
                edtSms2.setText(String.valueOf(verificationCode[1]));
                edtSms3.setIsAutoFill(true);
                edtSms3.setText(String.valueOf(verificationCode[2]));
                edtSms4.setText(String.valueOf(verificationCode[3]));
            }
        }
    };

    @Override
    protected void onResume() {
        LocalBroadcastManager.getInstance(this)
                .registerReceiver(smsBroadCastReceiver, new IntentFilter(MetaData.SMS_RECEIVED_BROAD_CAST_EVENT));
        super.onResume();
    }

    @Override
    protected void onPause() {
        LocalBroadcastManager.getInstance(this)
                .unregisterReceiver(smsBroadCastReceiver);
        super.onPause();
    }
}

