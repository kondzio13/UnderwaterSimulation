import java.util.List;
import java.util.ArrayList;
import java.awt.Color;
import java.util.Random;

/**
 * Write a description of class SimulatorDesigner here.
 *
 * @author (your name)
 * @version (a version number or a date)
 */
public class SimulatorBuilder
{
    // Constants representing configuration information for the simulation.
    // The default width for the grid.
    private static final int DEFAULT_WIDTH = 120;
    // The default depth of the grid.
    private static final int DEFAULT_DEPTH = 80;
    // The probability that a fox will be created in any given grid position.
    private static final double FOX_CREATION_PROBABILITY = 0.02;
    // The probability that a rabbit will be created in any given grid position.
    private static final double RABBIT_CREATION_PROBABILITY = 0.08;    
    // The probability that an algae will be created in any given grid position.
    private static final double ALGAE_CREATION_PROBABILITY = 0.1;  

    // List of animals in the field.
    protected List<Animal> animals;
    // The current state of the simulation field.
    protected MasterField simulationField;
    // A graphical view of the animals in the simulation.
    protected SimulatorView animalView;
    // A graphical view of the environment factors in the simulation.
    protected SimulatorView environmentView;
    // Keeps track of current step count
    protected int step;

    /**
     * Constructor for objects of class SimulatorBuilder
     */
    public SimulatorBuilder(){
        this(DEFAULT_DEPTH, DEFAULT_WIDTH);
    }
    
    public SimulatorBuilder(int depth, int width){
        if(width <= 0 || depth <= 0) {
            System.out.println("The dimensions must be greater than zero.");
            System.out.println("Using default values.");
            depth = DEFAULT_DEPTH;
            width = DEFAULT_WIDTH;
        }
        
        simulationField = new MasterField(depth, width);
        
        animals = new ArrayList<>();
            
        // Create a view of the state of animals in each location in the field.
        animalView = new SimulatorView(depth, width);
        animalView.setColor(Rabbit.class, Color.ORANGE);
        animalView.setColor(Fox.class, Color.BLUE);
        
        //Creates a view of the state of the environmental factors in each location in the field
        environmentView = new SimulatorView(depth, width);
        environmentView.setColor(Algae.class, Color.GREEN);

        
        // Setup a valid starting point.
        reset();
    }
    
    /**
     * Reset the simulation to a starting position.
     */
    public void reset()
    {
        step = 0;
        animals.clear();
        populate();
        
        // Show the starting state in the view.
        animalView.showStatus(step, simulationField.getAnimalField());
        environmentView.showStatus(step, simulationField.getEnvironmentField());
    }
    
        /**
     * Randomly populate the field with foxes and rabbits.
     */
    private void populate()
    {
        Random rand = Randomizer.getRandom();
        simulationField.getAnimalField().clear();
        for(int row = 0; row < simulationField.getDepth(); row++) {
            for(int col = 0; col < simulationField.getWidth(); col++) {
                // Animal field population
                if(rand.nextDouble() <= FOX_CREATION_PROBABILITY) {
                    Location location = new Location(row, col);
                    Fox fox = new Fox(true, simulationField, location);
                    animals.add(fox);
                }
                else if(rand.nextDouble() <= RABBIT_CREATION_PROBABILITY) {
                    Location location = new Location(row, col);
                    Rabbit rabbit = new Rabbit(true, simulationField, location);
                    animals.add(rabbit);
                }
                // else leave the location empty.
                // Environment Field population
                if(rand.nextDouble() <= ALGAE_CREATION_PROBABILITY) {
                    Location location = new Location(row, col);
                    Algae algae = new Algae(simulationField, location);
                    animals.add(algae);
                }
                
                
            }
        }
    }


}
