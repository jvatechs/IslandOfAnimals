package multithreading;

import island.CreateIsland;
import populators.AnimalPopulator;
import populators.PlantPopulator;

import java.io.IOException;
import java.lang.reflect.InvocationTargetException;
import java.util.concurrent.*;

public class MainSimulation {
    public static void main(String[] args) throws InvocationTargetException, NoSuchMethodException, InstantiationException, IllegalAccessException, IOException, InterruptedException {
        CreateIsland island = new CreateIsland(10, 2);
        PrintThread printThread = new PrintThread(island);
        int numberOfDays = 7;

        ExecutorService executorService = Executors.newSingleThreadExecutor();

        // TODO:
        //deserialize THREAD
        executorService.submit(new DeserializeThread());

        AnimalPopulator animalPopulator = new AnimalPopulator();
        PlantPopulator plantPopulator = new PlantPopulator();

        // TODO:
        //set island by animals and plants THREAD
        executorService.submit(new IslandSetterThread(animalPopulator, plantPopulator));

        // TODO:
        //print all info THREAD
        executorService.submit(printThread);

        // the last two threads we need do depends by how much days we want see changes

        for (int i = 0; i < numberOfDays; i++) {
            // TODO:
            //everyday Actions THREAD
            Thread.sleep(2000);
            executorService.submit(new DayActionsThread(plantPopulator));

            // TODO:
            //print all info THREAD
            executorService.submit(printThread);
        }

        executorService.shutdown();
    }
}
