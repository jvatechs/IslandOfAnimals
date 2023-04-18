package island;

import entities.Animal;
import entities.Plants;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public class Location {
    private int X;
    private int Y;
    private int maxX;
    private int maxY;
    private final HashMap<Animal, Integer> animalCurrentCountMap;
    private HashMap<Animal, Integer> animalOnTheLocationMap;
    private Plants plants;
    private final List<Animal> animals;

    public int getX() {
        return X;
    }

    public void setX(int x) {
        this.X = x;
    }

    public int getY() {
        return Y;
    }

    public void setY(int y) {
        this.Y = y;
    }

    public Location(int x, int y) {
        X = x;
        Y = y;
        animals = new ArrayList<>();
        animalCurrentCountMap = new HashMap<>();
        animalOnTheLocationMap = new HashMap<>();
    }

    public int getMaxX() {
        return maxX;
    }

    public void setMaxX(int maxX) {
        this.maxX = maxX;
    }

    public int getMaxY() {
        return maxY;
    }

    public void setMaxY(int maxY) {
        this.maxY = maxY;
    }

    public Plants getPlants() {
        return plants;
    }

    public void setPlants(Plants plants) {
        this.plants = plants;
    }

    public List<Animal> getAnimals() {
        return animals;
    }

    public void createMapOfCount() {
        for (Animal animal : animals) {
            animalCurrentCountMap.put(animal, animal.getCurrentCount());

        }
    }

    public HashMap<Animal, Integer> getAnimalOnTheLocMap() {
        return animalOnTheLocationMap;
    }

    public HashMap<Animal, Integer> getAnimalCurrentCount() {
        return animalCurrentCountMap;
    }

    @Override
    public String toString() {
//        if (plants == null && animals.isEmpty()) {
//            return "Location{" +
//                    "X=" + X +
//                    ", Y=" + Y +
//                    '}';
//        } else if (animals.isEmpty()) {
//            return "Location{" +
//                    "X=" + X +
//                    ", Y=" + Y +
//                    '}' + " " +
//                    plants;
//        }
//        if (plants == null) {
//            return "Location{" +
//                    "X=" + X +
//                    ", Y=" + Y +
//                    '}' + " " +
//                    animals;
//        }
        return "Location{" +
                "X=" + X +
                ", Y=" + Y +
                '}' + " " +
                plants + " " +
                animalOnTheLocationMap + animals;
    }
}
