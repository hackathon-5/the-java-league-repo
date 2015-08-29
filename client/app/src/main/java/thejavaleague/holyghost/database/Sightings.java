package thejavaleague.holyghost.database;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Chris on 8/29/15.
 */
public class Sightings {

    private List<Sighting> sightingList = new ArrayList<>();

    public Sightings() {
        initializeList();
    }

    private void initializeList() {
        Sighting stPhilips = new Sighting();
        stPhilips.setLocation(32.7790081, -79.929213);

        Sighting oldExchange = new Sighting();
        oldExchange.setLocation(32.7768094, -79.9268265);

        Sighting oldJail = new Sighting();
        oldJail.setLocation(32.7787699, -79.9381322);

        Sighting poogansPorch = new Sighting();
        poogansPorch.setLocation(32.7780909, -79.9319189);

        Sighting dockStTheatre = new Sighting();
        dockStTheatre.setLocation(32.7780514, -79.9294915);

        Sighting unitarianChurch = new Sighting();
        unitarianChurch.setLocation(32.7785522, -79.93435);

        Sighting battaeryCarrageInn = new Sighting();
        battaeryCarrageInn.setLocation(32.770596, -79.9308289);

        Sighting whitePointGarden = new Sighting();
        whitePointGarden.setLocation(32.7699815, -79.9302884);

        Sighting boonHallPlantation = new Sighting();
        boonHallPlantation.setLocation(32.8474085, -79.8263147);
        boonHallPlantation.setTitle("Boon Hall Plantation");
        boonHallPlantation.setDescription("test description");
        boonHallPlantation.setRating(3);

        sightingList.add(stPhilips);
        sightingList.add(oldExchange);
        sightingList.add(oldJail);
        sightingList.add(poogansPorch);
        sightingList.add(dockStTheatre);
        sightingList.add(unitarianChurch);
        sightingList.add(battaeryCarrageInn);
        sightingList.add(whitePointGarden);
        sightingList.add(boonHallPlantation);
    }

    public List<Sighting> getSightingList() {
        return sightingList;
    }
}
