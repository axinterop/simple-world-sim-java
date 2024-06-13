package project.simulation;
import java.awt.Color;
import java.awt.Point;
import java.io.Serializable;
    

public abstract class Organism implements Serializable {
    protected final int id;
    protected static int free_id = 1;
    protected int strength; 
    protected int initiative;
    protected int age;
    protected int breedPause = 0;

    public Point pos;
    public Point prevPos;

    public Color color;
    public ORGANISM_E type;

    public boolean canMakeTurn = false;

    public Organism(int s, int i, int a, Point p) {
        id = free_id++;
        strength = s;
        initiative = i;
        age = a;
        pos = p;

        prevPos = p;
    }

    abstract public void action(World W);
    abstract public COLLISION_STATUS collision(Organism other);
    abstract public String className();

    public void AfterTurn(World W) {
        getOlder();
        breedDecreasePause();
    };

    public int getId() { return id; }
    public ORGANISM_E getType() { return type; };
    public int getStrength() { return strength; };
    public int getAge() { return age; }
    public int getInitiative() { return initiative; }

    public int getMatrixLocation(int m_h) { return pos.y * m_h + pos.x; }
    public void setLocation(Point p) { pos = p; }

    public void getOlder() { age++; };
    public boolean isDead() { return age == -1; }
    public void Die() { age = -1; }

    public void breedDecreasePause() { if (breedPause > 0) breedPause--; };
    public void breedSetPause() { if (breedPause == 0) breedPause = 20; };
    public void breedSetPause(int value) { breedPause = value; };
    public boolean canBreed() { return breedPause == 0; };

    public Point getPrevPos() { return prevPos; };
    public void setPos(Point newPos) { prevPos = (Point) pos.clone(); pos = newPos; };
    public Point getPos() { return pos; };
    public void RevertPos() {
        Point t = pos;
        pos = prevPos;
        prevPos = t;
    }

    public String classInfo() {
        return className() + "[" + Integer.toString(id) + "]{" + Integer.toString(strength) + "," +
               Integer.toString(initiative) + "}";
    };

}
