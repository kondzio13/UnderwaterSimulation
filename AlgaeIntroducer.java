import java.util.ArrayList;
import java.util.List;

/**
 * Controller for spontaneous Algae introduction environmental event.
 *
 * @author Konrad Bylina [K20014050] & Matt Stanbrell [K21044080]
 * @version 2022.03.02
 */
public class AlgaeIntroducer
{
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
    
    /**
     * Creates and returns a list of new algae to be added to the simulation.
     * 
     * @param location      location of centre of new algae group
     * @return newAlgae     list containing new algae
     */
    public List<Organism> getNewAlgae(Location location){
        List<Location> locations = getLocations(location);
        List<Organism> newAlgae = new ArrayList<Organism>();
        
        clearLocationsForNewAlgae(locations);
        for (Location loc : locations){
            newAlgae.add(new Algae(simulationField, loc));
        }
        
        return newAlgae;
    }

    /**
     * Clears locations of the environment field for new algae
     * 
     * @param locations     List of all locations to be cleared
     */
    private void clearLocationsForNewAlgae(List<Location> locations){
        for (Location location : locations){
            environmentField.clear(location);
        }
    }
    
    /**
     * Returns a list of locations for new algae group
     * 
     * @param location      location of centre of algae group
     * @return locations    list of locations for new algae
     */
    private List<Location> getLocations(Location location){
        List<Location> locations = environmentField.adjacentLocations(location);
        locations.add(location);
        return locations;
    }
}
