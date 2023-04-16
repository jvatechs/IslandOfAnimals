package entities;

 public class Predator extends Animal implements EatOtherAnimalAble{

//     public Predator(int maxWeight, int maxCount, int step, int satiety) {
//         this.maxWeight = maxWeight;
//         this.maxCount = maxCount;
//         this.step = step;
//         this.satiety = satiety;
//     }

     public Predator() {
         isPredator = true;
     }

     @Override
     public void eat(Object o) {

     }




//     @Override
//    public void eat(Object o) {
//        this.animal = (Animal) o;
//    }
}
