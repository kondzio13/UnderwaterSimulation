import java.util.List;
import java.util.ArrayList;

/**
 * Write a description of class OilSpiller here.
 *
 * @author (your name)
 * @version (a version number or a date)
 */
public class OilSpiller
{
    // instance variables - replace the example below with your own
    private MasterField simulationField;
    private List<OilSpill> oilSpills;

    /**
     * Constructor for objects of class OilSpiller
     */
    public OilSpiller(MasterField simulationField)
    {
        this.simulationField = simulationField;
        this.oilSpills = new ArrayList<OilSpill>();
        
    }
}
