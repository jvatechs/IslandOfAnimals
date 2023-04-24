package multithreading;

import populators.AnimalController;
import populators.ControllerCommon;
import populators.PlantPopulator;

public class DayActionsThread extends ControllerCommon implements Runnable {
    PlantPopulator plantPopulator;

    public DayActionsThread(PlantPopulator plantPopulator) {
        this.plantPopulator = plantPopulator;
    }

    @Override
    public void run() {
        AnimalController animalController = new AnimalController();
        animalController.eatControl_ver2();
        try {
            animalController.moveControl_ver3();
        } catch (NoSuchFieldException | ClassNotFoundException e) {
            throw new RuntimeException(e);
        }
        animalController.reproduceControl();
        plantPopulator.plantGrow();
        System.out.println(Thread.currentThread().getName());

    }
}
