package entities;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonPropertyOrder;
import com.fasterxml.jackson.annotation.JsonTypeInfo;
import island.Location;
import lombok.Getter;
import lombok.Setter;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Random;
import java.util.concurrent.atomic.AtomicInteger;

import static java.lang.Math.max;

@JsonIgnoreProperties({"location", "animals", "currentCount", "animalOnTheLocation", "currentSatiety"
                        , "isHungry", "isMoved"})
@JsonTypeInfo(use = JsonTypeInfo.Id.CLASS, include = JsonTypeInfo.As.PROPERTY, property = "@class")
@JsonPropertyOrder({"@class", "maxWeight", "maxCount", "step", "satiety"})
@Getter
@Setter
public abstract class Animal extends Entity {
    // main fields of every animal
    protected double maxWeight;
    protected int maxCount;
    protected int step;
    protected double satiety;

    protected int currentCount;
    protected double currentSatiety;

    // #ATOMIC Integer block with incrementAndGet method in constructor
    protected Integer uniqueID;
    protected static AtomicInteger idGenerator = new AtomicInteger(1);

    protected boolean isPredator = false;
    protected boolean isMoved = false;
    protected boolean isHungry = true;
    protected String simpleName = this.getClass().getSimpleName();


    public Animal() {
        this.uniqueID = idGenerator.incrementAndGet();
    }

    // #DEAD BLOCK
    public void dead(Animal animal, Location currentLocation) {
        HashMap<Class<? extends Animal>, ArrayList<Animal>> animalListPerNameMap
                = currentLocation.getAnimalListPerNameMap();
        ArrayList<Animal> animalList = animalListPerNameMap.get(animal.getClass());

        if (animalList.size() - 1 == 0) {
            animalListPerNameMap.remove(animal.getClass());
        } else {
            animalList.remove(animal);
            animalListPerNameMap.put(animal.getClass(), animalList);
        }
    }


    // #MOVE BLOCK
    public Location moveUp(Location currentLocation) {
        int y = currentLocation.getY();
        return locations[currentLocation.getX()][Math.max(y - 1, 0)];
    }

    public Location moveDown(Location currentLocation) {
        int y = currentLocation.getY();
        return locations[currentLocation.getX()][Math.min(y + 1, currentLocation.getMaxY())];
    }

    public Location moveRight(Location currentLocation) {
        int x = currentLocation.getX();
        return locations[Math.min(x + 1, currentLocation.getMaxX())][currentLocation.getY()];
    }

    public Location moveLeft(Location currentLocation) {
        int x = currentLocation.getX();
        return locations[Math.max(x - 1, 0)][currentLocation.getY()];
    }

    //the method is gag and will be deleted

    public Location move(Location currentLocation, int step) {
        Random random = new Random();

        if (step == 0) {
            return null;
        }

        Location newLocation = null;

        this.isMoved = false;
        for (int i = 0; i < step; i++) {
            int result = random.nextInt(4);

            newLocation =  switch (result) {
                case 0 -> moveUp(currentLocation);
                case 1 -> moveDown(currentLocation);
                case 2 -> moveLeft(currentLocation);
                case 3 -> moveRight(currentLocation);
                default -> throw new IllegalStateException("Unexpected value: " + result);
            };
            currentLocation = newLocation;
        }
        this.isMoved = true;

        return newLocation;

    }


    //GETTERS AND SETTERS

    public boolean getPredator() {
        return isPredator;
    }


    public int getCurrentCount() {
        return currentCount;
    }

    public void setCurrentCount(int currentCount) {
        this.currentCount = currentCount;
    }

    public double getCurrentSatiety() {
        return currentSatiety;
    }

    public void setCurrentSatiety(double currentSatiety) {
        this.currentSatiety = currentSatiety;
    }

    public boolean getIsMoved() {
        return isMoved;
    }

    public void setIsMoved(boolean moved) {
        isMoved = moved;
    }


    public boolean getIsHungry() {
        return isHungry;
    }

    public void setIsHungry(boolean hungry) {
        isHungry = hungry;
    }

    @Override
    public String toString() {

//        maxWeight = Math.scalb(maxWeight, 2);
//        return String.format("%-11s %s %-6.2f %s %-4d  %s %-4d %s %d %s %-6.2f", this.getClass().getSimpleName(),
        return this.getClass().getSimpleName()
                + " id: " + this.uniqueID ;
//                "maxWeight=" , maxWeight,
//                ", maxCount=" + maxCount
//                ", animalOnTheLocation = ", animalOnTheLocation
//                + ", step=" + step;
//                ", satiety=", satiety
//        );
    }

}
