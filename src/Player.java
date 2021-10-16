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
    private Boolean ai;

    /**
     * Constructor for the Player object
     *
     * @param name sets the players name to this parameter
     */
    public Player(String name, Color color) {
        controlledProperties = new ArrayList<>();
        this.name = name;
        this.color = color;
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
    public static Player newPlayer(String name, Color color) {

        return new Player(name, color);
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
}
