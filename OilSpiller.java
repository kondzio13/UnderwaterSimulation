import java.util.List;
import java.util.Random;
import java.util.ArrayList;

/**
 * Controller for the oil spill environmental event
 *
 * @author Konrad Bylina [K20014050] & Matt Stanbrell [K21044080]
 * @version 2022.03.02
 */
public class OilSpiller
{
    // instance variables
    private MasterField simulationField;
    private List<OilSpill> oilSpills;
    private Random random;

    //Constant representing the maximum radius of an oil spill
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

    /**
     * Creates a new oil spill and adds it to the oilSpills list
     * 
     * @param location
     */
    public void spillOil(Location location){
        int oilSpillRadius = generateOilSpillRadius();
        OilSpill newOilSpill = new OilSpill(simulationField, location, oilSpillRadius);
        oilSpills.add(newOilSpill);

    }

    /**
     * Returns a random radius size
     */
    private int generateOilSpillRadius(){
        return random.nextInt(MAX_SPILL_RADIUS) + 1;
    }

    /**
     * Let all active oil spills know the simulation is at the next step
     */
    public void step(){
        for (OilSpill oilSpill : oilSpills)
        {
            oilSpill.step();
        }
        checkActiveOilSpills();
    }

    /**
     * Check whether oil spills are active and update oilSpills list
     */
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
