import java.util.List;
import java.util.Random;

/**
 * Write a description of class EnvironmentalEvent here.
 *
 * @author (your name)
 * @version (a version number or a date)
 */
public class EnvironmentalEventController
{
    
    private static final double ALGAE_INTRODUCTION_PROBABILITY = 0.02;
    
    private MasterField simulationField;
    private Field environmentField;
    
    private AlgaeIntroducer algaeIntroducer;
    
    /**
     * Constructor for objects of class EnvironmentalEvent
     */
    public EnvironmentalEventController(MasterField simulationField)
    {
        this.simulationField = simulationField;
        this.environmentField = simulationField.getEnvironmentField();
        this.algaeIntroducer = new AlgaeIntroducer(simulationField);
    }
    
    public void step(List newOrganisms, boolean isDay)
    {
        Random rand = Randomizer.getRandom();
        for (int row = 0; row < simulationField.getDepth(); row++) {
            for (int col = 0; col < simulationField.getWidth(); col++) {
                if (isDay && rand.nextDouble() <= ALGAE_INTRODUCTION_PROBABILITY) {
                    Location location = new Location(row, col);
                    newOrganisms.addAll(algaeIntroducer.getNewAlgae(location));
                }
            }
        }
    }
}
    

    

