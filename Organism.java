import java.util.List;
import java.util.Random;

/**
 * Write a description of class Organism here.
 *
 * @author (your name)
 * @version (a version number or a date)
 */
public abstract class Organism {
    // Whether the animal is alive or not.
    private boolean alive;
    // The simulation field.
    private MasterField simulationField;
    // The field in which the organism is found
    protected Field physicalField;
    // The animal's position in the field.
    private Location location;

    private double moveBufferProgress = 0;
    
    protected Simulator simulator;

    protected static final Random rand = Randomizer.getRandom();

    /**
     * Constructor for objects of class Organism
     */
    public Organism(MasterField field, Field physicalField, Location location) {
        alive = true;
        this.simulationField = field;
        this.physicalField = physicalField;
        setLocation(location);
    }

    protected boolean isAlive() {
        return alive;
    }

    protected void setDead() {
        alive = false;
        if (location != null) {
            physicalField.clear(location);
            location = null;
            physicalField = null;
        }
    }

    protected void incrementMoveBufferProgress() {
        double progressAdded = (double) 1 / getMoveBuffer();
        moveBufferProgress += progressAdded;
    }

    protected boolean canMove() {
        return moveBufferProgress >= 1;
    }

    /**
     * Return the animal's animal field.
     * 
     * @return The animal's animal field.
     */
    protected MasterField getSimulationField() {
        return simulationField;
    }

    /**
     * Return the organism's physicalField
     * 
     * @return
     */
    protected Field getPhysicalField() {
        return physicalField;
    }

    protected Location getLocation() {
        return location;
    }

    protected void setLocation(Location newLocation) {
        if (location != null) {
            physicalField.clear(location);
        }
        location = newLocation;
        physicalField.place(this, newLocation);
    }

    protected int breedAsexually() {
        int births = 0;
        if (rand.nextDouble() <= getAsexualBreedingProbability()) {
            births = 2;
        }
        return births;
    }
    
    protected int convertYearsToSteps(int years)
    {
        return years * 730;
    }

    abstract protected boolean isActive(boolean isDay);
    
    abstract protected void act(List<Organism> newOrganisms, boolean isDay);

    // just for testing
    abstract protected String getName();

    abstract protected int getFoodValue();

    abstract protected int getMoveBuffer();

    abstract protected double getAsexualBreedingProbability();
}
