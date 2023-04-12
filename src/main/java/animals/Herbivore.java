package animals;

public abstract class Herbivore extends Animal {
    Plants plants;
    @Override
    public void eat(Object o) {
        if (o instanceof Plants) {
            this.plants = (Plants) o;
        } else {
         animal = (Animal) o;
        }
    }
}
