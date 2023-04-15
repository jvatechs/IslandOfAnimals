package readers;

import com.fasterxml.jackson.core.JsonGenerator;
import com.fasterxml.jackson.databind.JavaType;
import com.fasterxml.jackson.databind.ObjectMapper;
import entities.Animal;
import entities.Entity;
import populators.AnimalPopulator;
import populators.AnimalsListCreator;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.List;

public class CreateOwnJson {

    public void createJson() throws IOException {
        //init: get list of animals

        List<? extends Animal> animalsOther = getAnimalList();

        //serialize #1
        ObjectMapper mapper = new ObjectMapper();
        String pathname = "src/main/java/readers/newAnimalJson5.json";
        JavaType plType = mapper.getTypeFactory().constructCollectionLikeType(List.class, Animal.class);

        //serialize #2
//        String s = mapper.writerFor(plType).writeValueAsString(animalsOther);
//        mapper.writerFor(plType).writeValue(new File(pathname), animalsOther);
//        System.out.println(s);

        //generator for increase readability
//        try (JsonGenerator generator = mapper.getFactory().createGenerator(new FileOutputStream(pathname))) {
//            generator.useDefaultPrettyPrinter();
//            mapper.writerFor(plType).writeValue(generator, animalsOther);
//            String s = mapper.writerFor(plType).writeValueAsString(animalsOther);
//            System.out.println(s);
//        }

        //deserialize

        List<Animal> someAnimal = mapper.readValue(new File(pathname), mapper.getTypeFactory().constructCollectionLikeType(List.class, Animal.class));

        for (Animal animal : someAnimal) {
            System.out.println(animal);
        }

        System.out.println(someAnimal.get(0).getClass().getSimpleName());

    }

    private List<? extends Animal> getAnimalList() {
        AnimalsListCreator listCreator = new AnimalsListCreator();
        return listCreator.getAnimals();
    }
}
