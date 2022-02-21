import java.util.List;

/**
 * Write a description of class Plankton here.
 *
 * @author (your name)
 * @version (a version number or a date)
 */
public class Plankton extends Plant
{
    private static final int FOOD_VALUE = 2;
    
    private static final double ASEXUAL_BREEDING_PROBABILITY = 0.2;
    
    private static final int MOVE_BUFFER = 2;
    
    public Plankton(Field field, Location location)
    {
        super(field, location);
    }

    public void act(List<Organism> newPlanktons)
    {
        
    }
}
