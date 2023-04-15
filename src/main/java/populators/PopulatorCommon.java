package populators;

import island.CreateIsland;
import island.Location;

import java.util.Random;

abstract class PopulatorCommon {
    Random random = new Random();
    Location[][] locations = CreateIsland.island;
}
