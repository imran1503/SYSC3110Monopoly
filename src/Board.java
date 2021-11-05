
import java.awt.*;
import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Scanner;

/**
 * Class Board, Scanner reader to store user inputs.
 * boardConstructor to store board constructor of the board.
 * players is List of players for the board.
 * propertiesArrayList to store list of properties on the board.
 * Boolean isValid to store if the board is valid or not.
 */
public class Board {

    private Scanner reader;
    private BoardConstructor boardConstructor;
    private static ArrayList<Player> players;
    private ArrayList<Properties> propertiesArrayList;
    private HashMap<Color, ArrayList<Properties>> colorPropertiesArrayList;
    private boolean isValid;
    /**
     * The source of user input the board will ask for while performing actions (attack, fortify, etc.)
     */
    private String userInputSource;

    /**
     * Constructor for Board
     * @param userInputSource the boardInput that the Board will ask for user input when needed
     */
    public Board(String userInputSource) {
        this.propertiesArrayList = new ArrayList<>();
        this.players = new ArrayList<>();
        this.userInputSource = userInputSource;
    }

    /**
     * Add a Color set Property list to the board
     * @param color Color of color set
     * @param properties Property list to add
     */
    public void addColorPropertySet(Color color, ArrayList<Properties> properties){
        colorPropertiesArrayList.put(color,properties);
    }

    /**
     * Getter method for color properties arraylist
     * @return HashMap<Color, ArrayList<Properties>
     */
    public HashMap<Color,ArrayList<Properties>> getColorPropertiesArrayList(){return colorPropertiesArrayList;}

    /**
     * Returns the user input source of the Board
     * @return The user input source of the Board
     */
    public String getUserInputSource() {
        return userInputSource;
    }

    /**
     * Getter method for ArrayList of Properties on the board
     * @return ArrayList<Properties> properties
     */
    public ArrayList<Properties> getPropertiesArrayList() {
        return propertiesArrayList;
    }

    /**
     * Returns a Property on a specific position from the Board
     * @param position Integer location of property interested in
     * @return Property on specific positon
     */
    public Properties getProperty(int position){ return propertiesArrayList.get(position); }

    /**
     * Setter method for isValid variable
     * @param valid Boolean argument to set isValid attribute to.
     */
    public void setIsValid(Boolean valid){this.isValid = valid;}

    /**
     * Setter method for list of players on the board
     * @param playersForBoard ArrayList<Player> to set players on the board to.
     */
    public void setPlayers(ArrayList<Player> playersForBoard){Board.players = playersForBoard;}

    /**
     * Creates a new Board using either a map or save file
     * @param userInputSource the boardInput that the Board will ask for user input when needed
     * @param gameIsNew If true, the Board will be generated from a map file. If false, it will be generated from a save file
     * @return The newly created Board
     */
    public static Board newBoard(String userInputSource, boolean gameIsNew){
        Color red = Color.RED;
        Color green = Color.GREEN;
        Color blue = Color.BLUE;
        Color yellow = Color.YELLOW;

        Board board = new Board(userInputSource);
        BoardConstructor boardConstructor = new BoardConstructor();

        String filename = "Board.xml";
        //userInputSource.getStringInput("Enter the name of the " + (gameIsNew? "map": "save") + " file (no file extension)", gameIsNew? "board.xml": null);

        /**
         * if(gameIsNew){
            int MIN_PLAYERS = 2;
            int MAX_PLAYERS = 0;
            System.out.println("Enter the total number of Players playing");
            MAX_PLAYERS = reader.nextInt();

            ArrayList<Player> players = new ArrayList<>();
            //Object[] options = {"Human", "AI"};
            for(int i = 0; i < MAX_PLAYERS; i++){
                System.out.println("Enter the name of Player "+i);
                String playerName = reader.nextLine();
                Player newPlayer = new Player(playerName, new Color(10*i,10*i,10*i), 1500);
                players.add(newPlayer);

            }
            board.players = players;

            board.isValid = boardConstructor.loadBoardFromMapFile(filename, board)
                            && boardConstructor.validateXMLSchema("board.xsd", "board.xml");

        }
        else{
            board.players = new ArrayList<>();
        }
             */

        return board;
    }
}