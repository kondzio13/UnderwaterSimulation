import java.util.List;
import java.util.Iterator;
import java.util.Random;
import java.util.ArrayList;

/**
 * A simple model of a fox.
 * Foxes age, move, eat rabbits, and die.
 * 
 * @author David J. Barnes and Michael Kölling
 * @version 2016.02.29 (2)
 */
public class Argonaut extends Animal
{
    // Characteristics shared by all foxes (class variables).
    
    // The age at which a fox can start to breed.
    private static final int MIN_BREEDING_AGE = 0;
    
    private static final int MAX_BREEDING_AGE = 1;
    
    private static final int MIN_DEATH_AGE = 1;
    
    private static final int MAX_DEATH_AGE = 2;
    
    private static final int MAX_FOOD_LEVEL = 30;
    
    private static final int FOOD_VALUE = 15;
    
    //
    private static final String name = "Argonaut";
    
    // The likelihood of a fox breeding.
    private static final double SEXUAL_BREEDING_PROBABILITY = 0.2;
    // The maximum number of births.
    private static final int MAX_LITTER_SIZE = 3;
    // A shared random number generator to control breeding.
    
    protected static List<Class<? extends Organism>> PREY = new ArrayList<Class<? extends Organism>>();
    

    /**
     * Create a fox. A fox can be created as a new born (age zero
     * and not hungry) or with a random age and food level.
     * 
     * @param randomAge If true, the fox will have random age and hunger level.
     * @param field The field currently occupied.
     * @param location The location within the field.
     */
    public Argonaut(boolean randomAge, MasterField field, Location location)
    {
        super(randomAge, field, location);
        PREY.add(Jellyfish.class);
        PREY.add(Snail.class);
    }
    
    /**
     * Check whether or not this fox is to give birth at this step.
     * New births will be made into free adjacent locations.
     * @param newFoxes A list to return newly born foxes.
     */
    protected void giveBirth(List<Organism> newArgonauts)
    {
        // New foxes are born into adjacent locations.
        // Get a list of adjacent free locations.
        Field field = getPhysicalField();
        List<Location> free = field.getFreeAdjacentLocations(getLocation());
        int births = breedSexually();
        for(int b = 0; b < births && free.size() > 0; b++) {
            Location loc = free.remove(0);
            Argonaut young = new Argonaut(false, getSimulationField(), loc);
            newArgonauts.add(young);
        }
    }

    protected int getMinBreedingAge()
    {
        return MIN_BREEDING_AGE;
    }
    
    protected int getMaxBreedingAge()
    {
        return MAX_BREEDING_AGE;
    }
    
    protected int getMaxFoodLevel()
    {
        return MAX_FOOD_LEVEL;
    }
    
    protected int getMinDeathAge()
    {
        return MIN_DEATH_AGE;
    }
    
    protected int getMaxDeathAge()
    {
        return MAX_DEATH_AGE;
    }
    
    protected int getFoodValue()
    {
        return FOOD_VALUE;
    }
    
    protected String getName()
    {
        return name;
    }
    
    protected List<Class<? extends Organism>> getPreyList()
    {
        return PREY;
    }
    
    protected double getSexualBreedingProbability()
    {
        return SEXUAL_BREEDING_PROBABILITY;
    }
    
    protected int getMaxLitterSize()
    {
        return MAX_LITTER_SIZE;
    }
}