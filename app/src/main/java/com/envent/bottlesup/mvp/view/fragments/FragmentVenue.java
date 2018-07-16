package com.envent.bottlesup.mvp.view.fragments;

import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.location.Location;
import android.os.Bundle;
import android.os.Handler;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.ActivityCompat;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.Editable;
import android.text.TextUtils;
import android.text.TextWatcher;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.inputmethod.EditorInfo;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.envent.bottlesup.R;
import com.envent.bottlesup.adapter.VenueListAdapter;
import com.envent.bottlesup.mvp.MVPView;
import com.envent.bottlesup.mvp.model.User;
import com.envent.bottlesup.mvp.model.server_response.event_response.EventResponse;
import com.envent.bottlesup.mvp.model.server_response.venue_list.VenueDataItem;
import com.envent.bottlesup.mvp.model.server_response.venue_list.VenueResponse;
import com.envent.bottlesup.mvp.presenter.MyPresenter;
import com.envent.bottlesup.mvp.presenter.VenuePresenterImpl;
import com.envent.bottlesup.mvp.view.activities.Dashboard;
import com.envent.bottlesup.mvp.view.customview.CustomToast;
import com.envent.bottlesup.mvp.view.customview.MyDialog;
import com.envent.bottlesup.mvp.view.fragments.base_fragment.LogoutMethodFragment;
import com.envent.bottlesup.utils.EndlessRecyclerViewScrollListener;
import com.envent.bottlesup.utils.GoogleLocationHelper;
import com.envent.bottlesup.utils.LocationComparator;
import com.envent.bottlesup.utils.MarshMalloPermission;
import com.envent.bottlesup.utils.MetaData;
import com.envent.bottlesup.utils.RecyclerItemClickListener;
import com.envent.bottlesup.utils.UtilityMethods;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.Unbinder;

/**
 * Created by ronem on 3/16/18.
 */

public class FragmentVenue
        extends LogoutMethodFragment
        implements MVPView.VenueView, GoogleLocationHelper.LocationFetcher {
    private String TAG = getClass().getSimpleName();

    @BindView(R.id.search_venue_edt)
    EditText searchVenueEdt;
    @BindView(R.id.swipe_refresh_layout)
    SwipeRefreshLayout swipeRefreshLayout;
    @BindView(R.id.recycler_view)
    RecyclerView venueRecyclerView;
    @BindView(R.id.empty_recycler_item_layout)
    LinearLayout emptyRecyclerLayout;
    @BindView(R.id.error_text_view)
    TextView errorTextView;

    private Unbinder unbinder;
    private MyPresenter.VenuePresenter presenter;
    private Handler refreshHandler;
    private Runnable refreshRunnable;

    private List<VenueDataItem> venues;
    private int NEXT_PAGE_COUNT = 1;
    private int LIMIT = 15;
    private int currentPage = 0;
    private String SEARCH_TAG = "";
    private boolean shouldScrollNext = true;
    private boolean isFromSearch = false;
    private boolean isFromTextChangedListener = false;
    private EndlessRecyclerViewScrollListener scrollListener;
    private MarshMalloPermission permission;
    private GoogleLocationHelper googleLocationHelper;
    private Location currentLocation;
    private MyDialog myDialog;
    private ProgressDialog progressDialog;


    public static FragmentVenue createNewInstance() {
        return new FragmentVenue();
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View root = inflater.inflate(R.layout.fragment_venue, container, false);
        unbinder = ButterKnife.bind(this, root);
        progressDialog = new ProgressDialog(getContext());
        progressDialog.setMessage(MetaData.MESSAGE.PLEASE_WAIT);
        progressDialog.setCancelable(false);

        refreshHandler = new Handler();
        return root;
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        myDialog = new MyDialog(getActivity());

        permission = new MarshMalloPermission(getContext());
        presenter = new VenuePresenterImpl();
        presenter.addView(this);
        presenter.getEvents(User.getUser().getAccess_token());
    }

    private void checkForPermission() {
        googleLocationHelper = new GoogleLocationHelper(getActivity());
        googleLocationHelper.setLocationFetcher(this);

        if (!permission.isFineLocationPermissionGranted()) {
            if (ActivityCompat.shouldShowRequestPermissionRationale(getActivity(), permission.fineLocationPermissions[0])) {
                myDialog.getDialogBuilder(MetaData.MESSAGE.FINE_LOCATION_MESSAGE)
                        .setPositiveButton("Give Permission", ((dialog, which) -> {
                            dialog.dismiss();
                            permission.requestFineLocationPermission();
                        })).create().show();
            } else {
                permission.requestFineLocationPermission();
            }

        } else {
            //todo all permission granted
            googleLocationHelper.getCachedLocationIfPossible();
        }

    }


    private void setSearchEditTextEvent() {
        searchVenueEdt.setOnEditorActionListener((v, actionId, event) -> {
            if (actionId == EditorInfo.IME_ACTION_DONE) {
                UtilityMethods.hideKeyboard(getActivity(), searchVenueEdt);
                return true;
            }
            return false;
        });

        searchVenueEdt.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

                int searchQueryLength = s.toString().length();
                if (searchQueryLength == 0) {
                    if (isFromTextChangedListener) {
                        presenter.cancelVenueCall();
                        firstFetch();
                    }
                } else {
                    presenter.cancelVenueCall();
                    fireSearchQuery();
                }
                isFromTextChangedListener = true;
            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        });
    }

    private void fireSearchQuery() {

        SEARCH_TAG = searchVenueEdt.getText().toString().trim();
        if (!TextUtils.isEmpty(SEARCH_TAG)) {
            shouldScrollNext = true;
            isFromSearch = true;
            presenter.getVenueList(SEARCH_TAG, null, null);
        }
    }

    private void setEndlessScrollListener() {
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(getActivity());
        if (venueRecyclerView != null) {

            venueRecyclerView.setLayoutManager(linearLayoutManager);
            venueRecyclerView.setHasFixedSize(false);
            venues = new ArrayList<>();
            VenueListAdapter adapter = new VenueListAdapter(getActivity(), venues);

//            SlideInLeftAnimationAdapter animationAdapter = new SlideInLeftAnimationAdapter(adapter);
//            animationAdapter.setDuration(300);
//            animationAdapter.setFirstOnly(false);
//            venueRecyclerView.setAdapter(animationAdapter);

            venueRecyclerView.setAdapter(adapter);
            scrollListener = new EndlessRecyclerViewScrollListener(linearLayoutManager) {
                @Override
                public void onLoadMore(int page, int totalItemsCount, RecyclerView view) {
                    //since we have use the header view for the recyclerview
                    //we need to check this other wise item will be fetched multiple times
                    if (currentPage == 0) {
                        currentPage = 1;
                    } else {
                        if (!isFromSearch) {
                            if (shouldScrollNext) {
                                presenter.getVenueList(SEARCH_TAG, String.valueOf(NEXT_PAGE_COUNT), String.valueOf(LIMIT));
                            }
                        }
                    }
                }
            };
            venueRecyclerView.addOnScrollListener(scrollListener);
        }

    }

    private void setPullToRefresh() {
        swipeRefreshLayout.setOnRefreshListener(() -> {
            googleLocationHelper.getCachedLocationIfPossible();
        });
    }

    private void firstFetch() {
        scrollListener.resetState();
        venues.clear();
        venueRecyclerView.setVisibility(View.GONE);
        SEARCH_TAG = "";
        NEXT_PAGE_COUNT = 1;
        shouldScrollNext = true;
        isFromSearch = false;
        isFromTextChangedListener = false;
        if (searchVenueEdt != null) {
            searchVenueEdt.setText("");
            presenter.getVenueList(SEARCH_TAG, String.valueOf(NEXT_PAGE_COUNT), String.valueOf(LIMIT));
        }
        UtilityMethods.hideKeyboard(getActivity(), searchVenueEdt);
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        unbinder.unbind();
        presenter.cancelVenueCall();
        presenter.cancelEventCall();
    }

    @Override
    public void showSwipeProgress() {
        if (!swipeRefreshLayout.isRefreshing()) {
            refreshRunnable = () -> {
                try {
                    swipeRefreshLayout.setRefreshing(true);
                } catch (Exception e) {
                    e.printStackTrace();
                }
            };
            refreshHandler.postDelayed(refreshRunnable, 200);
        }
    }

    @Override
    public void hideSwipeProgress() {
        refreshRunnable = () -> {
            try {
                swipeRefreshLayout.setRefreshing(false);
            } catch (Exception e) {
                e.printStackTrace();
            }
        };

        refreshHandler.postDelayed(refreshRunnable, 200);
    }

    @Override
    public void showMessage(String msg) {
        new CustomToast(getActivity(), msg, Toast.LENGTH_LONG);
    }

    @Override
    public void unAuthorized() {
        logoutNow();
    }

    @Override
    public void onEventsResponseReceived(EventResponse response) {
        if (response.getData() != null && response.getData().size() > 0) {
            MetaData.APP_LEVEL_CACHE.EVENTS = response;
        } else {
            EventResponse e = new EventResponse();
            e.setData(null);
            MetaData.APP_LEVEL_CACHE.EVENTS = e;
        }
        setEndlessScrollListener();

        if (venueRecyclerView != null) {
            venueRecyclerView.addOnItemTouchListener(new RecyclerItemClickListener(getActivity(),
                    (recyclerView, v, position) -> {
                        if (position == 0) {
                            return;
                        }
                        //we have to do this because we have added slider/static banner image
                        //as new item on 0th index of recyclerview
                        int newPosition = position - 1;
                        presenter.showCheckInCheckoutDialog(getContext(), venues.get(newPosition));
                    }
            ));
            setSearchEditTextEvent();
            checkForPermission();
            setPullToRefresh();
        }
    }

    @Override
    public void onVenueListResponse(VenueResponse response) {
        if (response.getVenueResponseData() != null) {
            Log.i(TAG, "DrinkItemData not null");

            if (response.getVenueResponseData().getLinks().getNext() != null) {

                String nxtPage = response.getVenueResponseData().getLinks().getNext();
                Log.i("NextPage:", nxtPage);

                nxtPage = nxtPage.substring(nxtPage.lastIndexOf("=") + 1);
                Log.i("NextPageSub:", nxtPage);

                NEXT_PAGE_COUNT = Integer.parseInt(nxtPage);
                shouldScrollNext = true;
            } else {
                /**
                 * we don't have any further page count to load the next page
                 * so we need to set the flag {@link shouldScrollNext to false}
                 */
                shouldScrollNext = false;
            }

            /**
             *Clear the venue list before adding the latest searched data
             * venue list might have some previously loaded data
             * also change the flag {@link shouldScrollNext to true}
             * so that {@link scrollListener}load more will not function while venues has the searched items
             */
            if (isFromSearch) {
                venues.clear();
                shouldScrollNext = true;
            }
            presenter.getManagedList(response.getVenueResponseData().getItems(), currentLocation);

        }

    }

    @Override
    public void onVenueManagedListReceived(List<VenueDataItem> items) {

        venues.addAll(items);
        venueRecyclerView.setVisibility(View.VISIBLE);
        Collections.sort(venues, new LocationComparator());

        if (venueRecyclerView != null) {
            if (venues.size() > 0) {
                emptyRecyclerLayout.setVisibility(View.GONE);

            } else {
                emptyRecyclerLayout.setVisibility(View.VISIBLE);
                errorTextView.setText(MetaData.MESSAGE.NO_VENUE_FOUND);
            }

            venueRecyclerView.getAdapter().notifyDataSetChanged();
        }
    }

    @Override
    public void onCheckIn(String venueName) {
        showMessage("You have checked in to " + venueName + " successfully");
        ((Dashboard) getActivity()).setUpBottomNavigationMenuView(false, false);
    }

    @Override
    public void onCheckOut(String venueName) {
        showMessage("You have checked out from " + venueName + " successfully");
        ((Dashboard) getActivity()).setUpBottomNavigationMenuView(false, false);
    }


    @Override
    public void onRequestPermissionsResult(int requestCode,
                                           @NonNull final String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        if (requestCode == MetaData.REQUEST_CODE.ACCESS_FINE_LOCATION_REQUEST_CODE) {
            if (grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                showSwipeProgress();
                googleLocationHelper.getCachedLocationIfPossible();
            } else {
                myDialog.getDialogBuilder(MetaData.MESSAGE.PERMISSION_DENIED)
                        .setPositiveButton("Give Permission", (dialog, which) -> {
                            dialog.dismiss();
                            permission.requestFineLocationPermission();
                        })
                        .create().show();
            }
        }
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        Log.i(TAG, "On result");
        switch (requestCode) {
            case MetaData.REQUEST_CODE.LOCATION_REQUEST_CODE:
                switch (resultCode) {
                    case Activity.RESULT_OK:
                    case Activity.RESULT_CANCELED:
                        showSwipeProgress();
                        googleLocationHelper.getCachedLocationIfPossible();
                        break;
                }
        }
    }

    @Override
    public void onLocationFetched(Location currentLocation) {
        this.currentLocation = currentLocation;
        Log.i(TAG, "Current Location" + currentLocation.getLatitude() + ":" + currentLocation.getLongitude());
        firstFetch();

    }

    @Override
    public void showProgressDialog() {
        if (progressDialog != null && !progressDialog.isShowing()) {
            progressDialog.show();
        }
    }

    @Override
    public void hideProgressDialog() {
        if (progressDialog != null && progressDialog.isShowing()) {
            progressDialog.dismiss();
        }
    }
}
