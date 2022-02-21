import java.util.List;
import java.util.Random;

/**
 * Write a description of class Organism here.
 *
 * @author (your name)
 * @version (a version number or a date)
 */
public abstract class Organism
{
    // Whether the animal is alive or not.
    private boolean alive;
    // The simulation field.
    private MasterField simulationField;
    //The field in which the organism is found
    private Field physicalField;
    // The animal's position in the field.
    private Location location;
    
    protected static final Random rand = Randomizer.getRandom();

    /**
     * Constructor for objects of class Organism
     */
    public Organism(MasterField field, Field physicalField, Location location)
    {
        alive = true;
        this.simulationField = field;
        this.physicalField = physicalField;
        setLocation(location);
    }
    
    protected boolean isAlive()
    {
        return alive;
    }
    
    protected void setDead()
    {
        alive = false;
        if(location != null) {
            physicalField.clear(location);
            location = null;
            physicalField = null;
        }
    }
    
    /**
     * Return the animal's animal field.
     * @return The animal's animal field.
     */
    protected MasterField getSimulationField()
    {
        return simulationField;
    }

    /**
     * Return the organism's physicalField
     * @return
     */
    protected Field getPhysicalField()
    {
        return physicalField;
    }
    
    protected Location getLocation()
    {
        return location;
    }
    
    protected void setLocation(Location newLocation)
    {
        if(location != null) {
            physicalField.clear(location);
        }
        location = newLocation;
        physicalField.place(this, newLocation);
    }
    
    abstract protected void act(List<Organism> newOrganisms);
}
