package project.simulation.animals;

import java.awt.Color;
import java.awt.Point;

import project.simulation.Animal;
import project.simulation.ORGANISM_E;

public class Sheep extends Animal {

    public Sheep(int s, int i, int a, Point p) {
        super(s, i, a, p);
        type = ORGANISM_E.SHEEP;
        color = Color.white;
    }

    @Override
    public String className() {
        return "Sheep";
    }
    
}


