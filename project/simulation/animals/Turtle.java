package project.simulation.animals;

import java.awt.Color;
import java.awt.Point;

import project.*;
import project.simulation.Animal;
import project.simulation.ORGANISM_E;
import project.simulation.World;

public class Turtle extends Animal {

    public Turtle(int s, int i, int a, Point p) {
        super(s, i, a, p);
        type = ORGANISM_E.TURTLE;
        color = Color.green;
    }

    @Override
    public String className() {
        return "Turtle";
    }

    @Override
    public void action(World W) {
        if (W.rand.nextInt(4) == 0)
            super.action(W);
    }
    
}



