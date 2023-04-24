package entities;

import island.Location;

import java.util.HashMap;

public class Herbivore extends Animal {
    Plants plants;



//    @Override
//    public void eat(Entity eaten, Location location) {
//        //if animal
//        if (eaten instanceof Animal) {
//            double currentWeight = ((Animal) eaten).getMaxWeight();
////            int current = ((Animal) eaten).location.getAnimalCurrentCount().get(eaten);
//            HashMap<Animal, Integer> animalOnTheLocMap = location.getAnimalOnTheLocMap();
//            int countOfTheEaten = animalOnTheLocMap.get(eaten);
//
//            //remove animal from the location if was eaten
//            if (countOfTheEaten - 1 == 0) {
//                animalOnTheLocMap.remove(eaten);
//                //or decrease the animal quantity if was more than 1
//            } else {
//                animalOnTheLocMap.put((Animal) eaten, countOfTheEaten - 1);
//            }
//
//            if (this.satiety < currentWeight) {
//                this.currentSatiety = this.satiety;
//            } else {
//                this.currentSatiety = currentWeight;
//            }
//            this.isHungry = false;
//
//        }
//        //if plants
//        else {
//            if (eaten instanceof Plants) {
//                Plants currentPlants = location.getPlants();
//                while (this.currentSatiety <= this.satiety && currentPlants.getCurrentCount() != 0) {
//                    int plantCount = currentPlants.getCurrentCount();
//                        currentPlants.setCurrentCount(plantCount - 1);
//                        this.currentSatiety += currentPlants.getMaxWeight();
//                }
//            }
//        }
//    }

//    @Override
//    public void eat(Object o) {
//        if (o instanceof Plants) {
//            this.plants = (Plants) o;
//        } else {
//         animal = (Animal) o;
//        }
//    }
}
