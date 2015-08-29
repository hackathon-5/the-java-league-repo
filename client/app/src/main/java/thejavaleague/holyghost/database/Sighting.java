package thejavaleague.holyghost.database;

import com.google.android.gms.maps.model.LatLng;

/**
 * Created by Chris on 8/29/15.
 */
public class Sighting {

    private String name;
    private String description;
    private int rating;
    private LatLng location;

    public Sighting(){}

    public Sighting(String name, String description, LatLng location, int rating){
        this.name = name;
        this.description = description;
        this.location = location;
        this.rating = rating;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
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
