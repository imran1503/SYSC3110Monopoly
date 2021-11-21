import javax.swing.*;
import java.awt.*;
import java.util.ArrayList;
import java.lang.Math;
import java.util.Scanner;

/**
 * Class BoardModel, int MAX_PLAYERS variable to store maxium number of players.
 * ArrayList of Players to store all Players in the BoardModel.
 * currentPlayer variable to keep track of current player in the BoardModel.
 * reader variable to store input from users.
 * nextRoll to store if current player will roll again (true) or not (false).
 * board variable to store Board of the BoardModel
 * board Constructor to store constructor of the board.
 */
public class BoardModel {
    private static int MAX_PLAYERS = 0;
    public ArrayList<Player> players;

    private Player currentPlayer;
    private Scanner reader; // for taking command input
    private Boolean nextRoll;
    private Board board;
    private BoardView boardView;
    private BoardConstructor boardConstructor;
    public enum Commands {quit, roll, passTurn, help, purchaseProperty, purchaseHouse, purchaseHotel}
    private boolean HasCurrPlayerRolled;
    private int diceValue1;
    private int diceValue2;

    public Player getCurrentPlayer() {
        return currentPlayer;
    }

    /**
     * Constructor for Game
     */
    public BoardModel( ){
        this.players = new ArrayList<Player>();
        this.currentPlayer = null;
        this.nextRoll = true;
        this.board = new Board("board.xml");
        this.reader = new Scanner(System.in);
        this.boardConstructor = new BoardConstructor();
        //creates the board
        boardConstructor.loadBoardFromMapFile(board);
        boardConstructor.validateXMLSchema("board.xsd", "board.xml");
        board.setIsValid(true);
        HasCurrPlayerRolled = false;
        boardView = null;
        diceValue1 = -1;
        diceValue2 = -2;

        /**
        //       DETERMINING WHERE THE AI SHOULD DRAW THE LINE FOR BUYING PROPERTIES DEPENDING OIN ITS BANK ACCOUNT BALANCE.  */
        Player test = new Player("test", new Color(255,255,205),750, true ) ;
        for (int i = 0; i < board.getPropertiesArrayList().size(); i++) {
            if (!board.getProperty(i).getColor().equals(new Color(255,255,255))){
                if(!board.getProperty(i).getColor().equals(new Color(250, 140, 0))){
                    System.out.println( board.getProperty(i).getName()
                            + " "+ board.getProperty(i).getCostBenfitRatio(test.getBalance(), board.getProperty(i).getHousePrice(), board.getProperty(i).getPrice() )
                            + "   HP >" + board.getProperty(i).getHousePrice()
                            + "   P >" + board.getProperty(i).getPrice()
                            + "   bal >" + test.getBalance()
                            );}
            }
        }

    }

    public boolean getHasCurrPlayerRolled() {
        return HasCurrPlayerRolled;
    }

    public void setHasCurrPlayerRolled(boolean setHasCurrPlayerRolled) {
        this.HasCurrPlayerRolled = setHasCurrPlayerRolled;
    }

    public BoardConstructor getBoardConstructor() {
        return boardConstructor;
    }
    public Board getBoard() {
        return board;
    }

    public Player getPlayer(int playerIndex){return players.get(playerIndex);}


    /**
     * Determines which player starts the game at random.
     * @return randomPlayer, player chosen at random to go first
     */
    public int determineFirstPlayer() {
        int totalPlayers = players.size();
        // generate a random number in the range 1 to totalPlayers inclusive
        int randomPlayer = 1 + (int)(Math.random() * ((totalPlayers - 1) + 1));
        return randomPlayer;
    }

    /**
     * Implements try and Checks for catch exceptions to check if param is integer.
     * Returns False If max_players does not work, and it catches either numberFormat or NullPointer
     * exception, indicating param is not an integer. Otherwise returns true.
     * @param s the scanned string to be checked
     * @return boolean true if param is integer, return false if not
     */
    public static boolean isInteger(String s) {
        try {
            MAX_PLAYERS = Integer.parseInt(s);
        } catch(NumberFormatException e) {
            return false;
        } catch(NullPointerException e) {
            return false;
        }
        // only got here if we didn't return false
        return true;
    }

    /**
     * Print out the opening message for the players on game start.
     */
    private void welcomeMessage() {
        System.out.println("Welcome to the game of Monopoly!");
        System.out.println("Type 'help' if you ever need a command list with explanation.");
        System.out.println();
    }

    /**
     * operates game commands, takes in command and undergoes respective functionality and returns boolean of true or false
     * @param command the command that the function will process
     * @return boolean true if game still in progress, false if not.
     */
    public boolean operateCommand(Commands command) {
        boardView.setEventLabel3Text("");
        String playerName = currentPlayer.getName();
        if (command.equals(Commands.quit)) {
            boardView.setEventLabel3Text("Game has ended.");
            System.exit(0);
        }
        if (command.equals(Commands.roll)) {
            if(nextRoll && (currentPlayer.getInJail() == false)) {
                nextRoll = roll();
                Properties propertyOn = board.getProperty(currentPlayer.getPositon());
                if (!propertyOn.getOwner().equals(currentPlayer)) {
                    propertyOn.payRent(currentPlayer);
                    if(!propertyOn.getOwner().getName().equals("bank")||(propertyOn.getLocation() == 4)||(propertyOn.getLocation() == 38)) {
                        boardView.setEventLabel3Text(playerName + " pays $" + propertyOn.getRent() + " to " + propertyOn.getOwner().getName() + " on " + propertyOn.getName());
                    }
                }
            }
            else{

                boardView.setEventLabelText(playerName+" can NOT roll again. Pass your turn or buy property.","");
            }
            if ((currentPlayer.getInJail() == true)){
                Boolean isDouble = roll();
                if (isDouble && (currentPlayer.getTurnsInJail() != 0)) {
                    currentPlayer.setInJail(false);
                    currentPlayer.setTurnsInJail(0);
                    boardView.setEventLabelText(playerName + " rolled a double and is out of jail.","");
                } else {
                    if (currentPlayer.getTurnsInJail() == 3) {
                        currentPlayer.removefromBalance(50);
                        currentPlayer.setInJail(false);
                        currentPlayer.setTurnsInJail(0);
                        boardView.setEventLabel3Text(playerName + " Payed $50 to get out of jail.");
                    }
                    else if((currentPlayer.getTurnsInJail() != 0)){
                        boardView.setEventLabel3Text(playerName + " did Not roll a double");
                    }
                    currentPlayer.setTurnsInJail(currentPlayer.getTurnsInJail() + 1); //add 1 to time in jail for player.
                    passPlayerTurn();
                }
            }
        }
        else if (command.equals(Commands.purchaseProperty)) {
            purchaseProperty();
        }
        else if (command.equals(Commands.purchaseHouse) || command.equals(Commands.purchaseHotel)) {
            boardView.setEventLabelText("Type in the property name on which you", "would like to purchase a house/hotel on.");

        }
        else if (command.equals(Commands.passTurn)) {

            if (nextRoll == true) {
                boardView.setEventLabelText(playerName + " needs to roll again before passing turn.","");
            } else {
                passPlayerTurn();
            }
        }
        else if (command.equals(Commands.help)) {
            System.out.println("All commands are below with brief explanation:");
            System.out.println("'quit' - Ends the game immediately");
            System.out.println("'roll' - Rolls a number die for current player");
            System.out.println("'purchase property' - Purchases property for current player, the property is the position player is on");
            System.out.println("'purchase house' or 'purchase hotel' - Purchase house/hotel, asks player to type name of house/hotel to be purchased");
            System.out.println("'pass turn' - Current player's turn ends, passes turn to next player");
            System.out.println("'check game state' - Outputs all Player's current status such as current Position, Balance, Bankrupt, Jail and Owned Properties status ");
        }
        else {
            System.out.println("No such command exists!");
        }
        if(currentPlayer.getBankruptStatus()){
            int playerIndex = this.getCurrentPlayerIndex();
            boardView.getPlayerLists().get(playerIndex)[currentPlayer.getPositon()].setVisible(false);
            boardView.setEventLabel3Text("Current player has bankrupted!");
            passPlayerTurn();
        }
        if(!checkNumOfActivePlayers()){operateCommand(Commands.quit);}
        if(currentPlayer.getHasAColorSet()){
            boardView.setPurchaseHouseButtonVisibility(true);
        }
        else{
            boardView.setPurchaseHouseButtonVisibility(false);
        }
        return true;
    }

    /**
     * Appends player object to Player Arraylist.
     * @param player param that is appended to arraylist
     */
    public void addPlayer(Player player){players.add(player);}

    /**
     * Sets the player param to the current player variable
     * @param player param that is set as currentPlayer
     */
    public void setCurrentPlayer(Player player){
        currentPlayer = player;
    }


    /**
     * Passes player's turn. Ends the current player's turn and passes it onto the next player.
     * If next player is bankrupt, passes player's turn again.
     */
    public void passPlayerTurn(){
        currentPlayer.setNumOfDoubleRolls(0);
        int indexOfCurrentPlayer = players.indexOf(currentPlayer);
        if(indexOfCurrentPlayer == (players.size() - 1)){
            currentPlayer = players.get(0);
        }
        else{
            currentPlayer = players.get(1+indexOfCurrentPlayer);
        }
        if(currentPlayer.getBankruptStatus()){
            passPlayerTurn();
        }
        else if(currentPlayer.getInJail()){
            boardView.setEventLabelText("It's Now " + currentPlayer.getName() + " turn to roll.", "Roll a double to get out of Jail");
        }
        else{
            boardView.setEventLabelText("It's Now " + currentPlayer.getName() + " turn to roll.", "");
        }
        nextRoll = true;
        if(currentPlayer.getAi()){
            currentPlayer.playAITurn();
        }

    }


    /**
     * Rolls 2 dices with integer values between 1 and 6.
     * If player is not in jail, the player's position on board will update according to total roll value.
     * Returns true if both both dices are the same value, otherwise false.
     * @return Boolean true if double, else false.
     */
    public boolean roll(){
        int totalNumOfSpaces = 40;
        int numberOfSidesOnDice = 6;
        int goToJailPosition = 30;
        int jailPosition = 10;
        int passingGoAmount = 200;
        int playerIndex = this.getCurrentPlayerIndex();

        int randomRoll1 = ((int)((Math.random()*totalNumOfSpaces)%numberOfSidesOnDice) + 1); // +1 to get range from 1 to 6.
        int randomRoll2 = ((int)((Math.random()*totalNumOfSpaces)%numberOfSidesOnDice) + 1);

        this.diceValue1 = randomRoll1;
        this.diceValue2 = randomRoll2;

        int playerPosition = currentPlayer.getPositon();
        int nextPlayerPosition = (randomRoll1 + randomRoll2 + playerPosition);
        String playerName = currentPlayer.getName();

        Boolean jailStatus = currentPlayer.getInJail();
        if(randomRoll1 == randomRoll2){
            currentPlayer.setNumOfDoubleRolls(currentPlayer.getNumOfDoubleRolls() + 1);
        }
        if(((nextPlayerPosition == goToJailPosition)||(currentPlayer.getNumOfDoubleRolls() == 3))&&!jailStatus){ // If player lands on Go to Jail
            boardView.getPlayerLists().get(playerIndex)[playerPosition].setVisible(false);
            currentPlayer.setNumOfDoubleRolls(0);
            currentPlayer.setInJail(true);
            jailStatus = true;
            currentPlayer.setPosition(jailPosition);
            boardView.getPlayerLists().get(playerIndex)[currentPlayer.getPositon()].setVisible(true);
            boardView.setEventLabel3Text(playerName+" has been set to Jail, roll a double to get out of Jail next turn.");
        }
        if(!jailStatus) {
            //Remove player icon of previous location
            boardView.getPlayerLists().get(playerIndex)[currentPlayer.getPositon()].setVisible(false);
            //For moving player
            if (nextPlayerPosition >= totalNumOfSpaces) { // if player passes Go
                currentPlayer.setPosition(nextPlayerPosition - totalNumOfSpaces);
                currentPlayer.addToBalance(passingGoAmount);
                boardView.setEventLabel3Text(playerName+" has passed Go, Balance is now "+currentPlayer.getBalance());
                playerPosition = (nextPlayerPosition - totalNumOfSpaces);
            } else {
                currentPlayer.setPosition(nextPlayerPosition);
                playerPosition = nextPlayerPosition;
            }
            //Make player icon visible at new location
            boardView.getPlayerLists().get(playerIndex)[currentPlayer.getPositon()].setVisible(true);
        }
        String propertyName = board.getProperty(playerPosition).getName();
        if(randomRoll1 == randomRoll2){
            if(!jailStatus) {
                boardView.setEventLabelText(playerName + " rolled a " + (randomRoll1 + randomRoll2) + ", landed on " + propertyName, "You rolled a double, you can roll again. ");
            }
            return true;
        }
        boardView.setEventLabelText(playerName+" rolled a "+(randomRoll1+randomRoll2), "Landed on "+propertyName);
        return false;
    }

    /**
     * Purchases property for currentPlayer based on player's position.
     * Purchases property currentPlayer has landed on.
     */
    public void purchaseProperty(){
        int playerPosition = currentPlayer.getPositon();
        Properties landedOnProperty = board.getProperty(playerPosition);
        String propertyName = landedOnProperty.getName();
        String playerName = currentPlayer.getName();
        if(landedOnProperty.getPrice() == 0){
            boardView.setEventLabelText("This property can not be purchased", "Property Name: "+propertyName);
        }
        else{ if(landedOnProperty.getOwner().equals(currentPlayer)){
            boardView.setEventLabelText("This property belongs to you already","Property Name: "+propertyName);
        }
        else{ if(!landedOnProperty.getOwner().getName().equals("bank")){
            boardView.setEventLabelText("This property belongs to someone else", "Property Name: "+propertyName);
        }
        else{ if(currentPlayer.getBalance() < landedOnProperty.getPrice()){
            boardView.setEventLabelText(playerName+" does Not have enough money to purchase this property", "Property Name: "+propertyName);
        }
        else {
            currentPlayer.removefromBalance(landedOnProperty.getPrice());
            landedOnProperty.setOwner(currentPlayer);
            currentPlayer.gainProperty(landedOnProperty);
            boardView.setEventLabelText(playerName + " purchased "+propertyName, "Remaining Balance: "+currentPlayer.getBalance());
            Color colorOfProperty = landedOnProperty.getColor();
            Boolean ownsColorSet = true;
            for(int i =0; i<board.getColorPropertiesArrayList().get(colorOfProperty).size(); i++){
                if(!board.getColorPropertiesArrayList().get(colorOfProperty).get(i).getOwner().equals(currentPlayer)){
                    ownsColorSet = false;
                }
            }
            if(ownsColorSet){
                currentPlayer.setHasAColorSet(true);
            }
        }}}}
    }

    /**
     * Returns void. Purchases house or hotel, depends on what the player chooses to purchase.
     * @param property param that is used to
     */
    public void purchaseHouseOrHotel(Properties property){
        Boolean owningColorSet = true;
        Boolean owningEqualHouses = true;
        int numOfHouseCurrent = property.getNumHouses();
        Color colorOfProperty = property.getColor();
        String propertyName = property.getName();
        String playerName = currentPlayer.getName();
        int sizeOfColorSet = board.getColorPropertiesArrayList().get(colorOfProperty).size();
        for(int i = 0; i < sizeOfColorSet; i++){
            Properties propertySameColor = board.getColorPropertiesArrayList().get(colorOfProperty).get(i);
            if(!propertySameColor.getOwner().equals(currentPlayer)){
                owningColorSet = false;
            }
            if(!((propertySameColor.getNumHouses() >= numOfHouseCurrent)||(propertySameColor.getNumHotels() == 1))){
                owningEqualHouses = false;
            }
        }
        int[] listOfNonHousesProperties= {5,12,15,25,28,35};
        for(int i = 0; i<listOfNonHousesProperties.length;i++){
            if(property.getLocation() == listOfNonHousesProperties[i]){
                boardView.setEventLabelText("Can Not buy a house on this property","");
                return;
            }
        }
        if(!property.getOwner().equals(currentPlayer)){
            boardView.setEventLabelText(playerName+" does NOT own this property", "Porperty Name: "+propertyName);
        }
        else if(!owningColorSet){
            String missingProperties = "";
            for(int i =0; i<sizeOfColorSet;i++){
                if(!board.getColorPropertiesArrayList().get(colorOfProperty).get(i).equals(property)){
                    missingProperties += "- "+board.getColorPropertiesArrayList().get(colorOfProperty).get(i).getName()+" ";
                }
            }
            boardView.setEventLabelText(playerName+" does NOT own the color set of this property", "Missing Properties: ");
            boardView.setEventLabel3Text(missingProperties);
        }
        else if(!owningEqualHouses){
            boardView.setEventLabelText(playerName+" does NOT own enough of houses for the color set", "Property Name: "+propertyName);
        }
        else if(currentPlayer.getBalance() < property.getHousePrice()){
            boardView.setEventLabelText(playerName+" does Not have enough money to purchase this property", "Property Name: "+propertyName);
        }
        else if(property.getNumHotels() == 1){
            boardView.setEventLabelText("This property already has a hotel", "property Name: "+propertyName);
        }
        else{
            currentPlayer.removefromBalance(property.getHousePrice());
            if(numOfHouseCurrent == 4) {
            property.setNumHotels(1);
            property.setNumHouses(0);
            boardView.setEventLabelText(playerName + " purchased Hotel on: "+propertyName, "Remaining Balance: "+currentPlayer.getBalance());
            }
            else{
            property.setNumHouses((1+numOfHouseCurrent));
            boardView.setEventLabelText(playerName + " purchased House on: "+propertyName, "Remaining Balance: "+currentPlayer.getBalance());
            }
            boardView.updateHousesIcons(property.getLocation());
        }
    }

    /**
     * Return false if number players that are not bankrupt equal to 1. Else returns true.
     * @return Boolean false to end game, true to continue.
     */
    public Boolean checkNumOfActivePlayers(){
        int totalNumPlayers = players.size();
        int totalBankruptPlayers = 0;
        for(int i=0; i<totalNumPlayers;i++) {
            if(players.get(i).getBankruptStatus()){
                totalBankruptPlayers +=1;
            }
        }
        if((totalNumPlayers-totalBankruptPlayers)==1){
            boardView.setEventLabelText("The Game has ended. "+currentPlayer.getName()+"wins!","");
            return false;
        }
        return true;
    }

    public BoardView getBoardView(){return boardView;}

    public void setBoardView(BoardView bd){this.boardView = bd;}


    /**
     * Sets diceValue1. Used for testing in gameTest.
     * @return
     */
    public void setDiceValue1(int diceValue2) {
        this.diceValue2 = diceValue2;
    }

    /**
     * Returns diceValue1 which is set to a random int in roll()
     * @return
     */
    public int getDiceValue1() {
        return diceValue1;
    }

    /**
     * Sets diceValue2. Used for testing in GameTest.
     * @return
     */
    public void setDiceValue2(int diceValue2) {
        this.diceValue2 = diceValue2;
    }

    /**
     * Returns diceValue2 which is set to a random int in roll()
     */
    public int getDiceValue2() {
        return diceValue2;
    }

    /**
     * Getter method for nextRoll
     */
    public Boolean getNextRoll(){return nextRoll;}

    /**
     * Getter method for currentPlayerIndex
     */
    public int getCurrentPlayerIndex(){return players.indexOf(currentPlayer);}

    public static void main(String args[]){
        BoardModel boardModel = new BoardModel();
        BoardView boardView = new BoardView(boardModel);
        boardModel.setBoardView(boardView);
        boardView.displayGUI();
    }

}
