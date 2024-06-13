package project.simulation.animals;

import java.awt.Color;
import java.awt.Point;

import project.simulation.Animal;
import project.simulation.ORGANISM_E;

public class Wolf extends Animal {

    public Wolf(int s, int i, int a, Point p) {
        super(s, i, a, p);
        type = ORGANISM_E.WOLF;
        color = Color.red;
    }

    @Override
    public String className() {
        return "Wolf";
    }
    
}
