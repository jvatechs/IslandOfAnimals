package service.populators_and_controllers;

import animals.Herbivores.*;
import animals.Predators.*;
import entities.Animal;

import java.util.List;

public class AnimalsListCreator {
    private static List<? extends Animal> animals;

    public AnimalsListCreator() {
        createAnimalList();
    }

    public void createAnimalList() {
        //predators
        Wolf wolf = new Wolf();
        Fox fox = new Fox();
        Eagle eagle = new Eagle();
        Boa boa = new Boa();
        Bear bear = new Bear();
        //herbivores
        Boar boar = new Boar();
        Buffalo buffalo = new Buffalo();
        Caterpillar caterpillar = new Caterpillar();
        Deer deer = new Deer();
        Duck duck = new Duck();
        Goat goat = new Goat();
        Horse horse = new Horse();
        Mouse mouse = new Mouse();
        Rabbit rabbit = new Rabbit();
        Sheep sheep = new Sheep();

        animals = List.of(wolf, fox, eagle, boa, bear,
                boar, buffalo, caterpillar, deer, duck, goat, horse, mouse, rabbit, sheep);

//        animalJsonList = List.of(wolf, fox, eagle, boa, bear);

    }

    public List<? extends Animal> getAnimals() {
        return animals;
    }
}
