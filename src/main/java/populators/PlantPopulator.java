package populators;

import entities.Plants;
import island.Location;

public class PlantPopulator extends ControllerCommon {
    private static final int MAX_PLANTS_PER_LOCATION = 200;
    public void plantRandomize() {
        for (Location[] location : locations) {
            for (Location currentLoc : location) {
//                int plantExistRandom = random.nextInt(0, 2);
//                if (plantExistRandom == 1) {
//                    currentLoc.setPlants(new Plants(random.nextInt(MAX_PLANTS_PER_LOCATION)));
//                }
                randomGrass(currentLoc);
            }
        }
    }

    public void plantGrow() {
        for (Location[] location : locations) {
            for (Location currentLoc : location) {
                if (currentLoc.getPlants() == null) {
                    randomGrass(currentLoc, 50);
//                    System.out.println("Count after grow: " + currentLoc.getPlants().getCurrentCount());
                } else {
                    int currentCount = currentLoc.getPlants().getCurrentCount();
                    //grow for +10%
//                    System.out.println("Current count: " + currentCount);
                    if (currentCount * 11 / 10 <= currentLoc.getPlants().getMaxCount()) {
                        currentLoc.getPlants().setCurrentCount(currentCount * 11 / 10);
                    }

                }
            }
        }
    }

    private void randomGrass(Location currentLocation) {
        int plantExistRandom = random.nextInt(0, 2);
        if (plantExistRandom == 1) {
            currentLocation.setPlants(new Plants(random.nextInt(MAX_PLANTS_PER_LOCATION)));
        }
    }
    private void randomGrass(Location currentLocation, int boundOfRandom) {
        int plantExistRandom = random.nextInt(0, 2);
        if (plantExistRandom == 1) {
            currentLocation.setPlants(new Plants(random.nextInt(boundOfRandom)));
        }
    }

}
