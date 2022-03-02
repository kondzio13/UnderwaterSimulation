import java.util.List;
import java.util.Set;
import java.util.Arrays;
import java.util.HashSet;

/**
 * A simple model of a shark
 * Sharks age, move, eat dolphins, breed, and die.
 * 
 * @author Konrad Bylina [] & Matt Stanbrell [K21044080]
 */
public class Shark extends Animal {
    // Characteristics shared by all sharks (class variables).

    // The minimum age at which a shark can start to breed
    private static final int MIN_BREEDING_AGE = 14;
    // The maximum age at which a shark is capable of breeding
    private static final int MAX_BREEDING_AGE = 30;
    // Minimum age a shark will die (from simply getting too old)
    private static final int MIN_DEATH_AGE = 20;
    // Maximum age a shark can live
    private static final int MAX_DEATH_AGE = 30;
    // Max amount of food a shark can store
    private static final int MAX_FOOD_LEVEL = 500;
    // Other animals will gain 250 food level if they eat a shark
    private static final int FOOD_VALUE = 250;
    // Number of steps it takes for shark to move 1 square in field
    private static final int MOVE_BUFFER = 1;
    
    protected static final String name = "Shark";
    //0.1
    // Probability of shark breeding successfuly when it meets
    // another shark
    private static final double SEXUAL_BREEDING_PROBABILITY = 0.2;
    // Probability of shark reproducing on its own
    private static final double ASEXUAL_BREEDING_PROBABILITY = 0.0;
    // The maximum number of births from sexual reproduction
    private static final int MAX_LITTER_SIZE = 10;
    // List of organisms sharks eat
    protected static Set<String> PREY = new HashSet<String>(Arrays.asList("Dolphin"));
    
    /**
     * Create a shark
     * 
     * @param randomAge  If true, the shark will have random age and hunger level.
     * @param field      Container for both animal and environment fields
     * @param location   The location within the field.
     */
    public Shark(boolean randomAge, MasterField field, Location location) {
        super(randomAge, field, location);
    }

    /**
     * Check whether or not this shark is to give birth at this step.
     * New births will be made into free adjacent locations.
     * 
     * @param newSharks A list to return newly born sharks.
     */
    protected void giveBirth(List<Organism> newSharks) {
        // Get field containing animals
        Field field = getPhysicalField();
        // Get a list of adjacent free locations
        List<Location> free = field.getFreeAdjacentLocations(getLocation());
        int births = breedSexually();
        for (int b = 0; b < births && free.size() > 0; b++) {
            Location loc = free.remove(0);
            Shark young = new Shark(false, getSimulationField(), loc);
            newSharks.add(young);
        }
    }
    
    /**
     * Determines whether this shark is currently active
     * Sharks are active during the day and night
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

    protected Set<String> getPreyList() {
        return PREY;
    }

    protected double getSexualBreedingProbability() {
        return SEXUAL_BREEDING_PROBABILITY;
    }

    protected int getMaxLitterSize() {
        return MAX_LITTER_SIZE;
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
}
