package multithreading;

import island.CreateIsland;

import java.util.concurrent.atomic.AtomicInteger;

public class PrintThread implements Runnable{
    private static AtomicInteger day = new AtomicInteger(1);
    CreateIsland island;

    public PrintThread(CreateIsland island) {
        this.island = island;
    }

    @Override
    public void run() {
//        island.printLocations();
        System.out.println("-".repeat(15) + "Day:\t" + day.getAndIncrement() + "-".repeat(15));
        island.printTotalAnimalsCount();
        island.printTotalPlantsCount();
    }

}
