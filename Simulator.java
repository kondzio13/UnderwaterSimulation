import java.util.Random;
import java.util.List;
import java.util.ArrayList;
import java.util.Iterator;
import java.awt.Color;

/**
 * A simple predator-prey simulator, based on a rectangular field
 * containing rabbits and foxes.
 * 
 * @author David J. Barnes and Michael Kölling
 * @version 2016.02.29 (2)
 */
public class Simulator
{
    // Constants representing configuration information for the simulation.
    // The default width for the grid.
    private static final int DEFAULT_WIDTH = 120;
    // The default depth of the grid.
    private static final int DEFAULT_DEPTH = 80;
    // The probability that a fox will be created in any given grid position.
    private static final double SHARK_CREATION_PROBABILITY = 0.02;
    // The probability that a rabbit will be created in any given grid position.
    private static final double JELLYFISH_CREATION_PROBABILITY = 0.08;
    
    private static final double DOLPHIN_CREATION_PROBABILITY = 0.04;
    
    private static final double ARGONAUT_CREATION_PROBABILITY = 0.04;
    
    private static final double SNAIL_CREATION_PROBABILITY = 0.04;

    // List of animals in the field.
    private List<Organism> organisms;
    // The current state of the field.
    private Field field;
    // The current step of the simulation.
    private int step;
    // A graphical view of the simulation.
    private SimulatorView view;
    
    /**
     * Construct a simulation field with default size.
     */
    public Simulator()
    {
        this(DEFAULT_DEPTH, DEFAULT_WIDTH);
    }
    
    /**
     * Create a simulation field with the given size.
     * @param depth Depth of the field. Must be greater than zero.
     * @param width Width of the field. Must be greater than zero.
     */
    public Simulator(int depth, int width)
    {
        if(width <= 0 || depth <= 0) {
            System.out.println("The dimensions must be greater than zero.");
            System.out.println("Using default values.");
            depth = DEFAULT_DEPTH;
            width = DEFAULT_WIDTH;
        }
        
        organisms = new ArrayList<>();
        field = new Field(depth, width);

        // Create a view of the state of each location in the field.
        view = new SimulatorView(depth, width);
        view.setColor(Jellyfish.class, Color.PINK);
        view.setColor(Shark.class, Color.LIGHT_GRAY);
        view.setColor(Dolphin.class, Color.DARK_GRAY);
        view.setColor(Argonaut.class, Color.RED);
        view.setColor(Snail.class, Color.ORANGE);
        
        // Setup a valid starting point.
        reset();
    }
    
    /**
     * Run the simulation from its current state for a reasonably long period,
     * (4000 steps).
     */
    public void runLongSimulation()
    {
        simulate(4000);
    }
    
    /**
     * Run the simulation from its current state for the given number of steps.
     * Stop before the given number of steps if it ceases to be viable.
     * @param numSteps The number of steps to run for.
     */
    public void simulate(int numSteps)
    {
        for(int step = 1; step <= numSteps && view.isViable(field); step++) {
            simulateOneStep();
            // delay(60);   // uncomment this to run more slowly
        }
    }
    
    /**
     * Run the simulation from its current state for a single step.
     * Iterate over the whole field updating the state of each
     * fox and rabbit.
     */
    public void simulateOneStep()
    {
        step++;

        // Provide space for newborn animals.
        List<Organism> newOrganisms = new ArrayList<>();        
        // Let all rabbits act.
        for(Iterator<Organism> it = organisms.iterator(); it.hasNext(); ) {
            Organism organism = it.next();
            organism.act(newOrganisms);
            if(! organism.isAlive()) {
                it.remove();
            }
        }
               
        // Add the newly born foxes and rabbits to the main lists.
        organisms.addAll(newOrganisms);

        view.showStatus(step, field);
    }
        
    /**
     * Reset the simulation to a starting position.
     */
    public void reset()
    {
        step = 0;
        organisms.clear();
        populate();
        
        // Show the starting state in the view.
        view.showStatus(step, field);
    }
    
    /**
     * Randomly populate the field with foxes and rabbits.
     */
    private void populate()
    {
        Random rand = Randomizer.getRandom();
        field.clear();
        for(int row = 0; row < field.getDepth(); row++) {
            for(int col = 0; col < field.getWidth(); col++) {
                if(rand.nextDouble() <= SHARK_CREATION_PROBABILITY) {
                    Location location = new Location(row, col);
                    Shark shark = new Shark(true, field, location);
                    organisms.add(shark);
                } else if(rand.nextDouble() <= JELLYFISH_CREATION_PROBABILITY) {
                    Location location = new Location(row, col);
                    Jellyfish jellyfish = new Jellyfish(true, field, location);
                    organisms.add(jellyfish);
                } else if(rand.nextDouble() <= DOLPHIN_CREATION_PROBABILITY) {
                    Location location = new Location(row, col);
                    Dolphin dolphin = new Dolphin(true, field, location);
                    organisms.add(dolphin);
                } else if(rand.nextDouble() <= ARGONAUT_CREATION_PROBABILITY) {
                    Location location = new Location(row, col);
                    Argonaut argonaut = new Argonaut(true, field, location);
                    organisms.add(argonaut);
                } else if(rand.nextDouble() <= SNAIL_CREATION_PROBABILITY) {
                    Location location = new Location(row, col);
                    Snail snail = new Snail(true, field, location);
                    organisms.add(snail);
                }
                // else leave the location empty.
            }
        }
    }
    
    /**
     * Pause for a given time.
     * @param millisec  The time to pause for, in milliseconds
     */
    private void delay(int millisec)
    {
        try {
            Thread.sleep(millisec);
        }
        catch (InterruptedException ie) {
            // wake up
        }
    }
}
