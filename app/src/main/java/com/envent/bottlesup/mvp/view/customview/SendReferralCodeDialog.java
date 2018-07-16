package com.envent.bottlesup.mvp.view.customview;

import android.app.Dialog;
import android.content.Context;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.view.View;
import android.view.Window;
import android.widget.Button;
import android.widget.EditText;

import com.envent.bottlesup.R;
import com.envent.bottlesup.mvp.presenter.MyPresenter;
import com.envent.bottlesup.mvp.presenter.ReferralCodeImpl;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.Unbinder;

/**
 * Created by ronem on 4/27/18.
 */

public class SendReferralCodeDialog extends Dialog {

    private Context context;
    @BindView(R.id.invite)
    Button invite;
    private MyPresenter.SendReferrelCodePresenter presenter;
    @BindView(R.id.edit_mobile_number)
    EditText edit_mobile_number;
    private String mobileNumber;
    private Unbinder unbinder;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        setContentView(R.layout.send_referral_dialog);
        unbinder = ButterKnife.bind(this);
        presenter= new ReferralCodeImpl();
        invite.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                mobileNumber = edit_mobile_number.getText().toString().trim();
                presenter.inviteUser(context, mobileNumber,SendReferralCodeDialog.this);
            }
        });


    }


    public SendReferralCodeDialog(@NonNull Context context) {
        super(context);
        this.context = context;
    }

    @Override
    public void dismiss() {
        super.dismiss();
        unbinder.unbind();

    }
}
