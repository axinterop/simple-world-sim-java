package project.simulation.animals;

import project.simulation.Animal;
import project.simulation.ORGANISM_E;
import project.simulation.PLAYER_ACTION;
import project.simulation.World;

import java.awt.Color;
import java.awt.Point;

public class Human extends Animal {
    private
    boolean power_is_active = false;
    int power_turns = 0;
    int power_cool_down = 0;
    public Human(int s, int i, int a, Point p) {
        super(s, i, a, p);
        type = ORGANISM_E.ANTILOPE;
        color = Color.lightGray;
    }

    @Override
    public String className() {
        return "Human";
    }

    @Override
    public void action(World W) {
        PLAYER_ACTION a = W.human_action;
        if (a != PLAYER_ACTION.NO_ACTION && a != PLAYER_ACTION.POWER) {
            Point potentialPos = new Point(pos);
            if (a == PLAYER_ACTION.GO_RIGHT) {
                potentialPos.x += 1 * W.grid_offset;
            } else if (a == PLAYER_ACTION.GO_LEFT) {
                potentialPos.x -= 1 * W.grid_offset;
            } else if (a == PLAYER_ACTION.GO_DOWN) {
                potentialPos.y += 1 * W.grid_offset;
            } else if (a == PLAYER_ACTION.GO_UP) {
                potentialPos.y -= 1 * W.grid_offset;
            }
            if (W.WithinWorldArea(potentialPos)) {
                setPos(potentialPos);
            }
        } else if (a == PLAYER_ACTION.POWER) {
            if (power_cool_down == 0) {
                PowerActivate();
                W.WListener.RecordEvent("Power activated: invincibility for 5 turns");
            }
        }
    }

    @Override
    public void AfterTurn(World W) {
        getOlder();
        if (power_is_active) {
            if (power_turns == 0) {
                PowerDeactivate();
                W.WListener.RecordEvent("Power deactivated.");
            }
            else {
                power_turns--;
                power_cool_down = 5;
            }
        } else if (!power_is_active) {
            if (power_cool_down != 0)
                power_cool_down--;
        }
    }

    public void PowerActivate() {
        power_is_active = true;
        power_turns = 6;
        power_cool_down = 0;
    }
    
    public void PowerDeactivate() {
        power_is_active = false;
        power_turns = 0;
        power_cool_down = 5;
    }

    public boolean isPowerful() {
        return power_is_active;
    }
}
