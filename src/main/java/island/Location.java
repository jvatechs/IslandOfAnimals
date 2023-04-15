package island;

import entities.Animal;
import entities.Entity;
import entities.Plants;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Random;

public class Location {
    private int X;
    private int Y;
    private int maxX;
    private int maxY;
    private HashMap<Entity, Integer> animalMap;
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

    @Override
    public String toString() {
        return "Location{" +
                "X=" + X +
                ", Y=" + Y +
                '}' + " " +
                plants + " " +
                animals;
    }
}
