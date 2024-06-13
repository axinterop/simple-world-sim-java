// file:///home/dixie/uni/sem2/po/pr2java/project/simulation/plants/Guarana.java {"mtime":1716034944810,"ctime":1716034018705,"size":700,"etag":"3cdrf349gmi","orphaned":false,"typeId":""}
package project.simulation.plants;

import java.awt.Color;
import java.awt.Point;

import project.simulation.ORGANISM_E;
import project.simulation.Plant;
import project.simulation.World;

public class Guarana extends Plant {

   public Guarana(int s, int i, int a, Point p) {
        super(s, i, a, p);
        type = ORGANISM_E.GUARANA;
        color = Color.cyan.darker().darker();
    }

    @Override
    public String className() {
        return "Guarana";
    }

    @Override
    public void action(World W) {
        if (chunk.seeded_this_turn)
            return;

        if (W.rand.nextInt(150) == 0) {
            chunk.seeded_this_turn = true;
            W.CreatePlantOffspring(this);
        }
    }
}
