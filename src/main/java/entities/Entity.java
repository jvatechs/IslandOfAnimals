package entities;

public abstract class Entity {
    public boolean canEatAnimalsOrNot() {
        return EatOtherAnimalAble.class.isAssignableFrom(this.getClass());
    }


}
