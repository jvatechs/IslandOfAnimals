package populators;

import animals.Herbivores.*;
import animals.Predators.*;
import entities.Animal;
import island.CreateIsland;
import island.Location;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public class AnimalPopulator extends PopulatorCommon{
    private final static int MAX_X = CreateIsland.lengthOfIsland;
    private final static int MAX_Y = CreateIsland.widthOfIsland;

    public AnimalPopulator() {
//        createAnimalList();
    }

    //random to island


    public void setAnimals(List<? extends Animal> list) {
        for (Animal animal : list) {

            //current count setting
            animal.setCurrentCount(random.nextInt(1,animal.getMaxCount() + 1));
            int currentCount = animal.getCurrentCount();
            for (int i = 0; i < currentCount; i++) {
                int randomX = random.nextInt(MAX_X);
                int randomY = random.nextInt(MAX_Y);
                Location location = locations[randomX][randomY];
                location.getAnimals().add(animal);

                HashMap<Animal, Integer> animalOnTheLocMap = location.getAnimalOnTheLocMap();
                if(!animalOnTheLocMap.containsKey(animal)) {
                    animalOnTheLocMap.put(animal, 1);
                } else {
                    animalOnTheLocMap.put(animal, animalOnTheLocMap.get(animal) + 1);
                }

            }


//            animal.setLocation(location);
        }
        for (Location[] location : locations) {
            for (Location value : location) {
                value.createMapOfCount();
            }
        }

    }




    //random of animals

}
