package populators;

import entities.Plants;
import island.CreateIsland;
import island.Location;

import java.util.Random;

public class PlantPopulator extends PopulatorCommon {
    private static final int MAX_PLANTS_PER_LOCATION = 200;
    public void plantRandomize() {
        for (int i = 0; i < locations.length; i++) {
            for (int j = 0; j < locations[i].length; j++) {
                locations[i][j].setPlants(new Plants(random.nextInt(MAX_PLANTS_PER_LOCATION)));
            }
        }
    }

}
