package entities;

import island.Location;

public class Herbivore extends Animal {
    Plants plants;



    @Override
    public void eat(Entity eaten) {
        //if animal
        if (eaten instanceof Animal) {
            double currentWeight = ((Animal) eaten).getMaxWeight();
            int current = ((Animal) eaten).location.getAnimalCurrentCount().get(eaten);

        }
        //if plants
        else {

        }
    }

//    @Override
//    public void eat(Object o) {
//        if (o instanceof Plants) {
//            this.plants = (Plants) o;
//        } else {
//         animal = (Animal) o;
//        }
//    }
}
