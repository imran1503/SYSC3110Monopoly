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
     *
     * @param name sets the players name to this parameter
     * @param color player's color set to this parameter
     * @param startingMoney starting money of player is set to this parameter
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

    /**
     * Gets and returns name of the player
     * @return name the name of the player
     */
    public String getName(){return name;}

    /**
     * gets number of Xtrains owned
     * @return ownsXtrains num int of xtrains owned by player
     */
    public int getOwnsXtrains() {
        return ownsXtrains;
    }

    /**
     * Sets number of player's owned Xtrains
     * @param ownsXtrains the number of Xtrains
     */
    public void setOwnsXtrains(int ownsXtrains) {
        this.ownsXtrains = ownsXtrains;
    }

    /**
     * gets and returns the board posiiton the player is landed on. this is an int value.
     * @return position of the player on the board
     */
    public int getPositon(){
        return positon;
    }

    /**
     * Gets boolean value of inJail. Returns whether the player is currently in jail or not.
     * True if so, False if not.
     * @return
     */
    public Boolean getInJail() {
        return inJail;
    }

    /**
     * Set inJail value. set to true if player is sent to jail, false if not.
     * @param inJail value indicating whether player is in jail or not
     */
    public void setInJail(Boolean inJail) {
        this.inJail = inJail;
    }

    /**
     * Get turnsInJail int value of player
     * @return turnsInJail int value
     */
    public int getTurnsInJail(){return turnsInJail;}

    /**
     * Set the turnsInJail for the player, assign it to turn variable of type int
     * @param turn value for turnsInJail
     */
    public void setTurnsInJail(int turn){this.turnsInJail = turn;}

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
     * Appends property onto controlledProperties ArrayList
     * @param property the property param appended onto controlledProperties ArrayList
     */
    public void gainTerritory(Properties property) {
        controlledProperties.add(property);
    }

    /**
     * Removes property param from
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
