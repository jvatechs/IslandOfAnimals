package island;

import java.util.ArrayList;

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
//                location.setMaxX(length - 1);
//                location.setMaxY(width - 1);
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

}
