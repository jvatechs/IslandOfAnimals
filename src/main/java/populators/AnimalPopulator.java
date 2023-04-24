package populators;

import entities.Animal;
import island.CreateIsland;
import island.Location;

import java.lang.reflect.InvocationTargetException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public class AnimalPopulator extends ControllerCommon {
    private final static int MAX_X = CreateIsland.lengthOfIsland;
    private final static int MAX_Y = CreateIsland.widthOfIsland;

    List<? extends Animal> listOfExamples = CreateIsland.getAnimalsInitList();

    //random to island
    public void setAnimals_ver2() throws NoSuchMethodException, InvocationTargetException, InstantiationException, IllegalAccessException {
        //mapping data from list to HashMap
        for (Location[] location : locations) {
            for (Location currentLoc : location) {
                HashMap<Class<? extends Animal>, ArrayList<Animal>> animalListPerNameMap
                        = currentLoc.getAnimalListPerNameMap();
                for (Animal currentAnimalType : listOfExamples) {
                    ArrayList<Animal> currentAnimalTypeObjects = new ArrayList<>();
                    Class<? extends Animal> currentClass = currentAnimalType.getClass();

                    currentAnimalType.setCurrentCount(random.nextInt(1, currentAnimalType.getMaxCount() + 1));
                    int currentCount = currentAnimalType.getCurrentCount();
                    // #TEST TOOLS
//                    int currentCount = 1;

                    //grabbing all the animal of the Class of animals new instances into list
                    for (int i = 0; i < currentCount; i++) {
                        currentAnimalTypeObjects.add(newAnimalObjectCreate(currentAnimalType, currentClass));
                    }

                    animalListPerNameMap.put(currentClass, currentAnimalTypeObjects);
                }
//                currentLoc.createMapOfCount();
            }
        }
    }
}
