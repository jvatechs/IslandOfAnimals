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
            int randomX = random.nextInt(MAX_X);
            int randomY = random.nextInt(MAX_Y);
            Location location = locations[randomX][randomY];
            //current count setting
            animal.setCurrentCount(random.nextInt(1,animal.getMaxCount() + 1));
            location.getAnimals().add(animal);
            animal.setLocation(location);
        }
        for (Location[] location : locations) {
            for (Location value : location) {
                value.createMapOfCount();
            }
        }

    }




    //random of animals

}
