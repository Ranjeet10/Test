package com.envent.bottlesup.mvp.view.activities;

import android.Manifest;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.LocalBroadcastManager;
import android.support.v4.widget.NestedScrollView;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.envent.bottlesup.R;
import com.envent.bottlesup.adapter.EventAdapter;
import com.envent.bottlesup.mvp.MVPView;
import com.envent.bottlesup.mvp.model.CheckedInVenue;
import com.envent.bottlesup.mvp.model.mycart.DrinkCart;
import com.envent.bottlesup.mvp.model.mycart.FoodCart;
import com.envent.bottlesup.mvp.model.server_response.event_response.EventData;
import com.envent.bottlesup.mvp.model.server_response.event_response.EventResponse;
import com.envent.bottlesup.mvp.presenter.MyPresenter;
import com.envent.bottlesup.mvp.presenter.VenueDetailPresenterImpl;
import com.envent.bottlesup.mvp.view.activities.base_activity.SimpleBaseActivity;
import com.envent.bottlesup.mvp.view.customview.CustomToast;
import com.envent.bottlesup.utils.MetaData;
import com.envent.bottlesup.utils.MyLog;
import com.envent.bottlesup.utils.RecyclerItemClickListener;
import com.envent.bottlesup.utils.SessionManager;
import com.envent.bottlesup.utils.UtilityMethods;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindString;
import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

/**
 * Created by ronem on 3/30/18.
 */

public class VenueDetailInfo extends SimpleBaseActivity implements MVPView.VenueDetailView {

    @BindString(R.string.venue_detail_info)
    String toolbarTitle;

    @BindView(R.id.venue_detail_venue_image_view)
    ImageView venueImageView;
    @BindView(R.id.venue_detail_venue_name)
    TextView venueName;
    @BindView(R.id.venue_detail_venue_address)
    TextView venueAddress;
    @BindView(R.id.venue_detail_venue_outlet_type)
    TextView outletType;
    @BindView(R.id.venue_detail_venue_detail)
    TextView venueDetailView;
    @BindView(R.id.event_recycler_view)
    RecyclerView eventRecyclerView;
    @BindView(R.id.nested_scroll)
    NestedScrollView nestedScrollView;
    @BindView(R.id.ssid)
    TextView ssid;
    @BindView(R.id.wifi_password)
    TextView wifiPassword;
    @BindView(R.id.contact)
    TextView contact;
    @BindView(R.id.event_key)
    TextView eventKey;

    private CheckedInVenue checkedInVenue;
    private MyPresenter.VenueDetailPresenter presenter;
    private List<EventData> events = new ArrayList<>();
    private SessionManager sessionManager;


    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.venue_detail_layout);
        ButterKnife.bind(this);
        sessionManager = new SessionManager(this);
        presenter = new VenueDetailPresenterImpl();
        presenter.addView(this);

        checkedInVenue = CheckedInVenue.getCheckedInVenue();
        Picasso.with(this)
                .load(checkedInVenue.getVenueImage())
                .into(venueImageView);

        venueName.setText(checkedInVenue.getVenueName());
        venueAddress.setText(getString(R.string.address_concat, checkedInVenue.getVenueAddress(), checkedInVenue.getCity()));
        venueDetailView.setText(checkedInVenue.getVenueDetail());
        outletType.setText(checkedInVenue.getOutLetType());
        ssid.setText(checkedInVenue.getSsid());
        wifiPassword.setText(checkedInVenue.getWifiPassword());
        contact.setText(checkedInVenue.getImmediateContact());

        eventRecyclerView.setLayoutManager(new LinearLayoutManager(this));
        eventRecyclerView.setHasFixedSize(false);
        eventRecyclerView.addItemDecoration(UtilityMethods.getDividerDecoration(this));

        nestedScrollView.setNestedScrollingEnabled(false);
        eventRecyclerView.addOnItemTouchListener(new RecyclerItemClickListener(this, ((recyclerView, view, position) -> {
            Intent detailIntent = new Intent(this, BannerDetailInfo.class);
            Bundle box = new Bundle();
            box.putSerializable(MetaData.KEY.EVENT, events.get(position));
            detailIntent.putExtras(box);
            startActivity(detailIntent);
        })));

        EventResponse er = sessionManager.getVenueEvent();
        if (er != null) {
            this.events = er.getData();
            MyLog.i(this, "Event Data was present");
            loadEvents();
        } else {
            MyLog.i(this, "Event Data was not present");
            presenter.getEvents(checkedInVenue.getVenueId(), getUser().getAccess_token());
        }

        loadMap();
    }


    private void loadMap() {

        SupportMapFragment mSupportMapFragment = (SupportMapFragment) getSupportFragmentManager().findFragmentById(R.id.map);

        mSupportMapFragment.getMapAsync((googleMap -> {

            final LatLng latLng = new LatLng(Double.parseDouble(checkedInVenue.getLat()), Double.parseDouble(checkedInVenue.getLongi()));
            googleMap.moveCamera(CameraUpdateFactory.newLatLngZoom(latLng, 13));


            googleMap.addMarker(new MarkerOptions()
                    .title("Venue: " + checkedInVenue.getVenueName())
                    .snippet("Address: " + checkedInVenue.getVenueAddress())
                    .position(latLng)
            );

            if (ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
                return;
            }
            googleMap.setMyLocationEnabled(true);

            /**
             googleMap.setInfoWindowAdapter(new GoogleMap.InfoWindowAdapter() {
            @Override public View getInfoWindow(Marker marker) {
            return null;
            }

            @Override public View getInfoContents(Marker marker) {
            View view = LayoutInflater.from(DetailActivity.this).inflate(R.layout.info_window, null);
            String title = marker.getTitle();
            String snippet = marker.getSnippet();

            TextView titleView = (TextView) view.findViewById(R.id.info_window_title);
            TextView snippetView = (TextView) view.findViewById(R.id.info_window_snipped);

            titleView.setText(title);
            snippetView.setText(snippet);
            return view;
            }
            });

             googleMap.setOnInfoWindowClickListener(new GoogleMap.OnInfoWindowClickListener() {
            @Override public void onInfoWindowClick(Marker marker) {
            for (int i = 0; i < locations.size(); i++) {
            AQIData a = locations.get(i);
            LatLng latLng = new LatLng(a.getLatitude(), a.getLongitude());
            if (latLng.equals(marker.getPosition())) {
            sessionManager.setLocationName(a.getLocationName());
            sessionManager.setEndPoint(a.getEndPoint());
            }
            }

            finish();
            startActivity(new Intent(DetailActivity.this, DetailActivity.class));
            overridePendingTransition(R.anim.left_to_right_in, R.anim.left_to_right_out);
            }
            });
             **/

        }));
    }

    @Override
    protected void onStart() {
        super.onStart();
        getToolbarTitleTextView().setText(toolbarTitle);
    }

    @OnClick(R.id.btn_checkout)
    public void onCheckOutClicked() {
        presenter.checkout(this, getUser().getAccess_token(), checkedInVenue);
    }


    @Override
    public void onEventResponseReceived(EventResponse eventResponse) {
        this.events = eventResponse.getData();
        loadEvents();
    }

    private void loadEvents() {
        eventRecyclerView.setAdapter(new EventAdapter(events, this));

        if (this.events.size() == 0) {
            eventKey.setVisibility(View.GONE);
        }
    }

    @OnClick(R.id.marker)
    public void onMarkerClicked() {
        nestedScrollView.fullScroll(View.FOCUS_DOWN);
    }

    @Override
    public void onCheckOutSuccess() {
        new SessionManager(this).saveVenueEvent(null);
        UtilityMethods.checkoutNow();

        //this broadcast will notify all the dashboard
        Intent checkoutIntent = new Intent();
        checkoutIntent.setAction(MetaData.INTENT_ACTIONS.CHECKOUT_ACTION);
        LocalBroadcastManager.getInstance(this).sendBroadcast(checkoutIntent);

        //this broadcast will notify all the Activity who has registered with
        //close action
        Intent closeIntent = new Intent();
        closeIntent.setAction(MetaData.INTENT_ACTIONS.ACTIVITY_CLOSE_ACTION);
        LocalBroadcastManager.getInstance(this).sendBroadcast(closeIntent);

        this.finish();
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
}
