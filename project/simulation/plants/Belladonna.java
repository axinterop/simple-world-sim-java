package project.simulation.plants;

import java.awt.Color;
import java.awt.Point;

import project.simulation.ORGANISM_E;
import project.simulation.Plant;
import project.simulation.World;

public class Belladonna extends Plant {

   public Belladonna(int s, int i, int a, Point p) {
        super(s, i, a, p);
        type = ORGANISM_E.BELLADONNA;
        color = Color.blue.darker().darker();
    }

    @Override
    public String className() {
        return "Belladonna";
    }

    @Override
    public void action(World W) {
        if (chunk.seeded_this_turn)
            return;

        if (W.rand.nextInt(150) == 0) {
            chunk.seeded_this_turn = true;
            W.CreatePlantOffspring(this);
        }
    }
}
