package island;

import entities.Animal;
import populators.AnimalController;
import populators.AnimalPopulator;
import populators.PlantPopulator;
import readers.DeserializeAnimalsInfo;
import readers.DeserializeProbability;


import java.io.IOException;
import java.lang.reflect.InvocationTargetException;
import java.util.List;

public class Main {
    public static void main(String[] args) throws IOException, NoSuchFieldException, ClassNotFoundException, InstantiationException, IllegalAccessException, InvocationTargetException, NoSuchMethodException {



        CreateIsland island = new CreateIsland(10, 2);

        //setting data from JSON to each animal
        new DeserializeAnimalsInfo().deserializeJSON();
        //create HashMap of probabilities from JSON
        new DeserializeProbability().deserializeJSON();

        //strictly must used after creating island
        AnimalPopulator animalPopulator = new AnimalPopulator();
        PlantPopulator plantPopulator = new PlantPopulator();

        //populate island randomly by animals
        animalPopulator.setAnimals_ver2();
        plantPopulator.plantRandomize();

        //print all locations
        island.printLocations();
        island.printTotalAnimalsCount();

        System.out.println("*".repeat(15) + "END OF DAY" + "*".repeat(15));

        //create HashMap of probabilities from JSON
        new DeserializeProbability().deserialize();

        //usage of controller methods
        AnimalController animalController = new AnimalController();
        animalController.eatControl_ver2();
        animalController.moveControl_ver3();
        animalController.reproduceControl();
        plantPopulator.plantGrow();

        //print locations and animals after the 1st day
        island.printLocations();
        island.printTotalAnimalsCount();

    }
}
