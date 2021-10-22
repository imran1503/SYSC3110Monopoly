import java.awt.*;
import java.util.ArrayList;
import java.util.List;

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

    private int ownsXtrains;

    /*** The player's name*/
    private String name;

    /**The player's integer position on the board*/
    private int positon;

    /**The color representing the player on the game board*/
    private Color color;

    private Boolean inJail;

    private int turnsInJail;

    /**
     * Indicates whether the player is AI controlled
     */
    //private Boolean ai;

    /** Status of player if Banrupt or not. If false then not bankrupt, If true then Bankrupt*/
    private Boolean bankruptStatus;
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
        ownsXtrains=0;
        this.positon = 0;
        this.bankruptStatus = false;
        this.inJail = false;
        this.turnsInJail = 0;
    }

    /**
     * Returns the color of the player
     *
     * @return The color of the player
     */
    public Color getColor() { return color; }

    public String getName(){return name;}

    public int getOwnsXtrains() {
        return ownsXtrains;
    }

    public void setOwnsXtrains(int ownsXtrains) {
        this.ownsXtrains = ownsXtrains;
    }

    public int getPositon(){
        return positon;
    }

    public Boolean getInJail() {
        return inJail;
    }

    public void setInJail(Boolean inJail) {
        this.inJail = inJail;
    }

    public int getTurnsInJail(){return turnsInJail;}

    public void setTurnsInJail(int turn){turnsInJail = turn;}

    /**
     * Changes position of player to the new position
     *
     * @param newPosition The amount to move forward
     */
    public void setPosition(int newPosition){
        this.positon = newPosition;
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
    public List<Properties> getControlledProperties() {
        return controlledProperties;
    }

    public void removeAllControlledProperties(){
        for(int i = 0; i < controlledProperties.size(); i++){
            controlledProperties.get(i).setOwner(null);
            controlledProperties.get(i).setNumHouses(0);
            controlledProperties.get(i).setNumHotels(0);
        }
    }

    public Boolean getOwnsBothUtil() {
        return ownsBothUtil;
    }

    public void setOwnsBothUtil(Boolean ownsBothUtil) {
        this.ownsBothUtil = ownsBothUtil;
    }

    public int getBalance(){return balance;}

    public void addToBalance(int amount){
        balance += amount;
    }

    public void removefromBalance(int amount){
        if(balance < amount){
            this.balance = 0;
            this.bankruptStatus = true;
            this.removeAllControlledProperties();
            System.out.println(this.name+" goes Bankrupt!");
        }
        else{
            balance -= amount;
        }
    }

    public Boolean getBankruptStatus(){return bankruptStatus;}

    public void setBankruptStatus(Boolean status){bankruptStatus=status;}
}
