package com.envent.bottlesup.mvp.view.fragments;

import android.app.AlertDialog;
import android.app.ProgressDialog;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.graphics.Color;
import android.os.Build;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.content.LocalBroadcastManager;
import android.text.TextUtils;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.envent.bottlesup.BuildConfig;
import com.envent.bottlesup.R;
import com.envent.bottlesup.adapter.SpinnerSeatingPlaceAdapter;
import com.envent.bottlesup.mvp.MVPView;
import com.envent.bottlesup.mvp.model.CheckedInVenue;
import com.envent.bottlesup.mvp.model.TableBar;
import com.envent.bottlesup.mvp.model.User;
import com.envent.bottlesup.mvp.model.server_response.PendingBalanceResponse;
import com.envent.bottlesup.mvp.model.server_response.StatusMessage;
import com.envent.bottlesup.mvp.model.server_response.mybalance.MyBalanceResponse;
import com.envent.bottlesup.mvp.model.server_response.notification_response.ItemsItem;
import com.envent.bottlesup.mvp.model.server_response.order_request_response.OrderRequestResponse;
import com.envent.bottlesup.mvp.model.server_response.seating_places.SeatingPlace;
import com.envent.bottlesup.mvp.model.server_response.seating_places.SeatingPlaceResponse;
import com.envent.bottlesup.mvp.presenter.MyCartPresenterImpl;
import com.envent.bottlesup.mvp.presenter.MyPresenter;
import com.envent.bottlesup.mvp.presenter.MyWalletPresenterImpl;
import com.envent.bottlesup.mvp.view.activities.Dashboard;
import com.envent.bottlesup.mvp.view.activities.NotificationDetail;
import com.envent.bottlesup.mvp.view.activities.RequestStatusActivity;
import com.envent.bottlesup.mvp.view.customview.CustomToast;
import com.envent.bottlesup.mvp.view.customview.EWalletPaymentDialog;
import com.envent.bottlesup.mvp.view.customview.MyDialog;
import com.envent.bottlesup.mvp.view.fragments.base_fragment.LogoutMethodFragment;
import com.envent.bottlesup.utils.MetaData;
import com.envent.bottlesup.utils.MyLog;
import com.envent.bottlesup.utils.SessionManager;
import com.envent.bottlesup.utils.UtilityMethods;
import com.esewa.android.sdk.payment.ESewaConfiguration;
import com.esewa.android.sdk.payment.ESewaPayment;
import com.esewa.android.sdk.payment.ESewaPaymentActivity;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import butterknife.Unbinder;
import khalti.checkOut.api.Config;
import khalti.checkOut.api.OnCheckOutListener;
import khalti.checkOut.helper.KhaltiCheckOut;

import static android.app.Activity.RESULT_CANCELED;
import static android.app.Activity.RESULT_OK;

/**
 * Created by ronem on 3/15/18.
 */

public class FragmentMyWallet extends LogoutMethodFragment
        implements AdapterView.OnItemSelectedListener, MVPView.MyWalletView, MVPView.MyCartView {

    private String TAG = getClass().getSimpleName();

    @BindView(R.id.refresh_balance)
    ImageView refreshBalance;
    @BindView(R.id.current_balance_text_view)
    TextView currentBalanceTextView;
    @BindView(R.id.edt_amount)
    EditText edtAmount;
    @BindView(R.id.plus_500)
    TextView plus500;
    @BindView(R.id.plus_1000)
    TextView plus1000;

    @BindView(R.id.top_up_at_venue_layout)
    LinearLayout topupAtVenueLayout;

    @BindView(R.id.not_checked_in_text_view)
    TextView notCheckedInVenueTV;

    @BindView(R.id.seating_place_layout)
    LinearLayout seatingPlaceLayout;
    @BindView(R.id.spinner_seating_place)
    Spinner spinnerSeatingPlace;
    @BindView(R.id.btn_request_topup_at_venue)
    Button btnRequestTopupAtVenue;
    @BindView(R.id.khalti_button_layout)
    LinearLayout khaltiButton;

    private Unbinder unbinder;

    private TableBar selectedTable;
    private CheckedInVenue checkedInVenue;
    private User user;
    private MyPresenter.MyWalletPresenter presenter;
    private MyPresenter.MyCartPresenter cartPresenter;
    private ProgressDialog progressDialog;
    private List<TableBar> seatingPlaces;
    private BroadcastReceiver receiver;
    private Long eWalletAmount = 0L;
    private SessionManager sessionManager;

    private String FROM_VENUE_TOP_UP_ACTION = "FROM_VENUE_TOP_UP";
    private String FROM_KHALTI_TOP_UP_ACTION = "FROM_KHALTI_TOP_UP";
    private String FROM_ESEWA_TOP_UP_ACTION = "FROM_ESEWA_TOP_UP";
    private SpinnerSeatingPlaceAdapter adapter;

    public static FragmentMyWallet createNewInstance() {
        return new FragmentMyWallet();
    }


    @Nullable
    @Override
    public View onCreateView(@Nullable LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View root = inflater.inflate(R.layout.fragment_mywallet, container, false);
        unbinder = ButterKnife.bind(this, root);
        presenter = new MyWalletPresenterImpl();
        presenter.addView(this);
        cartPresenter = new MyCartPresenterImpl();
        cartPresenter.addView(this);
        progressDialog = new ProgressDialog(getContext());
        progressDialog.setMessage(MetaData.MESSAGE.PLEASE_WAIT);
        progressDialog.setCancelable(false);

        registerBroadCast();
        return root;
    }

    private void registerBroadCast() {
        receiver = new BroadcastReceiver() {
            @Override
            public void onReceive(Context context, Intent intent) {
                setUpCurrentBalance();
            }
        };
        LocalBroadcastManager.getInstance(getContext()).registerReceiver(receiver, new IntentFilter(MetaData.INTENT_ACTIONS.UPDATE_BALANCE_TO_WALLET_ACTION));
    }

    private void setUpCurrentBalance() {
        currentBalanceTextView.setText(getResources().getString(R.string.total_value, UtilityMethods.getTwoPlaceDecimal(User.getUser().getBalance())));
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        sessionManager = new SessionManager(getContext());

        seatingPlaceLayout.setVisibility(View.VISIBLE);
        spinnerSeatingPlace.setOnItemSelectedListener(this);
        user = User.getUser();
        setUpCurrentBalance();
        CheckedInVenue checkedInVenue = CheckedInVenue.getCheckedInVenue();
        if (checkedInVenue == null) {
            notCheckedInVenueTV.setVisibility(View.VISIBLE);
            String txt = getResources().getString(R.string.you_are_not_checked_in);

            notCheckedInVenueTV.setText(UtilityMethods.getSpannableOfNormalText(txt,
                    65, 73, Color.parseColor("#0099c5")
            ));
            topupAtVenueLayout.setVisibility(View.GONE);
        } else {
            if (TableBar.getSeatingPlaces() != null && TableBar.getSeatingPlaces().size() > 0) {
                addChooseItemToSeatingPlaces();
                seatingPlaces.addAll(TableBar.getSeatingPlaces());
                updateSpinnerSeatingPlaces();

                int pos = sessionManager.getSelectedtableBarId() + 1;
                spinnerSeatingPlace.setSelection(pos);

            } else {
                cartPresenter.getSeatingPlace(checkedInVenue.getVenueId(), user.getAccess_token());
            }
        }

    }

    private void updateSpinnerSeatingPlaces() {
        adapter = new SpinnerSeatingPlaceAdapter(getContext(), seatingPlaces);
        spinnerSeatingPlace.setAdapter(adapter);
    }


    @Override
    public void onDestroyView() {
        super.onDestroyView();
        unbinder.unbind();
        presenter.cancelAllCall();
        Log.i(TAG, "OnDestroyView");
        LocalBroadcastManager.getInstance(getActivity()).unregisterReceiver(receiver);

    }


    @Override
    public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
        selectedTable = seatingPlaces.get(position);
        sessionManager.setSelectedTableBarId(position - 1);
    }

    @Override
    public void onNothingSelected(AdapterView<?> parent) {

    }

    @OnClick(R.id.khalti_button_layout)
    public void onKhaltiButtonClicked() {

//        showMessage(MetaData.MESSAGE.COMING_SOON);

        showEWalletDialogFor(R.drawable.khalti, MetaData.MESSAGE.TITLE_TOPUP_WITH_KHALTI, FROM_KHALTI_TOP_UP_ACTION);
    }

    @OnClick(R.id.topup_with_esewa)
    public void onTopUpwithEsewaClicked() {
//        showMessage(MetaData.MESSAGE.COMING_SOON);
        showEWalletDialogFor(R.drawable.esewa, MetaData.MESSAGE.TITLE_TOPUP_WITH_ESEWA, FROM_ESEWA_TOP_UP_ACTION);
    }

    private void showEWalletDialogFor(int icon, String title, String isFrom) {
        edtAmount.setText("");
        EWalletPaymentDialog EWalletPaymentDialog = new EWalletPaymentDialog(getContext(), icon, title);
        EWalletPaymentDialog.setOnEWalletPaymentDialogListener((amount -> {
            eWalletAmount = amount;
            presenter.getPendingBalance(User.getUser().getAccess_token(), isFrom);
        }));
        Window window = EWalletPaymentDialog.getWindow();
        window.setLayout(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT);
        EWalletPaymentDialog.show();
    }

    @OnClick(R.id.refresh_balance)
    public void onRefreshBalanceClicked() {
        presenter.getMyBalance(user.getAccess_token());
    }

    @OnClick(R.id.btn_request_topup_at_venue)
    public void onRequestTopupAtVenueClicked() {
        if (selectedTable.getSeatingPlaceName().equals(MetaData.MESSAGE.SELECT_SEATING_PLACE)) {
            showMessage(MetaData.MESSAGE.MUST_SELECT_SEATING_PLACE);
            return;
        }
        checkedInVenue = CheckedInVenue.getCheckedInVenue();
        if (checkedInVenue == null) {
            showNotCheckedInDialog();
        } else {
            if (getAmountFromEditField() == 0) {
                edtAmount.setError(MetaData.MESSAGE.EMPTY_AMOUNT);
            } else {
                if (presenter.isAmountValid(getAmountFromEditField()))
                    presenter.getPendingBalance(User.getUser().getAccess_token(), FROM_VENUE_TOP_UP_ACTION);
            }
        }
    }

    @OnClick(R.id.not_checked_in_text_view)
    public void onNotCheckedInToCheckinClicked() {
        ((Dashboard) getActivity()).bottomMenuView.setSelectedItemId(R.id.bottom_menu_venue);
    }

    private void showNotCheckedInDialog() {
        AlertDialog.Builder builder = new AlertDialog.Builder(getContext());
        String msg = MetaData.MESSAGE.NOT_CHECKED_IN + "\n\n" + MetaData.MESSAGE.WANT_TO_CHECKIN;
        builder.setMessage(msg);
        builder.setPositiveButton("Check In", (dialog, which) -> {
            dialog.dismiss();
            ((Dashboard) getActivity()).bottomMenuView.setSelectedItemId(R.id.bottom_menu_venue);
        });

        builder.setNegativeButton("Not now", (dialog, which) -> dialog.dismiss());
        builder.create().show();
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
        if (presenter.isAmountValid(total)) {
            edtAmount.setText(String.valueOf(total));
        }
    }

    private int getAmountFromEditField() {
        String a = edtAmount.getText().toString().trim();
        int amount = 0;
        if (!TextUtils.isEmpty(a)) {
            amount = Integer.parseInt(a);
        }
        return amount;
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == MetaData.REQUEST_CODE.ESEWA_REQUEST_CODE) {
            if (resultCode == RESULT_OK) {
                String s = data.getStringExtra(ESewaPayment.EXTRA_RESULT_MESSAGE);
                Log.i(TAG, "Proof of Success->" + s);
                try {

                    JSONObject node = new JSONObject(s);
                    String transactionNodeStr = "transactionDetails";
                    if (node.has(transactionNodeStr)) {

                        JSONObject tNode = node.getJSONObject(transactionNodeStr);
                        if (tNode.getString(MetaData.MESSAGE.STATUS).equalsIgnoreCase("COMPLETE")) {
                            String strAmount = node.getString("totalAmount");
                            eWalletAmount = (long) Float.parseFloat(strAmount);
                            String referenceId = tNode.getString("referenceId");


                            //now proceed to topUp through own app server
                            Log.i(TAG, "Transaction amount " + eWalletAmount);
                            Log.i(TAG, "Transaction ID " + referenceId);
                        }
                    }
                } catch (JSONException e) {
                    e.printStackTrace();
                }
            } else if (resultCode == RESULT_CANCELED) {
                if (resultCode == ESewaPayment.RESULT_EXTRAS_INVALID) {
                    String s = data.getStringExtra(ESewaPayment.EXTRA_RESULT_MESSAGE);
                    Log.i(TAG, "cancelled-proof:" + s);
                }
            }
        }
    }

    @Override
    public void showMessage(String msg) {
        CustomToast.makeText(getContext(), msg, Toast.LENGTH_SHORT).show();
    }

    @Override
    public void unAuthorized() {
    }

    @Override
    public void showProgressDialog() {
        if (!progressDialog.isShowing()) progressDialog.show();
    }

    @Override
    public void hideProgressDialog() {
        if (progressDialog.isShowing()) progressDialog.dismiss();
    }

    @Override
    public void onMybalanceReceived(MyBalanceResponse response) {
        updateBalance(response.getMyBalance().getBalance());
    }

    @Override
    public void onPendingBalanceReceived(PendingBalanceResponse response, String isFrom) {
        double pendingBalance = response.getPendingBalance();
        int totalAmount = (int) pendingBalance + getAmountFromEditField() + (int) User.getUser().getBalance();
        if (totalAmount <= MetaData.WALLET_BALANCE_LIMIT) {
            if (isFrom.equals(FROM_VENUE_TOP_UP_ACTION)) {
                presenter.requestTopup(User.getUser().getAccess_token(), getAmountFromEditField(), checkedInVenue.getVenueId(), selectedTable.getSeatingPlaceId());
            } else if (isFrom.equals(FROM_KHALTI_TOP_UP_ACTION)) {
                hideProgressDialog();
                proceedWithKhalti();
            } else if (isFrom.equals(FROM_ESEWA_TOP_UP_ACTION)) {
                hideProgressDialog();
                proceedWithESewa();
            }
            edtAmount.setText("");

        } else {
            hideProgressDialog();
            String msg = "Dear " + User.getUser().getFullName() + ", you already have Top up's pending with Rs." + UtilityMethods.getTwoPlaceDecimal(pendingBalance);
            AlertDialog.Builder builder = new MyDialog(getContext()).getDialogBuilder(msg);
            builder.setPositiveButton("Okay", ((dialog, which) -> dialog.dismiss()))
                    .create()
                    .show();
        }
    }

    private void proceedWithESewa() {

        ESewaConfiguration eSewaConfiguration = new ESewaConfiguration()
                .clientId(BuildConfig.esewa_client_id)
                .secretKey(BuildConfig.esewa_secret_key)
                .environment(ESewaConfiguration.ENVIRONMENT_TEST);

        String callBackUrl = BuildConfig.BASE_URL + "api/v1/payment/esewa";
        Log.i(TAG,"Callback: "+callBackUrl);
        ESewaPayment eSewaPayment = new ESewaPayment(String.valueOf(eWalletAmount),
                User.getUser().getUserName(),
                String.valueOf(User.getUser().getUserId()),
                callBackUrl);

        Intent eSewaIntent = new Intent(getContext(), ESewaPaymentActivity.class);
        eSewaIntent.putExtra(ESewaConfiguration.ESEWA_CONFIGURATION, eSewaConfiguration);
        eSewaIntent.putExtra(ESewaPayment.ESEWA_PAYMENT, eSewaPayment);

        /**
         * Result will be dropped to {@link #onActivityResult}
         */
        startActivityForResult(eSewaIntent, MetaData.REQUEST_CODE.ESEWA_REQUEST_CODE);

    }

    private void proceedWithKhalti() {
        Config config = new Config(BuildConfig.khalti_api_key,
                String.valueOf(User.getUser().getUserId()),
                User.getUser().getUserName(),
                BuildConfig.BASE_URL,
                eWalletAmount * 100,//convert to paisa
                new OnCheckOutListener() {
                    @Override
                    public void onSuccess(HashMap<String, Object> data) {
                        int amount = ((int) data.get("amount")) / 100;//get amount and convert to rupees
                        String token = (String) data.get("token");
                        MyLog.i(TAG, "Amount " + amount + "\nToken " + token);
                        presenter.requestKhaltiTopup(User.getUser().getAccess_token(), amount, token);
                        eWalletAmount = 0L;//resetting the value
                    }

                    @Override
                    public void onError(String action, String message) {
                        showMessage(message);
                        eWalletAmount = 0L;//resetting the value
                    }
                });


        KhaltiCheckOut khaltiCheckOut = new KhaltiCheckOut(getContext(), config);
        khaltiCheckOut.show();
    }

    private void updateBalance(double b) {
        user.setBalance(b);
        user.save();
        setUpCurrentBalance();
        ((Dashboard) getActivity()).updateUserStatus();
    }

    @Override
    public void onTopUpReceived(StatusMessage response) {

        AlertDialog.Builder builder = new MyDialog(getContext()).getDialogBuilder(response.getMessage());
        builder
                .setPositiveButton("Okay", (dialog, which) -> dialog.dismiss())
                .setNegativeButton("View detail", ((dialog, which) -> {
                    dialog.dismiss();

                    Intent dIntent = new Intent(getContext(), RequestStatusActivity.class);
                    dIntent.putExtra(MetaData.KEY.NOTIFICATION_TYPE, MetaData.KEY.NOTIFICATION_TYPE_TOPUP);
                    startActivity(dIntent);

                })).create().show();
    }

    @Override
    public void onMyCartItemsReceived(List<Object> items, boolean isForOnlyBalanceUpdate) {

    }

    @Override
    public void onSubTotalReceived(double subTotal, double serviceCharge, double vatAmount, double grandTotal, double saving) {

    }

    @Override
    public void onSeatingPlaceResponseReceived(SeatingPlaceResponse response) {
        if (response.getData() != null) {
            for (SeatingPlace sp : response.getData()) {
                new TableBar(sp.getId(), sp.getName()).save();
            }
            addChooseItemToSeatingPlaces();
            seatingPlaces.addAll(TableBar.getSeatingPlaces());
            updateSpinnerSeatingPlaces();
        }

    }

    private void addChooseItemToSeatingPlaces() {
        seatingPlaces = new ArrayList<>();
        TableBar t0 = new TableBar();
        t0.setSeatingPlaceName(MetaData.MESSAGE.SELECT_SEATING_PLACE);
        seatingPlaces.add(t0);
    }

    @Override
    public void onOrderResponseReceived(OrderRequestResponse statusMessage) {

    }

    @Override
    public void onResume() {
        super.onResume();
        edtAmount.setText("");
    }

}

