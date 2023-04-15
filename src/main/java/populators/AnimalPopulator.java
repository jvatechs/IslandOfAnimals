package populators;

import animals.Herbivores.*;
import animals.Predators.*;
import entities.Animal;
import island.CreateIsland;
import island.Location;

import java.util.ArrayList;
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
            location.getAnimals().add(animal);
        }

    }




    //random of animals

}
