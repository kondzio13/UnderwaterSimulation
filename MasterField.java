import java.util.ArrayList;

/**
 * MasterField is a container for both the animal and environment fields
 * Enables organisms from both fields to interact
 *
 * @author Konrad Bylina [K20014050] & Matt Stanbrell [K21044080]
 * @version 2022.03.02
 */
public class MasterField
{
    // Field containing animals
    private Field animalField;
    // Field containing plants and other environmental objects (e.g. oil)
    private Field environmentField;
    
    // The depth and width of the field.
    private int depth, width;

    /**
     * Constructor for objects of class MasterField
     * Constructs both fields
     * 
     * @param depth  Depth of both fields
     * @param width  Width of both fields
     */
    public MasterField(int depth, int width){
        this.depth = depth;
        this.width = width;
        this.animalField = new Field(depth, width);
        this.environmentField = new Field(depth, width);
    }


    /**
     * Returns a list with all fields
     * 
     * @return  fieldList
     */
    public ArrayList<Field> getFieldList(){
        ArrayList<Field> fieldList = new ArrayList<Field>();
        fieldList.add(animalField);
        fieldList.add(environmentField);
        return fieldList;
    }

    /**
     * Returns the field containing animals
     * 
     * @return animalField
     */
    public Field getAnimalField(){
        return animalField;
    }
    
    /**
     * Returns the environment field
     * 
     * @return environmentField
     */
    public Field getEnvironmentField(){
        return environmentField;
    }
    
    /**
     * Empty all fields.
     */
    public void clear()
    {
        animalField.clear();
        environmentField.clear();
    }
    
    /**
     * Empty the field at location
     * Empties location at both fields
     * 
     * @param location  Location in field to empty
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
