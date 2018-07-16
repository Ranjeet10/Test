package com.envent.bottlesup.mvp.view.activities;

import android.app.DatePickerDialog;
import android.app.ProgressDialog;
import android.content.CursorLoader;
import android.content.Intent;
import android.database.Cursor;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.support.annotation.Nullable;
import android.support.v4.app.ActivityCompat;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TextView;
import android.widget.Toast;

import com.envent.bottlesup.R;
import com.envent.bottlesup.application.BottlesUpApplication;
import com.envent.bottlesup.mvp.model.User;
import com.envent.bottlesup.mvp.model.server_response.profile_image.ProfileImageResponse;
import com.envent.bottlesup.mvp.model.server_response.user_profile.request.ProfileEditRequestModel;
import com.envent.bottlesup.mvp.model.server_response.user_profile.response.ProfileEditResponseModelHead;
import com.envent.bottlesup.mvp.model.server_response.user_profile.response.ProfileEditResponseModelMain;
import com.envent.bottlesup.mvp.view.activities.base_activity.SimpleBaseActivity;
import com.envent.bottlesup.mvp.view.customview.MyDialog;
import com.envent.bottlesup.network.MessageParser;
import com.envent.bottlesup.utils.MarshMalloPermission;
import com.envent.bottlesup.utils.MetaData;
import com.envent.bottlesup.utils.UtilityMethods;
import com.squareup.picasso.Picasso;

import java.io.File;
import java.io.IOException;
import java.util.Calendar;
import java.util.HashMap;

import butterknife.BindString;
import butterknife.BindView;
import butterknife.OnClick;
import de.hdodenhof.circleimageview.CircleImageView;
import okhttp3.MediaType;
import okhttp3.MultipartBody;
import okhttp3.RequestBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

/**
 * Created by ronem on 4/19/18.
 */

public class EditProfileActivity extends SimpleBaseActivity {

    @BindString(R.string.edit_profile)
    String titleEditProfile;
    @BindView(R.id.user_image)
    CircleImageView userImage;
    @BindView(R.id.btn_edit_profile)
    ImageView btnEditProfile;
    @BindView(R.id.profile_email)
    EditText profileEmail;
    @BindView(R.id.name)
    EditText name;
    @BindView(R.id.profile_phone)
    EditText profilePhone;
    @BindView(R.id.dob)
    TextView edtDob;
    @BindView(R.id.radio_group_gender)
    RadioGroup radioGroupGender;
    @BindView(R.id.save)
    Button save;

    String nameString;
    String dobString;
    String email;
    int gender;

    private HashMap<String, Integer> genderMap;
    private int PROFILE_IMAGE_REQUEST_CODE = 117;
    private Uri fileUri;
    private File originalFile;
    private String filePath;
    private MarshMalloPermission permission;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.edit_profile_layout);

        genderMap = new HashMap<>();
        genderMap.put(getResources().getString(R.string.male), 0);
        genderMap.put(getResources().getString(R.string.female), 1);
        genderMap.put(getResources().getString(R.string.other), 2);

        setUpUserData();
        setUpRadioGroup();

        permission = new MarshMalloPermission(this);

        UtilityMethods.hideKeyboard(this, userImage);
    }


    private void setUpRadioGroup() {

        radioGroupGender.setOnCheckedChangeListener((group, checkedId) -> {
            RadioButton rb = (RadioButton) findViewById(checkedId);
            String s = rb.getText().toString();
            if (s.equalsIgnoreCase(getString(R.string.male))) {
                gender = 0;
            } else if (s.equalsIgnoreCase(getString(R.string.female))) {
                gender = 1;
            } else {
                gender = 2;
            }
        });

    }

    @OnClick(R.id.btn_edit_profile)
    public void onBtnEditProfileClicked() {
        if (save.getVisibility() == View.VISIBLE) {
            setEditEnabled(false);
        } else {
            setEditEnabled(true);
        }
    }

    @OnClick(R.id.user_image)
    public void onuserImageClicked() {
        if (!permission.isExternalPermissionGranted()) {
            if (ActivityCompat.shouldShowRequestPermissionRationale(this, permission.ExternalPermissions[0])) {
                new MyDialog(this)
                        .getDialogBuilder(MetaData.MESSAGE.SHOULD_SHOW_EXTERNAL_STORAGE_MESSAGE)
                        .setCancelable(true)
                        .setPositiveButton(MetaData.MESSAGE.GIVE_PERMISSION, (dialog, which) -> {
                            dialog.dismiss();
                            permission.requestExternalStorage();
                        })
                        .create().show();
            } else {
                permission.requestExternalStorage();
            }
            return;
        }
        Intent intent = new Intent(Intent.ACTION_PICK);
        intent.setType("image/*");
        startActivityForResult(intent, PROFILE_IMAGE_REQUEST_CODE);
    }

    @OnClick(R.id.save)
    public void onSaveClicked() {
        showMyProgressDialog();
        getEditedData();
        editProfile(new ProfileEditRequestModel(nameString, dobString, email, gender));
    }

    private void setUpUserData() {
        User user = User.getUser();
        Picasso.with(this).load(user.getProfileImage()).placeholder(R.drawable.users).into(userImage);
        profileEmail.setText(user.getUserEmail());
        name.setText(user.getFullName());
        edtDob.setText(user.getDateOfBirth());
        profilePhone.setText(user.getUserName());

        gender = genderMap.get(user.getGender());
        ((RadioButton) radioGroupGender.getChildAt(gender)).setChecked(true);
    }


    private void getEditedData() {
        nameString = name.getText().toString().trim();
        dobString = edtDob.getText().toString();
        email = profileEmail.getText().toString();
    }

    private void setEditEnabled(boolean enable) {
        name.setEnabled(enable);
        edtDob.setEnabled(enable);

        for (int i = 0; i < radioGroupGender.getChildCount(); i++) {
            RadioButton rb = (RadioButton) radioGroupGender.getChildAt(i);
            rb.setEnabled(enable);
        }
        profileEmail.setEnabled(enable);

        if (enable) {
            save.setVisibility(View.VISIBLE);
        } else {
            save.setVisibility(View.GONE);
        }

    }

    @OnClick(R.id.dob)
    public void onDobClicked() {
        DatePickerDialog.OnDateSetListener onDateSetListener = (datePicker, year, month, day) -> {
            Calendar cal = Calendar.getInstance();
            cal.set(Calendar.YEAR, year);
            cal.set(Calendar.MONTH, month);
            cal.set(Calendar.DAY_OF_MONTH, day);

            String dob = UtilityMethods.getDateFormat().format(cal.getTime());
            edtDob.setText(dob);
        };

        Calendar cl = Calendar.getInstance();
        String[] dates = getUser().getDateOfBirth().split("-");
        int year = Integer.parseInt(dates[0]);
        int month = Integer.parseInt(dates[1]) - 1;
        int day = Integer.parseInt(dates[2]);

        cl.set(year, month, day);

        DatePickerDialog dialog = new DatePickerDialog(this,
                onDateSetListener,
                cl.get(Calendar.YEAR),
                cl.get(Calendar.MONTH),
                cl.get(Calendar.DAY_OF_MONTH));
        dialog.getDatePicker().setMaxDate(cl.getTimeInMillis());
        dialog.show();
    }

    @Override
    protected void onStart() {
        super.onStart();
        getToolbarTitleTextView().setText(titleEditProfile);
    }

    @Override
    protected void updateBalance() {

    }


    public void editProfile(ProfileEditRequestModel profileEditRequestModel) {
        Call<ProfileEditResponseModelHead> resendReferralCodeCall = BottlesUpApplication.getApi().editProfile(User.getUser().getAccess_token(), profileEditRequestModel);
        resendReferralCodeCall.enqueue(new Callback<ProfileEditResponseModelHead>() {
            @Override
            public void onResponse(Call<ProfileEditResponseModelHead> call, Response<ProfileEditResponseModelHead> response) {
                cancelProgressDialog();
                if (response.isSuccessful()) {
                    User user = User.getUser();
                    ProfileEditResponseModelMain data = response.body().getData();
                    user.setDateOfBirth(data.getDob());
                    user.setFullName(data.getName());
                    user.setGender(data.getGender());
                    user.setUserEmail(data.getEmail());
                    user.save();
                    setEditEnabled(false);
                    Toast.makeText(EditProfileActivity.this, response.body().getMessage(), Toast.LENGTH_SHORT).show();
                } else {
                    if (response.code() == 401) {
                        logoutNow();
                    } else {
                        try {
                            String err = response.errorBody().string();
                            Toast.makeText(getApplicationContext(), MessageParser.showMessageForErrorCode(err), Toast.LENGTH_SHORT).show();
                        } catch (IOException e) {
                            e.printStackTrace();
                        }
                    }
                }
            }

            @Override
            public void onFailure(Call<ProfileEditResponseModelHead> call, Throwable t) {
                cancelProgressDialog();
            }
        });
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (requestCode == PROFILE_IMAGE_REQUEST_CODE && data != null) {
            Uri myData = data.getData();
            if (resultCode == RESULT_OK && myData != null) {
                fileUri = myData;
                User.getUser();
                filePath = getRealPathFromURI(fileUri);
                originalFile = new File(filePath);

                uploadImage();
            }
        }

    }

    private void uploadImage() {

        ProgressDialog progressDialog = new ProgressDialog(this);
        progressDialog.setMessage("Please wait...");
        progressDialog.show();

        RequestBody filePart = RequestBody.create(MediaType.parse(getContentResolver().getType(fileUri)), originalFile);
        MultipartBody.Part file = MultipartBody.Part.createFormData("profileImage", originalFile.getName(), filePart);

        Call<ProfileImageResponse> profileImage = BottlesUpApplication.getApi().editProfileImage(User.getUser().getAccess_token(), file);

        profileImage.enqueue(new Callback<ProfileImageResponse>() {
            @Override
            public void onResponse(Call<ProfileImageResponse> call, Response<ProfileImageResponse> response) {
                progressDialog.dismiss();

                if (response.isSuccessful()) {

                    User user = User.getUser();
                    user.setProfileImage(response.body().getData().getProfileImage());
                    user.save();

                    Picasso.with(EditProfileActivity.this)
                            .load(response.body().getData().getProfileImage())
                            .placeholder(R.drawable.users)
                            .into(userImage);
                } else {
                    MessageParser.showMessageForErrorCode(response.message());
                    if (response.code() == 401) {
                        logoutNow();
                    }
                }

            }

            @Override
            public void onFailure(Call<ProfileImageResponse> call, Throwable t) {
                progressDialog.dismiss();

                Toast.makeText(EditProfileActivity.this, t.getLocalizedMessage(), Toast.LENGTH_SHORT).show();
            }
        });


    }

    public String getRealPathFromURI(Uri contentUri) {
        String[] proj = {MediaStore.Images.Media.DATA};
        CursorLoader cursorLoader = new CursorLoader(
                this,
                contentUri, proj, null, null, null);
        Cursor cursor = cursorLoader.loadInBackground();
        int column_index =
                cursor.getColumnIndexOrThrow(MediaStore.Images.Media.DATA);
        cursor.moveToFirst();
        return cursor.getString(column_index);
    }

}
