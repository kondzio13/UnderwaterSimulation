import java.util.List;
import java.util.Set;
import java.util.Iterator;
import java.util.ArrayList;

/**
 * A class representing shared characteristics of animals.
 * 
 * @author Konrad Bylina [] & Matt Stanbrell [K21044080]
 */
public abstract class Animal extends Organism {
    // Animal's current age in steps (each step is half a day)
    protected int ageInSteps;
    // Age the animal can live up to
    protected int maxAge;
    // Current food level
    protected int foodLevel;
    // Whether the animal is female (for breeding purposes)
    protected boolean isFemale;

    /**
     * Create a new animal at location in field.
     * 
     * @param randomAge  Whether the assigned age should be random or 0
     * @param field      Container for bo
     * @param location   The location within the field.
     */
    public Animal(Boolean randomAge, MasterField simField, Location location) {
        super(simField, simField.getAnimalField(), location);
        // Age animal can live up to, random age between species max and min death age
        maxAge = rand.nextInt(getMaxDeathAge() - getMinDeathAge()) + getMinDeathAge();
        isFemale = rand.nextBoolean();
        if (randomAge) {
            ageInSteps = convertYearsToSteps(rand.nextInt(maxAge));
            foodLevel = rand.nextInt(getMaxFoodLevel());
        } else {
            ageInSteps = 0;
            foodLevel = getMaxFoodLevel();
        }
    }

    /**
     * Make animal do whatever it needs to
     * 
     * @param newAnimals  A list to receive newly born animals
     * @param isDay       Is it day or night
     */
    public void act(List<Organism> newAnimals, boolean isDay) {
        incrementAge();
        incrementHunger();
        incrementMoveBufferProgress();
        // If animal is alive and active (some are inactive at night)
        if (isAlive() && isActive(isDay)) {
            // Only females search for a mate for efficiency
            if (isFemale && canMove()) {
                findMate(newAnimals);
            } else if (getAsexualBreedingProbability() != 0) {
                //System.out.println(getName() + " is trying to give birth asexually");
                giveBirth(newAnimals);
            }
            // Move towards a source of food if found.
            // If animal can't move, will search for plants 
            // in its current location (if the aninmal eats plants)
            Location newLocation = findFood();
            if (canMove()) {
                if (newLocation == null) {
                    // No food found - try to move to a free location.
                    newLocation = getPhysicalField().freeAdjacentLocation(getLocation());
                }
                // See if it was possible to move.
                if (newLocation != null) {
                    setLocation(newLocation);
                } else {
                    // Overcrowding.
                    setDead();
                }
            }
        }
    }
    
    /**
     * Animal finding food to eat
     * 
     * @return Location where food was found (or null if no food found)
     */
    protected Location findFood()
    {
        Location foodLocation = null;
        // if animal can move, searches adjacent locations for food
        if (canMove()) {
            foodLocation = findFoodAdjacent(getLocation());
        }
        // if animal can't move (or can but didn't find food adjacent)
        // searches current location for food
        if (foodLocation == null && eatsPlants()) {
            foodLocation = findFoodHere();
        }
        return foodLocation;
    }
    
    /**
     * Finding food in adjacent locations
     * 
     * @param currentLocation Location of this animal
     * 
     * @return Location where food was found (or null if no food found)
     */
    protected Location findFoodAdjacent(Location currentLocation)
    {
        ArrayList<Field> fieldList;
        // only search plant field if animal eats plants
        if (eatsPlants()) {
            fieldList = getSimulationField().getFieldList();
        } else {
            fieldList = new ArrayList<Field>();
            fieldList.add(physicalField);
        }
        
        for (Field field : fieldList) {
            for (Location adjacentLocation : field.adjacentLocations(currentLocation)) {
                Location foodLocation = findFoodAt(adjacentLocation, field, false);
                if (foodLocation != null) {
                    return foodLocation;
                }
            }
        }
        return null;
    }
    
    /**
     * Find food at current location
     * 
     * @return Location where food was found (or null if no food found)
     */
    protected Location findFoodHere()
    {
        return findFoodAt(getLocation(), getSimulationField().getEnvironmentField(), true);
    }
    
    /**
     * Find food at a specific location
     * 
     * @param searchLocation Location to search
     * @param searchField    Field being searched 
     * @param here           Whether animal is searching for food in it's current location
     * 
     * @return Location where food was found (or null if no food found)
     */
    protected Location findFoodAt(Location searchLocation, Field searchField, boolean here)
    {
        Organism preyOrganism = searchField.getOrganismAt(searchLocation);
        if (preyOrganism == null) {
            return null;
        } else {
            // for testing
            //System.out.println(getName() + " found " + preyOrganism.getName());
        }
        
        if (isInPreyList(preyOrganism)) {
            if (preyOrganism instanceof Plant) {
                // if searching an adjacent location for plants and that location isn't occupied by another animal
                // or if animal is searching it's current location for plants 
                if ((!here && isLocationFree(searchLocation)) || here) {
                    eatOrganism(preyOrganism);
                    return searchLocation;
                }
            } else {
                eatOrganism(preyOrganism);
                return searchLocation;
            }
        }
        return null;
    }
    
    /**
     * Checks if a location in the animal field is free
     * 
     * @return Boolean free or not
     */
    protected boolean isLocationFree(Location searchLocation)
    {
        return physicalField.getOrganismAt(searchLocation) == null;
    }
    
    /**
     * Checks if an organism is in this animal's prey list
     * 
     * @return Boolean
     */
    protected boolean isInPreyList(Organism organism) 
    {
        return getPreyList().contains(organism.getName());
    }
    
    /**
     * Animal eats an organism
     * 
     * @param foodOrganism Organism being consumed
     */
    protected void eatOrganism(Organism foodOrganism)
    {
        foodOrganism.setDead();
        foodLevel += foodOrganism.getFoodValue();
    }
    
    /**
     * Checks if this animal eats plants or not
     * 
     * @return Boolean
     */
    protected boolean eatsPlants()
    {
        return getPreyList().contains("Algae") || getPreyList().contains("Plankton");
    }
    
    /**
     * Finds an animal of the same species and opposite sex to mate with
     * 
     * @param newAnimals  List for new baby animals produced  
     * 
     * @return   Location of mate (or null if not found)
     */
    protected Location findMate(List<Organism> newAnimals) {
        // Get field with animals in
        Field field = getPhysicalField();
        List<Location> adjacent = field.adjacentLocations(getLocation());
        Iterator<Location> it = adjacent.iterator();
        while (it.hasNext()) {
            Location where = it.next();
            Animal animal = field.getAnimalAt(where);
            // if animal exists and is of the same type as current animal
            if (animal != null && animal.getName() == getName()) {
                // if animals are of opposite sex
                if (!animal.isFemale) {
                    // if both animals of breeding age
                    if (canBreed() && animal.canBreed()) {
                        giveBirth(newAnimals);
                    }
                }
            }
        }
        return null;
    }
    /**
     * Checks if this is of breeding age
     * 
     * @return Boolean
     */
    public boolean canBreed() {
        return (ageInSteps >= convertYearsToSteps(getMinBreedingAge())) && (ageInSteps <= convertYearsToSteps(getMaxBreedingAge()));
    }
    
    /**
     * Increments animal's age by one step
     */
    protected void incrementAge() {
        ageInSteps++;
        if (ageInSteps > convertYearsToSteps(maxAge)) {
            System.out.println(getName() + " died from old age");
            setDead();
        }
    }

    /**
     * Decreases animal's foodLevel by a certain amount
     */
    protected void incrementHunger() {
        // Amount of foodLevel to be removed
        double foodRemoved = getMaxFoodLevel() * 0.03;
        // Ensures food level is actually decreased by at least 1
        if (foodRemoved <1) {
            foodRemoved = 1;
        }
        foodLevel = foodLevel - (int) foodRemoved;
        // Animal starved
        if (foodLevel <= 0) {
            setDead();
        }
    }

    /**
     * Generates number of births the animal will have
     * 
     * @return births  The number of births the animal can have 
     *                 (actual number born depends on space available)
     */
    protected int breedSexually() {
        int births = 0;
        if (canBreed() && rand.nextDouble() <= getSexualBreedingProbability()) {
            births = rand.nextInt(getMaxLitterSize()) + 1;
        }
        return births;
    }
    
    abstract protected int getMinBreedingAge();

    abstract protected int getMaxBreedingAge();

    abstract protected int getMaxFoodLevel();

    abstract protected int getMinDeathAge();

    abstract protected int getMaxDeathAge();

    abstract protected int getMaxLitterSize();

    abstract protected double getSexualBreedingProbability();

    abstract protected double getAsexualBreedingProbability();

    abstract protected void giveBirth(List<Organism> newAnimals);

    abstract protected Set<String> getPreyList();

}
