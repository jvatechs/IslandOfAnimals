package readers;

import entities.Animal;
import populators.AnimalsListCreator;

import java.util.List;

public class JsonFiller {
    public JsonFiller() {
        getListAndFill();
    }

    private void getListAndFill() {
        AnimalsListCreator listCreator = new AnimalsListCreator();
        List<? extends Animal> animals = listCreator.getAnimals();
        for (Animal animal : animals) {
//            new CreateOwnJson().createJson(animal);
        }
    }
}
