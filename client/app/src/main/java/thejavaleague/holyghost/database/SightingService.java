package thejavaleague.holyghost.database;

import thejavaleague.holyghost.database.remote.SightingWebService;

import java.io.IOException;

/**
 * Created by Chris on 8/29/15.
 */
public class SightingService {

    private SightingWebService webService;

    public SightingService() {}

    public Sighting getSightings(){
        try {
            webService.getStringData("www.someserver.com");
        } catch (IOException e) {
            e.printStackTrace();
        }
        return null;
    }

    public void saveSighting(Sighting sighting) {

    }

}
