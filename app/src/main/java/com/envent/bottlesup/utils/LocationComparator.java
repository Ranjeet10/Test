package com.envent.bottlesup.utils;

import com.envent.bottlesup.mvp.model.server_response.venue_list.VenueDataItem;

import java.util.Comparator;

/**
 * Created by ronem on 5/11/18.
 */

public class LocationComparator implements Comparator<VenueDataItem> {

    @Override
    public int compare(VenueDataItem venue1, VenueDataItem venue2) {
        Double venue1Number = Double.parseDouble(venue1.getDistance());
        Double venue2Number = Double.parseDouble(venue2.getDistance());
        return venue1Number.compareTo(venue2Number);

    }


}
