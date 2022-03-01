import java.util.List;
import java.util.ArrayList;

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
    private boolean isActive;

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
        this.isActive = true;
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
        updateActiveStatus();
    }

    public boolean IsActive(){
        return isActive;
    }

    private boolean shouldBeActivated(Oil oil){
        Location oilLocation  = oil.getLocation();
        if (inValidRow(oilLocation) && inValidCol(oilLocation)){
            return true;
        }
        return false;
        
    }
    private boolean inValidRow(Location location){
        return ((location.getRow() <= (epicentre.getRow() + currentLayer)) &&
                (location.getRow() >= (epicentre.getRow() - currentLayer)));
    }

    private boolean inValidCol(Location location){
        return ((location.getCol() <= (epicentre.getCol() + currentLayer)) &&
                (location.getCol() >= (epicentre.getCol() - currentLayer)));
    }

    private List<Oil> prepareOils(){
        List<Oil> oilsList = new ArrayList<Oil>();
        List<Location> oilLocations = environmentField.locationsWithinOf(radius, epicentre);
        for (Location location : oilLocations){
            oilsList.add(new Oil(simulationField, location));
        }
        return oilsList;
    }

    private void updateActiveStatus(){
        for (Oil oil : oils){
            if (oil.isPolluting()){
                isActive = true;
                return;
            }
        }
        isActive = false;
    }
}
