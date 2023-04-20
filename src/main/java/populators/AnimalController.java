package populators;

import entities.Animal;
import entities.Entity;
import island.Location;
import readers.DeserializeProbability;

import java.io.IOException;
import java.lang.reflect.InvocationTargetException;
import java.util.ArrayList;
import java.util.HashMap;

public class AnimalController extends ControllerCommon {
    private int currentProbability;
    private static final HashMap<Class<? extends Animal>, HashMap<Class<? extends Entity>, Integer>> hashMap =
            new DeserializeProbability().getAllProbabilitiesMap();

    //working
    public void eatControl() {
        for (Location[] location : locations) {
            for (Location currentLoc : location) {
                HashMap<Class<? extends Animal>, ArrayList<Animal>> animalListPerNameMap = currentLoc.getAnimalListPerNameMap();
                if (!animalListPerNameMap.isEmpty()) {
                    animalListPerNameMap.forEach((animal, animalsList) -> {
                        HashMap<Class<? extends Entity>, Integer> probabilities = getCurrentProbabilityMap(animal);

                        for (Animal animalObject : animalsList) {
                            if (animalObject.canEatAnimalsOrNot()) {
                                probabilities.forEach((eaten, probability) -> {
                                    if (animalListPerNameMap.containsKey(eaten)) {
                                        if (Math.random() <= ((double) currentProbability) / 100) {
                                            //need work under eat logic of animals
                                            animalObject.eat(animalListPerNameMap.get(eaten).get(0), currentLoc);
                                        }
                                    }
                                });

                            }
                            //this if case made for Herbivores which can eat other animals
                            //for Boar, Mouse, Duck and for other Herbivores
                            if (animalObject.getPredator()
                                    && currentLoc.getPlants().getCurrentCount() != 0
                                    && currentLoc.getPlants() != null) {
                                //also  need work under eat logic of plants
                                animalObject.eat(currentLoc.getPlants(), currentLoc);
                            }

                            //if not eat decrease current satiety
                            if (animalObject.getHungry()) {
                                animalObject.setCurrentSatiety(animalObject.getCurrentSatiety() * 0.5);
                            }

                            if (animalObject.getCurrentSatiety() < 0.1*animalObject.getSatiety()) {
                                //should think about logic of dead
                                //I think that also need to add to dead() Location parameter.
                                animalObject.dead(animalObject, currentLoc);
                            }
                        }
                    });

                }

            }

        }

    }
    //do after eating
    public void moveControl() {
        for (Location[] location : locations) {
            for (Location currentLoc : location) {
                HashMap<Class<? extends Animal>, ArrayList<Animal>> animalListPerNameMap =
                        currentLoc.getAnimalListPerNameMap();


                if (!animalListPerNameMap.isEmpty()) {
                    animalListPerNameMap.forEach((animal, list) -> {
                        for (Animal animalInList : list) {
                            int step = animalInList.getStep();
                            if (animalInList.getMoved()) {
                                for (int i = 0; i < step; i++) {
                                    Location newLocation = animalInList.move(currentLoc);

                                    HashMap<Class<? extends Animal>, ArrayList<Animal>> newAnimalListPerNameMap
                                            = newLocation.getAnimalListPerNameMap();

                                    //add to new loc
                                    ArrayList<Animal> animalArrayList;
                                    if(!newAnimalListPerNameMap.containsKey(animal)) {
                                        animalArrayList = new ArrayList<>();
                                    } else {
                                        animalArrayList = newAnimalListPerNameMap.get(animal);
                                    }
                                    animalInList.setMoved(true);
                                    animalArrayList.add(animalInList);
                                    newAnimalListPerNameMap.put(animal, animalArrayList);

                                    //delete from old loc
                                    if (list.size() - 1 == 0) {
                                        animalListPerNameMap.remove(animal);
                                    } else {
                                        list.remove(animalInList);
                                    }
                                }
                            }
                        }
                    });
                }
            }
        }

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
                                } catch (ReflectiveOperationException e) {
                                    System.err.println(e.getMessage());
                                }
                            }
                        }
                    });
                }
            }
        }
    }
    //reset boolean values
    public void resetBooleans() {
        for (Location[] location : locations) {
            for (Location currentLoc : location) {
                HashMap<Class<? extends Animal>, ArrayList<Animal>> animalListPerNameMap = currentLoc.getAnimalListPerNameMap();
                if (!animalListPerNameMap.isEmpty()) {
                    animalListPerNameMap.forEach((animal, animalsList) -> {

                        for (Animal animalObject : animalsList) {
                            animalObject.setHungry(true);
                            animalObject.setMoved(false);
                        }
                    });

                }

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
                    if (animal.getMoved()) {
                        for (int i = 0; i < count; i++) {
                            Location movedLoc = animal.move(currentLoc);
                            HashMap<Animal, Integer> animalMovedCountMap = movedLoc.getAnimalCurrentCount();
//                            movedLoc.getAnimalOnTheLocMap().put()
//                            if(!animalCurrentCount.containsKey(animal)) {
//                                animalCurrentCount.put(animal, 1);
//                            } else {
//                                animalCurrentCount.put(animal, animalCurrentCount.get(animal) + 1);
//                            }
                            animal.setMoved(true);
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




    private void checkIfContains(Class<? extends Entity> eaten, int percentage) {

    }

    private HashMap<Class<? extends Entity>, Integer> getCurrentProbabilityMap(Class<? extends Entity> eater) {
        return hashMap.get(eater);
    }

    private boolean checkIfContains(HashMap<Class<? extends Entity>, Integer> map, Class<? extends Entity> eaten) {
        return map.containsKey(eaten);
    }

    private void getEatenProbability(HashMap<Class<? extends Entity>, Integer> mapOfEaten, Class<? extends Entity> eaten) {
        currentProbability = mapOfEaten.get(eaten);
    }


}
