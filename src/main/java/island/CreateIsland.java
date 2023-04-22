package island;

import entities.Animal;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.stream.Collectors;

public class CreateIsland {
    public static int lengthOfIsland;
    public static int widthOfIsland;
    public static Location[][] island;
    ArrayList<Location> locations;

    public CreateIsland(int length, int width) {
        lengthOfIsland = length;
        widthOfIsland = width;
        island = new Location[length][width];
        setIslandLocations();
    }

    public void setIslandLocations() {
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

            //not necessary just for test
//            System.out.println("********** HASHMAP of COUNTS:");
//            HashMap<Animal, Integer> animalCurrentCount = location.getAnimalCurrentCount();
//            animalCurrentCount.forEach((a, b) -> System.out.println(a.getClass().getSimpleName() + "\t , currentCount = " + b));
//            System.out.println("********** ENDED");
//            System.out.println();
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

}
