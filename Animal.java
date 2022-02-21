import java.util.List;
import java.util.Iterator;
import java.util.ArrayList;

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
    
    /**
     * Create a new animal at location in field.
     * 
     * @param field The field currently occupied.
     * @param location The location within the field.
     */
    public Animal(Boolean randomAge, Field field, Location location)
    {
        super(field, location);
        maxAge = rand.nextInt(getMaxDeathAge() - getMinDeathAge()) + getMinDeathAge();
        if(randomAge) {
            age = rand.nextInt(maxAge);
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
            Location newLocation = findFood();
            if(newLocation == null) { 
                // No food found - try to move to a free location.
                newLocation = getField().freeAdjacentLocation(getLocation());
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
        Field field = getField();
        List<Location> adjacent = field.adjacentLocations(getLocation());
        Iterator<Location> it = adjacent.iterator();
        while(it.hasNext()) {
            Location where = it.next();
            Animal animal = field.getAnimalAt(where);
            if (animal != null) {
                for (Class<?> preyClass : getPreyList()) {
                    if (preyClass.isInstance(animal)) {
                        if(animal.isAlive()) { 
                            animal.setDead();
                            foodLevel += animal.getFoodValue();
                            System.out.println(getName() + " ate " + animal.getName());
                            return where;
                        }
                    }
                }
            }
        }
        return null;
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
        foodLevel = foodLevel - (int) (getMaxFoodLevel() * 0.1);
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
    
    abstract protected int getFoodValue();
    
    abstract protected int getMaxLitterSize();
    
    abstract protected double getSexualBreedingProbability();
    
    abstract protected void giveBirth(List<Organism> newAnimals);
    
    abstract protected List<Class<? extends Organism>> getPreyList();
    
    // just for testing
    abstract protected String getName();
}
