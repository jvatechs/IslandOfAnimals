package island;

import entities.Animal;
import populators.AnimalsListCreator;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.stream.Collectors;

public class CreateIsland {
    public static int lengthOfIsland;
    public static int widthOfIsland;
    public static Location[][] island;
    ArrayList<Location> locations;
    private static List<? extends Animal> animalsInitList;


    public CreateIsland(int length, int width) {
        lengthOfIsland = length;
        widthOfIsland = width;
        island = new Location[length][width];
        setIslandLocations();
        setAnimalsInitList();
    }


    private void setIslandLocations() {
        locations = new ArrayList<>();
        //set
        for (int i = 0; i < island.length; i++) {
            for (int j = 0; j < island[i].length; j++) {
                Location location = new Location(i, j);
                //setting maxX and maxY for every location
                location.setMaxX(lengthOfIsland - 1);
                location.setMaxY(widthOfIsland - 1);
                locations.add(location);


                island[i][j] = location;

//                System.out.println(location);

            }
        }
    }

    public void printLocations() {
        for (Location location : locations) {
            System.out.println(location);
        }
    }

    public void printTotalAnimalsCount() {
        locations.stream()
                .flatMap(location -> location.getAnimalListPerNameMap().entrySet().stream())
                .collect(Collectors.groupingBy(
                        entry -> entry.getKey().getSimpleName(),
                        Collectors.summingInt(entry -> entry.getValue().size())
                ))
                .forEach((x, y) -> System.out.println(x + "\t" + y));
    }

    private void setAnimalsInitList() {
        AnimalsListCreator listCreator = new AnimalsListCreator();
        animalsInitList = listCreator.getAnimals();
    }

    public static List<? extends Animal> getAnimalsInitList() {
        return animalsInitList;
    }
}
