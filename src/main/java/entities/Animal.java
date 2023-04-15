package entities;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonPropertyOrder;
import com.fasterxml.jackson.annotation.JsonTypeInfo;
import island.Location;

import java.math.BigDecimal;
import java.util.List;

@JsonIgnoreProperties({"location", "animals"})
@JsonTypeInfo(use = JsonTypeInfo.Id.CLASS, include = JsonTypeInfo.As.PROPERTY, property = "@class")
@JsonPropertyOrder({"@class", "maxWeight", "maxCount", "step", "satiety"})

public abstract class Animal extends Entity{
    protected double maxWeight;
    protected int maxCount;
    protected int step;
    protected int satiety;



    protected Location location;
    private List<Animal> animals;

    protected boolean isPredator = false;

//    public Animal() {
//        this.typeName = this.getClass().getName();
//    }



    public abstract void eat(Object o);


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

    public int getSatiety() {
        return satiety;
    }

    public void setSatiety(int satiety) {
        this.satiety = satiety;
    }
//
//    public String getTypeName() {
//        return typeName;
//    }
//
//    public void setTypeName(String typeName) {
//        this.typeName = typeName;
//    }

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

    @Override
    public String toString() {

//        maxWeight = Math.scalb(maxWeight, 2);
        return String.format("%-11s %s %-6.2f %s %-4d %s %d %s %d", this.getClass().getSimpleName(),
                "maxWeight=" , maxWeight,
                ", maxCount=", maxCount,
                ", step=", step,
                ", satiety=", satiety);


//        return  this.getClass().getSimpleName() +
//                "\t\t\t maxWeight=" + maxWeight +
//                ", maxCount=" + maxCount +
//                ", step=" + step +
//                ", satiety=" + satiety;
    }
}
