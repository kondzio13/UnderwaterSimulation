import java.util.List;
import java.util.Random;

/**
 * A class representing the shared characteristics of all organisms
 *
 * @author Konrad Bylina [] & Matt Stanbrell [K21044080]
 */
public abstract class Organism {
    // Whether the organism is alive or not.
    private boolean alive;
    // The simulation field.
    private MasterField simulationField;
    // The field in which the organism is found
    protected Field physicalField;
    // The animal's position in the field.
    private Location location;
    // Organism's progress in moving one square
    private double moveBufferProgress = 0;
    
    protected Simulator simulator;

    protected static final Random rand = Randomizer.getRandom();

    /**
     * Create a new organism at location in field.
     * 
     * @param field           The field containing all organisms
     * @param physicalField   The field in which the organism is found
     * @param location        The location within the field
     */
    public Organism(MasterField field, Field physicalField, Location location) {
        alive = true;
        this.simulationField = field;
        this.physicalField = physicalField;
        setLocation(location);
    }

    /**
     * Check if organism is alive
     * 
     * @return  Boolean true if alive
     */
    protected boolean isAlive() {
        return alive;
    }

    /**
     * Set organism to dead
     */
    protected void setDead() {
        alive = false;
        if (location != null) {
            physicalField.clear(location);
            location = null;
            physicalField = null;
        }
    }

    /**
     * Increment the move buffer progress 
     * by however much the specifc organism is able to move per step
     */
    protected void incrementMoveBufferProgress() {
        // Move buffer is how many steps it takes to move one square
        // So 1/moveBuffer is the amount of one square the organism moves per step
        double progressAdded = (double) 1 / getMoveBuffer();
        moveBufferProgress += progressAdded;
    }

    /**
     * Check if organism is capable of moving
     * 
     * @return  Boolean true if moveBufferProgress has reached 1
     */
    protected boolean canMove() {
        return moveBufferProgress >= 1;
    }

    /**
     * Return the organism's organism field.
     * 
     * @return The organism's organism field.
     */
    protected MasterField getSimulationField() {
        return simulationField;
    }

    /**
     * Return the organism's physicalField
     * the field they can actually be found in
     * 
     * @return  physical field
     */
    protected Field getPhysicalField() {
        return physicalField;
    }

    /**
     * Set the organism's location
     */
    protected void setLocation(Location newLocation) {
        if (location != null) {
            physicalField.clear(location);
        }
        location = newLocation;
        physicalField.place(this, newLocation);
    }

    /**
     * Breeding asexually
     * 
     * @return  The number of possible births, always 2
     */
    protected int breedAsexually() {
        int births = 0;
        if (rand.nextDouble() <= getAsexualBreedingProbability()) {
            births = 2;
        }
        return births;
    }

    /**
     * Converts years to steps
     * steps are half a day
     * 
     * @return  Equivalent number of steps
     */
    protected int convertYearsToSteps(int years)
    {
        return years * 730;
    }
    
    protected Location getLocation() {
        return location;
    }

    abstract protected boolean isActive(boolean isDay);
    
    abstract protected void act(List<Organism> newOrganisms, boolean isDay);

    abstract protected String getName();

    abstract protected int getFoodValue();

    abstract protected int getMoveBuffer();

    abstract protected double getAsexualBreedingProbability();
}
