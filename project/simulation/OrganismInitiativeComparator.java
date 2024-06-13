package project.simulation;
import java.util.Comparator;

public class OrganismInitiativeComparator implements Comparator<Organism> {
    @Override
    public int compare(Organism o1, Organism o2) {
        if (o1.isDead())
            return -1;
        if (o2.isDead())
            return 1;

        if (o1.getInitiative() == o2.getInitiative())
            return o2.getAge() - o1.getAge();
        return o2.getInitiative() - o1.getInitiative() ;

    }

}