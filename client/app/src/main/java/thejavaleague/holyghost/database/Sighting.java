package thejavaleague.holyghost.database;

import android.database.Cursor;
import com.google.android.gms.maps.model.LatLng;

/**
 * Created by Chris on 8/29/15.
 */
public class Sighting {

    private String title;
    private String description;
    private int rating;
    private LatLng location;

    public Sighting(){}
    
    public Sighting(Cursor cursor){
        title = cursor.getString(cursor.getColumnIndex(DatabaseOperations.TITLE));
        description = cursor.getString(cursor.getColumnIndex(DatabaseOperations.DESCRIPTION));
        rating = cursor.getInt(cursor.getColumnIndex(DatabaseOperations.RATING));
        double latitude = cursor.getDouble(cursor.getColumnIndex(DatabaseOperations.LATITUDE));
        double longitude = cursor.getDouble(cursor.getColumnIndex(DatabaseOperations.LONGITUDE));
        location = new LatLng(latitude, longitude);
    }

    public Sighting(String title, String description, LatLng location, int rating){
        this.title = title;
        this.description = description;
        this.location = location;
        this.rating = rating;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public int getRating() {
        return rating;
    }

    public void setRating(int rating) {
        this.rating = rating;
    }

    public LatLng getLocation() {
        return location;
    }

    public void setLocation(LatLng location) {
        this.location = location;
    }

    public void setLocation(double latitude, double longitude) {
        this.location = new LatLng(latitude, longitude);
    }
}
