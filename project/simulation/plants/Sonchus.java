package project.simulation.plants;

import java.awt.Color;
import java.awt.Point;

import project.simulation.ORGANISM_E;
import project.simulation.Plant;
import project.simulation.World;

public class Sonchus extends Plant {

   public Sonchus(int s, int i, int a, Point p) {
        super(s, i, a, p);
        type = ORGANISM_E.SONCHUS;
        color = Color.yellow.darker().darker();
    }

    @Override
    public String className() {
        return "Sonchus";
    }

    @Override
    public void action(World W) {
        if (chunk.seeded_this_turn)
            return;

        for (int tr = 0; tr < 3; tr++) {
            if (W.rand.nextInt(80) == 0) {
                chunk.seeded_this_turn = true;
                W.CreatePlantOffspring(this);
                break;
            }
        }
    }
}
