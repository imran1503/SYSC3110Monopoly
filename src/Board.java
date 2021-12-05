
import java.awt.*;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Scanner;

/**
 * Class Board, Scanner reader to store user inputs.
 * boardConstructor to store board constructor of the board.
 * players is List of players for the board.
 * propertyArrayList to store list of Property on the board.
 * Boolean isValid to store if the board is valid or not.
 * Hash Map with key for a color and value is a ArrayList Property that have the same color as key.
 */
public class Board {

    private BoardConstructor boardConstructor;
    private ArrayList<Property> propertyArrayList;
    private HashMap<Color, ArrayList<Property>> colorPropertyArrayList;
    private ArrayList<Color> allColorsList;
    private Boolean isValid;
    private String currency;

    /**
     * The source of user input the board will ask for while performing actions (attack, fortify, etc.)
     */
    private String userInputSource;

    /**
     * Constructor for Board
     * @param userInputSource the boardInput that the Board will ask for user input when needed
     */
    public Board(String userInputSource) {
        this.propertyArrayList = new ArrayList<>();
        this.userInputSource = userInputSource;
        this.colorPropertyArrayList = new HashMap<>();
        this.allColorsList = new ArrayList<>();
        currency = "";
    }

    /**
     * Method to set board with filename given
     * @param filename Name of file to set with
     */
    public void setBoard(String filename) {
        boardConstructor = new BoardConstructor(this);
        boardConstructor.loadBoardFromMapFile(filename,true);
    }

    /**
     * Add a Color set Property list to the board
     * @param color Color of color set
     * @param Property Property list to add
     */
    public void addColorPropertySet(Color color, ArrayList<Property> Property){
        colorPropertyArrayList.put(color,Property);
    }

    /**
     * Getter method for color Property arraylist
     * @return HashMap<Color, ArrayList<Property>
     */
    public HashMap<Color,ArrayList<Property>> getColorPropertyArrayList(){return colorPropertyArrayList;}

    /**
     * Returns the user input source of the Board
     * @return The user input source of the Board
     */
    public String getUserInputSource() {
        return userInputSource;
    }

    /**
     * Getter method for ArrayList of Property on the board
     * @return ArrayList<Property> Property
     */
    public ArrayList<Property> getPropertyArrayList() {
        return propertyArrayList;
    }

    /**
     * Returns a Property on a specific position from the Board
     * @param position Integer location of property interested in
     * @return Property on specific positon
     */
    public Property getProperty(int position){ return propertyArrayList.get(position); }

    /**
     * Add property given to propertyArrayList of board
     * @param property Property to add
     */
    public void addProperty(Property property){
        propertyArrayList.add(property);
    }

    /**
     * Set Property at a specific location in the propertyArrayList to the property given
     * @param location int location to set at
     * @param property Property to set with
     */
    public void setProperty(int location, Property property){
        propertyArrayList.set(location,property);
    }

    /**
     * Get currency of the board
     * @return String currency of the board
     */
    public String getCurrency(){return currency;}

    /**
     * Setter method for currency of board
     * @param currency String of currency to set with
     */
    public void setCurrency(String currency){this.currency = currency;}

    /**
     * Setter method for isValid variable
     * @param valid Boolean argument to set isValid attribute to.
     */
    public void setIsValid(Boolean valid){this.isValid = valid;}

    /**
     * Getter method for list of all colors of Property
     */
    public ArrayList<Color> getAllColorsList(){return allColorsList;}

    /**
     * Setter method for list of all colors of Property
     * @param colorsList ArrayList of Colors to set with
     */
    public void setAllColorsList(ArrayList<Color> colorsList){allColorsList = colorsList;}

}