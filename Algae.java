import java.util.List;

/**
 * Write a description of class Plankton here.
 *
 * @author (your name)
 * @version (a version number or a date)
 */
public class Algae extends Plant
{
    private static final int FOOD_VALUE = 1;
    
    private static final double ASEXUAL_BREEDING_PROBABILITY = 0.4;
    
    private static final int MOVE_BUFFER = 999999;
    
    public Algae(Field field, Location location)
    {
        super(field, location);
    }

    public void act(List<Organism> newAlgae)
    {
        
    }
}
