package project.simulation;

import java.awt.Point;

public abstract class Plant extends Organism {
    
    protected PlantChunk chunk;

    public Plant(int s, int i, int a, Point p) {
        super(s, i, a, p);
    }

    @Override
    public COLLISION_STATUS collision(Organism other) {
        return COLLISION_STATUS.STAY;
    }

    public void setChunk(PlantChunk c) {
        chunk = c;
    }

    public PlantChunk getChunk() {
        return chunk;
    }
    
}
