package project.simulation.animals;

import java.awt.Color;
import java.awt.Point;

import project.*;
import project.simulation.Animal;
import project.simulation.ORGANISM_E;
import project.simulation.World;

public class Antilope extends Animal {

    public Antilope(int s, int i, int a, Point p) {
        super(s, i, a, p);
        type = ORGANISM_E.ANTILOPE;
        color = Color.yellow;
    }

    @Override
    public String className() {
        return "Antilope";
    }

    @Override
    public void action(World W) {
        setPos(W.getRandomPosNearby(pos, 2));
    }
    
}



