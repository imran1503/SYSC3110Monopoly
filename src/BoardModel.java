import java.awt.*;
import java.util.ArrayList;
import java.lang.Math;

/**
 * ArrayList of Players to store all Players in the BoardModel.
 * currentPlayer variable to keep track of current player in the BoardModel.
 * nextRoll to store if current player will roll again (true) or not (false).
 * board variable to store Board of the BoardModel
 * boardView variable to store BoardView of the game.
 * board Constructor to store constructor of the board.
 * enum Commands to use to operate game commands with.
 * diceValue1 and diceValue2 to store the random dice values that are rolled.
 */
public class BoardModel {
    public ArrayList<Player> players;
    private Player currentPlayer;
    private Boolean nextRoll;
    private Board board;
    private BoardView boardView;
    private BoardConstructor boardConstructor;
    public enum Commands {quit, roll, passTurn, help, purchaseProperty, purchaseHouse}
    private int diceValue1;
    private int diceValue2;

    /**
     * Constructor for Game
     */
    public BoardModel( ){
        this.players = new ArrayList<>();
        this.currentPlayer = null;
        this.nextRoll = true;
        this.board = new Board("board.xml");
        this.boardConstructor = new BoardConstructor();
        //creates the board
        boardConstructor.loadBoardFromMapFile(board);
        boardConstructor.validateXMLSchema("board.xsd", "board.xml");
        board.setIsValid(true);
        boardView = null;
        diceValue1 = -1;
        diceValue2 = -2;

        /**
        //       DETERMINING WHERE THE AI SHOULD DRAW THE LINE FOR BUYING PROPERTIES DEPENDING OIN ITS BANK ACCOUNT BALANCE.
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
        */

    }

    /**
     * Determines which player starts the game at random.
     * @return int of randomPlayer index, player chosen at random to go first
     */
    public int determineFirstPlayer() {
        int totalPlayers = players.size();
        int multiplierFactor = 70;
        // generate a random integer number in the range 0 to totalPlayers - 1 inclusive
        int randomPlayer = (int)((Math.random() * multiplierFactor)%totalPlayers);
        return randomPlayer;
    }

    /**
     * Implements try and Checks for catch exceptions to check if param is an integer.
     * Returns False if it does not work, and it catches either numberFormat or NullPointer
     * exception, indicating param is not an integer. Otherwise returns true.
     * @param s the scanned string to be checked
     * @return boolean true if param is integer, return false if not
     */
    public static boolean isInteger(String s) {
        try {
            Integer.parseInt(s);
        } catch(NumberFormatException e) {
            return false;
        } catch(NullPointerException e) {
            return false;
        }
        // only got here if we didn't return false
        return true;
    }

    /**
     * operates game commands, takes in command and undergoes respective functionality and returns boolean of true or false
     * @param command the command that the function will process
     */
    public void operateCommand(Commands command) {
        boardView.setEventLabel3Text("");
        String playerName = currentPlayer.getName();
        if (command.equals(Commands.quit)) {
            boardView.setEventLabel3Text("Game has ended.");
            System.exit(0);
            return;
        }
        if (command.equals(Commands.roll)) {
            if(nextRoll && (currentPlayer.getInJail() == false)) {
                nextRoll = roll();
                Properties propertyOn = board.getProperty(currentPlayer.getPositon());
                Player owner = propertyOn.getOwner();
                //if current player lands on a property it does not own, pay rent (which will pay if owner is not bank or is a tax property)
                if (!owner.equals(currentPlayer)) {
                    propertyOn.payRent(currentPlayer);
                    int taxPropertyLocation1 = 4;
                    int taxPropertyLocation2 = 38;
                    //if owner is not bank or Property is a Tax Property, then set label text to show player paying rent amount.
                    if(!owner.getName().equals("bank")||(propertyOn.getLocation() == taxPropertyLocation1)||(propertyOn.getLocation() == taxPropertyLocation2)) {
                        boardView.setEventLabel3Text(playerName + " pays $" + propertyOn.getRent() + " to " + owner.getName() + " on " + propertyOn.getName());
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
                        currentPlayer.removefromBalance(50); //Get out of Jail fee for not rolling a double after 3 turns.
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
        else if (command.equals(Commands.purchaseHouse)) {
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
            boardView.setEventLabelText("'roll' - Rolls a number die for current player","'purchase property' - Purchases property for current player is on");
            boardView.setEventLabel3Text("'purchase house' or 'purchase hotel' - Purchase house/hotel, asks player to type name of property to buy house/hotel on");
            System.out.println("'pass turn' - Current player's turn ends, passes turn to next player");
        }
        else {
            System.out.println("No such command exists!");
        }
        //if next player is bankrupt, pass turn and set event label text to current player has gone bankrupt.
        if(currentPlayer.getBankruptStatus()){
            int playerIndex = this.getCurrentPlayerIndex();
            boardView.getPlayerLists().get(playerIndex)[currentPlayer.getPositon()].setVisible(false);
            boardView.setEventLabel3Text(currentPlayer.getName()+ " has gone bankrupt!");
            passPlayerTurn();
        }
        //if 1 player left, end game.
        if(!checkNumOfActivePlayers()){operateCommand(Commands.quit);}
        //if next player has a color set, make purchase house button visible. Else make the button not visible.
        if(currentPlayer.getHasAColorSet()){
            boardView.setPurchaseHouseButtonVisibility(true);
        }
        else{
            boardView.setPurchaseHouseButtonVisibility(false);
        }
    }

    /**
     * Passes player's turn. Ends the current player's turn and passes it onto the next player.
     * If next player is bankrupt, passes player's turn again.
     * If next player is an AI Player, calls playAITurn method from AIPlayer class.
     */
    public void passPlayerTurn(){
        //Reset number of double rolls of current player
        currentPlayer.setNumOfDoubleRolls(0);
        int indexOfCurrentPlayer = players.indexOf(currentPlayer);
        //if current player is last player in list, set next player to first player in players list.
        //else next player is current player index + 1 Player from list
        if(indexOfCurrentPlayer == (players.size() - 1)){
            currentPlayer = players.get(0);
        }
        else{
            currentPlayer = players.get(1+indexOfCurrentPlayer);
        }
        //if after selecting next player and that player is bankrupt, pass player turn again.
        if(currentPlayer.getBankruptStatus()){
            passPlayerTurn();
        }
        //if next player is in Jail, have event label Text2 to say "roll a double to get out jail", Else set it to ""
        else if(currentPlayer.getInJail()){
            boardView.setEventLabelText("It's Now " + currentPlayer.getName() + " turn to roll.", "Roll a double to get out of Jail");
        }
        else{
            boardView.setEventLabelText("It's Now " + currentPlayer.getName() + " turn to roll.", "");
        }
        nextRoll = true;
        //if next player is AI, playAITurn()
        if(currentPlayer.getAi()){
            currentPlayer.playAITurn();
        }
    }


    /**
     * Rolls 2 dices with integer values between 1 and 6.
     * If player is not in jail, the player's position on the board will update according to total roll value.
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
        String playerName = currentPlayer.getName();
        int playerPosition = currentPlayer.getPositon();
        Boolean jailStatus = currentPlayer.getInJail();

        //generate 2 random integer numbers between 1 and 6
        int randomRoll1 = ((int)((Math.random()*totalNumOfSpaces)%numberOfSidesOnDice) + 1);
        int randomRoll2 = ((int)((Math.random()*totalNumOfSpaces)%numberOfSidesOnDice) + 1);
        this.diceValue1 = randomRoll1;
        this.diceValue2 = randomRoll2;

        int nextPlayerPosition = (randomRoll1 + randomRoll2 + playerPosition);

        //if double roll, increment current player's number of double rolls
        if(randomRoll1 == randomRoll2){
            currentPlayer.setNumOfDoubleRolls(currentPlayer.getNumOfDoubleRolls() + 1);
        }
        //if current player (lands on Go To Jail or rolls 3 doubles) and is not in Jail, send player to Jail
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
            //Remove current player's icon from previous location
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
            //Make current player's icon visible at new location
            boardView.getPlayerLists().get(playerIndex)[currentPlayer.getPositon()].setVisible(true);
        }
        String propertyName = board.getProperty(playerPosition).getName();
        //if double roll, return true. Else return false
        if(randomRoll1 == randomRoll2){
            if(!jailStatus) {
                boardView.setEventLabelText(playerName + " rolled a " + (randomRoll1 + randomRoll2) + ", landed on " + propertyName, "You rolled a double, you can roll again. ");
            }
            return true;
        }
        else {
            boardView.setEventLabelText(playerName + " rolled a " + (randomRoll1 + randomRoll2), "Landed on " + propertyName);
            return false;
        }
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
        int[] nonPurchasablePropertyLocations = {0,2,4,7,10,17,20,22,30,33,36,38};

        //Check property is not a non-Purchaseable property location
        for (int i = 0; i < nonPurchasablePropertyLocations.length; i++) {
            if(landedOnProperty.getLocation() == nonPurchasablePropertyLocations[i]){
                boardView.setEventLabelText("This property can not be purchased", "Property Name: "+propertyName);
                return;
            }
        }

        //if property owner is currentPlayer, elseif property owner is Not bank, else if currentPlayer balance below purchase price
        if(landedOnProperty.getOwner().equals(currentPlayer)){
            boardView.setEventLabelText("This property belongs to you already","Property Name: "+propertyName);
        }
        else{ if(!landedOnProperty.getOwner().getName().equals("bank")){
            boardView.setEventLabelText("This property belongs to someone else", "Property Name: "+propertyName);
        }
        else{ if(currentPlayer.getBalance() < landedOnProperty.getPrice()){
            boardView.setEventLabelText(playerName+" does Not have enough money to purchase this property", "Property Name: "+propertyName);
        }
        else { // Purchase the property for the current player
            currentPlayer.removefromBalance(landedOnProperty.getPrice());
            landedOnProperty.setOwner(currentPlayer);
            currentPlayer.gainProperty(landedOnProperty);
            boardView.setEventLabelText(playerName + " purchased "+propertyName, "Remaining Balance: "+currentPlayer.getBalance());

            //If after buying this property and it completes a colorSet, set hasAColorSet to true for the current player.
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
        }}}
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
        //Check if player owns the color set and houses numbers are correct
        for(int i = 0; i < sizeOfColorSet; i++){
            Properties propertySameColor = board.getColorPropertiesArrayList().get(colorOfProperty).get(i);
            if(!propertySameColor.getOwner().equals(currentPlayer)){
                owningColorSet = false;
            }
            if(!((propertySameColor.getNumHouses() >= numOfHouseCurrent)||(propertySameColor.getNumHotels() == 1))){
                owningEqualHouses = false;
            }
        }
        //Check if property is Not a nonHousesProperty
        int[] listOfNonHousesProperties= {5,12,15,25,28,35};
        for(int i = 0; i<listOfNonHousesProperties.length;i++){
            if(property.getLocation() == listOfNonHousesProperties[i]){
                boardView.setEventLabelText("Can Not buy a house on this property","");
                return;
            }
        }
        //if owner is Not current player
        if(!property.getOwner().equals(currentPlayer)){
            boardView.setEventLabelText(playerName+" does NOT own this property", "Porperty Name: "+propertyName);
        }
        // else if does not have color set of property
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
        //else if not correct number of houses bought, else if not enough balance, else if hotel on property already
        else if(!owningEqualHouses){
            boardView.setEventLabelText(playerName+" does NOT own enough of houses for the color set", "Property Name: "+propertyName);
        }
        else if(currentPlayer.getBalance() < property.getHousePrice()){
            boardView.setEventLabelText(playerName+" does Not have enough money to purchase this property", "Property Name: "+propertyName);
        }
        else if(property.getNumHotels() == 1){
            boardView.setEventLabelText("This property already has a hotel", "property Name: "+propertyName);
        }
        else{ // else buy house or hotel for current player on property
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
            //Update to display new changes to houses on property.
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
        //if 1 player left that is not bankrupt return false, else return true.
        if((totalNumPlayers-totalBankruptPlayers)==1){
            boardView.setEventLabelText("The Game has ended. "+currentPlayer.getName()+"wins!","");
            return false;
        }
        return true;
    }

    /**
     * Getter method for board of the game.
     * @return Board
     */
    public Board getBoard() {return board;}

    /**
     * Get a player at a specfic player index from the players List
     * @param playerIndex index of player to get from players plist
     * @return Player from the list at playerIndex
     */
    public Player getPlayer(int playerIndex){return players.get(playerIndex);}

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
     * Getter method for Board View of the game
     * @return BoardView
     */
    public BoardView getBoardView(){return boardView;}

    /**
     * Set the board view of this Board Model to the parameter
     * @param bd BoardView to set with
     */
    public void setBoardView(BoardView bd){this.boardView = bd;}

    /**
     * Returns diceValue1 which is set to a random int in roll()
     */
    public int getDiceValue1() { return diceValue1;}

    /**
     * Returns diceValue2 which is set to a random int in roll()
     */
    public int getDiceValue2() { return diceValue2;}

    /**
     * Getter method for nextRoll
     */
    public Boolean getNextRoll(){return nextRoll;}

    /**
     * Getter method for currentPlayerIndex
     */
    public int getCurrentPlayerIndex(){return players.indexOf(currentPlayer);}

    /**
     * Getter method of currentPlayer in the game.
     */
    public Player getCurrentPlayer() { return currentPlayer; }

    /**
     * Main method to initialize and start the game.
     */
    public static void main(String args[]){
        BoardModel boardModel = new BoardModel();
        BoardView boardView = new BoardView(boardModel);
        boardModel.setBoardView(boardView);
        boardView.displayGUI();
    }

}
