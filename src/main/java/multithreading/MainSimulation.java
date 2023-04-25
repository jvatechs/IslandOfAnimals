package multithreading;

import island.CreateIsland;
import service.populators_and_controllers.AnimalPopulator;
import service.populators_and_controllers.PlantPopulator;

import java.io.IOException;
import java.lang.reflect.InvocationTargetException;
import java.util.concurrent.*;

public class MainSimulation {
    public static void main(String[] args) throws InvocationTargetException, NoSuchMethodException, InstantiationException, IllegalAccessException, IOException, InterruptedException {
        CreateIsland island = new CreateIsland(100, 20);
        PrintThread printThread = new PrintThread(island);
        int numberOfDays = 7;
        ExecutorService executorService = Executors.newSingleThreadExecutor();

        executorService.submit(new DeserializeThread());

        AnimalPopulator animalPopulator = new AnimalPopulator();
        PlantPopulator plantPopulator = new PlantPopulator();

        executorService.submit(new IslandSetterThread(animalPopulator, plantPopulator));
        executorService.submit(printThread);

        // the last two threads we need do depends by how much days we want see changes

        for (int i = 0; i < numberOfDays; i++) {

            Thread.sleep(3000);
            executorService.submit(new DayActionsThread(plantPopulator));
            executorService.submit(printThread);
        }
        executorService.shutdown();
    }
}
