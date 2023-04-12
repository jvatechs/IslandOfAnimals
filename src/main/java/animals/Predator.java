package animals;

 public abstract class Predator extends Animal {
    @Override
    public void eat(Object o) {
        this.animal = (Animal) o;
    }
}
