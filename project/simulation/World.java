package project.simulation;

import java.awt.Point;
import java.awt.Rectangle;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.Random;
import java.util.concurrent.CopyOnWriteArrayList;

import project.simulation.animals.*;
import project.simulation.plants.*;

public class World implements Serializable {
    public CopyOnWriteArrayList<Organism> organisms;
    public CopyOnWriteArrayList<PlantChunk> plantChunks;
    public Rectangle world_area;

    public Random rand = new Random();
    public PLAYER_ACTION human_action;

    public WorldListener WListener;

    public int turnsNum = 0;

    public int grid_offset = 20;

    public World(Rectangle sim_area) {
        organisms = new CopyOnWriteArrayList<>();
        plantChunks = new CopyOnWriteArrayList<>();
        world_area = sim_area;
        WListener = new WorldListener();
        human_action = PLAYER_ACTION.NO_ACTION;

        InitOrganisms();
    }

    public void InitOrganisms() {
        double f = 5; // 80
        double c = f / 100000.0f;
        int oNum = (int) (world_area.height * world_area.width * c);
        for (int i = 0; i < oNum * 0.2; i++) {
            CreateOrganism(ORGANISM_E.WOLF);
            CreateOrganism(ORGANISM_E.SHEEP);
            CreateOrganism(ORGANISM_E.FOX);
            CreateOrganism(ORGANISM_E.TURTLE);
            CreateOrganism(ORGANISM_E.ANTILOPE);
            CreatePlantChunk(ORGANISM_E.GRASS);
            CreatePlantChunk(ORGANISM_E.SONCHUS);
            CreatePlantChunk(ORGANISM_E.GUARANA);
            CreatePlantChunk(ORGANISM_E.BELLADONNA);
            CreatePlantChunk(ORGANISM_E.H_SOSNOWSKYI);
        }
        CreateOrganism(ORGANISM_E.HUMAN);
    }

    public void OrganismsSortAndCleanUp() {
        organisms.sort(new OrganismInitiativeComparator());
        for (Organism organism : organisms) {
            organism.pos.x -= organism.pos.x % grid_offset;
            organism.pos.y -= organism.pos.y % grid_offset;

            if (organism.isDead()) {
                organisms.remove(organism);
            }
        }

        for (Organism o : organisms)
            o.canMakeTurn = true;

        for (PlantChunk chunk : plantChunks) {
            chunk.seeded_this_turn = false;
        }
    }

    public void makeTurn() {
        turnsNum++;

        OrganismsSortAndCleanUp();
        // Sort organisms by initiative
        for (Organism this_o : organisms) {
            if (this_o.isDead())
                continue;

            ReactOnAction(this_o);
            for (Organism other_o : organisms) {
                if (this_o == other_o || other_o.isDead()) {
                    continue;
                }
                if (this_o.pos.equals(other_o.pos)) {
                    ReactOnCollision(this_o, other_o);
                }
            }

            if (!this_o.isDead())
                this_o.AfterTurn(this);
        }

    }

    public boolean isPlaceFree(Point p) {
        for (Organism animal : organisms) {
            if (animal.pos.equals(p))
                return false;
        }
        return true;
    }

    public Organism CreateOrganism(ORGANISM_E o_t) {
        Point potentialPos = new Point();
        do {
            potentialPos = new Point(
                    rand.nextInt(world_area.width / grid_offset) * grid_offset,
                    rand.nextInt(world_area.height / grid_offset) * grid_offset);
        } while (!isPlaceFree(potentialPos));
        return CreateOrganism(o_t, potentialPos);
    }

    public Organism CreateOrganism(Organism o) {
        if (o == null)
            return null;
        if (turnsNum != 0) {
            o.breedSetPause(30);
            o.canMakeTurn = false;
        } else {
            o.breedSetPause(20);
            o.canMakeTurn = true;
        }
        organisms.add(o);
        return o;
    }

    public Organism CreateOrganism(ORGANISM_E o_t, Point p) {
        if (o_t == ORGANISM_E.WOLF)
            return CreateOrganism(new Wolf(9, 5, 0, p));
        else if (o_t == ORGANISM_E.SHEEP)
            return CreateOrganism(new Sheep(4, 4, 0, p));
        else if (o_t == ORGANISM_E.FOX)
            return CreateOrganism(new Fox(3, 7, 0, p));
        else if (o_t == ORGANISM_E.TURTLE)
            return CreateOrganism(new Turtle(2, 1, 0, p));
        else if (o_t == ORGANISM_E.ANTILOPE)
            return CreateOrganism(new Antilope(4, 4, 0, p));
        else if (o_t == ORGANISM_E.HUMAN)
            return CreateOrganism(new Human(5, 4, 0, p));

        else if (o_t == ORGANISM_E.GRASS)
            return CreateOrganism(new Grass(0, 0, 0, p));
        else if (o_t == ORGANISM_E.SONCHUS)
        return CreateOrganism(new Sonchus(0, 0, 0, p));
        else if (o_t == ORGANISM_E.GUARANA)
            return CreateOrganism(new Guarana(0, 0, 0, p));
        else if (o_t == ORGANISM_E.BELLADONNA)
            return CreateOrganism(new Belladonna(99, 0, 0, p));
        else if (o_t == ORGANISM_E.H_SOSNOWSKYI)
            return CreateOrganism(new H_Sosnowskyi(10, 0, 0, p));
        return null;
    }

    public void CreateOffspring(Organism p1, Organism p2) {
        if (!p1.getType().equals(p2.getType()))
            return;

        Point newPos = new Point();
        if (p1.getPos().equals(p2.getPos()))
            newPos = FindPosNearParents(p1.getPrevPos(), p2.getPos());
        else
            newPos = FindPosNearParents(p1.getPos(), p2.getPos());

        ORGANISM_E type = p1.getType();
        CreateOrganism(type, newPos);
    }

    public void CreatePlantOffspring(Plant p) {
        ORGANISM_E type = p.getType();
        PlantChunk pChunk = p.getChunk();
        int randPlantID = pChunk.getRandomPlantID();
        for (Organism organism : organisms) {
            if (organism.getId() == randPlantID) {
                Point new_pos = getFreePosNearby(organism.getPos(), 1);
                if (new_pos == organism.getPos())
                    return;
                Plant new_plant = (Plant) CreateOrganism(type, new_pos);
                new_plant.setChunk(pChunk);
                pChunk.addPlantID(new_plant.getId());
                break;
            }
        }
    }

    public void CreatePlantChunk(ORGANISM_E o_t, Point pos) {
        PlantChunk plantChunk = new PlantChunk();

        Plant plant = (Plant) CreateOrganism(o_t, pos);

        plantChunk.addPlantID(plant.getId());
        plantChunks.add(plantChunk);
        plant.setChunk(plantChunk);
    }

    public void CreatePlantChunk(ORGANISM_E o_t) {
        Point potentialPos;
        do {
            potentialPos = new Point(
                    rand.nextInt(world_area.width / grid_offset) * grid_offset,
                    rand.nextInt(world_area.height / grid_offset) * grid_offset);
        } while (!isPlaceFree(potentialPos));
        CreatePlantChunk(o_t, potentialPos);
    }

    public Point FindPosNearParents(Point p1, Point p2) {
        Point potentialPos = new Point(p2);
        while (potentialPos.equals(p2)) {
            potentialPos = getFreePosNearby(p1, 0);
        }
        return potentialPos;
    }

    public void ReactOnCollision(Organism this_o, Organism other_o) {
        COLLISION_STATUS c_s = this_o.collision(other_o);

        if (c_s == COLLISION_STATUS.BREED) {
            CreateOffspring(this_o, other_o);
            this_o.breedSetPause();
            other_o.breedSetPause();
        }

        if (c_s == COLLISION_STATUS.ESCAPE) {
            other_o.setPos(getFreePosNearby(other_o.getPos(), 0));
        }

        WListener.RecordCollision(c_s, this_o, other_o);
    }

    public void ReactOnAction(Organism this_o) {
        this_o.action(this);
    }

    public Point getRandomPosNearby(Point pos, int k) {
        Point potentialPos = new Point(0, 0);
        int dx[] = { -1, 1, 0, 0, -1, -1, 1, 1 };
        int dy[] = { 0, 0, -1, 1, -1, 1, -1, 1 };
        int dIndex[] = { 0, 1, 2, 3, 4, 5, 6, 7 };
        int iCount = 0;

        while (iCount != 8) {
            potentialPos = (Point) pos.clone();
            int randIndex = rand.nextInt(8);
            int sIndex = dIndex[randIndex];
            if (sIndex == -1)
                continue;

            potentialPos.x += dx[sIndex] * k * grid_offset;
            potentialPos.y += dy[sIndex] * k * grid_offset;
            if (world_area.contains(potentialPos))
                return potentialPos;
            else
                dIndex[sIndex] = -1;
            iCount++;
        }
        return pos;
    }

    public Point getFreePosNearby(Point pos, int close) {
        boolean min_pos_found = false;
        int radius = 1;
        ArrayList<Point> availablePoses = new ArrayList<>();

        while (!min_pos_found) {
            int k = radius * grid_offset;
            Point new_pos = null;
            availablePoses.clear();
            for (int y = pos.y - k; y <= pos.y + k; y += grid_offset) {
                for (int x = pos.x - k; x <= pos.x + k; x += grid_offset) {

                    if (x == pos.x - k || x == pos.x + k)
                        new_pos = new Point(x, y);
                    else if (x != pos.x - k || x != pos.x + k)
                        if (y == pos.y - k || y == pos.y + k)
                            new_pos = new Point(x, y);

                    if (new_pos != null && WithinWorldArea(new_pos)) {
                        min_pos_found = true;
                        for (Organism other_o : organisms)
                            if (other_o.getPos().equals(new_pos)) {
                                min_pos_found = false;
                                break;
                            }
                        if (min_pos_found)
                            availablePoses.add(new_pos);
                    }
                }
            }
            if (availablePoses.size() == 0) {
                if (close == 0 || radius + 1 <= close)
                    radius++;
                else 
                    return pos;
            } else
                return availablePoses.get(rand.nextInt(availablePoses.size()));
        }
        return pos;
    }

    public boolean WithinWorldArea(Point pos) {
        return world_area.contains(pos);
    }
}
