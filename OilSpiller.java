import java.util.List;
import java.util.Random;
import java.util.ArrayList;
import java.util.LinkedList;

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
    private Random random;

    private static final int MAX_SPILL_RADIUS = 3;

    /**
     * Constructor for objects of class OilSpiller
     */
    public OilSpiller(MasterField simulationField)
    {
        this.simulationField = simulationField;
        this.oilSpills = new ArrayList<OilSpill>();
        this.random = new Random();
    }

    public void spillOil(Location location){
        int oilSpillRadius = generateOilSpillRadius();
        OilSpill newOilSpill = new OilSpill(simulationField, location, oilSpillRadius);
        oilSpills.add(newOilSpill);

    }

    private int generateOilSpillRadius(){
        return random.nextInt(MAX_SPILL_RADIUS) + 1;
    }

    public void step(){
        for (OilSpill oilSpill : oilSpills)
        {
            oilSpill.step();
        }
        checkActiveOilSpills();
    }

    private void checkActiveOilSpills(){
        ArrayList<OilSpill> remainingActive = new ArrayList<OilSpill>();
        for (OilSpill oilSpill : oilSpills){
            if (oilSpill.IsActive()){
                remainingActive.add(oilSpill);
            }
        }
        oilSpills = remainingActive;
    }
}
