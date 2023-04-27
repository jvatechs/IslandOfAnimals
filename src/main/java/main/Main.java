package main;

import island.CreateIsland;
import service.populators_and_controllers.AnimalController;
import service.populators_and_controllers.AnimalPopulator;
import service.populators_and_controllers.PlantPopulator;
import service.json_readers.DeserializeAnimalsInfo;
import service.json_readers.DeserializeProbability;


import java.io.IOException;
import java.lang.reflect.InvocationTargetException;

public class Main {
    public static void main(String[] args) throws IOException, NoSuchFieldException, ClassNotFoundException, InstantiationException, IllegalAccessException, InvocationTargetException, NoSuchMethodException, InterruptedException {



        CreateIsland island = new CreateIsland(100, 20);

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
        island.printTotalPlantsCount();

        System.out.println("*".repeat(15) + "END OF DAY" + "*".repeat(15));


        //usage of controller methods
        AnimalController animalController = new AnimalController();
        animalController.eatControl_ver2();
        animalController.moveControl_ver3();
        animalController.reproduceControl();
        plantPopulator.plantGrow();

        //print locations and animals after the 1st day
//        island.printLocations();
        island.printTotalAnimalsCount();
        island.printTotalPlantsCount();

    }
}
