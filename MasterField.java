import java.util.HashSet;

/**
 * Write a description of class MasterField here.
 *
 * @author (your name)
 * @version (a version number or a date)
 */
public class MasterField
{
    // instance variables - replace the example below with your own
    private Field animalField;
    private Field environmentField;
    
    // The depth and width of the field.
    private int depth, width;

    /**
     * Constructor for objects of class MasterField
     */
    public MasterField(int depth, int width){
        this.depth = depth;
        this.width = width;
        this.animalField = new Field(depth, width);
        this.environmentField = new Field(depth, width);
    }


    /**
     * Returns a HashSet with all fields
     * @return fieldSet
     */
    public HashSet<Field> getFieldSet(){
        HashSet<Field> fieldSet = new HashSet<Field>();
        fieldSet.add(animalField);
        fieldSet.add(environmentField);
        return fieldSet;
    }

    /**
     * Returns the animalField
     */
    public Field getAnimalField(){
        return animalField;
    }
    
    /**
     * Returns the environment Field
     */
    public Field getEnvironmentField(){
        return environmentField;
    }
    
    /**
     * Empty the field.
     */
    public void clear()
    {
        animalField.clear();
        environmentField.clear();
    }
    
    /**
     * Empty the field at location.
     */
    public void clear(Location location){
        animalField.clear(location);
        environmentField.clear(location);
    }
    
    /**
     * Returns the depth of the MasterField
     */
    public int getDepth(){
        return depth;
    }
    
    /**
     * Returns the width of the MasterField
     */
    public int getWidth(){
        return width;
    }
}
