import java.util.List;
import java.util.Set;
import java.util.Arrays;
import java.util.HashSet;

/**
 * A simple model of a jellyfish
 * Jellyfish age, move, eat plankton, breed, and die.
 * 
 * @author Konrad Bylina [] & Matt Stanbrell [K21044080]
 */
public class Jellyfish extends Animal {
    // Characteristics shared by all jellyfishs (class variables).

    // The minimum age at which a jellyfish can start to breed
    private static final int MIN_BREEDING_AGE = 1;
    // The maximum age at which a jellyfish is capable of breeding
    private static final int MAX_BREEDING_AGE = 999999;
    // Minimum age a jellyfish will die (from simply getting too old)
    private static final int MIN_DEATH_AGE = 1;
    // Maximum age a jellyfish can live (some jellyfish can effectively live forever)
    private static final int MAX_DEATH_AGE = 999999;
    // Max amount of food a jellyfish can store
    private static final int MAX_FOOD_LEVEL = 100;
    // Other animals will gain 5 food level if they eat a jellyfish
    private static final int FOOD_VALUE = 5;
    // Number of steps it takes for jellyfish to move 1 square in field
    private static final int MOVE_BUFFER = 6;

    protected static final String name = "Jellyfish";
    // Probability of jellyfish breeding successfuly when it meets
    // another jellyfish
    private static final double SEXUAL_BREEDING_PROBABILITY = 0.02;
    // Probability of jellyfish reproducing on its own
    private static final double ASEXUAL_BREEDING_PROBABILITY = 0.01;
    // The maximum number of births from sexual reproduction
    private static final int MAX_LITTER_SIZE = 2;
    // List of organisms jellyfishs eat
    protected static Set<String> PREY = new HashSet<String>(Arrays.asList("Plankton"));

    /**
     * Create a jellyfish
     * 
     * @param randomAge  If true, the jellyfish will have random age and hunger level.
     * @param field      The field currently occupied.
     * @param location   The location within the field.
     */
    public Jellyfish(boolean randomAge, MasterField field, Location location) {
        super(randomAge, field, location);
    }

    /**
     * Check whether or not this jellyfish is to give birth at this step.
     * New births will be made into free adjacent locations.
     * 
     * @param newJellyfishs A list to return newly born jellyfishs.
     */
    protected void giveBirth(List<Organism> newJellyfish) {
        // Get field containing animals
        Field field = getPhysicalField();
        // Get a list of adjacent free locations
        List<Location> free = field.getFreeAdjacentLocations(getLocation());
        int births = breedSexually();
        births += breedAsexually();
        for (int b = 0; b < births && free.size() > 0; b++) {
            Location loc = free.remove(0);
            Jellyfish young = new Jellyfish(false, getSimulationField(), loc);
            newJellyfish.add(young);
        }
    }
    
    /**
     * Determines whether this jellyfish is currently active
     * Jellyfishs are active during the day and night
     * 
     * @param isDay  Is it day or night
     * 
     * @return       Boolean (active or not, always true)
     */
    protected boolean isActive(boolean isDay)
    {
        return true;
    }
    
    protected int getMinBreedingAge() {
        return MIN_BREEDING_AGE;
    }

    protected int getMaxBreedingAge() {
        return MAX_BREEDING_AGE;
    }

    protected int getMaxFoodLevel() {
        return MAX_FOOD_LEVEL;
    }

    protected int getMinDeathAge() {
        return MIN_DEATH_AGE;
    }

    protected int getMaxDeathAge() {
        return MAX_DEATH_AGE;
    }

    protected int getFoodValue() {
        return FOOD_VALUE;
    }

    protected String getName() {
        return name;
    }

    protected Set<String> getPreyList() {
        return PREY;
    }

    protected double getSexualBreedingProbability() {
        return SEXUAL_BREEDING_PROBABILITY;
    }

    protected int getMaxLitterSize() {
        return MAX_LITTER_SIZE;
    }

    protected double getAsexualBreedingProbability() {
        return ASEXUAL_BREEDING_PROBABILITY;
    }

    protected int getMoveBuffer() {
        return MOVE_BUFFER;
    }
}
