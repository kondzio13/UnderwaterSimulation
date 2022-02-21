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
    // The animal's field.
    private Field field;
    // The animal's position in the field.
    private Location location;
    
    protected static final Random rand = Randomizer.getRandom();

    /**
     * Constructor for objects of class Organism
     */
    public Organism(Field field, Location location)
    {
        alive = true;
        this.field = field;
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
            field.clear(location);
            location = null;
            field = null;
        }
    }
    
    protected Field getField()
    {
        return field;
    }
    
    protected Location getLocation()
    {
        return location;
    }
    
    protected void setLocation(Location newLocation)
    {
        if(location != null) {
            field.clear(location);
        }
        location = newLocation;
        field.place(this, newLocation);
    }
    
    abstract public void act(List<Organism> newOrganisms);
}
