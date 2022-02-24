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

    protected static final String name = "Plankton";
    
    public Plankton(MasterField field, Location location)
    {
        super(field, location);
    }

    public void act(List<Organism> newPlanktons)
    {
        
    }

    protected String getName(){
        return name;
    }

    @Override
    protected int getFoodValue() {
        return FOOD_VALUE;
    }
}
