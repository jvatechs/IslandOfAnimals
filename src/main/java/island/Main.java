package island;

import entities.Animal;
import populators.AnimalController;
import populators.AnimalPopulator;
import populators.AnimalsListCreator;
import populators.PlantPopulator;
import readers.DeserializeAnimalsInfo;
import readers.DeserializeProbability;


import java.io.IOException;
import java.lang.reflect.InvocationTargetException;
import java.util.List;

public class Main {
    public static void main(String[] args) throws IOException, NoSuchFieldException, ClassNotFoundException, InstantiationException, IllegalAccessException, InvocationTargetException, NoSuchMethodException {
        // SUCCESS! - creating island for set locations with randomly counted plants and animals

        CreateIsland island = new CreateIsland(10, 2);
        PlantPopulator plantPopulator = new PlantPopulator();

        AnimalsListCreator listCreator = new AnimalsListCreator();
        List<? extends Animal> animalsInitList = listCreator.getAnimals();

        //setting data from JSON to each animal
        new DeserializeAnimalsInfo().deserializeJSON(animalsInitList);
        //create HashMap of probabilities from JSON
        new DeserializeProbability().deserialize();

        //populate island randomly by animals
        AnimalPopulator animalPopulator = new AnimalPopulator();
        animalPopulator.setAnimals(animalsInitList);
        plantPopulator.plantRandomize();
        //print all locations
        island.printLocations();

        System.out.println("*".repeat(15) + "END OF DAY" + "*".repeat(15));

        island.printTotalAnimalsCount();


        //usage of controller methods
        AnimalController animalController = new AnimalController();
        animalController.eatControl_ver2();
        animalController.cleanMapFromEmptyValue();
        animalController.moveControl_ver3();
        animalController.cleanMapFromEmptyValue();

        animalController.reproduceControl();
        plantPopulator.plantGrow();
        animalController.resetBooleans();

        //print locations and animals after the 1st day
        island.printLocations();
        island.printTotalAnimalsCount();

    }
}
