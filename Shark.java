import java.util.List;
import java.util.Set;
import java.util.Arrays;
import java.util.HashSet;

/**
 * A simple model of a fox.
 * Foxes age, move, eat rabbits, and die.
 * 
 * @author David J. Barnes and Michael Kölling
 * @version 2016.02.29 (2)
 */
public class Shark extends Animal {
    private static final int MIN_BREEDING_AGE = 14;

    private static final int MAX_BREEDING_AGE = 30;

    private static final int MAX_FOOD_LEVEL = 500;

    private static final int MIN_DEATH_AGE = 20;

    private static final int MAX_DEATH_AGE = 30;

    private static final int FOOD_VALUE = 250;

    private static final double SEXUAL_BREEDING_PROBABILITY = 0.1;

    private static final double ASEXUAL_BREEDING_PROBABILITY = 0.0;

    private static final int MAX_LITTER_SIZE = 10;

    private static final int MOVE_BUFFER = 1;

    protected static Set<String> PREY = new HashSet<String>(Arrays.asList("Dolphin"));

    // testing
    protected static final String name = "Shark";

    /**
     * Create a fox. A fox can be created as a new born (age zero
     * and not hungry) or with a random age and food level.
     * 
     * @param randomAge If true, the fox will have random age and hunger level.
     * @param field     The field currently occupied.
     * @param location  The location within the field.
     */
    public Shark(boolean randomAge, MasterField field, Location location) {
        super(randomAge, field, location);
    }

    /**
     * Check whether or not this fox is to give birth at this step.
     * New births will be made into free adjacent locations.
     * 
     * @param newFoxes A list to return newly born foxes.
     */
    protected void giveBirth(List<Organism> newSharks) {
        // New foxes are born into adjacent locations.
        // Get a list of adjacent free locations.
        Field field = getPhysicalField();
        List<Location> free = field.getFreeAdjacentLocations(getLocation());
        int births = breedSexually();
        for (int b = 0; b < births && free.size() > 0; b++) {
            Location loc = free.remove(0);
            Shark young = new Shark(false, getSimulationField(), loc);
            newSharks.add(young);
        }
    }
    
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

    // testing
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
