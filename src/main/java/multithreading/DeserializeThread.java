package multithreading;

import readers.DeserializeAnimalsInfo;
import readers.DeserializeProbability;

import java.io.IOException;

public class DeserializeThread implements Runnable{

    @Override
    public void run() {
        try {
            DeserializeAnimalsInfo animalsInfo = new DeserializeAnimalsInfo();
            animalsInfo.deserializeJSON();

            DeserializeProbability probability = new DeserializeProbability();
            probability.deserializeJSON();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
        //create HashMap of probabilities from JSON
    }
}
