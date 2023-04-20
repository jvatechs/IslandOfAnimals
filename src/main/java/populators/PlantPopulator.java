package populators;

import entities.Plants;
import island.Location;

public class PlantPopulator extends ControllerCommon {
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

    public void plantGrow() {
        for (Location[] location : locations) {
            for (Location currentLoc : location) {
                int currentCount = currentLoc.getPlants().getCurrentCount();
                //grow for +10%
                if (currentCount * 11 / 10 <= currentLoc.getPlants().getMaxCount()) {
                    currentLoc.getPlants().setCurrentCount(currentCount * 11 / 10);
                }
            }
        }
    }

}
