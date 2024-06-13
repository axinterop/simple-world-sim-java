package project.simulation;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Random;

public class PlantChunk implements Serializable {
    public int id;
    private static int free_id = 0;

    public ArrayList<Integer> plantsIDS;

    public boolean seeded_this_turn = false; 

    public PlantChunk() {
        id = free_id++;
        plantsIDS = new ArrayList<>();
    }

    public void addPlantID(int value_id) {
        for (Integer id: plantsIDS)
            if (id == value_id)
                return;
        plantsIDS.add(value_id);
    }

    public void RemovePlant(int value_id) {
        plantsIDS.removeIf(p -> p == id);
    }

    public boolean isEmpty() {
        return plantsIDS.isEmpty();
    }
    public int size() {
        return plantsIDS.size();
    }

    public int getRandomPlantID() {
        Random rand = new Random();
        return plantsIDS.get(rand.nextInt(plantsIDS.size()));
    }

}
