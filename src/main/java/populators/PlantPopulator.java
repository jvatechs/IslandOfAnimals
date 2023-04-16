package populators;

import entities.Plants;
import island.CreateIsland;
import island.Location;

import java.util.Random;

public class PlantPopulator extends PopulatorCommon {
    private static final int MAX_PLANTS_PER_LOCATION = 200;
    public void plantRandomize() {
        for (Location[] location : locations) {
            for (Location value : location) {
                int plantExistRandom = random.nextInt(0, 2);
                if (plantExistRandom == 1) {
                    value.setPlants(new Plants(random.nextInt(MAX_PLANTS_PER_LOCATION)));
                }
            }
        }
    }

}
