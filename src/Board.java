
import java.lang.reflect.Array;
import java.util.ArrayList;

public class Board {


    private BoardConstructor boardConstructor;
    private static ArrayList<Player> players;
    private ArrayList<Properties> propertiesArrayList;
    private boolean isValid;

    /**
     * Returns the user input source of the Board
     * @return The user input source of the Board
     */
    public String getUserInputSource() {
        return userInputSource;
    }

    public ArrayList<Properties> getPropertiesArrayList() {
        return propertiesArrayList;
    }

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
     * Creates a new Board using either a map or save file
     * @param userInputSource the boardInput that the Board will ask for user input when needed
     * @param gameIsNew If true, the Board will be generated from a map file. If false, it will be generated from a save file
     * @return The newly created Board
     */
    public static Board newBoard(String userInputSource, boolean gameIsNew){
        Board board = new Board(userInputSource);
        BoardConstructor boardConstructor = new BoardConstructor();

        String filename = userInputSource.getStringInput("Enter the name of the " + (gameIsNew? "map": "save") + " file (no file extension)", gameIsNew? "board.xml": null);

        if(gameIsNew){
            int MIN_PLAYERS = 0;
            int MAX_PLAYERS = 0;
           // int numPlayers = Integer.parseInt("Enter the number of players", MIN_PLAYERS, MAX_PLAYERS); //TODO Scanner IO
            ArrayList<Player> players = new ArrayList<>();
            //Object[] options = {"Human", "AI"};
            for(int i = 0; i < numPlayers; i++){
                players.add(Player.newPlayer(("Enter a name for the " + i + "th player",
                  Board.players.get(i).getName() + " player"),
                             Board.players.get(i).getColor(),
                 1500));   //TODO scanner for name
                
            }
            board.players = players;
            
            board.isValid = boardConstructor.loadBoardFromMapFile(filename, board) 
                            && boardConstructor.validateXMLSchema("board.xsd", "board.xml");
        }
        else{
            board.players = new ArrayList<>();
        }

        return board;
    }
}