package island;

import entities.Animal;
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
        List<? extends Animal> animals = listCreator.getAnimals();

        //setting data from JSON to each animal
        new DeserializeAnimalsInfo().deserializeJSON(animals);

        //populate island randomly by animals
        new AnimalPopulator().setAnimals(animals);
        plantPopulator.plantRandomize();
        island.printLocations();

        //create HashMap of probabilities from JSON
        new DeserializeProbability().deserialize();

//        List<? extends Animal> animals = animalPopulator.getAnimals();
//        for (Animal animal : animals) {
//            System.out.println(animal);
//        }

//        new CreateOwnJson().createJson();

//        JsonFiller jsonFiller = new JsonFiller();

//        CreateOwnJson createOwnJson = new CreateOwnJson();
//        createOwnJson.createJson();


    }
}
