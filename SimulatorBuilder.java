import java.util.List;
import java.util.ArrayList;
import java.awt.Color;
import java.util.Random;

/**
 * Superclass of simulator, builds the simulator when first run
 *
 * @author Konrad Bylina [K20014050] & Matt Stanbrell [K21044080]
 * @version 2022.03.02
 */
public class SimulatorBuilder {
    // Constants representing configuration information for the simulation.
    // The default width for the grid.
    private static final int DEFAULT_WIDTH = 120;
    // The default depth of the grid.
    private static final int DEFAULT_DEPTH = 80;
    // 0.02
    private static final double SHARK_CREATION_PROBABILITY = 0.02;
    // 0.08
    private static final double JELLYFISH_CREATION_PROBABILITY = 0.08;
    // 0.04
    private static final double DOLPHIN_CREATION_PROBABILITY = 0.08;
    // 0.04 
    private static final double ARGONAUT_CREATION_PROBABILITY = 0.04;
    // 0.04
    private static final double SNAIL_CREATION_PROBABILITY = 0.04;
    // 0.1
    private static final double ALGAE_CREATION_PROBABILITY = 0.1;
    // 0.1
    private static final double PLANKTON_CREATION_PROBABILITY = 0.1;

    // List of animals in the field.
    protected List<Organism> organisms;
    // The current state of the simulation field.
    protected MasterField simulationField;
    // A graphical view of the animals in the simulation.
    protected SimulatorView animalView;
    // A graphical view of the environment in the simulation.
    protected SimulatorView environmentView;
    // Controls environmental events in the simuulation.
    protected EnvironmentalEventController environment;
    // Keeps track of current step count
    protected int step;

    /**
     * Construct a simulation field with default size.
     */
    public SimulatorBuilder() {
        this(DEFAULT_DEPTH, DEFAULT_WIDTH);
    }

    /**
     * Create a simulation field with the given size.
     * 
     * @param depth Depth of the field. Must be greater than zero.
     * @param width Width of the field. Must be greater than zero.
     */
    public SimulatorBuilder(int depth, int width) {
        if (width <= 0 || depth <= 0) {
            System.out.println("The dimensions must be greater than zero.");
            System.out.println("Using default values.");
            depth = DEFAULT_DEPTH;
            width = DEFAULT_WIDTH;
        }
        // Constructs both environmental and animal fields
        simulationField = new MasterField(depth, width);
        organisms = new ArrayList<>();
        environment = new EnvironmentalEventController(simulationField);

        // Create a view of the state of each location in the field.
        animalView = new SimulatorView(depth, width);
        animalView.setColor(Jellyfish.class, Color.PINK);
        animalView.setColor(Shark.class, Color.LIGHT_GRAY);
        animalView.setColor(Dolphin.class, Color.DARK_GRAY);
        animalView.setColor(Argonaut.class, Color.RED);
        animalView.setColor(Snail.class, Color.ORANGE);

        // Creates a view of the state of the environmental factors in each location in
        // the field
        environmentView = new SimulatorView(depth, width);
        environmentView.setColor(Algae.class, Color.GREEN.darker());
        environmentView.setColor(Plankton.class, new Color(67, 120, 124));
        environmentView.setColor(Oil.class, Color.BLACK);

        // Setup a valid starting point.
        reset();
    }

    /**
     * Reset the simulation to a starting position.
     */
    public void reset() {
        step = 0;
        organisms.clear();
        populate();

        // Show the starting state in the view.
        animalView.showStatus(step, simulationField.getAnimalField());
        environmentView.showStatus(step, simulationField.getEnvironmentField());
    }

    /**
     * Randomly populate the fields with organisms.
     */
    private void populate() {
        Random rand = Randomizer.getRandom();
        simulationField.clear();
        for (int row = 0; row < simulationField.getDepth(); row++) {
            for (int col = 0; col < simulationField.getWidth(); col++) {
                // Populate the animal field
                if (rand.nextDouble() <= SHARK_CREATION_PROBABILITY) {
                    Location location = new Location(row, col);
                    Shark shark = new Shark(true, simulationField, location);
                    organisms.add(shark);
                } else if (rand.nextDouble() <= JELLYFISH_CREATION_PROBABILITY) {
                    Location location = new Location(row, col);
                    Jellyfish jellyfish = new Jellyfish(true, simulationField, location);
                    organisms.add(jellyfish);
                } else if (rand.nextDouble() <= DOLPHIN_CREATION_PROBABILITY) {
                    Location location = new Location(row, col);
                    Dolphin dolphin = new Dolphin(true, simulationField, location);
                    organisms.add(dolphin);
                } else if (rand.nextDouble() <= ARGONAUT_CREATION_PROBABILITY) {
                    Location location = new Location(row, col);
                    Argonaut argonaut = new Argonaut(true, simulationField, location);
                    organisms.add(argonaut);
                } else if (rand.nextDouble() <= SNAIL_CREATION_PROBABILITY) {
                    Location location = new Location(row, col);
                    Snail snail = new Snail(true, simulationField, location);
                    organisms.add(snail);
                }
                // Populate the environment field
                if (rand.nextDouble() <= ALGAE_CREATION_PROBABILITY) {
                    Location location = new Location(row, col);
                    Algae algae = new Algae(simulationField, location);
                    organisms.add(algae);
                } else if (rand.nextDouble() <= PLANKTON_CREATION_PROBABILITY) {
                    Location location = new Location(row, col);
                    Plankton plankton = new Plankton(simulationField, location);
                    organisms.add(plankton);
                }
                // else leave the location empty.
            }
        }
    }

}