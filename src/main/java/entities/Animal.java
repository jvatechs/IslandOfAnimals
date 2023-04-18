package entities;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonPropertyOrder;
import com.fasterxml.jackson.annotation.JsonTypeInfo;
import island.Location;

import java.math.BigDecimal;
import java.util.HashMap;
import java.util.List;

@JsonIgnoreProperties({"location", "animals", "currentCount", "animalOnTheLocation", "currentSatiety"})
@JsonTypeInfo(use = JsonTypeInfo.Id.CLASS, include = JsonTypeInfo.As.PROPERTY, property = "@class")
@JsonPropertyOrder({"@class", "maxWeight", "maxCount", "step", "satiety"})

public abstract class Animal extends Entity{
    protected double maxWeight;
    protected int maxCount;
    protected int step;
    protected double satiety;



    protected Location location;
    private List<Animal> animals;
    protected int currentCount;
    protected int animalOnTheLocation;
    protected double currentSatiety;
    protected boolean isHungry;

    protected boolean isPredator = false;

//    public Animal() {
//        this.typeName = this.getClass().getName();
//    }



    public void eat(Entity eaten, Location currentLocation) {

        //if animal
        if (eaten instanceof Animal) {
            double currentWeight = ((Animal) eaten).getMaxWeight();
//            int current = ((Animal) eaten).location.getAnimalCurrentCount().get(eaten);
            HashMap<Animal, Integer> animalOnTheLocMap = location.getAnimalOnTheLocMap();
            int countOfTheEaten = animalOnTheLocMap.get(eaten);

            //remove animal from the location if was eaten
            if (countOfTheEaten - 1 == 0) {
                animalOnTheLocMap.remove(eaten);
                //or decrease the animal quantity if was more than 1
            } else {
                animalOnTheLocMap.put((Animal) eaten, countOfTheEaten - 1);
            }

            if (this.satiety < currentWeight) {
                this.currentSatiety = this.satiety;
            } else {
                this.currentSatiety = currentWeight;
            }
            this.isHungry = false;

        }
        //if plants
        else {
            if (eaten instanceof Plants) {
                Plants currentPlants = location.getPlants();
                while (this.currentSatiety <= this.satiety && currentPlants.getCurrentCount() != 0) {
                    int plantCount = currentPlants.getCurrentCount();
                    currentPlants.setCurrentCount(plantCount - 1);
                    this.currentSatiety += currentPlants.getMaxWeight();
                }
            }
        }
    }


    public int reproduce(int currentCount) {
        int afterCount = currentCount;
        if (currentCount + 1 < maxCount) {
            afterCount = currentCount + 1;
        }
        return afterCount;
    }


    public void directionChoose() {

    }

    public void dead() {
        if (satiety <= 0) {
            System.out.println("The animal is dead...");
        }
    }

    public void moveUp() {
        int y = location.getY();
        location.setY(Math.max(y - step, 0));
    }

    public void moveDown() {
        int y = location.getY();
        location.setY(Math.min(y + step, location.getMaxY()));
    }

    public void moveRight() {
        int x = location.getX();
        location.setX(Math.min(x + step, location.getMaxX()));
    }

    public void moveLeft() {
        int x = location.getX();
        location.setX(Math.max(x - step, 0));
    }

//    protected abstract void isPredatorOrHerbivore();


    public boolean isPredator() {
        return isPredator;
    }

    public void setPredator(boolean predator) {
        isPredator = predator;
    }

    public double getMaxWeight() {
        return maxWeight;
    }

    public void setMaxWeight(double maxWeight) {
        this.maxWeight = maxWeight;
    }

    public int getMaxCount() {
        return maxCount;
    }

    public void setMaxCount(int maxCount) {
        this.maxCount = maxCount;
    }

    public int getStep() {
        return step;
    }

    public void setStep(int step) {
        this.step = step;
    }

    public double getSatiety() {
        return satiety;
    }

    public void setSatiety(double satiety) {
        this.satiety = satiety;
    }

    public int getCurrentCount() {
        return currentCount;
    }

    public void setCurrentCount(int currentCount) {
        this.currentCount = currentCount;
    }

    public int getAnimalOnTheLocation() {
        return animalOnTheLocation;
    }

    public void setAnimalOnTheLocation(int animalOnTheLocation) {
        this.animalOnTheLocation = animalOnTheLocation;
    }



    public Location getLocation() {
        return location;
    }

    public void setLocation(Location location) {
        this.location = location;
    }

    public List<Animal> getAnimals() {
        return animals;
    }

    public void setAnimals(List<Animal> animals) {
        this.animals = animals;
    }

    public double getCurrentSatiety() {
        return currentSatiety;
    }

    public void setCurrentSatiety(double currentSatiety) {
        this.currentSatiety = currentSatiety;
    }

    @Override
    public String toString() {

//        maxWeight = Math.scalb(maxWeight, 2);
//        return String.format("%-11s %s %-6.2f %s %-4d  %s %-4d %s %d %s %-6.2f", this.getClass().getSimpleName(),
        return this.getClass().getSimpleName();
//                "maxWeight=" , maxWeight,
//                ", maxCount=", maxCount,
//                ", animalOnTheLocation = ", animalOnTheLocation
//                , ", step=", step,
//                ", satiety=", satiety
//        );



    }
}
