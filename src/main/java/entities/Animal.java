package entities;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonPropertyOrder;
import com.fasterxml.jackson.annotation.JsonTypeInfo;
import island.Location;

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

public abstract class Animal extends Entity {
    // main fields of every
    protected double maxWeight;
    protected int maxCount;
    protected int step;
    protected double satiety;



//    protected Location location;
    private List<Animal> animals;
    protected int currentCount;
    protected int animalOnTheLocation;
    protected double currentSatiety;

    // #ATOMIC Integer block with incrementAndGet method in constructor
    protected Integer uniqueID;
    protected static AtomicInteger idGenerator = new AtomicInteger(1);

    // #STATIC ID manager block with increment in constructor
    private static int maxId = 0;
    private Integer id;

    protected boolean isPredator = false;
    private final Random random = new Random();
    //booleans should to set
    //moved after every simulate must be "false"
    //hungry is only eat
    protected boolean isMoved;
    protected boolean isHungry = true;
    protected String simpleName = this.getClass().getSimpleName();



    public Animal() {
        this.uniqueID = idGenerator.incrementAndGet();
        this.id = maxId;
        maxId++;
    }

    //constructor for easy copying
//    public Animal(Animal other) {
//        this.id = maxId;
//        maxId++;
//        this.uniqueID = idGenerator.incrementAndGet();
//
//        this.maxWeight = other.maxWeight;
//        this.maxCount = other.maxCount;
//        this.step = other.step;
//        this.satiety = other.satiety;
//    }


    public void eat(Entity eaten, Location currentLocation) {
        if (eaten instanceof Animal) {
            double currentWeight = ((Animal) eaten).getMaxWeight();
//            int current = ((Animal) eaten).location.getAnimalCurrentCount().get(eaten);
//            HashMap<Animal, Integer> animalOnTheLocMap = currentLocation.getAnimalOnTheLocMap();
            HashMap<Class<? extends Animal>, ArrayList<Animal>> animalListPerNameMap
                    = currentLocation.getAnimalListPerNameMap();

            ArrayList<Animal> eatenList = animalListPerNameMap.get(eaten.getClass());
            int countOfTheEaten = eatenList.size();

//            int countOfTheEaten = animalOnTheLocMap.get(eaten);

            //remove animal from the location if was eaten
            if (countOfTheEaten - 1 == 0) {
//                animalOnTheLocMap.remove(eaten);
                animalListPerNameMap.remove(eaten.getClass());
                //or decrease the animal quantity if was more than 1
            } else {
//                animalOnTheLocMap.put((Animal) eaten, countOfTheEaten - 1);
                eatenList.remove(0);
                animalListPerNameMap.put((Class<? extends Animal>) eaten.getClass(), eatenList);
            }
//            animals.remove(eaten);

            if (this.satiety < currentWeight) {
                this.currentSatiety = this.satiety;
            } else {
                this.currentSatiety = currentWeight;
            }

        }         //if plants
        else if (eaten instanceof Plants) {
                Plants currentPlants = currentLocation.getPlants();
                //stops if plants discontinue or the animal is not hungry
                while (this.currentSatiety <= this.satiety && currentPlants.getCurrentCount() != 0) {
                    int plantCount = currentPlants.getCurrentCount();
                    currentPlants.setCurrentCount(plantCount - 1);
                    this.currentSatiety += currentPlants.getMaxWeight();
                }
            }

        this.isHungry = false;

    }
    @Deprecated
    public void eat2
            (Entity eaten, Location currentLocation) {

        //if animal
        if (eaten instanceof Animal) {
            double weightOfEaten = ((Animal) eaten).getMaxWeight();
//            int current = ((Animal) eaten).location.getAnimalCurrentCount().get(eaten);
            HashMap<Animal, Integer> animalOnTheLocMap = currentLocation.getAnimalOnTheLocMap();
            int countOfTheEaten = animalOnTheLocMap.get(eaten);

            //remove animal from the location if was eaten
            if (countOfTheEaten - 1 == 0) {
                animalOnTheLocMap.remove(eaten);
                //or decrease the animal quantity if was more than 1
            } else {
                animalOnTheLocMap.put((Animal) eaten, countOfTheEaten - 1);
            }
            animals.remove(eaten);

            if (this.satiety < weightOfEaten) {
                this.currentSatiety = this.satiety;
            } else {
                this.currentSatiety = weightOfEaten;
            }
            //need think under do true after every move
            this.isHungry = false;

        }
        //if plants
        else {
            if (eaten instanceof Plants) {
                Plants currentPlants = currentLocation.getPlants();
                //stops if plants discontinue or the animal is not hungry
                while (this.currentSatiety <= this.satiety && currentPlants.getCurrentCount() != 0) {
                    int plantCount = currentPlants.getCurrentCount();
                    currentPlants.setCurrentCount(plantCount - 1);
                    this.currentSatiety += currentPlants.getMaxWeight();
                }
            }
        }
    }

    // #REPRODUCE BLOCK
    public int reproduce(int currentCount, Location currentLocation) {
        int afterCount = currentCount;
        if (currentCount + 1 < maxCount) {
            afterCount = currentCount + 1;
        }
        return afterCount;
    }

    // #DEAD BLOCK
    public void dead(Animal animal, Location currentLocation) {
//        if (currentSatiety <= 0) {
//            System.out.println("The animal is dead...");
//        }
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

    private void deadHelper(HashMap<Class<? extends Animal>, ArrayList<Animal>> map, Animal animal) {
        ArrayList<Animal> animalList = map.get(animal.getClass());
        if (animalList.size() - 1 == 0) {
            map.remove(animal.getClass());
        } else {
            animalList.remove(animal);
            map.put(animal.getClass(), animalList);
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

    public Location move(Location currentLocation) {
        int result = random.nextInt(4);

        return switch (result) {
            case 0 -> moveUp(currentLocation);
            case 1 -> moveDown(currentLocation);
            case 2 -> moveLeft(currentLocation);
            case 3 -> moveRight(currentLocation);
            default -> throw new IllegalStateException("Unexpected value: " + result);
        };
    }


    //GETTERS AND SETTERS

    public boolean getPredator() {
        return isPredator;
    }

//    public void setPredator(boolean predator) {
//        isPredator = predator;
//    }

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

    public boolean getMoved() {
        return isMoved;
    }

    public void setMoved(boolean moved) {
        isMoved = moved;
    }

    public Integer getUniqueID() {
        return uniqueID;
    }

    public void setUniqueID(Integer uniqueID) {
        this.uniqueID = uniqueID;
    }

    public String getSimpleName() {
        return simpleName;
    }

    public boolean getHungry() {
        return isHungry;
    }

    public void setHungry(boolean hungry) {
        isHungry = hungry;
    }

    @Override
    public String toString() {

//        maxWeight = Math.scalb(maxWeight, 2);
//        return String.format("%-11s %s %-6.2f %s %-4d  %s %-4d %s %d %s %-6.2f", this.getClass().getSimpleName(),
        return this.getClass().getSimpleName()
                + " id: " + this.uniqueID +
//                "maxWeight=" , maxWeight,
                ", maxCount=" + maxCount
//                ", animalOnTheLocation = ", animalOnTheLocation
                + ", step=" + step;
//                ", satiety=", satiety
//        );



    }


//    @Override
//    public Animal clone() {
//        try {
//            Animal clone = (Animal) super.clone();
//            clone.id = maxId;
//            maxId++;
//            //I will try or AtomicInteger or old option
//            clone.uniqueID = idGenerator.incrementAndGet();
//            return clone;
//        } catch (CloneNotSupportedException e) {
//            throw new AssertionError();
//        }
//    }
}
