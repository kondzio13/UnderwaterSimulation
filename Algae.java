import java.util.List;

/**
 * Model for Algae.
 * Algae can be eaten or grow to neighbouring fields.
 * 
 * @author David J. Barnes and Michael Kölling
 * @version 2016.02.29 (2)
 */
public class Algae extends Plant {
    // Characteristics shared by all algae (class variables).
    // The likelihood of a fox breeding.
    private static final double ASEXUAL_BREEDING_PROBABILITY = 0.01;
    private static final int FOOD_VALUE = 1;

    // The food value of a single rabbit. In effect, this is the
    private static final int MOVE_BUFFER = -1;

    protected static final String name = "Algae";

    /**
     * Create a fox. A fox can be created as a new born (age zero
     * and not hungry) or with a random age and food level.
     * 
     * @param randomAge If true, the fox will have random age and hunger level.
     * @param field     The field currently occupied.
     * @param location  The location within the field.
     */
    public Algae(MasterField simField, Location location) {
        super(simField, location);
    }

    /**
     * Check whether or not this fox is to give birth at this step.
     * New births will be made into free adjacent locations.
     * 
     * @param newFoxes A list to return newly born foxes.
     */
    protected void giveBirth(List<Organism> newAlgae,  boolean isDay) {
        // New foxes are born into adjacent locations.
        // Get a list of adjacent free locations.
        Field field = getPhysicalField();
        List<Location> free = field.getFreeAdjacentLocations(getLocation());
        int births = breedAsexually();
        for (int b = 0; b < births && free.size() > 0; b++) {
            Location loc = free.remove(0);
            Algae young = new Algae(getSimulationField(), loc);
            newAlgae.add(young);
        }
    }
    
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