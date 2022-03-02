import java.util.List;
import java.util.Random;

/**
 * Class responsible for controlling environmental events such as spontaneous plant growth and oil spills
 *
 * @author Konrad Bylina [K20014050] & Matt Stanbrell [K21044080]
 * @version 2022.03.02
 */
public class EnvironmentalEventController
{
    // Constants for probabilities of environmental events occuring
    private static final double ALGAE_INTRODUCTION_PROBABILITY = 0.005;
    private static final double OIL_SPILL_PROBABILITY = 0.00001;
    
    private MasterField simulationField;
    private Field environmentField;
    
    // Environmental Event Controllers
    private AlgaeIntroducer algaeIntroducer;
    private OilSpiller oilSpiller;
    
    private Random rand = Randomizer.getRandom();
    
    /**
     * Constructor for objects of class EnvironmentalEvent
     */
    public EnvironmentalEventController(MasterField simulationField)
    {
        this.simulationField = simulationField;
        this.environmentField = simulationField.getEnvironmentField();
        this.algaeIntroducer = new AlgaeIntroducer(simulationField);
        this.oilSpiller = new OilSpiller(simulationField);
        this.rand = Randomizer.getRandom();
    }
    
    /**
     * Decides whether an environmental event should be initiated during current step and if so
     * initiates it.
     * 
     * @param newOrganisms  list containing organisms added to simulation during current step 
     * @param isDay         boolean true if day
     */
    public void step(List<Organism> newOrganisms, boolean isDay)
    {
        for (int row = 0; row < simulationField.getDepth(); row++) {
            for (int col = 0; col < simulationField.getWidth(); col++) {
                if (isDay && rand.nextDouble() <= ALGAE_INTRODUCTION_PROBABILITY) {
                    Location location = new Location(row, col);
                    
                    if (environmentField.getAnimalAt(location) == null) {   
                        newOrganisms.addAll(algaeIntroducer.getNewAlgae(location)); // Only introduce algae if location is not occupied by other objects
                    }
                } else if (rand.nextDouble() <= OIL_SPILL_PROBABILITY){
                    Location location = new Location(row, col);
                    oilSpiller.spillOil(location);
                }
            }
        }
        // Let environmental event controllers with trackable events know the simulation is at the next step.
        oilSpiller.step();
    }
}
    

    

