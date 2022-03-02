import java.util.List;
import java.util.Set;
import java.util.Arrays;
import java.util.HashSet;

/**
 * A simple model of a snail
 * Snails age, move, eat algae, breed, and die.
 * 
 * @author Konrad Bylina [K20014050] & Matt Stanbrell [K21044080]
 * @version 2022.03.02
 */
public class Snail extends Animal {
    // Characteristics shared by all snails (class variables).

    // The minimum age at which a snail can start to breed
    private static final int MIN_BREEDING_AGE = 1;
    // The maximum age at which a snail is capable of breeding
    private static final int MAX_BREEDING_AGE = 8;
    // Minimum age a snail will die (from simply getting too old)
    private static final int MIN_DEATH_AGE = 2;
    // Maximum age a snail can live
    private static final int MAX_DEATH_AGE = 10;
    // Max amount of food a snail can store
    private static final int MAX_FOOD_LEVEL = 100;
    // Other animals will gain 5 food level if they eat a snail
    private static final int FOOD_VALUE = 5;
    //10 changed for testing
    // Number of steps it takes for snail to move 1 square in field
    private static final int MOVE_BUFFER = 5;

    protected static final String name = "Snail";
    // 0.1
    // Probability of snail breeding successfuly when it meets
    // another snail
    private static final double SEXUAL_BREEDING_PROBABILITY = 0.99;
    // Probability of snail reproducing on its own
    private static final double ASEXUAL_BREEDING_PROBABILITY = 0.0;
    // The maximum number of births from sexual reproduction
    private static final int MAX_LITTER_SIZE = 100;
    // List of organisms snails eat
    protected static Set<String> PREY = new HashSet<String>(Arrays.asList("Algae"));

    /**
     * Create a snail
     * 
     * @param randomAge  If true, the snail will have random age and hunger level.
     * @param field      The field currently occupied.
     * @param location   The location within the field.
     */
    public Snail(boolean randomAge, MasterField field, Location location) {
        super(randomAge, field, location);
    }

    /**
     * Check whether or not this snail is to give birth at this step.
     * New births will be made into free adjacent locations.
     * 
     * @param newSnails A list to return newly born snails.
     */
    protected void giveBirth(List<Organism> newSnails) {
        // Get field containing animals
        Field field = getPhysicalField();
        // Get a list of adjacent free locations
        List<Location> free = field.getFreeAdjacentLocations(getLocation());
        int births = breedSexually();
        for (int b = 0; b < births && free.size() > 0; b++) {
            Location loc = free.remove(0);
            Snail young = new Snail(false, getSimulationField(), loc);
            newSnails.add(young);
        }
    }
    
    /**
     * Determines whether this snail is currently active
     * Snails are active during the day and inactive at night
     * 
     * @param isDay  Is it day or night
     * 
     * @return       Boolean (active or not)
     */
    protected boolean isActive(boolean isDay)
    {
        return isDay;
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
