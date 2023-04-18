package entities;

import island.CreateIsland;
import island.Location;

public abstract class Entity {
    public boolean canEatAnimalsOrNot() {
        return EatOtherAnimalAble.class.isAssignableFrom(this.getClass());
    }

    Location[][] locations = CreateIsland.island;

}
