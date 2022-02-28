import java.util.ArrayList;
import java.util.List;

/**
 * Write a description of class OilSpill here.
 *
 * @author (your name)
 * @version (a version number or a date)
 */
public class OilSpill implements TrackableEvent 
{
    // instance variables - replace the example below with your own
    private MasterField simulationField;
    private Field environmentField;
    private Location epicentre;
    private int radius;
    private int currentLayer;

    private List<Oil> oils;


    /**
     * Constructor for objects of class OilSpill
     */
    public OilSpill(MasterField simulationField, Location location, int radius)
    { 
        this.simulationField = simulationField;
        this.environmentField = simulationField.getEnvironmentField();
        this.epicentre = location;
        this.radius = radius;
        this.currentLayer = 0;
        this.oils = prepareOils();

        
    }

    

    public void step(){
        for (Oil oil : oils){
            if (shouldBeActivated(oil)){
                oil.startPolluting();
            }
            if (oil.isPolluting()){
                oil.step();
            }
        }
        currentLayer = currentLayer + 1;
    }

    private boolean shouldBeActivated(Oil oil){
        Location oilLocation  = oil.getLocation();
        if (inValidRow(oilLocation) && inValidCol(oilLocation)){
            return true;
        }
        return false;
        
    }
    private boolean inValidRow(Location location){
        return ((location.getRow() == (epicentre.getRow() + currentLayer)) ||
                (location.getRow() == (epicentre.getRow() - currentLayer)));
    }

    private boolean inValidCol(Location location){
        return ((location.getCol() == (epicentre.getCol() + currentLayer)) ||
                (location.getCol() == (epicentre.getCol() - currentLayer)));
    }

    private List<Oil> prepareOils(){
        List<Oil> oilsList = new ArrayList<Oil>();
        List<Location> oilLocations = environmentField.adjacentLocations(epicentre, radius);
        for (Location location : oilLocations){
            oils.add(new Oil(simulationField, location));
        }
        return oilsList;
    }
}
