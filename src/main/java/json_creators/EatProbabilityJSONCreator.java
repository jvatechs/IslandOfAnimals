package json_creators;

import animals.Herbivores.*;
import animals.Predators.*;
import com.fasterxml.jackson.databind.ObjectMapper;
import entities.Animal;
import entities.Entity;
import entities.Plants;

import java.io.BufferedInputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.util.HashMap;

public class EatProbabilityJSONCreator {
    public static void main(String[] args) throws IOException {
        HashMap<Class<? extends Animal>, HashMap<Class<? extends Entity>, Integer>> map
                = new HashMap<>();


        Class<Wolf> wolf = Wolf.class;
        Class<Fox> fox = Fox.class;
        Class<Eagle> eagle = Eagle.class;
        Class<Boa> boa = Boa.class;
        Class<Bear> bear = Bear.class;
        Class<Boar> boar = Boar.class;
        Class<Buffalo> buffalo = Buffalo.class;
        Class<Caterpillar> caterpillar = Caterpillar.class;
        Class<Deer> deer = Deer.class;
        Class<Duck> duck = Duck.class;
        Class<Goat> goat = Goat.class;
        Class<Horse> horse = Horse.class;
        Class<Mouse> mouse = Mouse.class;
        Class<Rabbit> rabbit = Rabbit.class;
        Class<Sheep> sheep = Sheep.class;
        Class<Plants> plants = Plants.class;

        HashMap<Class<? extends Entity>, Integer> wolfMap = new HashMap<>();
        HashMap<Class<? extends Entity>, Integer> foxMap = new HashMap<>();
        HashMap<Class<? extends Entity>, Integer> boaMap = new HashMap<>();
        HashMap<Class<? extends Entity>, Integer> bearMap = new HashMap<>();
        HashMap<Class<? extends Entity>, Integer> eagleMap = new HashMap<>();

        HashMap<Class<? extends Entity>, Integer> horseMap = new HashMap<>();
        HashMap<Class<? extends Entity>, Integer> deerMap = new HashMap<>();
        HashMap<Class<? extends Entity>, Integer> rabbitMap = new HashMap<>();
        HashMap<Class<? extends Entity>, Integer> mouseMap = new HashMap<>();
        HashMap<Class<? extends Entity>, Integer> goatMap = new HashMap<>();
        HashMap<Class<? extends Entity>, Integer> sheepMap = new HashMap<>();
        HashMap<Class<? extends Entity>, Integer> boarMap = new HashMap<>();
        HashMap<Class<? extends Entity>, Integer> buffaloMap = new HashMap<>();
        HashMap<Class<? extends Entity>, Integer> duckMap = new HashMap<>();
        HashMap<Class<? extends Entity>, Integer> caterpillarMap = new HashMap<>();

        wolfMap.put(horse, 10);
        wolfMap.put(deer, 15);
        wolfMap.put(rabbit, 60);
        wolfMap.put(mouse, 80);
        wolfMap.put(goat, 60);
        wolfMap.put(sheep, 70);
        wolfMap.put(boar, 15);
        wolfMap.put(buffalo, 10);
        wolfMap.put(duck, 40);
        map.put(wolf, wolfMap);

        boaMap.put(fox, 15);
        boaMap.put(rabbit, 20);
        boaMap.put(mouse, 40);
        boaMap.put(duck, 10);
        map.put(boa, boaMap);

        foxMap.put(rabbit, 70);
        foxMap.put(mouse, 90);
        foxMap.put(duck, 60);
        foxMap.put(caterpillar, 40);
        map.put(fox, foxMap);

        bearMap.put(boa, 80);
        bearMap.put(horse, 40);
        bearMap.put(deer, 80);
        bearMap.put(rabbit, 80);
        bearMap.put(mouse, 90);
        bearMap.put(goat, 70);
        bearMap.put(sheep, 70);
        bearMap.put(boar, 50);
        bearMap.put(buffalo, 20);
        bearMap.put(duck, 10);
        map.put(bear, bearMap);


        eagleMap.put(fox, 10);
        eagleMap.put(rabbit, 90);
        eagleMap.put(mouse, 90);
        eagleMap.put(duck, 80);
        map.put(eagle, eagleMap);

        horseMap.put(plants, 100);
        map.put(horse, horseMap);

        deerMap.put(plants, 100);
        map.put(deer, deerMap);

        rabbitMap.put(plants, 100);
        map.put(rabbit, rabbitMap);

        mouseMap.put(caterpillar, 90);
        mouseMap.put(plants, 100);
        map.put(mouse, mouseMap);

        goatMap.put(plants, 100);
        map.put(goat, goatMap);

        sheepMap.put(plants, 100);
        map.put(sheep, sheepMap);

        boarMap.put(mouse, 50);
        boarMap.put(caterpillar, 90);
        boarMap.put(plants, 100);
        map.put(boar, boarMap);

        buffaloMap.put(plants, 100);
        map.put(buffalo, buffaloMap);

        duckMap.put(caterpillar, 90);
        duckMap.put(plants, 100);
        map.put(duck, duckMap);

        caterpillarMap.put(plants, 100);
        map.put(caterpillar, caterpillarMap);



        //serialize
        String pathname = "src/main/java/json_creators/animal_probability.json";
        ObjectMapper mapper = new ObjectMapper();
        mapper.writeValue(new File(pathname), map);

        //deserialize
        HashMap hashMap = mapper.readValue(new BufferedInputStream(new FileInputStream(pathname)), HashMap.class);
        System.out.println(hashMap);

    }
}
