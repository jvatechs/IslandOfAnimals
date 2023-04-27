package service.populators_and_controllers;

import entities.Animal;
import island.CreateIsland;
import island.Location;

import java.lang.reflect.InvocationTargetException;
import java.util.HashMap;
import java.util.Random;

public abstract class ControllerCommon {
    protected Random random = new Random();
    protected Location[][] locations = CreateIsland.island;


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
