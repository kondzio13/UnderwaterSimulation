import java.util.List;

/**
 * Write a description of class Plant here.
 *
 * @author (your name)
 * @version (a version number or a date)
 */
public abstract class Plant extends Organism {
    /**
     * Constructor for objects of class Plant
     */
    public Plant(MasterField field, Location location) {
        super(field, field.getEnvironmentField(), location);
    }

    public void act(List<Organism> newPlants, boolean isDay) {
        if (isAlive() && isActive(isDay)) {
            giveBirth(newPlants, isDay);

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

    abstract protected void giveBirth(List<Organism> newPlants, boolean isDay);
}
