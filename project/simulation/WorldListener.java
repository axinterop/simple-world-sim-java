package project.simulation;

import java.io.Serializable;
import java.util.PriorityQueue;
import java.util.Queue;

public class WorldListener implements Serializable {
    public Queue<String> events;

    public WorldListener() {
        events = new PriorityQueue<>();
    }

    public void RecordEvent(String s) {
        events.add(s);
    }

    public void RecordCollision(COLLISION_STATUS c_s, Organism this_o, Organism other_o) {
        String e = "";
        if (c_s == COLLISION_STATUS.BREED) {
            e = this_o.classInfo() + " bred with another " + other_o.classInfo();
        } else if (c_s == COLLISION_STATUS.KILL) {
            e = this_o.classInfo() + " killed " + other_o.classInfo();
        } else if (c_s == COLLISION_STATUS.DIE) {
            e = this_o.classInfo() + " ran into " + other_o.classInfo() + " and died";
        } else if (c_s == COLLISION_STATUS.BLOCK_ATTACK) {
            e = other_o.classInfo() + " blocked attack from " + this_o.classInfo();
        } else if (c_s == COLLISION_STATUS.ESCAPE) {
            e = other_o.classInfo() + " escaped from " + this_o.classInfo();
        } else if (c_s == COLLISION_STATUS.AVOID_DEATH) {
            e = this_o.classInfo() + " avoided death from " + other_o.classInfo();
        } else if (c_s == COLLISION_STATUS.BOOST_EATING) {
            e = this_o.classInfo() + " has eaten " + other_o.className() + " and became stronger";
        } else if (c_s == COLLISION_STATUS.DIE_EATING) {
            e = this_o.classInfo() + " has eaten " + other_o.className() + " and died";
        } else if (c_s == COLLISION_STATUS.UNDEFINED) {
            e = "Undefined collision between " + this_o.classInfo() + " and " + other_o.classInfo();
        }

        if (c_s != COLLISION_STATUS.STAY)
            RecordEvent(e);
    }

    public int size() {
        return events.size();
    }
}
