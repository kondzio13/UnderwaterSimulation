import java.util.List;
import java.util.ArrayList;

/**
 * Simple model for an oil spill
 *
 * @author Konrad Bylina [K20014050] & Matt Stanbrell [K21044080]
 * @version 2022.03.02
 */
public class OilSpill
{
    // instance variable
    private MasterField simulationField;
    private Field environmentField;

    // Source of oil spill (centre location)
    private Location epicentre;
    // Size of oil spill
    private int radius;
    // Tracks which layers of the oil spill should be activated
    private int currentLayer;
    // Returns true if the oil spill is still active and affecting the simulation
    private boolean isActive;

    // List containing oils within affected area in simulation
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

    /**
     * Chooses oil objects which should be polluting the simulation and lets active Oil objects know
     * the simulation is at the next step.
     */
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

    /**
     * Returns true if the oil spill is affecting the simulation
     */
    public boolean IsActive(){
        return isActive;
    }

    /**
     * Returns true if the Oil object in the oil spill should start polluting the simulation
     * 
     * @param oil   Oil object to be checked
     */
    private boolean shouldBeActivated(Oil oil){
        Location oilLocation  = oil.getLocation();
        if (inValidRow(oilLocation) && inValidCol(oilLocation)){
            return true;
        }
        return false;
    }

    /**
     * Returns true if an Oil spill is in the rigt row to be activated 
     * 
     * @param location  location of Oil object
     */
    private boolean inValidRow(Location location){
        return ((location.getRow() <= (epicentre.getRow() + currentLayer)) &&
                (location.getRow() >= (epicentre.getRow() - currentLayer)));
    }

    /**
     * Returns true if an Oil spill is in the rigt column to be activated 
     * 
     * @param location  location of Oil object
     */
    private boolean inValidCol(Location location){
        return ((location.getCol() <= (epicentre.getCol() + currentLayer)) &&
                (location.getCol() >= (epicentre.getCol() - currentLayer)));
    }

    /**
     * Returns a list of all Oil objects which are part of the oil spill
     */
    private List<Oil> prepareOils(){
        List<Oil> oilsList = new ArrayList<Oil>();
        List<Location> oilLocations = environmentField.locationsWithinOf(radius, epicentre);
        for (Location location : oilLocations){
            oilsList.add(new Oil(simulationField, location));
        }
        return oilsList;
    }

    /**
     * Updates the isActive attribute to false if no more Oil objects in the oil spill are polluting
     */
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
