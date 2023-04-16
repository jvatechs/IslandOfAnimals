package json_creators;

import com.fasterxml.jackson.core.JsonGenerator;
import com.fasterxml.jackson.databind.JavaType;
import com.fasterxml.jackson.databind.ObjectMapper;
import entities.Animal;
import populators.AnimalsListCreator;

import java.io.FileOutputStream;
import java.io.IOException;
import java.util.List;

public class AnimalsDataJSONCreator {

    public static void main(String[] args) throws IOException {
        new AnimalsDataJSONCreator().createJson();
    }
    public void createJson() throws IOException {
        //init: get list of animals

        List<? extends Animal> animalsOther = getAnimalList();

        //serialize #1
        ObjectMapper mapper = new ObjectMapper();
        String pathname = "src/main/java/json_creators/animaldata.json";

        JavaType plType = mapper.getTypeFactory().constructCollectionLikeType(List.class, Animal.class);

        //serialize #2
//        String s = mapper.writerFor(plType).writeValueAsString(animalsOther);
//        mapper.writerFor(plType).writeValue(new File(pathname), animalsOther);
//        System.out.println(s);

//        generator for increase readability
        try (JsonGenerator generator = mapper.getFactory().createGenerator(new FileOutputStream(pathname))) {
            generator.useDefaultPrettyPrinter();
            mapper.writerFor(plType).writeValue(generator, animalsOther);
            String s = mapper.writerFor(plType).writeValueAsString(animalsOther);
            System.out.println(s);
        }

    }

    private List<? extends Animal> getAnimalList() {
        AnimalsListCreator listCreator = new AnimalsListCreator();
        return listCreator.getAnimals();
    }
}
