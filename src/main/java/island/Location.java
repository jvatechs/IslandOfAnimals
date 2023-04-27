package island;

import entities.Animal;
import entities.Plants;
import lombok.Getter;
import lombok.Setter;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
@Getter
@Setter
public class Location {
    private int X;
    private int Y;
    private int maxX;
    private int maxY;

    private HashMap<Class<? extends Animal>, ArrayList<Animal>> animalListPerNameMap;
    private Plants plants;
    private final List<Animal> animals;


    public Location(int x, int y) {
        X = x;
        Y = y;
        animals = new ArrayList<>();
        animalListPerNameMap = new HashMap<>();
    }

    public List<Animal> getAnimals() {
        return animals;
    }


    public HashMap<Class<? extends Animal>, ArrayList<Animal>> getAnimalListPerNameMap() {
        return animalListPerNameMap;
    }

    @Override
    public String toString() {

        return "Location{" +
                "X=" + X +
                ", Y=" + Y +
                '}' + " " +
                plants + " " +
                animalListPerNameMap;
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
