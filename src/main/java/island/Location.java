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

    private HashMap<Class<? extends Animal>, ArrayList<Animal>> animalListPerNameMap;
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
        animalListPerNameMap = new HashMap<Class<? extends Animal>, ArrayList<Animal>>();
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

    public HashMap<Class<? extends Animal>, ArrayList<Animal>> getAnimalListPerNameMap() {
        return animalListPerNameMap;
    }

    public HashMap<Animal, Integer> getAnimalCurrentCount() {
        return animalCurrentCountMap;
    }

    @Override
    public String toString() {

        return "Location{" +
                "X=" + X +
                ", Y=" + Y +
                '}' + " " +
                plants + " " +
//                animalOnTheLocationMap + animals;
                animalListPerNameMap;
//        animals;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        Location location = (Location) o;

        if (X != location.X) return false;
        return Y == location.Y;
    }

    @Override
    public int hashCode() {
        int result = X;
        result = 31 * result + Y;
        return result;
    }
}
