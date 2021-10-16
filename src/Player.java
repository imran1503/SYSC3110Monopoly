import java.awt.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Properties;

/**
 * Models a RISK player.
 * Each player controls a list of territories.
 */
public class Player {
    /**
     * The list of territories the player owns
     */
    private List<Properties> controlledProperties;

    private Boolean ownsBothUtil;

    private int balance;

    /**
     * The player's name
     */
    private String name;

    /**
     * The color representing the player on the game board
     */
    private Color color;

    /**
     * Indicates whether the player is AI controlled
     */
    //private Boolean ai;

    /**
     * Constructor for the Player object
     *
     * @param name sets the players name to this parameter
     */
    public Player(String name, Color color, int startingMoney) {
        controlledProperties = new ArrayList<>();
        this.name = name;
        this.color = color;
        ownsBothUtil = false;
        balance = startingMoney; // Should be 1000$
    }

    /**
     * Returns the color of the player
     *
     * @return The color of the player
     */
    public Color getColor() {
        return color;
    }


    /**
     * Creates a new Player
     *
     * @param name  The player name
     * @param color The player color
     * @return
     */
    public static Player newPlayer(String name, Color color, int startingMoney) {

        return new Player(name, color, startingMoney);
    }


    /**
     * @param property
     */
    public void gainTerritory(Properties property) {
        controlledProperties.add(property);
    }

    /**
     * @param property
     */
    public void removeTerritory(Properties property) {
        controlledProperties.remove(property);
    }

    /**
     * Gets the number of territories the player own by getting the size of the controlledTerritories list.
     *
     * @return the size of the controlledTerritories list
     */
    public int getControlledProperties() {
        return controlledProperties.size();
    }

    public Boolean getOwnsBothUtil() {
        return ownsBothUtil;
    }

    public void setOwnsBothUtil(Boolean ownsBothUtil) {
        this.ownsBothUtil = ownsBothUtil;
    }

    public void addToBalance(int amount){
        balance += amount;
    }

    public void removeToBalance(int amount){
        balance -= amount;
    }
}
