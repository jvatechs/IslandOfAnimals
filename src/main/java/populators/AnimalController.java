package populators;

import entities.Animal;
import entities.Entity;
import entities.Plants;
import island.Location;
import readers.DeserializeProbability;

import java.util.*;
import java.util.concurrent.CopyOnWriteArrayList;

public class AnimalController extends ControllerCommon {
    private int currentProbability;
    private static final HashMap<Class<? extends Animal>, HashMap<Class<? extends Entity>, Integer>> hashMap =
            new DeserializeProbability().getAllProbabilitiesMap();



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

    }
    public void moveControl_ver3() {
        for (Location[] location : locations) {
            for (Location currentLoc : location) {
                HashMap<Class<? extends Animal>, ArrayList<Animal>> animalListPerNameMap = currentLoc.getAnimalListPerNameMap();

                if (!animalListPerNameMap.isEmpty()) {
                    ArrayList<Map.Entry<Class<? extends Animal>, ArrayList<Animal>>> entries
                            = new ArrayList<>(animalListPerNameMap.entrySet());

                    for (Map.Entry<Class<? extends Animal>, ArrayList<Animal>> entry : entries) {
                        for (Animal a : entry.getValue()) {
                            a.setIsMoved(false);
                        }

                        Class<? extends Animal> animal = entry.getKey();
                        CopyOnWriteArrayList<Animal> list = new CopyOnWriteArrayList<>(entry.getValue());


                        Location newLocation = null;

                        for (Animal animalInList : list) {
                            int step = animalInList.getStep();

//                            System.out.println(animalInList.getIsMoved());
                            if (!animalInList.getIsMoved()) {
                                    newLocation = animalInList.move(currentLoc, step);
                                    HashMap<Class<? extends Animal>, ArrayList<Animal>> newAnimalListPerNameMap
                                            = newLocation.getAnimalListPerNameMap();

                                    // add to new location
                                    ArrayList<Animal> animalArrayList;
                                    if (!newAnimalListPerNameMap.containsKey(animal)) {
                                        animalArrayList = new ArrayList<>();
                                    } else {
                                        animalArrayList = newAnimalListPerNameMap.get(animal);
                                    }
//                                    animalInList.setIsMoved(true);
                                    animalArrayList.add(animalInList);
                                    newAnimalListPerNameMap.put(animal, animalArrayList);


                                if (!newLocation.equals(currentLoc)) {
                                    list.remove(animalInList);
                                    ArrayList<Animal> animals = new ArrayList<>();
                                    animals.addAll(list);
                                    animalListPerNameMap.put(animal, animals);
                                }


                            }
                            //# TEST TOOLS
//                            System.out.println(animalInList + " NEW: " + newLocation );
//                            System.out.println("Old location contains the animal object? " + currentLoc.getAnimalListPerNameMap().get(animal).contains(animalInList));
//                            assert newLocation != null;
//                            System.out.println("New location contains the animal object? " + newLocation.getAnimalListPerNameMap().get(animal).contains(animalInList));
//                            if (newLocation.equals(currentLoc)) System.out.println("Old and New Locations are EQUAL!");
//                            System.out.println("*".repeat(15));
                        }
                    }
                }
            }
        }
        System.out.println("*".repeat(15) + "ANIMALS MOVED" + "*".repeat(15));
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

    }
    //reset boolean values
    public void resetBooleans() {
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

    public void cleanMapFromEmptyValue() {
        for (Location[] location : locations) {
            for (Location currentLoc : location) {
                currentLoc.getAnimalListPerNameMap().entrySet().removeIf(entry -> entry.getValue().isEmpty());
            }
        }
    }

    @Deprecated
    public void controlEating() {
        for (Location[] location : locations) {
            for (Location currentLoc : location) {
                //need change animal to animalist
                HashMap<Animal, Integer> animalOnTheLocMap = currentLoc.getAnimalOnTheLocMap();
                if (!animalOnTheLocMap.isEmpty()) {
                    animalOnTheLocMap.forEach((animal, count) ->
                    {
                        //getclass of current animal object
                        Class<? extends Entity> currentAnimalClass = animal.getClass();
                        //get probabilities map of the animal
                        HashMap<Class<? extends Entity>, Integer> probabilities = getCurrentProbabilityMap(currentAnimalClass);
                        if (animal.canEatAnimalsOrNot()) {
                            animalOnTheLocMap.forEach((eaten, countEaten) -> {
                                Class<? extends Entity> canBeEatenClass = eaten.getClass();
                                if (checkIfContains(probabilities, canBeEatenClass)) {
                                    getEatenProbability(probabilities, canBeEatenClass);

                                    //if count of the same animals in the same loc more than 1 we should use that
                                    for (int i = 0; i < count; i++) {
                                        if (Math.random() <= ((double) currentProbability) / 100) {
                                            //need work under eat logic of animals
                                            animal.eat(eaten, currentLoc);
                                        }
                                    }

                                }
                            });
                        }

                        //this if case made for Herbivores which can eat other animals
                        //for Boar, Mouse, Duck and for other Herbivores
                        if (animal.getPredator()
                                && currentLoc.getPlants().getCurrentCount() != 0
                                && currentLoc.getPlants() != null) {
                            //also  need work under eat logic of plants
                            animal.eat(currentLoc.getPlants(), currentLoc);
                        }


                        if (animal.getCurrentSatiety() < 0.1*animal.getSatiety()) {
                            //should think about logic of dead
                            //I think that also need to add to dead() Location parameter.
//                            animal.dead();
                        }
                    });
                }
            }
        }
    }
    @Deprecated
    public void controlMoving() {
        int result = random.nextInt(4);

        for (Location[] location : locations) {
            for (Location currentLoc : location) {
                HashMap<Animal, Integer> animalCurrentCount = currentLoc.getAnimalCurrentCount();
                animalCurrentCount.forEach((animal, count) -> {
                    if (animal.getIsMoved()) {
                        for (int i = 0; i < count; i++) {
                            Location movedLoc = animal.move(currentLoc);
                            HashMap<Animal, Integer> animalMovedCountMap = movedLoc.getAnimalCurrentCount();
//                            movedLoc.getAnimalOnTheLocMap().put()
//                            if(!animalCurrentCount.containsKey(animal)) {
//                                animalCurrentCount.put(animal, 1);
//                            } else {
//                                animalCurrentCount.put(animal, animalCurrentCount.get(animal) + 1);
//                            }
                            animal.setIsMoved(true);
                            //add to new loc
                            putInHashMapAnimal(animalMovedCountMap, animal);
                            //delete from old loc
                            if(animalCurrentCount.get(animal) == 1) {
                                animalCurrentCount.remove(animal);
                            } else {
                                animalCurrentCount.put(animal, animalCurrentCount.get(animal) - 1);
                            }
                        }
                    }
                } );
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

    private boolean checkIfContains(HashMap<Class<? extends Entity>, Integer> map, Class<? extends Entity> eaten) {
        return map.containsKey(eaten);
    }

    private void getEatenProbability(HashMap<Class<? extends Entity>, Integer> mapOfEaten, Class<? extends Entity> eaten) {
        currentProbability = mapOfEaten.get(eaten);
    }
}
