package multithreading;

import island.CreateIsland;

public class PrintThread implements Runnable{
    CreateIsland island;

    public PrintThread(CreateIsland island) {
        this.island = island;
    }

    @Override
    public void run() {
        island.printLocations();
        island.printTotalAnimalsCount();
    }
}
