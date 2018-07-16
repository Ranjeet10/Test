package com.envent.bottlesup.mvp.view.customview;

import android.app.Dialog;
import android.content.Context;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.support.annotation.NonNull;
import android.text.TextUtils;
import android.view.Window;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.envent.bottlesup.R;
import com.envent.bottlesup.mvp.model.User;
import com.envent.bottlesup.utils.MetaData;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import butterknife.Unbinder;

/**
 * Created by ronem on 6/12/18.
 */

public class EWalletPaymentDialog extends Dialog {

    private Unbinder unbinder;

    @BindView(R.id.wallet_icon)
    ImageView walletIcon;
    @BindView(R.id.wallet_title)
    TextView walletTitle;
    @BindView(R.id.edt_amount)
    EditText edtAmount;

    private EWalletPaymentDialogListener listener;

    public interface EWalletPaymentDialogListener {
        void onAmountEntered(Long amount);
    }

    public void setOnEWalletPaymentDialogListener(EWalletPaymentDialogListener listener) {
        this.listener = listener;
    }

    public EWalletPaymentDialog(@NonNull Context context, int icon, String title) {
        super(context);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
        setCancelable(false);
        setContentView(R.layout.khalti_payment_dialog);
        unbinder = ButterKnife.bind(this);

        walletIcon.setImageResource(icon);
        walletTitle.setText(title);

    }

    private int getAmountFromEditField() {
        String a = edtAmount.getText().toString().trim();
        int amount = 0;
        if (!TextUtils.isEmpty(a)) {
            amount = Integer.parseInt(a);
        }
        return amount;
    }

    @OnClick(R.id.plus_500)
    public void onPlus500Clicked() {
        int amount = getAmountFromEditField();
        int total = amount + 500;
        setAmount(total);
    }


    @OnClick(R.id.plus_1000)
    public void onPlus1000Clicked() {
        int amount = getAmountFromEditField();
        int total = amount + 1000;
        setAmount(total);
    }

    @OnClick(R.id.plus_2000)
    public void onPlus2000Clicked() {
        int amount = getAmountFromEditField();
        int total = amount + 2000;
        setAmount(total);
    }

    private void setAmount(int total) {
        if (isAmountValid(total)) {
            edtAmount.setText(String.valueOf(total));
        }
    }

    private boolean isAmountValid(long amount) {
        if (amount > 10000) {
            new CustomToast(getContext(), MetaData.MESSAGE.EXCESS_TRANSACTION_LIMIT, Toast.LENGTH_SHORT);
            return false;
        } else {
            int walletBalance = (int) User.getUser().getBalance();
            long balanceAfterTopUp = walletBalance + amount;
            if (balanceAfterTopUp > 25000) {
                new CustomToast(getContext(), MetaData.MESSAGE.EXCESS_BALANCE_LIMIT, Toast.LENGTH_SHORT);
                return false;
            }
        }
        return true;
    }

    @Override
    protected void onStop() {
        super.onStop();
        unbinder.unbind();
    }

    @OnClick(R.id.btn_proceed)
    public void onProceedClicked() {
        String amountS = edtAmount.getText().toString().trim();
        if (TextUtils.isEmpty(amountS)) {
            edtAmount.setError(MetaData.MESSAGE.EMPTY);
            return;
        }
        long amount = (long) Double.parseDouble(amountS);
        if (amount < 1) {
            edtAmount.setError(MetaData.MESSAGE.EMPTY_AMOUNT);
            return;
        }

        if (!isAmountValid(amount)) {
            return;
        }
        listener.onAmountEntered(amount);
        dismiss();
    }

    @OnClick(R.id.btn_cancel)
    public void onBtnCancelClicked() {
        dismiss();
    }
}
