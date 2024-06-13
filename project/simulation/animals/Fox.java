package project.simulation.animals;

import java.awt.Color;
import java.awt.Point;

import project.*;
import project.simulation.Animal;
import project.simulation.ORGANISM_E;
import project.simulation.Organism;
import project.simulation.World;

public class Fox extends Animal {

    public Fox(int s, int i, int a, Point p) {
        super(s, i, a, p);
        type = ORGANISM_E.FOX;
        color = Color.orange;
    }

    @Override
    public String className() {
        return "Fox";
    }
    
    @Override
    public void action(World W) {
        Point potentialPos = new Point();
        potentialPos = W.getRandomPosNearby(pos, 1);
        boolean enemy_avoided = false;
        for (Organism other_o: W.organisms) {
            while (potentialPos.equals(other_o.getPos()) && strength < other_o.getStrength()) {
                potentialPos = W.getRandomPosNearby(other_o.getPos(), 1);
                enemy_avoided = true;
            }
        }
        if (enemy_avoided)
            W.WListener.RecordEvent(classInfo() + " avoided animal(s) of greater strength");
        setPos(potentialPos);
    }
    
}


