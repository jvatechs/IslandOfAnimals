package animals;

public abstract class Animal {
    int weight;
    int maxCount;
    int speed;
    int satiety;
    Animal animal;

    public abstract void eat(Object o);
    public int reproduce(int currentCount) {
        int afterCount = currentCount;
        if (currentCount + 1 < maxCount) {
            afterCount = currentCount + 1;
        }
        return afterCount;
    }
    public void directionChoose() {
        if (weight <= 10) {
            speed = 3;
        } else if (weight < 100) {
            speed = 2;
        } else {
            speed = 1;
        }


    }

    public void dead() {
        if (satiety <= 0) {
            System.out.println("The animal is dead...");
        }
    }
}
