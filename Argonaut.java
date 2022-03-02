import java.util.List;
import java.util.Set;
import java.util.Arrays;
import java.util.HashSet;

/**
 * A simple model of an Argonaut (a type of octopus)
 * Argonauts age, move, eat jellyfish and snails, breed, and die.
 * 
 * @author Konrad Bylina [K20014050] & Matt Stanbrell [K21044080]
 * @version 2022.03.02
 */
public class Argonaut extends Animal {
    // Characteristics shared by all argonauts (class variables).

    // The minimum age at which an argonaut can start to breed
    private static final int MIN_BREEDING_AGE = 0;
    // The maximum age at which an argonaut is capable of breeding
    private static final int MAX_BREEDING_AGE = 1;
    // Minimum age an argonaut will die (from simply getting too old)
    private static final int MIN_DEATH_AGE = 1;
    // Maximum age an argonaut can live
    private static final int MAX_DEATH_AGE = 2;
    // Max amount of food an argonaut can store
    private static final int MAX_FOOD_LEVEL = 30;
    // Other animals will gain 15 food level if they eat an argonaut
    private static final int FOOD_VALUE = 15;
    // Number of steps it takes for argonaut to move 1 square in field
    private static final int MOVE_BUFFER = 4;

    protected static final String name = "Argonaut";

    //0.2
    // Probability of argonaut breeding successfuly when it meets
    // another argonaut
    private static final double SEXUAL_BREEDING_PROBABILITY = 0.15;
    // Probability of argonaut reproducing on its own
    private static final double ASEXUAL_BREEDING_PROBABILITY = 0.0;
    // The maximum number of births from sexual reproduction
    private static final int MAX_LITTER_SIZE = 3;
    // List of organisms argonauts eat
    protected static Set<String> PREY = new HashSet<String>(Arrays.asList("Jellyfish", "Snail"));

    /**
     * Create an argonaut
     * 
     * @param randomAge  If true, the fox will have random age and hunger level.
     * @param field      The field currently occupied.
     * @param location   The location within the field.
     */
    public Argonaut(boolean randomAge, MasterField field, Location location) {
        super(randomAge, field, location);
    }

    /**
     * Check whether or not this argonaut is to give birth at this step.
     * New births will be made into free adjacent locations.
     * 
     * @param newArgonauts A list to return newly born argonauts.
     */
    protected void giveBirth(List<Organism> newArgonauts) {
        // Get field containing animals
        Field field = getPhysicalField();
        // Get a list of adjacent free locations
        List<Location> free = field.getFreeAdjacentLocations(getLocation());
        int births = breedSexually();
        for (int b = 0; b < births && free.size() > 0; b++) {
            Location loc = free.remove(0);
            Argonaut young = new Argonaut(false, getSimulationField(), loc);
            newArgonauts.add(young);
        }
    }
    
    /**
     * Determines whether this argonaut is currently active
     * Argonauts are not active at night
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
