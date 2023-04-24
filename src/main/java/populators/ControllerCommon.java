package populators;

import entities.Animal;
import island.CreateIsland;
import island.Location;

import java.lang.reflect.InvocationTargetException;
import java.util.HashMap;
import java.util.Random;

public abstract class ControllerCommon {
    protected Random random = new Random();
    protected Location[][] locations = CreateIsland.island;




    protected void putInHashMapAnimal(HashMap<Animal, Integer> map, Animal animal) {
        if(!map.containsKey(animal)) {
            map.put(animal, 1);
        } else {
            map.put(animal, map.get(animal) + 1);
        }
    }

    protected Animal newAnimalObjectCreate(Animal example, Class<? extends Animal> currentClass) throws NoSuchMethodException, InvocationTargetException, InstantiationException, IllegalAccessException {
        Animal newAnimal = currentClass.getDeclaredConstructor().newInstance();
        newAnimal.setMaxWeight(example.getMaxWeight());
        newAnimal.setMaxCount(example.getMaxCount());
        newAnimal.setStep(example.getStep());
        newAnimal.setSatiety(example.getSatiety());
        return newAnimal;
    }

//    protected HashMap<Class<? extends Entity>, Integer> getCurrentProbabilityMap(Class<? extends Entity> eater) {
////        System.out.println("Classname" + eater.getTypeName());
////        System.out.println("Class: " + eater);
////        System.out.println("HashMap: " + hashMap.get(eater));
////        System.out.println("*".repeat(15));
//        return hashMap.get(eater);
//    }
}
