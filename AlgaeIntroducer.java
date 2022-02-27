import java.util.List;
import java.util.ArrayList;

/**
 * Write a description of class AlgaeIntroduction here.
 *
 * @author (your name)
 * @version (a version number or a date)
 */
public class AlgaeIntroducer
{
    // instance variables - replace the example below with your own
    
    private MasterField simulationField;
    
    private Field environmentField;

    /**
     * Constructor for objects of class AlgaeIntroduction
     */
    public AlgaeIntroducer(MasterField simulationField)
    {
        this.simulationField = simulationField;
        this.environmentField = simulationField.getEnvironmentField();
    }
    
    public List<Organism> getNewAlgae(Location location){
        List<Location> locations = getLocations(environmentField, location);
        List newAlgae = new ArrayList<Organism>();
        
        clearLocationsForNewAlgae(environmentField, locations);
        for (Location loc : locations){
            newAlgae.add(new Algae(simulationField, loc));
        }
        
        return newAlgae;
    }

    private void clearLocationsForNewAlgae(Field environmentField, List<Location> locations){
        for (Location location : locations){
            environmentField.clear(location);
        }
    }
    
    private List<Location> getLocations(Field environmentField, Location location){
        List<Location> locations = environmentField.adjacentLocations(location);
        locations.add(location);
        return locations;
    }
}
