import java.util.List;

/**
 * A simple model of plankton
 * Plankton moves, breeds (asexually), and dies
 * Because of its characteristics, plankton is treated as a plant in this simulation
 * 
 * @author Konrad Bylina [] & Matt Stanbrell [K21044080]
 */
public class Plankton extends Plant {
    // Characteristics shared by all plankton (class variables).
    
    // Other animals will gain 2 food level if they eat a plankton
    private static final int FOOD_VALUE = 2;
    // Probability of plankton reproducing on its own
    private static final double ASEXUAL_BREEDING_PROBABILITY = 0.2;
    // Number of steps it takes for plankton to move 1 square in field
    private static final int MOVE_BUFFER = 2;

    protected static final String name = "Plankton";

    /**
     * Create a plankton object
     * 
     * @param field      Container for both animal and environment fields
     * @param location   Location of the plankton
     */
    public Plankton(MasterField field, Location location) {
        super(field, location);
    }
    
    /**
     * Check whether or not this plankton is to reproduce at this step.
     * New births will be made into free adjacent locations.
     * 
     * @param newPlankton A list to return newly produced plankton.
     */
    protected void giveBirth(List<Organism> newPlankton) {
        // Get field containing plants
        Field field = getPhysicalField();
        // Get a list of adjacent free locations
        List<Location> free = field.getFreeAdjacentLocations(getLocation());
        int births = breedAsexually();
        for (int b = 0; b < births && free.size() > 0; b++) {
            Location loc = free.remove(0);
            Plankton young = new Plankton(getSimulationField(), loc);
            newPlankton.add(young);
        }
    }
    
    /**
     * Determines whether this plankton is currently active
     * Plankton are active during the day and night
     * 
     * @param isDay  Is it day or night
     * 
     * @return       Boolean (active or not, always true)
     */
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
