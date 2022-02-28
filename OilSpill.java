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
    private int oilLayersLeft;

    private List<Oil> oils;


    /**
     * Constructor for objects of class OilSpill
     */
    public OilSpill(MasterField simulationField, Location location, int size)
    { 
        this.simulationField = simulationField;
        this.environmentField = simulationField.getEnvironmentField();
        this.epicentre = location;
        this.oilLayersLeft = size;
        this.oils = new ArrayList<Oil>();

        spillOil(epicentre);
        oilLayersLeft = oilLayersLeft - 1;
    }

    public void spillOil(Location location){
        Oil newOil = new Oil(simulationField, location);
        oils.add(newOil);
        simulationField.clear(location);
        environmentField.place(newOil, location);
    }

    public void step(){

        for (Oil oil : oils){
            oil.step();
        }
        oilLayersLeft = oilLayersLeft - 1;
    }

}
