package thejavaleague.holyghost.mapping;

import com.google.android.gms.maps.GoogleMap;

/**
 * Created by Chris on 8/29/15.
 */
public enum MapViewType {

    NORMAL (GoogleMap.MAP_TYPE_NORMAL),
    HYBRID (GoogleMap.MAP_TYPE_HYBRID),
    SATELLITE (GoogleMap.MAP_TYPE_SATELLITE),
    TERRAIN (GoogleMap.MAP_TYPE_TERRAIN),
    NONE (GoogleMap.MAP_TYPE_NONE);

    private int mapType;

    MapViewType(int type) {
        this.mapType = type;
    }

    public int getType() {
        return mapType;
    }
}
