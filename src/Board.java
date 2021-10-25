
import java.awt.*;
import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.Scanner;

public class Board {

    private Scanner reader;
    private BoardConstructor boardConstructor;
    private static ArrayList<Player> players;
    private ArrayList<Properties> propertiesArrayList;
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
     * Returns the user input source of the Board
     * @return The user input source of the Board
     */
    public String getUserInputSource() {
        return userInputSource;
    }

    public ArrayList<Properties> getPropertiesArrayList() {
        return propertiesArrayList;
    }

    public void setIsValid(Boolean valid){this.isValid = valid;}

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