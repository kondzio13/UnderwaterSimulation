import java.util.List;

/**
 * Write a description of class Plant here.
 *
 * @author (your name)
 * @version (a version number or a date)
 */
public abstract class Plant extends Organism
{
    /**
     * Constructor for objects of class Plant
     */
    public Plant(MasterField field, Location location)
    {
        super(field, field.getEnvironmentField(), location);
    }
}
