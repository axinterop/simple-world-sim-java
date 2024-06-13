package project.simulation;
import java.awt.Point;
import java.util.Random;

import project.simulation.animals.Human;

public abstract class Animal extends Organism {

    public Animal(int s, int i, int a, Point p) {
        super(s, i, a, p);
    }

    @Override
    public void action(World W) {
        setPos(W.getRandomPosNearby(pos, 1));
    }


    @Override
    public COLLISION_STATUS collision(Organism other) {
        if (this.getType().equals(other.getType())) {
            if (this.canBreed() && other.canBreed()) {
                RevertPos();
                return COLLISION_STATUS.BREED;
            }
        } else if (this.getStrength() >= other.getStrength()) {
            if (other.getType().equals(ORGANISM_E.GRASS) || other.getType().equals(ORGANISM_E.SONCHUS))
                return COLLISION_STATUS.STAY;
            if (other.getType().equals(ORGANISM_E.GUARANA)) {
                strength += 3;
                other.Die();
                return COLLISION_STATUS.BOOST_EATING;
            } else if (isAttackBlocked(other)) {
                RevertPos();
                return COLLISION_STATUS.BLOCK_ATTACK;
            } else if (escapedFight(other)) {
                return COLLISION_STATUS.ESCAPE;
            } else {
                other.Die();
                return COLLISION_STATUS.KILL;
            }
        } else if (this.getStrength() < other.getStrength()) {
            if (avoidedDeath(other)) {
                RevertPos();
                return COLLISION_STATUS.AVOID_DEATH;
            } else {
                if (other.getType().equals(ORGANISM_E.BELLADONNA) || other.getType().equals(ORGANISM_E.H_SOSNOWSKYI)) {
                    this.Die();
                    other.Die();
                    return COLLISION_STATUS.DIE_EATING;
                } else {
                    this.Die();
                    return COLLISION_STATUS.DIE;
                }
            }
        }
        RevertPos();
        return COLLISION_STATUS.STAY;
    }

    public boolean isAttackBlocked(Organism other) {
        // `this` is an attacker
        if (this.getStrength() >= other.getStrength()) {
            if (other.getType().equals(ORGANISM_E.TURTLE) && this.getStrength() < 5)
                return true;
        }
        return false;
    }
    
    public boolean escapedFight(Organism other) {
        // `this` is an attacker
        if (other instanceof Human) {
            Human h = (Human) other;
            if (h.isPowerful())
                return true;
            }
        if (other.getType().equals(ORGANISM_E.ANTILOPE)) {
            Random r = new Random();
            return r.nextBoolean();
        }
        return false;
    }
    
    public boolean avoidedDeath(Organism other) {
        // `this` is a defender
        if (this instanceof Human) {
            Human h = (Human) this;
            if (h.isPowerful()) {
                return true;
            }
        }
        return false;
    }
    
}

