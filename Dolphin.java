import java.util.List;
import java.util.Set;
import java.util.Arrays;
import java.util.HashSet;

/**
 * A simple model of a dolphin
 * Dolphins age, move, eat jellyfish, argonauts, and plankton, breed, and die.
 * 
 * @author Konrad Bylina [] & Matt Stanbrell [K21044080]
 */
public class Dolphin extends Animal {
    // Characteristics shared by all dolphins (class variables).

    // The minimum age at which a dolphin can start to breed
    private static final int MIN_BREEDING_AGE = 5;
    // The maximum age at which a dolphin is capable of breeding
    private static final int MAX_BREEDING_AGE = 15;
    // Minimum age a dolphin will die (from simply getting too old)
    private static final int MIN_DEATH_AGE = 40;
    // Maximum age a dolphin can live
    private static final int MAX_DEATH_AGE = 60;
    // Max amount of food a dolphin can store
    private static final int MAX_FOOD_LEVEL = 300;
    // Other animals will gain 100 food level if they eat a dolphin
    private static final int FOOD_VALUE = 100;
    // Number of steps it takes for dolphin to move 1 square in field
    private static final int MOVE_BUFFER = 2;
    
    protected static final String name = "Dolphin";
    //0.1
    // Probability of dolphin breeding successfuly when it meets
    // another dolphin
    private static final double SEXUAL_BREEDING_PROBABILITY = 0.99;
    // Probability of dolphin reproducing on its own
    private static final double ASEXUAL_BREEDING_PROBABILITY = 0.0;
    // The maximum number of births from sexual reproduction
    private static final int MAX_LITTER_SIZE = 1;
    // List of organisms dolphins eat
    protected static Set<String> PREY = new HashSet<String>(Arrays.asList("Jellyfish", "Plankton", "Argonaut"));

    /**
     * Create a dolphin
     * 
     * @param randomAge  If true, the dolphin will have random age and hunger level.
     * @param field      The field currently occupied.
     * @param location   The location within the field.
     */
    public Dolphin(boolean randomAge, MasterField field, Location location) {
        super(randomAge, field, location);
    }

    /**
     * Check whether or not this dolphin is to give birth at this step.
     * New births will be made into free adjacent locations.
     * 
     * @param newDolphins A list to return newly born dolphins.
     */
    protected void giveBirth(List<Organism> newDolphins) {
        // Get field containing animals
        Field field = getPhysicalField();
        // Get a list of adjacent free locations
        List<Location> free = field.getFreeAdjacentLocations(getLocation());
        int births = breedSexually();
        for (int b = 0; b < births && free.size() > 0; b++) {
            Location loc = free.remove(0);
            Dolphin young = new Dolphin(false, getSimulationField(), loc);
            newDolphins.add(young);
        }
    }
    
    /**
     * Determines whether this dolphin is currently active
     * Dolphins are active during the day and night
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
