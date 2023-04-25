package service.populators_and_controllers;

import entities.Animal;
import entities.Entity;
import entities.Plants;
import island.Location;
import service.json_readers.DeserializeProbability;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.CopyOnWriteArrayList;

public class AnimalController extends ControllerCommon {

    protected static final HashMap<Class<? extends Animal>, HashMap<Class<? extends Entity>, Integer>> ALL_PROBABILITIES_MAP
            = new DeserializeProbability().getAllProbabilitiesMap();

    // THE MAIN METHODS
    public void eatControl_ver2() throws ClassNotFoundException {
        for (Location[] location : locations) {
            for (Location currentLoc : location) {
                HashMap<Class<? extends Animal>, ArrayList<Animal>> animalListPerNameMap = currentLoc.getAnimalListPerNameMap();
                if (animalListPerNameMap.isEmpty()) continue;
                List<Map.Entry<Class<? extends Animal>, ArrayList<Animal>>> entries = new ArrayList<>(animalListPerNameMap.entrySet());

                for (Map.Entry<Class<? extends Animal>, ArrayList<Animal>> entry : entries) {
                    Class<? extends Animal> animalClass = entry.getKey();
                    ArrayList<Animal> animalsList = entry.getValue();
                    //copied for changing animalList when iterating copyAnimalsList
                    ArrayList<Animal> copyAnimalsList = new ArrayList<>(animalsList);

                    HashMap<Class<? extends Animal>, Integer> probabilities = getCurrentProbabilityMap(animalClass);

                    for (Animal animalObject : copyAnimalsList) {

                        if (!animalObject.canEatAnimalsOrNot()) continue;
                        eatAnimal(animalListPerNameMap, probabilities, animalObject);
                        eatPlants(animalObject, currentLoc);
                        decreaseSatiety(animalObject);
                        deadControl(animalObject, currentLoc);
                    }
                    animalListPerNameMap.put(animalClass, copyAnimalsList);
                }
            }
        }
        System.out.println("*".repeat(15) + "ANIMALS ATE" + "*".repeat(15));
        cleanMapFromEmptyValue();
    }

    public void moveControl_ver3() throws NoSuchFieldException, ClassNotFoundException {
        for (Location[] location : locations) {
            for (Location currentLoc : location) {
                HashMap<Class<? extends Animal>, ArrayList<Animal>> currAnimalListPerNameMap = currentLoc.getAnimalListPerNameMap();

                if (currAnimalListPerNameMap.isEmpty()) continue;

                ArrayList<Map.Entry<Class<? extends Animal>, ArrayList<Animal>>> entries = new ArrayList<>(currAnimalListPerNameMap.entrySet());
                for (Map.Entry<Class<? extends Animal>, ArrayList<Animal>> entry : entries) {

                    Class<? extends Animal> animalClass = entry.getKey();

                    // if caterpillar - skip it cuz step = 0
                    if (animalClass == Class.forName("animals.Herbivores.Caterpillar")) continue;

                    CopyOnWriteArrayList<Animal> list = new CopyOnWriteArrayList<>(entry.getValue());
                    Location newLocation = null;

                    for (Animal animalInList : list) {
                        int step = animalInList.getStep();

                        //# TEST TOOLS
//                            System.out.println(animalInList + " OLD: " + currentLoc );
//                            System.out.println("Is the animal moved? " + animalInList.getIsMoved());

                        if (animalInList.getIsMoved()) continue;

                        newLocation = animalInList.move(currentLoc, step);
                        HashMap<Class<? extends Animal>, ArrayList<Animal>> newAnimalListPerNameMap = newLocation.getAnimalListPerNameMap();

                        if (newLocation.equals(currentLoc)) continue;
                        addToNewLocation(newAnimalListPerNameMap, animalClass, animalInList);
                        removeFromOldLocation(list, animalInList, animalClass, currAnimalListPerNameMap);
                        //# TEST TOOLS
//                            System.out.println(animalInList + " NEW: " + newLocation );
//                            System.out.println();
//                            System.out.println("Old location contains the animalClass object? " + currentLoc.getAnimalListPerNameMap().get(animalClass).contains(animalInList));
//                            if (newLocation != null) System.out.println("New location contains the animalClass object? " + newLocation.getAnimalListPerNameMap().get(animalClass).contains(animalInList));
//                            if (Objects.equals(newLocation, currentLoc)) System.out.println("Old and New Locations are EQUAL!");
//                            System.out.println("*".repeat(15));
                    }
                }
            }
        }
        System.out.println("*".repeat(15) + "ANIMALS MOVED" + "*".repeat(15));
        cleanMapFromEmptyValue();
    }

    public void reproduceControl() {
        for (Location[] location : locations) {
            for (Location currentLoc : location) {
                HashMap<Class<? extends Animal>, ArrayList<Animal>> animalListPerNameMap = currentLoc.getAnimalListPerNameMap();

                if (!animalListPerNameMap.isEmpty()) {
                    animalListPerNameMap.forEach((animalClass, list) -> {
                        int sameAnimalCount = list.size();
                        if (sameAnimalCount <= 1) return;

                        int countOfBabies = countFutureBabies(list);
                        animalListPerNameMap.put(animalClass, addnewBabiesToList(list, countOfBabies, animalClass));
                    });
                }
            }
        }
        System.out.println("*".repeat(15) + "ANIMALS REPRODUCED" + "*".repeat(15));
        resetBooleans();
    }

    // METHODS-HELPERS

    //# COMMON HELPERS
    private void resetBooleans() {
        for (Location[] location : locations) {
            for (Location currentLoc : location) {
                HashMap<Class<? extends Animal>, ArrayList<Animal>> animalListPerNameMap = currentLoc.getAnimalListPerNameMap();
                if (!animalListPerNameMap.isEmpty()) {
                    animalListPerNameMap.forEach((animal, animalsList) -> {

                        for (Animal animalObject : animalsList) {
                            animalObject.setIsHungry(true);
                            animalObject.setIsMoved(false);

//                            System.out.println("H" + animalObject.getIsHungry());
//                            System.out.println("M" + animalObject.getIsMoved());
                        }
                    });

                }

            }

        }
        System.out.println("*".repeat(15) + "END OF THE DAY" + "*".repeat(15));
        System.out.println("*".repeat(15) + "MORNING: ANIMALS ARE HUNGRY AND NOT MOVED" + "*".repeat(15));
    }

    private void cleanMapFromEmptyValue() {
        for (Location[] location : locations) {
            for (Location currentLoc : location) {
                currentLoc.getAnimalListPerNameMap().entrySet().removeIf(entry -> entry.getValue().isEmpty());
            }
        }
    }

    //#eatControl Helper methods
    private HashMap<Class<? extends Animal>, Integer> getCurrentProbabilityMap(Class<? extends Entity> eater) throws ClassNotFoundException {
        HashMap<Class<? extends Entity>, Integer> probabilities = ALL_PROBABILITIES_MAP.get(eater);
        probabilities.remove(Class.forName("entities.Plants"));
        HashMap<Class<? extends Animal>, Integer> probabilitiesWoutPlants = new HashMap<>();

        probabilities.entrySet().stream().forEach(entry -> probabilitiesWoutPlants.put((Class<? extends Animal>) entry.getKey(), entry.getValue()));

        return probabilitiesWoutPlants;
    }

    private void eatAnimal(Map<Class<? extends Animal>, ArrayList<Animal>> animalListPerNameMap, Map<Class<? extends Animal>, Integer> probabilities, Animal animalObject) {
        probabilities.forEach((eaten, probability) -> {

            ArrayList<Animal> eatenList = animalListPerNameMap.get(eaten);
            if (animalListPerNameMap.containsKey(eaten) && !eatenList.isEmpty()) {
                double currentProbability = probabilities.get(eaten);
                if (Math.random() <= currentProbability / 100) {
                    Animal eatenAnimal = eatenList.remove(0);
                    double eatenWeight = eatenAnimal.getMaxWeight();
                    animalListPerNameMap.put(eaten, eatenList);
                    animalObject.setCurrentSatiety(Math.min(animalObject.getSatiety(), eatenWeight));
                }
            }
        });
    }

    private void eatPlants(Animal animalObject, Location currentLocation) {
        if (!animalObject.getPredator() && currentLocation.getPlants() != null && currentLocation.getPlants().getCurrentCount() != 0) {
            Plants currentPlants = currentLocation.getPlants();
            while (animalObject.getCurrentSatiety() <= animalObject.getSatiety() && currentPlants.getCurrentCount() != 0) {
                int plantCount = currentPlants.getCurrentCount();
                currentPlants.setCurrentCount(plantCount - 1);
                animalObject.setCurrentSatiety(animalObject.getCurrentSatiety() + currentPlants.getMaxWeight());
            }
        }
    }

    private void decreaseSatiety(Animal animalObject) {
        if (animalObject.getIsHungry()) {
            animalObject.setCurrentSatiety(animalObject.getCurrentSatiety() * 0.5);

        }
    }

    private void deadControl(Animal animalObject, Location currentLocation) {
        if (animalObject.getCurrentSatiety() < 0.1 * animalObject.getSatiety()) {
            animalObject.dead(animalObject, currentLocation);
        }
    }


    // #moveControl Helper methods
    private void addToNewLocation(HashMap<Class<? extends Animal>, ArrayList<Animal>> newAnimalListPerNameMap, Class<? extends Animal> animalClass, Animal animalInList) {
        ArrayList<Animal> animalArrayList;
        if (!newAnimalListPerNameMap.containsKey(animalClass)) {
            animalArrayList = new ArrayList<>();
        } else {
            animalArrayList = newAnimalListPerNameMap.get(animalClass);
        }
        animalArrayList.add(animalInList);
//                                    System.out.println("Added animals list to new Location: " + animalArrayList);
        newAnimalListPerNameMap.put(animalClass, animalArrayList);

    }

    private void removeFromOldLocation(CopyOnWriteArrayList<Animal> list, Animal animalInList, Class<? extends Animal> animalClass, HashMap<Class<? extends Animal>, ArrayList<Animal>> animalListPerNameMap) {
        list.remove(animalInList);
        ArrayList<Animal> animals = new ArrayList<>(list);
        animalListPerNameMap.put(animalClass, animals);
    }

    // #reproduceControl Helper methods
    private int countFutureBabies(ArrayList<Animal> list) {
        int sameAnimalCount = list.size();

        int countOfBabies = sameAnimalCount / 2;
        if (countOfBabies + sameAnimalCount > list.get(0).getMaxCount()) {
            countOfBabies = list.get(0).getMaxCount() - sameAnimalCount;
        }
        return countOfBabies;
    }

    private ArrayList<Animal> addnewBabiesToList(ArrayList<Animal> list, int countOfBabies, Class<? extends Animal> animalClass) {
        for (int i = 0; i < countOfBabies; i++) {
            try {
                Animal newAnimal = newAnimalObjectCreate(list.get(0), animalClass);
                list.add(newAnimal);
            } catch (ReflectiveOperationException e) {
                System.err.println(e.getMessage());
            }
        }
        return list;
    }

}
