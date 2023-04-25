package multithreading;

import service.populators_and_controllers.AnimalController;
import service.populators_and_controllers.ControllerCommon;
import service.populators_and_controllers.PlantPopulator;

public class DayActionsThread extends ControllerCommon implements Runnable {
    PlantPopulator plantPopulator;

    public DayActionsThread(PlantPopulator plantPopulator) {
        this.plantPopulator = plantPopulator;
    }

    @Override
    public void run() {
        AnimalController animalController = new AnimalController();

        try {
            animalController.eatControl_ver2();
            animalController.moveControl_ver3();
        } catch (NoSuchFieldException | ClassNotFoundException e) {
            throw new RuntimeException(e);
        }
        animalController.reproduceControl();
        plantPopulator.plantGrow();
    }
}
