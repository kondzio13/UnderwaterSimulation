import java.util.List;

/**
 * A class representing shared characteristics of animals.
 * 
 * @author David J. Barnes and Michael Kölling
 * @version 2016.02.29 (2)
 */
public abstract class Animal
{
    // Whether the animal is alive or not.
    private boolean alive;
    // The animal's field.
    protected MasterField simulationField;
    //The field in which the animal is found
    protected Field physicalField;
    // The animal's position in the field.
    protected Location location;
    
    /**
     * Create a new animal at location in field.
     * 
     * @param field The field currently occupied.
     * @param location The location within the field.
     */
    public Animal(MasterField simulationField, Field physicalField, Location location)
    {
        alive = true;
        this.simulationField = simulationField;
        this.physicalField = physicalField;
        setLocation(location);
    }
    
    /**
     * Make this animal act - that is: make it do
     * whatever it wants/needs to do.
     * @param newAnimals A list to receive newly born animals.
     */
    abstract public void act(List<Animal> newAnimals);

    /**
     * Check whether the animal is alive or not.
     * @return true if the animal is still alive.
     */
    protected boolean isAlive()
    {
        return alive;
    }

    /**
     * Indicate that the animal is no longer alive.
     * It is removed from the field.
     */
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
     * Return the animal's location.
     * @return The animal's location.
     */
    protected Location getLocation()
    {
        return location;
    }
    
    /**
     * Place the animal at the new location in the given field.
     * @param newLocation The animal's new location.
     */
    protected void setLocation(Location newLocation)
    {
        if(location != null) {
            physicalField.clear(location);
        }
        location = newLocation;
        physicalField.place(this, newLocation);
    }
    
    /**
     * Return the animal's animal field.
     * @return The animal's animal field.
     */
    protected Field getAnimalField()
    {
        return simulationField.getAnimalField();
    }
    
    /**
     * Return the animal's environment field.
     * @return The animal's environment field.
     */
    protected Field getEnvironmentField()
    {
        return simulationField.getEnvironmentField();
    }
}
