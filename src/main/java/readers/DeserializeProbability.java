package readers;

import com.fasterxml.jackson.databind.ObjectMapper;
import entities.Animal;
import entities.Entity;

import java.io.BufferedInputStream;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.HashMap;

public class DeserializeProbability {
    private static HashMap<Class<? extends Animal>, HashMap<Class<? extends Entity>, Integer>> hashMap;

    public void deserialize() throws IOException {
        String pathname = "src/main/java/json_creators/animal_probability.json";
        ObjectMapper mapper = new ObjectMapper();

        //deserialize
         hashMap = mapper.readValue(new BufferedInputStream(new FileInputStream(pathname)), HashMap.class);
        System.out.println(hashMap);

    }

    public HashMap<Class<? extends Animal>, HashMap<Class<? extends Entity>, Integer>> getAllProbabilitiesMap() {
        return hashMap;
    }


}
