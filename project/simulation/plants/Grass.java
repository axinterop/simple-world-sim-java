package project.simulation.plants;

import java.awt.Color;
import java.awt.Point;

import project.simulation.ORGANISM_E;
import project.simulation.Plant;
import project.simulation.World;

public class Grass extends Plant {

   public Grass(int s, int i, int a, Point p) {
        super(s, i, a, p);
        type = ORGANISM_E.GRASS;
        color = Color.green.darker().darker();
    }

    @Override
    public String className() {
        return "Grass";
    }

    @Override
    public void action(World W) {
        if (chunk.seeded_this_turn)
            return;

        if (W.rand.nextInt(80) == 0) {
            chunk.seeded_this_turn = true;
            W.CreatePlantOffspring(this);
        }
    }
}
