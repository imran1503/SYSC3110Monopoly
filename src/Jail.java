import java.awt.*;

/**
 * Class Jail, is a special Property.
 *
 */
public class Jail extends Property {
    private int enteredJailTurn;
    private int timeInJail = 3;

    /**
     * Constructor for Jail.
     * @param name name of Jail
     * @param color Color of Jail
     * @param location location of Jail
     */
    public Jail(String name, Color color, int location ) {
        super(name, 0, 0, color, location);
    }

    /**
     * Method to update a Player's time in Jail.
     * @param player Player that is in Jail
     * @param turncount Integer number of turns a player is Jail
     */
    public void timeInJail(Player player, int turncount){
        enteredJailTurn = turncount;

    }

    /**
     * Decrements a Player's time in Jail. If it reaches Zero, the Player is set free from Jail.
     * @param player Player that is in Jail
     */
    public void timePass(Player player){
        timeInJail -= 1;
        if (timeInJail == 0){
            player.setInJail(false);
        }
    }


}
