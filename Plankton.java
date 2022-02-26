import java.util.List;

/**
 * Write a description of class Plankton here.
 *
 * @author (your name)
 * @version (a version number or a date)
 */
public class Plankton extends Plant {

    private static final int FOOD_VALUE = 2;

    private static final double ASEXUAL_BREEDING_PROBABILITY = 0.2;

    private static final int MOVE_BUFFER = 2;

    protected static final String name = "Plankton";

    public Plankton(MasterField field, Location location) {
        super(field, location);
    }

    protected void giveBirth(List<Organism> newPlankton, boolean isDay) {
        // New foxes are born into adjacent locations.
        // Get a list of adjacent free locations.
        Field field = getPhysicalField();
        List<Location> free = field.getFreeAdjacentLocations(getLocation());
        int births = breedAsexually();
        for (int b = 0; b < births && free.size() > 0; b++) {
            Location loc = free.remove(0);
            Plankton young = new Plankton(getSimulationField(), loc);
            newPlankton.add(young);
        }
    }
    
    protected boolean isActive(boolean isDay)
    {
        return true;
    }

    protected String getName() {
        return name;
    }

    protected double getAsexualBreedingProbability() {
        return ASEXUAL_BREEDING_PROBABILITY;
    }

    protected int getMoveBuffer() {
        return MOVE_BUFFER;
    }

    protected int getFoodValue() {
        return FOOD_VALUE;
    }
}
