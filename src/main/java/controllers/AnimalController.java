package controllers;

import entities.Animal;
import entities.EatOtherAnimalAble;
import entities.Entity;
import entities.Plants;
import island.CreateIsland;
import island.Location;
import readers.DeserializeProbability;

import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.stream.Collectors;

public class AnimalController {
    private int currentProbability;
    private static final HashMap<Class<? extends Animal>, HashMap<Class<? extends Entity>, Integer>> hashMap =
            new DeserializeProbability().getAllProbabilitiesMap();

    public void control() {
        Location[][] locations = CreateIsland.island;

        for (int i = 0; i < locations.length; i++) {
            for (int j = 0; j < locations[i].length; j++) {
                Location currentLoc = locations[i][j];
                if (!currentLoc.getAnimals().isEmpty()) {
                    for (Animal animal : currentLoc.getAnimals()) {
                        //isPredator can help define is animal eat plants or not
                        //but I wanna create an interface which will be marker of can be EaterOfAnimals
                        Class<? extends Entity> currentAnimalClass = animal.getClass();
                        HashMap<Class<? extends Entity>, Integer> probabilities = getCurrentProbabilityMap(currentAnimalClass);
                        List<Animal> animalsOnTheLoc = currentLoc.getAnimals();

                        if (animal.canEatAnimalsOrNot() && animalsOnTheLoc.size() > 1) {
                            for (Animal canBeEaten : animalsOnTheLoc) {
                                Class<? extends Animal> canBeEatenClass = canBeEaten.getClass();
                                if (checkIfContains(probabilities, canBeEatenClass)) {
                                    getEatenProbability(probabilities, canBeEatenClass);
                                    if (Math.random() <= ((double)currentProbability)/100) {
                                        //need work under eat logic of animals
                                        animal.eat(canBeEaten);
                                    }

                                }
                            }
                            //this if case made for Herbivores which can eat other animals
                            //for Boar, Mouse, Duck
                            if (!animal.isPredator() && currentLoc.getPlants().getCurrentCount() != 0) {
                                //also  need work under eat logic of plants
                                animal.eat(currentLoc.getPlants());
                            }
                            //this if case made especially ONLY FOR HERBIVORE 100% animals.
                        } else if (currentLoc.getPlants().getCurrentCount() != 0){
                            animal.eat(currentLoc.getPlants());
                        }

                    }
                }
            }
        }
    }

    private HashMap<Class<? extends Entity>, Integer> getCurrentProbabilityMap(Class<? extends Entity> eater) {
        HashMap<Class<? extends Entity>, Integer> current = hashMap.get(eater);
        return current;
    }

    private boolean checkIfContains(HashMap<Class<? extends Entity>, Integer> mapOfEaten, Class<? extends Entity> eaten) {
        return mapOfEaten.containsKey(eaten);
    }

    private void getEatenProbability(HashMap<Class<? extends Entity>, Integer> mapOfEaten, Class<? extends Entity> eaten) {
        currentProbability = mapOfEaten.get(eaten);
    }


}
