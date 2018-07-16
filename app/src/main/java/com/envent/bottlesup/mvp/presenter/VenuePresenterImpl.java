package com.envent.bottlesup.mvp.presenter;

import android.app.AlertDialog;
import android.content.Context;
import android.location.Location;
import android.os.Build;
import android.text.Html;

import com.envent.bottlesup.R;
import com.envent.bottlesup.application.BottlesUpApplication;
import com.envent.bottlesup.mvp.MVPView;
import com.envent.bottlesup.mvp.model.CheckedInVenue;
import com.envent.bottlesup.mvp.model.User;
import com.envent.bottlesup.mvp.model.mycart.DrinkCart;
import com.envent.bottlesup.mvp.model.mycart.FoodCart;
import com.envent.bottlesup.mvp.model.server_response.CheckInCheckOut;
import com.envent.bottlesup.mvp.model.server_response.StatusMessage;
import com.envent.bottlesup.mvp.model.server_response.event_response.EventResponse;
import com.envent.bottlesup.mvp.model.server_response.venue_list.VenueDataItem;
import com.envent.bottlesup.mvp.model.server_response.venue_list.VenueResponse;
import com.envent.bottlesup.network.MessageParser;
import com.envent.bottlesup.utils.DistanceCalculator;
import com.envent.bottlesup.utils.MetaData;
import com.envent.bottlesup.utils.SessionManager;
import com.envent.bottlesup.utils.UtilityMethods;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

/**
 * Created by ronem on 3/16/18.
 */

public class VenuePresenterImpl implements MyPresenter.VenuePresenter {
    private MVPView.VenueView view;
    private Call<VenueResponse> venueCall;
    private Call<EventResponse> eventResponseCall;

    @Override
    public void addView(MVPView.VenueView view) {
        this.view = view;
    }

    @Override
    public void getEvents(String apiKey) {
        Call<EventResponse> call = BottlesUpApplication.getApi().getEvents(apiKey);
        call.enqueue(new Callback<EventResponse>() {
            @Override
            public void onResponse(Call<EventResponse> call, Response<EventResponse> response) {
                if (response.isSuccessful() && view != null) {
                    if (view != null) {
                        view.onEventsResponseReceived(response.body());
                    }
                }
            }

            @Override
            public void onFailure(Call<EventResponse> call, Throwable t) {

            }
        });
    }

    @Override
    public void getVenueList(String tag, String pageCount, String limit) {
        view.showSwipeProgress();
        venueCall = BottlesUpApplication.getApi().getVenueListResponse(tag, pageCount, limit);
        venueCall.enqueue(new Callback<VenueResponse>() {
            @Override
            public void onResponse(Call<VenueResponse> call, Response<VenueResponse> response) {
                view.hideSwipeProgress();
                if (response.isSuccessful()) {
                    view.onVenueListResponse(response.body());
                } else {
                    try {
                        String error = response.errorBody().string();
                        view.showMessage(MessageParser.showMessageForErrorCode(error));
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                    if (response.code() == 401) {
                        view.unAuthorized();
                    }
                }
            }

            @Override
            public void onFailure(Call<VenueResponse> call, Throwable t) {
                if (!call.isCanceled()) {
                    view.hideSwipeProgress();
                    MessageParser.showMessageForFailure(t);
                }
            }
        });
    }

    @Override
    public void getManagedList(List<VenueDataItem> venues, Location currentLocation) {
        List<VenueDataItem> items = new ArrayList<>();
        for (VenueDataItem d : venues) {
            String latitude = d.getLatitude();
            String longitude = d.getLongitude();
            try {
                double la = Double.parseDouble(latitude);
                double lo = Double.parseDouble(longitude);
                double distance = DistanceCalculator.getDistance(la, lo, currentLocation.getLatitude(), currentLocation.getLongitude(), "K");
                d.setDistance(UtilityMethods.getTwoPlaceDecimalWithoutCommaSeparated(distance));

            } catch (Exception e) {
                e.printStackTrace();
            }
            items.add(d);
        }
        view.onVenueManagedListReceived(items);
    }

    @Override
    public void showCheckInCheckoutDialog(final Context context, final VenueDataItem data) {

        final CheckedInVenue checkedInVenue = CheckedInVenue.getCheckedInVenue();
        boolean tryinToCheckOutToSameVenue = false;
        if (checkedInVenue == null) {
            //user has not checked in yet
            checkInToVenue(context, User.getUser().getAccess_token(), data.getId(), data, true);

        } else {
            //user has checked in already
            //check if the clicked item is checked in venue or different than checked in venue
            List<FoodCart> fc = FoodCart.getFoodCartItems();
            List<DrinkCart> dc = DrinkCart.getDrinkCartItems();
            boolean isItemPresent = false;
            if (fc.size() > 0) {
                isItemPresent = true;
            } else if (dc.size() > 0) {
                isItemPresent = true;
            }
            AlertDialog.Builder alertBuilder = new AlertDialog.Builder(context);
            alertBuilder.setCancelable(false);

            final String msg;
            String itemPresentMsg = isItemPresent ? context.getResources().getString(R.string.item_present) : "";
            String checkinCheckoutBtnText;
            if (checkedInVenue.getVenueId() == data.getId()) {
                msg = itemPresentMsg + context.getResources().getString(R.string.already_checked_in_same, checkedInVenue.getVenueName());
                tryinToCheckOutToSameVenue = true;
                checkinCheckoutBtnText = "Check Out";
            } else {
                msg = itemPresentMsg + context.getResources().getString(R.string.already_checked_in_different, checkedInVenue.getVenueName(), data.getName());
                checkinCheckoutBtnText = "Check In";
            }

            final boolean finalTryingToCheckOutSameVenue = tryinToCheckOutToSameVenue;

            alertBuilder.setPositiveButton(checkinCheckoutBtnText, (dialog, which) -> {
                String checkedInVenueName = checkedInVenue.getVenueName();
                dialog.dismiss();

                //only checkout if the user taped on the checked in venue item
                //else checkout from the previous venue and check-in to the tapped new venue
                if (finalTryingToCheckOutSameVenue) {
                    checkoutToVenue(context,
                            User.getUser().getAccess_token(),
                            checkedInVenue.getCheckInCheckOutId(),
                            checkedInVenueName,
                            false,
                            0,
                            null);
                } else {

                    //first checkout from the checked in venue and re-check-in to the selected venue
//                    checkInToVenue(context, User.getUser().getAccess_token(), data.getId(), data, false);
                    checkoutToVenue(context,
                            User.getUser().getAccess_token(),
                            checkedInVenue.getCheckInCheckOutId(),
                            checkedInVenueName,
                            true,
                            data.getId(), data
                    );
                }
            });

            alertBuilder.setNegativeButton("Close", (dialog, which) -> dialog.dismiss());

            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.N) {
                alertBuilder.setMessage(Html.fromHtml(msg, Html.FROM_HTML_MODE_LEGACY));
            } else {
                alertBuilder.setMessage(Html.fromHtml(msg));
            }
            AlertDialog dialog = alertBuilder.create();
            dialog.show();
        }

    }

    @Override
    public void checkInToVenue(Context context, String apiKey, int venueId, VenueDataItem data, boolean isFirst) {
        view.showProgressDialog();
        Call<CheckInCheckOut> call = BottlesUpApplication.getApi().checkIn(apiKey, venueId);
        call.enqueue(new Callback<CheckInCheckOut>() {
            @Override
            public void onResponse(Call<CheckInCheckOut> call, Response<CheckInCheckOut> response) {
                view.hideProgressDialog();
                if (response.isSuccessful()) {
                    if (!isFirst) {
                        checkOutNow(context);
                    }
                    setNewCheckedInData(response.body().getCheckInCheckOutId(), data);
                } else {
                    try {
                        String error = response.errorBody().string();
                        view.showMessage(MessageParser.showMessageForErrorCode(error));
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                    if (response.code() == 401) {
                        view.unAuthorized();
                    }
                }
            }

            @Override
            public void onFailure(Call<CheckInCheckOut> call, Throwable t) {
                if (!call.isCanceled()) {
                    view.hideProgressDialog();
                    view.showMessage(MessageParser.showMessageForFailure(t));
                }
            }
        });
    }

    @Override
    public void checkoutToVenue(Context context, String apiKey, int checkInCheckoutId, String checkedInVenueName, boolean isCheckoutAndCheckIn, int venueId, VenueDataItem data) {
        view.showProgressDialog();
        Call<StatusMessage> call = BottlesUpApplication.getApi().checkOut(apiKey, checkInCheckoutId);
        call.enqueue(new Callback<StatusMessage>() {
            @Override
            public void onResponse(Call<StatusMessage> call, Response<StatusMessage> response) {
                //only hide the progress dialog if request is for only checkout
                //but if the request is from checkout and checkIn do not hide the progress dialog
                if (!isCheckoutAndCheckIn) {
                    view.hideProgressDialog();
                }

                if (response.isSuccessful()) {
                    if (response.body().getStatus().equals(MetaData.MESSAGE.SUCCESS)) {

                        checkOutNow(context);

                        //only notify the view if request is for only checkout
                        //if the request is for checkout and check-in then proceed to check-in without notifying the checkout
                        //to the user
                        if (!isCheckoutAndCheckIn) {
                            view.onCheckOut(checkedInVenueName);
                        } else {
                            //Now check-in to the selected venue
                            checkInToVenue(context, apiKey, venueId, data, false);
                        }
                    } else {
                        view.showMessage(response.body().getMessage());
                    }
                } else {
                    try {
                        String error = response.errorBody().string();
                        view.showMessage(MessageParser.showMessageForErrorCode(error));
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                    if (response.code() == 401) {
                        view.unAuthorized();
                    }
                }
            }

            @Override
            public void onFailure(Call<StatusMessage> call, Throwable t) {
                if (!call.isCanceled()) {
                    view.hideProgressDialog();
                    view.showMessage(MessageParser.showMessageForFailure(t));
                }
            }
        });
    }

    private void checkOutNow(Context context) {
        UtilityMethods.checkoutNow();
        SessionManager sessionManager = new SessionManager(context);
        sessionManager.saveVenueEvent(null);
    }


    private void setNewCheckedInData(int checkInCheckOutId, VenueDataItem data) {
        CheckedInVenue c = new CheckedInVenue();
        c.setCheckInCheckOutId(checkInCheckOutId);
        c.setLat(data.getLatitude());
        c.setLongi(data.getLongitude());
        c.setVenueName(data.getName());
        c.setVenueId(data.getId());
        c.setCity(data.getCity());
        c.setOutLetType(data.getOutletType());
        c.setServiceCharge(data.getServiceCharge());
        c.setVat(data.getVat());
        c.setVenueAddress(data.getAddress());
        c.setVenueDetail(data.getDescription());
        c.setVenueImage(data.getImage());
        c.setTaxInclusive(data.isTaxInclusive());
        c.save();
        view.onCheckIn(data.getName());
    }

    @Override
    public void cancelVenueCall() {
        if (venueCall != null)
            venueCall.cancel();

    }

    @Override
    public void cancelEventCall() {

        if (eventResponseCall != null) {
            eventResponseCall.cancel();
        }
    }

}
