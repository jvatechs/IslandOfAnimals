package readers;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import entities.Animal;
import entities.Entity;

import java.io.*;
import java.util.HashMap;

public class DeserializeProbability implements Deserializable {
    private static HashMap<Class<? extends Animal>, HashMap<Class<? extends Entity>, Integer>> hashMap;
    @Override
    public void deserializeJSON() throws IOException {
        String pathname = "src/main/java/animal_probabilities.json";
//        ObjectMapper mapper = new ObjectMapper();

        TypeReference<HashMap<Class<? extends Animal>, HashMap<Class<? extends Entity>, Integer>>> typeRef
                = new TypeReference<>() {};
        hashMap = mapper.readValue(new File(pathname), typeRef);

        //deserialize
//         hashMap = mapper.readValue(new BufferedInputStream(new FileInputStream(pathname)), HashMap.class);
        System.out.println(hashMap);
    }
    public HashMap<Class<? extends Animal>, HashMap<Class<? extends Entity>, Integer>> getAllProbabilitiesMap() {
        return hashMap;
    }
}
