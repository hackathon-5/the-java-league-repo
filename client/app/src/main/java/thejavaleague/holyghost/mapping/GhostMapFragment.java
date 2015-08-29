package thejavaleague.holyghost.mapping;

import android.app.Dialog;
import android.app.Fragment;
import android.content.Context;
import android.graphics.Point;
import android.location.Location;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
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
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.Marker;
import thejavaleague.holyghost.R;

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

        //TODO: Set the zoom level
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
     * centers the marker at a position
     *
     * @param mark    the marker to center on the screen
     * @param center  the position on the screen to move the marker. If null will center in the screen.
     * @param animate set true to animation movement otherwise normal move
     */
    public void centerOnMarker(Marker mark, int[] center, boolean animate) {
        if (mark == null) {
            Log.e(LOG_TAG, "error: marker is null");
            return;
        }
        LatLng coor = mark.getPosition();
        if (center != null) {
            Point markLoc = map.getProjection().toScreenLocation(coor);
            Point newPos = new Point();
            //x stays the same
            newPos.x = markLoc.x;
            //requested center y location is added to marker y location for offset
            newPos.y = markLoc.y + center[1];
            Log.i(LOG_TAG, "new marker position: " + newPos);
            //set coor = newPos projection to center new position
            coor = map.getProjection().fromScreenLocation(newPos);
        }
        moveCameraTo(coor, animate);
    }

//// MAP LISTENERS
    @Override
    public void onMapLoaded() {
        Log.d(LOG_TAG, "Map loaded");
        this.initializeMap();
    }

    @Override
    public void onInfoWindowClick(Marker marker) {
        Log.d(LOG_TAG, "Info window clicked");
    }

    @Override
    public void onMapLongClick(LatLng latLng) {
        Log.d(LOG_TAG, "Map LONG clicked");
    }

    @Override
    public boolean onMarkerClick(Marker marker) {
        Log.d(LOG_TAG, "Marker clicked");
        return false;
    }

    @Override
    public void onMyLocationChange(Location location) {
        Log.d(LOG_TAG, "My location changed");
    }
}
