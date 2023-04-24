package populators;

import entities.Animal;
import entities.Entity;
import entities.Plants;
import island.Location;
import readers.DeserializeProbability;

import java.util.*;
import java.util.concurrent.CopyOnWriteArrayList;

public class AnimalController extends ControllerCommon {

    protected static final HashMap<Class<? extends Animal>, HashMap<Class<? extends Entity>, Integer>> hashMap =
            new DeserializeProbability().getAllProbabilitiesMap();
    protected int currentProbability;

    public void eatControl_ver2() {
        for (Location[] location : locations) {
            for (Location currentLoc : location) {
                HashMap<Class<? extends Animal>, ArrayList<Animal>> animalListPerNameMap = currentLoc.getAnimalListPerNameMap();
                if (!animalListPerNameMap.isEmpty()) {
                    List<Map.Entry<Class<? extends Animal>, ArrayList<Animal>>> entries = new ArrayList<>(animalListPerNameMap.entrySet());

                    for (Map.Entry<Class<? extends Animal>, ArrayList<Animal>> entry : entries) {



                        Class<? extends Animal> animalClass = entry.getKey();
                        ArrayList<Animal> animalsList = entry.getValue();

                        ArrayList<Animal> copyAnimalsList = new ArrayList<>(animalsList);


                        HashMap<Class<? extends Entity>, Integer> probabilities = getCurrentProbabilityMap(animalClass);
                        for (Animal animalObject : copyAnimalsList) {
//                            System.out.println(animalObject);
                            if (animalObject.canEatAnimalsOrNot()) {
                                probabilities.forEach((eaten, probability) -> {
                                    ArrayList<Animal> eatenList = animalListPerNameMap.get(eaten);

                                    if (animalListPerNameMap.containsKey(eaten) && eatenList.size() != 0) {
                                        currentProbability = probabilities.get(eaten);
                                        if (Math.random() <= ((double) currentProbability) / 100) {
                                            double currentWeight = animalListPerNameMap.get(eaten).get(0).getMaxWeight();
//                                            int countOfTheEaten = eatenList.size();

                                            eatenList.remove(0);
                                            animalListPerNameMap.put((Class<? extends Animal>) eaten, eatenList);

                                            if (animalObject.getSatiety() < currentWeight) {
                                                animalObject.setCurrentSatiety(animalObject.getSatiety());
                                            } else {
                                                animalObject.setCurrentSatiety(currentWeight);
                                            }
                                        }
                                    }
                                });
                            }

                            if (animalObject.getPredator() && currentLoc.getPlants() != null && currentLoc.getPlants().getCurrentCount() != 0 ) {
                                Plants currentPlants = currentLoc.getPlants();
                                while (animalObject.getCurrentSatiety() <= animalObject.getSatiety() && currentPlants.getCurrentCount() != 0) {
                                    int plantCount = currentPlants.getCurrentCount();
                                    currentPlants.setCurrentCount(plantCount - 1);
                                    animalObject.setCurrentSatiety(animalObject.getCurrentSatiety() + currentPlants.getMaxWeight());
                                }
                            }

                            if (animalObject.getIsHungry()) {
                                animalObject.setCurrentSatiety(animalObject.getCurrentSatiety() * 0.5);
                            }

                            if (animalObject.getCurrentSatiety() < 0.1*animalObject.getSatiety()) {
                                animalObject.dead(animalObject, currentLoc);
                            }
                        }

                        animalListPerNameMap.put(animalClass, copyAnimalsList);
                    }
                }
            }
        }
        System.out.println("*".repeat(15) + "ANIMALS ATE" + "*".repeat(15));
        cleanMapFromEmptyValue();
    }
    public void moveControl_ver3() throws NoSuchFieldException, ClassNotFoundException {
        for (Location[] location : locations) {
            for (Location currentLoc : location) {
                HashMap<Class<? extends Animal>, ArrayList<Animal>> animalListPerNameMap = currentLoc.getAnimalListPerNameMap();

                if (!animalListPerNameMap.isEmpty()) {
                    ArrayList<Map.Entry<Class<? extends Animal>, ArrayList<Animal>>> entries
                            = new ArrayList<>(animalListPerNameMap.entrySet());

                    for (Map.Entry<Class<? extends Animal>, ArrayList<Animal>> entry : entries) {

                        Class<? extends Animal> animalClass = entry.getKey();

                        // if caterpillar - skip it cuz step = 0
                        Class caterpillar = Class.forName("animals.Herbivores.Caterpillar");
                        if (animalClass == caterpillar) {
                            continue;
                        }




                        CopyOnWriteArrayList<Animal> list = new CopyOnWriteArrayList<>(entry.getValue());

//                        for (Animal a : entry.getValue()) {
//                            a.setIsMoved(false);
//                        }

                        Location newLocation = null;

//                        if (list.get(0).getStep() == 0) {
//                            continue;
//                        }

                        for (Animal animalInList : list) {
//                            animalInList.setIsMoved(false);

                            int step = animalInList.getStep();

                            //# TEST TOOLS
//                            System.out.println(animalInList + " OLD: " + currentLoc );
//                            System.out.println("Is the animal moved? " + animalInList.getIsMoved());

                            if (!animalInList.getIsMoved()) {
                                    newLocation = animalInList.move(currentLoc, step);
                                    HashMap<Class<? extends Animal>, ArrayList<Animal>> newAnimalListPerNameMap
                                            = newLocation.getAnimalListPerNameMap();

                                    // add to new location
//                                    ArrayList<Animal> animalArrayList;
//                                    if (!newAnimalListPerNameMap.containsKey(animalClass)) {
//                                        animalArrayList = new ArrayList<>();
//                                    } else {
//                                        animalArrayList = newAnimalListPerNameMap.get(animalClass);
//                                    }
//                                    animalArrayList.add(animalInList);
//                                    System.out.println("Added animals list to new Location: " + animalArrayList);
//                                    newAnimalListPerNameMap.put(animalClass, animalArrayList);

//
//                                animalArrayList.clear();
//                                animalArrayList.addAll(entry.getValue());
//                                newAnimalListPerNameMap.put(animalClass, animalArrayList);



                                if (!newLocation.equals(currentLoc)) {

                                    // add to new location
                                    ArrayList<Animal> animalArrayList;
                                    if (!newAnimalListPerNameMap.containsKey(animalClass)) {
                                        animalArrayList = new ArrayList<>();
                                    } else {
                                        animalArrayList = newAnimalListPerNameMap.get(animalClass);
                                    }
                                    animalArrayList.add(animalInList);
//                                    System.out.println("Added animals list to new Location: " + animalArrayList);
                                    newAnimalListPerNameMap.put(animalClass, animalArrayList);


                                    //removing from the old location
                                    list.remove(animalInList);
                                    ArrayList<Animal> animals = new ArrayList<>();
                                    animals.addAll(list);
                                    animalListPerNameMap.put(animalClass, animals);
                                }


                            }
                            //# TEST TOOLS
//                            System.out.println(animalInList + " NEW: " + newLocation );
//                            System.out.println();
//                            System.out.println("Old location contains the animalClass object? " + currentLoc.getAnimalListPerNameMap().get(animalClass).contains(animalInList));
//                            assert newLocation != null;
//                            System.out.println("New location contains the animalClass object? " + newLocation.getAnimalListPerNameMap().get(animalClass).contains(animalInList));
//                            if (newLocation.equals(currentLoc)) System.out.println("Old and New Locations are EQUAL!");
//                            System.out.println("*".repeat(15));
                        }
                    }
                }
            }
        }
        System.out.println("*".repeat(15) + "ANIMALS MOVED" + "*".repeat(15));
        cleanMapFromEmptyValue();
    }
    //reproduce do after all
    public void reproduceControl() {
        for (Location[] location : locations) {
            for (Location currentLoc : location) {
                HashMap<Class<? extends Animal>, ArrayList<Animal>> animalListPerNameMap =
                        currentLoc.getAnimalListPerNameMap();

                if (!animalListPerNameMap.isEmpty()) {
                    animalListPerNameMap.forEach((animalClass, list) -> {
                        //if total <= maxCount : add all of news' to list
                        // if total > maxCount : add maxCount - current = count of news to list
                        // use animal new instance for new obj
                        if (list.size() > 1) {
                            int countOfBabies = list.size() / 2;
                            if (countOfBabies + list.size() > list.get(0).getMaxCount()) {
                                countOfBabies = list.get(0).getMaxCount() - list.size();
                            }
                            for (int i = 0; i < countOfBabies; i++) {
                                try {
                                    Animal newAnimal = newAnimalObjectCreate(list.get(0), animalClass);
//                                    newAnimal.setID(getMaxId());
//                                    System.out.println(newAnimal);
                                    list.add(newAnimal);
                                } catch (ReflectiveOperationException e) {
                                    System.err.println(e.getMessage());
                                }
                            }
                            animalListPerNameMap.put(animalClass, list);
                        }
                    });
                }
            }
        }
        System.out.println("*".repeat(15) + "ANIMALS REPRODUCED" + "*".repeat(15));
        resetBooleans();
    }
    //reset boolean values
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


    private HashMap<Class<? extends Entity>, Integer> getCurrentProbabilityMap(Class<? extends Entity> eater) {
//        System.out.println("Classname" + eater.getTypeName());
//        System.out.println("Class: " + eater);
//        System.out.println("HashMap: " + hashMap.get(eater));
//        System.out.println("*".repeat(15));
        return hashMap.get(eater);
    }



}
