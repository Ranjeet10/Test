package com.envent.bottlesup.mvp.model;

import com.activeandroid.Model;
import com.activeandroid.annotation.Column;
import com.activeandroid.annotation.Table;
import com.activeandroid.query.Delete;
import com.activeandroid.query.Select;

import java.util.List;

/**
 * Created by ronem on 6/15/18.
 */

@Table(name = "seating_place")
public class TableBar extends Model {
    @Column(name = "seating_place_id")
    private int seatingPlaceId;
    @Column(name = "seating_place_name")
    private String seatingPlaceName;

    public TableBar() {
        super();
    }

    public TableBar(int seatingPlaceId, String seatingPlaceName) {
        this.seatingPlaceId = seatingPlaceId;
        this.seatingPlaceName = seatingPlaceName;
    }

    public int getSeatingPlaceId() {
        return seatingPlaceId;
    }

    public void setSeatingPlaceId(int seatingPlaceId) {
        this.seatingPlaceId = seatingPlaceId;
    }

    public String getSeatingPlaceName() {
        return seatingPlaceName;
    }

    public void setSeatingPlaceName(String seatingPlaceName) {
        this.seatingPlaceName = seatingPlaceName;
    }

    public static List<TableBar> getSeatingPlaces() {
        return new Select().from(TableBar.class).execute();
    }

    public static void clearSeatingPlaces() {
        new Delete().from(TableBar.class).execute();
    }
}
