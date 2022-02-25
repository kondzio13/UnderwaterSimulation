import java.util.List;
import java.util.Set;
import java.util.Arrays;
import java.util.HashSet;

/**
 * A simple model of a rabbit.
 * Rabbits age, move, breed, and die.
 * 
 * @author David J. Barnes and Michael Kölling
 * @version 2016.02.29 (2)
 */
public class Jellyfish extends Animal {
    // Characteristics shared by all rabbits (class variables).

    // The age at which a rabbit can start to breed.
    private static final int MIN_BREEDING_AGE = 1;

    private static final int MAX_BREEDING_AGE = 999999;
    // The age to which a rabbit can live.
    private static final int MIN_DEATH_AGE = 1;

    private static final int MAX_DEATH_AGE = 999999;

    private static final int MAX_FOOD_LEVEL = 100;

    private static final int FOOD_VALUE = 5;

    protected static Set<String> PREY = new HashSet<String>(Arrays.asList("Plankton"));

    //
    protected static final String name = "Jellyfish";

    // The likelihood of a rabbit breeding.
    private static final double SEXUAL_BREEDING_PROBABILITY = 0.02;

    private static final double ASEXUAL_BREEDING_PROBABILITY = 0.01;

    private static final int MAX_LITTER_SIZE = 2;

    private static final int MOVE_BUFFER = 6;

    /**
     * Create a new rabbit. A rabbit may be created with age
     * zero (a new born) or with a random age.
     * 
     * @param randomAge If true, the rabbit will have a random age.
     * @param field     The field currently occupied.
     * @param location  The location within the field.
     */
    public Jellyfish(boolean randomAge, MasterField field, Location location) {
        super(randomAge, field, location);
    }

    /**
     * Check whether or not this rabbit is to give birth at this step.
     * New births will be made into free adjacent locations.
     * 
     * @param newRabbits A list to return newly born rabbits.
     */
    protected void giveBirth(List<Organism> newJellyfish) {
        // New rabbits are born into adjacent locations.
        // Get a list of adjacent free locations.
        Field field = getPhysicalField();
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
     * Generate a number representing the number of births,
     * if it can breed.
     * 
     * @return The number of births (may be zero).
     */

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
