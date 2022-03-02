
/**
 * Oil, when polluting, removes any objects which are at the location of the Oil object
 * in all fields of the simulation.
 *
 * @author Konrad Bylina [K20014050] & Matt Stanbrell [K21044080]
 * @version 2022.03.02
 */
public class Oil
{
    // instance variables
    private MasterField simulationField;
    private Field environmentField;

    // Location of Oil object
    private Location location;
    // Tracks how long the oil object has been polluting for
    private int age;
    // True if oil is currently polluting
    private boolean isPolluting;
    // True if oil object has already started polluting (makes sure oil object is only activated once)
    private boolean hasBeenActivated;

    // Determines how many steps the oil pollutes the field space for
    private static final int OIL_LIFETIME = 5;

    /**
     * Constructor for objects of class Oil
     */
    public Oil(MasterField simulationField, Location location)
    {
        this.simulationField = simulationField;
        this.environmentField = simulationField.getEnvironmentField();
        this.location = location;
        this.age = 0;
        this.isPolluting = false;
        this.hasBeenActivated = false;
    }

    /**
     * Removes all objects at location of Oil object in all fields and checks whether the oil
     * object should still be polluting. 
     */
    public void step(){
        simulationField.clear(location);
        if (validAge()){
            environmentField.place(this, location);
            age = age + 1;
        } else {
            isPolluting = false;
        }
    }

    /**
     * Lets the oil object know when it should start polluting 
     */
    public void startPolluting(){
        if (!hasBeenActivated){
            isPolluting = true;
        }
    }

    /**
     * Returns false if oil has polluted for enough time
     * @return
     */
    private boolean validAge(){
        return age < OIL_LIFETIME; 
    }

    /**
     * Returns true is oil is still polluting
     */
    public boolean isPolluting(){
        return isPolluting;
    }

    /**
     * Returns the location of the oil object
     */
    public Location getLocation(){
        return location;
    }
}
