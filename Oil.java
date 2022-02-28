
/**
 * Write a description of class Oil here.
 *
 * @author (your name)
 * @version (a version number or a date)
 */
public class Oil
{
    // instance variables - replace the example below with your own
    private MasterField simulationField;
    private Field environmentField;
    private Location location;
    private int age;
    private boolean isPolluting;

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
    }

    public void step(){
        if (isStillPolluting()){
            simulationField.clear(location);
            age = age + 1;
        } else {
            isPolluting = false;
        }
    }

    public void startPolluting(){
        isPolluting = true;
    }

    private boolean isStillPolluting(){
        return age < OIL_LIFETIME; 
    }

    public boolean isPolluting(){
        return isPolluting;
    }

    public Location getLocation(){
        return location;
    }
}
