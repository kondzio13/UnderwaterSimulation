import java.util.List;
import java.util.Set;
import java.util.Iterator;
import java.util.ArrayList;

/**
 * A class representing shared characteristics of animals.
 * 
 * @author David J. Barnes and Michael Kölling
 * @version 2016.02.29 (2)
 */
public abstract class Animal extends Organism {
    protected int ageInSteps;

    protected int maxAge;

    protected int foodLevel;

    protected boolean isFemale;

    /**
     * Create a new animal at location in field.
     * 
     * @param field    The field currently occupied.
     * @param location The location within the field.
     */
    public Animal(Boolean randomAge, MasterField simField, Location location) {
        super(simField, simField.getAnimalField(), location);
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

    public void act(List<Organism> newAnimals, boolean isDay) {
        incrementAge();
        incrementHunger();
        incrementMoveBufferProgress();
        if (isAlive() && isActive(isDay)) {
            //System.out.println(getName() + " is doing something");
            if (isFemale && canMove()) {
                //System.out.println(getName() + " is trying to find a mate");
                findMate(newAnimals);
            } else if (getAsexualBreedingProbability() != 0) {
                //System.out.println(getName() + " is trying to give birth asexually");
                giveBirth(newAnimals);
            }
            // Move towards a source of food if found.
            Location newLocation = findFood();
            if (canMove()) {
                if (newLocation == null) {
                    // No food found - try to move to a free location.
                    newLocation = getPhysicalField().freeAdjacentLocation(getLocation());
                } else {
                    //System.out.println(getName() + " found food");
                }
                // See if it was possible to move.
                if (newLocation != null) {
                    setLocation(newLocation);
                    //System.out.println(getName() + " moved");
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
        // if animal can't move (and didn't find food adjacent), searches current location for food
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
            System.out.println(getName() + " found " + preyOrganism.getName());
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
        // for testing
        System.out.println(getName() + " ate " + foodOrganism.getName());
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
    
    protected Location findMate(List<Organism> newAnimals) {
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
                        // testing that this actually works
                        //System.out.println(getName() + " is giving birth");
                    }
                }
            }
        }
        return null;
    }

    public boolean canBreed() {
        return (ageInSteps >= convertYearsToSteps(getMinBreedingAge())) && (ageInSteps <= convertYearsToSteps(getMaxBreedingAge()));
    }

    protected void incrementAge() {
        ageInSteps++;
        if (ageInSteps > convertYearsToSteps(maxAge)) {
            System.out.println(getName() + " died from old age");
            setDead();
        }
    }

    protected void incrementHunger() {
        foodLevel = foodLevel - (int) (getMaxFoodLevel() * 0.1);
        if (foodLevel <= 0) {
            setDead();
        }
    }

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
