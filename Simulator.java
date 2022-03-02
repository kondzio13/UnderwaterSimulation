import java.util.List;
import java.util.ArrayList;
import java.util.Iterator;

/**
 * A simple predator-prey simulator, based on a rectangular field
 * containing sharks, argonauts, dolphins, jellyfish, snails, algae, and plankton
 * 
 * @author Konrad Bylina [K20014050] & Matt Stanbrell [K21044080]
 * @version 2022.03.02
 */
public class Simulator extends SimulatorBuilder {
    /**
     * Call simulationBuilder constructor to construct a simulation field with default size.
     */
    public Simulator() {
        super();
    }

    /**
     * Create a simulation field with the given size.
     * 
     * @param depth Depth of the field. Must be greater than zero.
     * @param width Width of the field. Must be greater than zero.
     */
    public Simulator(int depth, int width) {
        super(depth, width);
    }

    /**
     * Run the simulation from its current state for a reasonably long period,
     * (4000 steps).
     */
    public void runLongSimulation() {
        simulate(4000);
    }

    /**
     * Run the simulation from its current state for the given number of steps.
     * Stop before the given number of steps if it ceases to be viable.
     * 
     * @param numSteps The number of steps to run for.
     */
    public void simulate(int numSteps) {
        for (int step = 1; step <= numSteps; step++) {
            if (viableViews()) {   
                simulateOneStep();
            } else {
                System.out.println("Simulation no longer viable");
                break;
            }
            //delay(100); // uncomment this to run more slowly
        }
    }

    /**
     * Check if there are still multiple species alive in both
     * environmental and physical fields
     * 
     * @return  Boolean viable or not
     */
    private boolean viableViews() {
        return animalView.isViable(simulationField.getAnimalField())
                || environmentView.isViable(simulationField.getEnvironmentField());
    }

    /**
     * Run the simulation from its current state for a single step.
     * Iterate over the whole field updating the state of each organism
     */
    public void simulateOneStep() {
        step++;
        // Provide space for newborn organisms.
        List<Organism> newOrganisms = new ArrayList<>();
        // Let all organisms act.
        for (Iterator<Organism> it = organisms.iterator(); it.hasNext();) {
            Organism organism = it.next();
            organism.act(newOrganisms, isDay());
            if (!organism.isAlive()) {
                it.remove();
            }
        }
        // Let environment do its thing
        // Spawn new plants and check for oil spills
        environment.step(newOrganisms, isDay());

        // Add the newly born organisms to the main lists.
        organisms.addAll(newOrganisms);
        
        animalView.showStatus(step, simulationField.getAnimalField());
        environmentView.showStatus(step, simulationField.getEnvironmentField());
    }
    
    /**
     * Check if it is day or night
     * It is day if the step count is even 
     * and night if it is odd
     * 
     * @return  Boolean true if day
     */
    private boolean isDay()
    {
         return step % 2 == 0;
    }

    /**
     * Pause for a given time.
     * 
     * @param millisec The time to pause for, in milliseconds
     */
    private void delay(int millisec) {
        try {
            Thread.sleep(millisec);
        } catch (InterruptedException ie) {
            // wake up
        }
    }
}
