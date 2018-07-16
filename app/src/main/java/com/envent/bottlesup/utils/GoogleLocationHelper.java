package com.envent.bottlesup.utils;

import android.app.Activity;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.IntentSender;
import android.location.Location;
import android.net.Uri;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v7.app.AlertDialog;
import android.util.Log;

import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.common.GoogleApiAvailability;
import com.google.android.gms.common.api.GoogleApiClient;
import com.google.android.gms.common.api.PendingResult;
import com.google.android.gms.common.api.ResultCallback;
import com.google.android.gms.common.api.Status;
import com.google.android.gms.location.LocationAvailability;
import com.google.android.gms.location.LocationCallback;
import com.google.android.gms.location.LocationRequest;
import com.google.android.gms.location.LocationResult;
import com.google.android.gms.location.LocationServices;
import com.google.android.gms.location.LocationSettingsRequest;
import com.google.android.gms.location.LocationSettingsResult;
import com.google.android.gms.location.LocationSettingsStatusCodes;

/**
 * Created by ronem on 3/16/18.
 */
public class GoogleLocationHelper implements GoogleApiClient.ConnectionCallbacks, GoogleApiClient.OnConnectionFailedListener {

    private String TAG = getClass().getSimpleName();
    private GoogleApiClient googleApiClient;
    private LocationFetcher locationFetcher;
    private Location currentLocation = null;
    private LocationRequest locationRequest;
    private boolean cachedLocationEnough = false;
    private Activity context;

    public interface LocationFetcher {
        void onLocationFetched(Location currentLocation);
    }

    public GoogleLocationHelper(Activity context) {
        this.context = context;
    }

    public void setLocationFetcher(LocationFetcher fetcher) {
        this.locationFetcher = fetcher;
    }

    public void getCachedLocationIfPossible() {
        cachedLocationEnough = true;
        getLocation();
    }

    private void getLocation() {
        googleApiClient = new GoogleApiClient.Builder(context)
                .addConnectionCallbacks(this)
                .addOnConnectionFailedListener(this)
                .addApi(LocationServices.API).build();
        googleApiClient.connect();
    }

    @SuppressWarnings("MissingPermission")
    private Location getLastLocation() {
        return LocationServices.FusedLocationApi.getLastLocation(googleApiClient);
    }

    @Override
    public void onConnected(@Nullable Bundle bundle) {
        Log.i(TAG, "Client Connected");
        if (cachedLocationEnough) {
            cachedLocationEnough = false;
            currentLocation = getLastLocation();
            if (currentLocation != null) {
                if (locationFetcher != null) {
                    locationFetcher.onLocationFetched(currentLocation);
                }
            } else {
                Log.i(TAG, "Current location null");
                requestLocation();
            }
        } else {
            requestLocation();
            Log.i(TAG, "Cached Location null");
        }
    }

    private void requestLocation() {
        Log.i(TAG, "Requesting Location");
        locationRequest = LocationRequest.create()
                .setNumUpdates(1)
                .setPriority(LocationRequest.PRIORITY_HIGH_ACCURACY)
                .setInterval(0);

        LocationSettingsRequest.Builder builder = new LocationSettingsRequest.Builder()
                .addLocationRequest(locationRequest);
        builder.setAlwaysShow(true);
        PendingResult<LocationSettingsResult> result =
                LocationServices.SettingsApi.checkLocationSettings(googleApiClient, builder.build());
        result.setResultCallback(new ResultCallback<LocationSettingsResult>() {
            @Override
            public void onResult(@NonNull LocationSettingsResult locationSettingsResult) {
                Status status = locationSettingsResult.getStatus();
                switch (status.getStatusCode()) {
                    case LocationSettingsStatusCodes.RESOLUTION_REQUIRED:
                        try {
                            status.startResolutionForResult(context, MetaData.REQUEST_CODE.LOCATION_REQUEST_CODE);
                        } catch (IntentSender.SendIntentException e) {
                            e.printStackTrace();
                        }
                        break;
                    case LocationSettingsStatusCodes.SUCCESS:
                        startRequest();
                        break;
                }
            }
        });
    }

    @SuppressWarnings("MissingPermission")
    public void startRequest() {
        LocationServices.FusedLocationApi.requestLocationUpdates(googleApiClient, locationRequest, new LocationCallback() {
            @Override
            public void onLocationResult(LocationResult locationResult) {
                super.onLocationResult(locationResult);
                //this callback not called if no results, only if result actually available.
                Log.i(TAG, "Location Result");
                currentLocation = locationResult.getLastLocation();
                locationFetcher.onLocationFetched(currentLocation);
            }

            @Override
            public void onLocationAvailability(LocationAvailability locationAvailability) {
                super.onLocationAvailability(locationAvailability);
                //always called, could be falsely true.
            }
        }, null);
    }

    @Override
    public void onConnectionSuspended(int i) {
        Log.i(TAG, "Connection suspended");
        locationFetcher.onLocationFetched(null);
    }

    @Override
    public void onConnectionFailed(@NonNull ConnectionResult connectionResult) {
        int status = GoogleApiAvailability.getInstance().isGooglePlayServicesAvailable(context);
        Log.i(TAG, "Connection Failed");
        if (status != ConnectionResult.SUCCESS) {

            AlertDialog.Builder builder = new AlertDialog.Builder(context);
            builder.setMessage("Your google play services feature is out of date")
                    .setTitle("Do you want to update?");
            builder.setPositiveButton("Okay", ((dialog, which) -> {
                final String appPackageName = "com.google.android.gms";
                try {
                    context.startActivity(new Intent(Intent.ACTION_VIEW, Uri.parse("market://details?id=" + appPackageName)));
                } catch (android.content.ActivityNotFoundException anfe) {
                    context.startActivity(new Intent(Intent.ACTION_VIEW, Uri.parse("https://play.google.com/store/apps/details?id=" + appPackageName)));
                }
            }));
            builder.setNegativeButton("Cancel", ((dialog, which) -> {
                dialog.dismiss();
                locationFetcher.onLocationFetched(null);
            }));
            AlertDialog dialog = builder.create();
            dialog.show();
        } else {
            locationFetcher.onLocationFetched(null);
        }
    }

}
