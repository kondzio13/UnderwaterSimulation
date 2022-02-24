import java.util.List;
import java.util.Set;
import java.util.Iterator;
import java.util.ArrayList;
import java.util.HashSet;

/**
 * A class representing shared characteristics of animals.
 * 
 * @author David J. Barnes and Michael Kölling
 * @version 2016.02.29 (2)
 */
public abstract class Animal extends Organism
{
    protected int age;
    
    protected int maxAge;
    
    protected int foodLevel;

    protected boolean isFemale;

    /**
     * Create a new animal at location in field.
     * 
     * @param field The field currently occupied.
     * @param location The location within the field.
     */
    public Animal(Boolean randomAge, MasterField simField, Location location)
    {
        super(simField, simField.getAnimalField(), location);
        maxAge = rand.nextInt(getMaxDeathAge() - getMinDeathAge()) + getMinDeathAge();
        if(randomAge) {
            age = rand.nextInt(maxAge);
            isFemale = rand.nextBoolean();
            foodLevel = rand.nextInt(getMaxFoodLevel());
        }
        else {
            age = 0;
            foodLevel = getMaxFoodLevel();
        }
    }
    
    public void act(List<Organism> newAnimals)
    {
        incrementAge();
        incrementHunger();
        if(isAlive()) {
            giveBirth(newAnimals);            
            // Move towards a source of food if found.
            Location newLocation = findFood(); /// Need to check whether there is another animal already there in the physical field
            if(newLocation == null) { 
                // No food found - try to move to a free location.
                newLocation = getPhysicalField().freeAdjacentLocation(getLocation());
            }
            // See if it was possible to move.
            if(newLocation != null) {
                setLocation(newLocation);
            }
            else {
                // Overcrowding.
                setDead();
            }
        }
    }
    
    protected Location findFood()
    {
        for (Field field : getSimulationField().getFieldSet()){
            List<Location> adjacent = field.adjacentLocations(getLocation());
            Iterator<Location> it = adjacent.iterator();
            while(it.hasNext()) {
                Location loc = it.next();
                Organism organism = field.getOrganismAt(loc);
                if (organism == null){
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

    private boolean canConsumeOrganismAt(Organism animal, Location loc){
        return getPreyList().contains(animal.getName()) && animal.isAlive() && (physicalField.getOrganismAt(loc) == null);

    }
    
    public boolean canBreed()
    {
        return (age >= getMinBreedingAge()) && (age <= getMaxBreedingAge());
    }
    
    protected void incrementAge()
    {
        age++;
        if(age > maxAge) {
            setDead();
        }
    }
    
    protected void incrementHunger()
    {
        foodLevel = foodLevel -1;
        if(foodLevel <= 0) {
            setDead();
        }
    }
    
    protected int breedSexually()
    {
        int births = 0;
        if(canBreed() && rand.nextDouble() <= getSexualBreedingProbability()) {
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
    
    abstract protected void giveBirth(List<Organism> newAnimals);
    
    abstract protected Set<String> getPreyList();
    
    
}
