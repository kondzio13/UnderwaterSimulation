import java.util.List;
import java.util.Set;
import java.util.Iterator;

/**
 * A class representing shared characteristics of animals.
 * 
 * @author David J. Barnes and Michael Kölling
 * @version 2016.02.29 (2)
 */
public abstract class Animal extends Organism {
    protected int age;

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
            age = rand.nextInt(maxAge);
            foodLevel = rand.nextInt(getMaxFoodLevel());
        } else {
            age = 0;
            foodLevel = getMaxFoodLevel();
        }
    }

    public void act(List<Organism> newAnimals) {
        incrementAge();
        incrementHunger();
        incrementMoveBufferProgress();
        if (isAlive()) {
            if (isFemale || getMoveBufferProgress() >= 1) {
                findMate(newAnimals);
            } else if (getAsexualBreedingProbability() != 0) {
                giveBirth(newAnimals);
            }
            // Move towards a source of food if found.
            if (getMoveBufferProgress() >= 1) {
                Location newLocation = findFood();
                if (newLocation == null) {
                    // No food found - try to move to a free location.
                    newLocation = getPhysicalField().freeAdjacentLocation(getLocation());
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

    protected Location findFood() {
        for (Field field : getSimulationField().getFieldSet()) {
            List<Location> adjacent = field.adjacentLocations(getLocation());
            Iterator<Location> it = adjacent.iterator();
            while (it.hasNext()) {
                Location loc = it.next();
                Organism organism = field.getOrganismAt(loc);
                if (organism == null) {
                    return null;
                }
                if (canConsumeOrganismAt(organism, loc)) {
                    organism.setDead();
                    foodLevel += organism.getFoodValue();
                    System.out.println(getName() + " ate " + organism.getName());
                    return loc;
                }
            }
        }
        return null;
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
                        // System.out.println(getName() + " is giving birth");
                    }
                }
            }
        }
        return null;
    }

    private boolean canConsumeOrganismAt(Organism animal, Location loc) {
        return getPreyList().contains(animal.getName()) && animal.isAlive()
                && (physicalField.getOrganismAt(loc) == null);

    }

    public boolean canBreed() {
        return (age >= getMinBreedingAge()) && (age <= getMaxBreedingAge());
    }

    protected void incrementAge() {
        age++;
        if (age > maxAge) {
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
