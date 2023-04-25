package service.json_readers;

import entities.Animal;
import island.CreateIsland;

import java.io.File;
import java.io.IOException;
import java.util.List;

public class DeserializeAnimalsInfo implements Deserializable {


    @Override
    public void deserializeJSON() throws IOException {
        //init list

        //init mapper and pathname
//        ObjectMapper mapper = new ObjectMapper();
         String pathname = "src/main/java/entities_info.json";

        List<? extends Animal> animals = CreateIsland.getAnimalsInitList();
        //deserialize
        List<Animal> jsonAnimalList = mapper.readValue(new File(pathname),
                mapper.getTypeFactory().constructCollectionLikeType(List.class, Animal.class));

        for (Animal jsonAnimal : jsonAnimalList) {
//            System.out.println(jsonAnimal);

            //filling data from json file to our animals' list
            for (Animal animal : animals) {
                if (animal.getClass().getSimpleName().equals(jsonAnimal.getClass().getSimpleName())) {
                    animal.setMaxWeight(jsonAnimal.getMaxWeight());
                    animal.setMaxCount(jsonAnimal.getMaxCount());
                    animal.setStep(jsonAnimal.getStep());
                    animal.setSatiety(jsonAnimal.getSatiety());
//                    System.out.println(animal + "\t" + animal.getLocation());
                }
            }
        }

        System.out.println("***** Reading from animaldata.json completed ****");
        System.out.println();
    }

}
