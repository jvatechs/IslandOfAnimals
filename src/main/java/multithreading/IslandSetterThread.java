package multithreading;

import populators.AnimalPopulator;
import populators.PlantPopulator;

import java.lang.reflect.InvocationTargetException;

public class IslandSetterThread implements Runnable {
    AnimalPopulator animalPopulator;
    PlantPopulator plantPopulator;

    public IslandSetterThread(AnimalPopulator animalPopulator, PlantPopulator plantPopulator) {
        this.animalPopulator = animalPopulator;
        this.plantPopulator = plantPopulator;
    }

    @Override
    public void run() {
        try {
            //populate island randomly by animals
            animalPopulator.setAnimals_ver2();
            plantPopulator.plantRandomize();
        } catch (InvocationTargetException | NoSuchMethodException | InstantiationException |
                 IllegalAccessException e) {
            throw new RuntimeException(e);
        }
    }
}
