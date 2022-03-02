import java.util.List;

/**
 * A class representing shared characteristics of plants.
 * 
 * @author Konrad Bylina [K20014050] & Matt Stanbrell [K21044080]
 * @version 2022.03.02
 */
public abstract class Plant extends Organism {
    /**
     * Constructor for objects of class Plant
     * 
     * @param field     Container for both animal and environment fields
     * @param location  Location of this plant
     */
    public Plant(MasterField field, Location location) {
        super(field, field.getEnvironmentField(), location);
    }

    /**
     * Make plant do whatever it needs to do
     * 
     * @param newPlants  A list to receive newly produced plants
     * @param isDay      Whether it is currently day or night
     */
    public void act(List<Organism> newPlants, boolean isDay) {
        // If plant is alive and active (algae is inactive at night as it requires sunlight)
        if (isAlive() && isActive(isDay)) {
            giveBirth(newPlants);

            Location newLocation = getPhysicalField().freeAdjacentLocation(getLocation());
            // See if it was possible to move.
            if (newLocation != null) {
                setLocation(newLocation);
            } else {
                // Overcrowding.
                setDead();
            }
        }
    }

    abstract protected void giveBirth(List<Organism> newPlants);
}
