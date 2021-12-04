import java.awt.*;
import java.util.ArrayList;
import java.util.List;

/**
 * Class Player, Models a Monopoly player.
 * Each player controls a list of Property (controlledProperty)
 * ownsBothUtil, true if Player owns both utilities on the board in the game, else false.
 * Integer balance to store the balance a player has and can use.
 * Also owns a number train stations on the board (ownsXtrains).
 * name is String name of Player.
 * postion is the player's integer position on the board.
 * color is the color representing the player on the game board.
 * inJail, true if Player is in Jail in the game, else false.
 * turnsInJail is Integer of how many turns a Player is in Jail, can go from 1 to 3.
 * bankruptStatus is Status of player if Bankrupt or not. If false then not bankrupt, If true then Bankrupt.
 */
public class Player {

    private List<Property> controlledProperty;
    private Boolean ownsBothUtil;
    private int balance;
    private int ownsXtrains;
    private String name;
    private int positon;
    private Color color;
    private Boolean inJail;
    private int turnsInJail;
    private int numOfDoubleRolls;
    private Boolean ai;
    private Boolean bankruptStatus;
    private Boolean hasAColorSet;

    /**
     *
     * @param name sets the players name to this parameter
     * @param color player's color set to this parameter
     * @param startingMoney starting money of player is set to this parameter
     */
    public Player(String name, Color color, int startingMoney, boolean isAi) {
        controlledProperty = new ArrayList<>();
        this.name = name;
        this.color = color;
        ownsBothUtil = false;
        balance = startingMoney; // Should be 1000$
        ownsXtrains=0;
        this.positon = 0;
        this.bankruptStatus = false;
        this.inJail = false;
        this.turnsInJail = 0;
        this.ai = isAi;
        this.numOfDoubleRolls = 0;
        this.hasAColorSet = false;

    }

    /**
     * Returns the color of the player
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
     * @param newPosition The amount to move forward
     */
    public void setPosition(int newPosition){
        this.positon = newPosition;
    }

    /**
     * Creates a new Player
     * @param name  The player name
     * @param color The player color
     * @return the new Player created
     */
    public static Player newPlayer(String name, Color color, int startingMoney, boolean isAi) {

        return new Player(name, color, startingMoney, isAi);
    }

    /**
     * Appends property onto controlledProperty ArrayList
     * @param property the property param appended onto controlledProperty ArrayList
     */
    public void gainProperty(Property property) {
        controlledProperty.add(property);
    }

    /**
     * Removes a given property from the controlledProperty List of the Player
     * @param property
     */
    public void removeProperty(Property property) {
        controlledProperty.remove(property);
    }

    /**
     * Gets the List of Property the player owns.
     * @return List<Property>
     */
    public List<Property> getControlledProperty() {
        return controlledProperty;
    }

    /**
     * Removes all the Property the Player owns.
     */
    public void removeAllControlledProperty(){
        for(int i = 0; i < controlledProperty.size(); i++){
            controlledProperty.get(i).setOwner(newPlayer("bank",new Color(10,10,10),1000000,false));
            controlledProperty.get(i).setNumHouses(0);
            controlledProperty.get(i).setNumHotels(0);

        }
        controlledProperty = new ArrayList<>();
        hasAColorSet = false;
        ownsBothUtil = false;
        ownsXtrains = 0;
    }

    /**
     * Getter method for ownsBothUtil status of the Player
     * @return Boolean
     */
    public Boolean getOwnsBothUtil() {
        return ownsBothUtil;
    }

    /**
     * Setter method for ownsBothUtil by a given Boolean.
     * @param ownsBothUtil Boolean to set ownsBothUtil to.
     */
    public void setOwnsBothUtil(Boolean ownsBothUtil) {
        this.ownsBothUtil = ownsBothUtil;
    }

    /**
     * Getter method for the balance a Player has
     * @return Integer balance of the Player
     */
    public int getBalance(){return balance;}

    /**
     * Method to add a given integer amount to Player's balance
     * @param amount the Integer amount to add
     */
    public void addToBalance(int amount){
        balance += amount;
    }

    /**
     * Method to remove a given integer amount from Player's balance.
     * Player will go bankrupt if the amount is greater than player's current balance.
     * @param amount Integer amount to remove
     */
    public void removefromBalance(int amount){
        if(balance < amount){
            this.balance = 0;
            this.bankruptStatus = true;
            this.removeAllControlledProperty();
            System.out.println(this.name+" goes Bankrupt!");
        }
        else{
            balance -= amount;
        }
    }

    /**
     * Getter method for a Player's bankruptStatus.
     * @return Boolean of the Player's bankrupt status, true if player is bankrupt, else false.
     */
    public Boolean getBankruptStatus(){return bankruptStatus;}

    /**
     * Setter method for Player's bankruptStatus.
     * @param status Boolean to set bankruptStatus with.
     */
    public void setBankruptStatus(Boolean status){bankruptStatus=status;}

    /**
     * Getter method for numOfDoubleRolls
     */
    public int getNumOfDoubleRolls(){return numOfDoubleRolls;}

    /**
     * Setter method for numOfDoubleRolls
     */
    public void setNumOfDoubleRolls(int number){numOfDoubleRolls = number;}

    /**
     * Getter method for hasAColorSet
     */
    public Boolean getHasAColorSet(){return hasAColorSet;}

    /**
     * Setter method for hasAColorSet
     */
    public void setHasAColorSet(Boolean status){hasAColorSet = status;}

    /**
     * Getter method for AI
     */
    public Boolean getAi(){return ai;}

    /**
     * Blank method for AI player to override
     */
    public void playAITurn(){}


}
