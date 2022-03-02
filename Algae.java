import java.util.List;

/**
 * A simple model of plankton
 * Plankton moves, breeds (asexually), and dies
 * Because of its characteristics, plankton is treated as a plant in this simulation
 * 
 * @author Konrad Bylina [K20014050] & Matt Stanbrell [K21044080]
 * @version 2022.03.02
 */
public class Algae extends Plant {
    // Characteristics shared by all algae (class variables).
    
    // Other animals will gain 1 food level if they eat algae
    private static final int FOOD_VALUE = 1;
    // The likelihood of algae reproducing asexually
    private static final double ASEXUAL_BREEDING_PROBABILITY = 0.01;
    // Algae does not move
    private static final int MOVE_BUFFER = -1;

    protected static final String name = "Algae";

    /**
     * Create a plankton object
     * 
     * @param simField   Container for both animal and environment fields
     * @param location   Location of the algae
     */
    public Algae(MasterField simField, Location location) {
        super(simField, location);
    }

    /**
     * Check whether or not this algae is to reproduce at this step.
     * New births will be made into free adjacent locations.
     * 
     * @param newAlgae  A list to return newly produced algae.
     */
    protected void giveBirth(List<Organism> newAlgae) {
        // Get field containing plants
        Field field = getPhysicalField();
        // Get a list of adjacent free locations
        List<Location> free = field.getFreeAdjacentLocations(getLocation());
        int births = breedAsexually();
        for (int b = 0; b < births && free.size() > 0; b++) {
            Location loc = free.remove(0);
            Algae young = new Algae(getSimulationField(), loc);
            newAlgae.add(young);
        }
    }
    
    /**
     * Determines whether this algae is currently active
     * Algae are active during the day and inactive at night
     * 
     * @param isDay  Is it day or night
     * 
     * @return       Boolean (active or not)
     */
    protected boolean isActive(boolean isDay)
    {
        return isDay;
    }

    protected String getName() {
        return name;
    }

    protected int getFoodValue() {
        return FOOD_VALUE;
    }

    protected double getAsexualBreedingProbability() {
        return ASEXUAL_BREEDING_PROBABILITY;
    }

    protected int getMoveBuffer() {
        return MOVE_BUFFER;
    }
}