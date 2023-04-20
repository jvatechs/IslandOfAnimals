package populators;

import entities.Animal;
import island.CreateIsland;
import island.Location;

import java.lang.reflect.InvocationTargetException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public class AnimalPopulator extends ControllerCommon {
    private final static int MAX_X = CreateIsland.lengthOfIsland;
    private final static int MAX_Y = CreateIsland.widthOfIsland;

//    public AnimalPopulator() {
//    }

    //random to island
    public void setAnimals(List<? extends Animal> listOfExamples) throws NoSuchMethodException, InvocationTargetException, InstantiationException, IllegalAccessException {
        for (Animal animal : listOfExamples) {
            Class<? extends Animal> currentClass = animal.getClass();


            //current count setting
            animal.setCurrentCount(random.nextInt(1,animal.getMaxCount() + 1));
            int currentCount = animal.getCurrentCount();
            ArrayList<Animal> animalsObjects = new ArrayList<>();
            ArrayList<Animal> trysmth = new ArrayList<>();

//            Animal newAnimal;

            //grabbing all the animal new instances into list
            for (int i = 0; i < currentCount; i++) {
//                newAnimal = currentClass.getDeclaredConstructor().newInstance();
//                newAnimal.setMaxWeight(animal.getMaxWeight());
//                newAnimal.setMaxCount(animal.getMaxCount());
//                newAnimal.setStep(animal.getStep());
//                newAnimal.setSatiety(animal.getSatiety());

                trysmth.add(newAnimalObjectCreate(animal, currentClass));
//                trysmth.add(newAnimal);
//                trysmth.add(animal.clone()); also junk I think
            }

            //rendomly set new instances from the list into locations' lists
            for (Animal animalFromList : trysmth) {
                int randomX = random.nextInt(MAX_X);
                int randomY = random.nextInt(MAX_Y);
                Location location = locations[randomX][randomY];

                location.getAnimals().add(animalFromList);
            }

//junk
//
//            for (int i = 0; i < currentCount; i++) {
//
//                int randomX = random.nextInt(MAX_X);
//                int randomY = random.nextInt(MAX_Y);
//                Location location = locations[randomX][randomY];
////                location.getAnimals().add(animal.clone());
//
//                HashMap<Animal, Integer> animalOnTheLocMap = location.getAnimalOnTheLocMap();
////                if(!animalOnTheLocMap.containsKey(animal)) {
////                    animalOnTheLocMap.put(animal, 1);
////                } else {
////                    animalOnTheLocMap.put(animal, animalOnTheLocMap.get(animal) + 1);
////                }
//                putInHashMapAnimal(animalOnTheLocMap, animal);
//
//            }
        }

        for (Location[] location : locations) {
            for (Location locationValue : location) {
                List<Animal> animals = locationValue.getAnimals();
                HashMap<Class<? extends Animal>, ArrayList<Animal>> animalListPerNameMap = locationValue.getAnimalListPerNameMap();
                for (Animal animal : animals) {
                    ArrayList<Animal> sortedAnimals = new ArrayList<>();

                    if (!animalListPerNameMap.containsKey(animal.getClass())) {
                        sortedAnimals.add(animal);
                    } else {
                        sortedAnimals = animalListPerNameMap.get(animal.getClass());
                        sortedAnimals.add(animal);
                    }
                    animalListPerNameMap.put(animal.getClass(), sortedAnimals);
                }
                locationValue.createMapOfCount();
            }
        }

    }




    //random of animals

}
