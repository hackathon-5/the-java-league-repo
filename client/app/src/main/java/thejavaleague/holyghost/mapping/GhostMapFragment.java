package thejavaleague.holyghost.mapping;

import android.app.Dialog;
import android.app.Fragment;
import android.content.Context;
import android.graphics.Point;
import android.location.Criteria;
import android.location.Location;
import android.location.LocationManager;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;
import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.common.GooglePlayServicesUtil;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.GoogleMap.OnInfoWindowClickListener;
import com.google.android.gms.maps.GoogleMap.OnMapLoadedCallback;
import com.google.android.gms.maps.GoogleMap.OnMapLongClickListener;
import com.google.android.gms.maps.GoogleMap.OnMarkerClickListener;
import com.google.android.gms.maps.GoogleMap.OnMyLocationChangeListener;
import com.google.android.gms.maps.MapFragment;
import com.google.android.gms.maps.model.BitmapDescriptorFactory;
import com.google.android.gms.maps.model.CameraPosition;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.Marker;
import com.google.android.gms.maps.model.MarkerOptions;
import thejavaleague.holyghost.R;
import thejavaleague.holyghost.database.GhostDatabase;
import thejavaleague.holyghost.database.Sighting;
import thejavaleague.holyghost.database.Sightings;

import java.util.List;

/**
 * Created by Chris on 8/28/15.
 */
public class GhostMapFragment extends Fragment implements OnMapLoadedCallback, OnMapLongClickListener,
        OnMarkerClickListener, OnInfoWindowClickListener, OnMyLocationChangeListener {

    private static final String LOG_TAG = "GhostMapFragment";

    private Context appContext;
    private GoogleMap map;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        Log.i(LOG_TAG, "creating view");
        inflater.inflate(R.layout.fragment_ghost_map, container);
        return super.onCreateView(inflater, container, savedInstanceState);
    }

    @Override
    public void onActivityCreated(Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        Log.i(LOG_TAG, "on activity created");
        int errorCode = GooglePlayServicesUtil.isGooglePlayServicesAvailable(getActivity());
        if (errorCode != ConnectionResult.SUCCESS) {
            Log.e(LOG_TAG, "google play service(s) are NOT available");

            //we show an error dialog so the user can correct the issue
            Dialog dialog = GooglePlayServicesUtil.getErrorDialog(errorCode, getActivity(), 1);
            dialog.setCancelable(false);
            dialog.show();
        } else {
            // Must call this manually or will get a null pointer.
            MapFragment mapFragment = (MapFragment) getFragmentManager().findFragmentById(R.id.map);
            map = mapFragment.getMap();
            map.setOnMapLoadedCallback(this);
        }
    }

    private void initializeMap() {
        // Set map options
        map.setMapType(GoogleMap.MAP_TYPE_NORMAL);
        map.setMyLocationEnabled(true);

        // Set map listeners
        map.setOnMapLongClickListener(this);
        map.setOnMarkerClickListener(this);
        map.setOnInfoWindowClickListener(this);
    }

    private void putSightingsMarkers(){
        // Get the hard coded Sightings.
        Sightings sightings = new Sightings();
        List<Sighting> list = sightings.getSightingList();

        // Add all Sightings to the map.
        for(Sighting sighting : list){
            createMarker(sighting);
        }
        // Move and zoom in to the last Sighting
        animateCameraTo(list.get(list.size() - 1).getLocation(), 13);
    }

    public Location getCurrentLocation() {
        return map.getMyLocation();
    }

///// MAP CAMERA

    /**
     * Set the zoom level of the map with the option to animate the motion.
     *
     * @param zoomLevel The zoom level to set.
     * @param animate   True to animate, false to move directly.
     */
    public void setZoomLevel(float zoomLevel, boolean animate) {
        if (animate) {
            map.animateCamera(CameraUpdateFactory.zoomTo(zoomLevel));
        } else {
            map.moveCamera(CameraUpdateFactory.zoomTo(zoomLevel));
        }
    }

    /**
     * moves camera to position on the screen
     *
     * @param point   The point on the screen to move the camera to
     * @param animate Boolean for animate to Point (true) or move to Point (false)
     */
    public void moveCameraTo(Point point, boolean animate) {
        LatLng coordinate = map.getProjection().fromScreenLocation(point);
        if (animate) {
            map.animateCamera(CameraUpdateFactory.newLatLng(coordinate));
        } else {
            map.moveCamera(CameraUpdateFactory.newLatLng(coordinate));
        }
    }

    /**
     * moves camera to coordinates on map
     *
     * @param coordinates The coordinates on the map to move the camera to
     * @param animate     Boolean for animate to LatLng (true) or move to LatLng (false)
     */
    public void moveCameraTo(LatLng coordinates, boolean animate) {
        if (animate) {
            map.animateCamera(CameraUpdateFactory.newLatLng(coordinates));
        } else {
            map.moveCamera(CameraUpdateFactory.newLatLng(coordinates));
        }
    }

    /**
     * moves camera to the {@link Location} on the map
     *
     * @param location The coordinates on the map to move the camera to
     * @param animate     Boolean for animate to LatLng (true) or move to LatLng (false)
     */
    public void moveCameraTo(Location location, boolean animate) {
        LatLng coordinates = new LatLng(location.getLatitude(), location.getLongitude());
        if (animate) {
            map.animateCamera(CameraUpdateFactory.newLatLng(coordinates));
        } else {
            map.moveCamera(CameraUpdateFactory.newLatLng(coordinates));
        }
    }

    /**
     * Animates camera to the {@link Location} on the map
     *
     * @param location The coordinates on the map to move the camera to
     * @param zoom     The zoom level for the map to go to during the move.
     */
    public void animateCameraTo(Location location, float zoom) {
        LatLng coordinates = new LatLng(location.getLatitude(), location.getLongitude());
        CameraPosition cp = new CameraPosition.Builder()
                .target(coordinates)
                .zoom(15)
                .build();
        map.animateCamera(CameraUpdateFactory.newCameraPosition(cp));
    }

    /**
     * Animates camera to the {@link LatLng} on the map
     *
     * @param location The coordinates on the map to move the camera to
     * @param zoom     The zoom level for the map to go to during the move.
     */
    public void animateCameraTo(LatLng location, float zoom) {
        CameraPosition cp = new CameraPosition.Builder()
                .target(location)
                .zoom(zoom)
                .build();
        map.animateCamera(CameraUpdateFactory.newCameraPosition(cp));
    }

    //// MAP LISTENERS
    @Override
    public void onMapLoaded() {
        Log.d(LOG_TAG, "Map loaded");
        this.initializeMap();
        this.putSightingsMarkers();
    }

    @Override
    public void onInfoWindowClick(Marker marker) {
//        Toast.makeText(appContext, "Info window clicked", Toast.LENGTH_SHORT).show();
    }

    @Override
    public void onMapLongClick(LatLng latLng) {
//        Toast.makeText(appContext, "Map long clicked", Toast.LENGTH_SHORT).show();
    }

    @Override
    public boolean onMarkerClick(Marker marker) {
//        Toast.makeText(appContext, "Marker clicked", Toast.LENGTH_SHORT).show();
        return false;
    }

    @Override
    public void onMyLocationChange(Location location) {
        Toast.makeText(appContext, "My location changed", Toast.LENGTH_SHORT).show();
    }

    public void createMarker(Sighting sighting) {
        map.addMarker(new MarkerOptions()
                .icon(BitmapDescriptorFactory.fromResource(R.drawable.ghost_marker))
                .anchor(1f, 1f) // Makes the ghost's tail as the marker point
                .title(sighting.getTitle())
                .snippet(sighting.getDescription() + "\n" + getString(R.string.rating) + ": " + sighting.getRating())
                .position(sighting.getLocation())
                .draggable(false));
    }

    public void setMapType(MapViewType type) {
        map.setMapType(type.getType());
    }
}
