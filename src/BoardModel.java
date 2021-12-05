import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;
import org.xml.sax.SAXException;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;
import java.awt.*;
import java.io.*;
import java.util.ArrayList;
import java.lang.Math;

/**
 * Class BoardModel, Model of the board of the game. Has most of the game functionality commands.
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
    //todo add to uml
    private ArrayList<Player> winnersList, losersList;
    //todo add to uml
    private int numPropertiesLeft; // count of purchasable properties left for purchase
    private Boolean nextRoll;
    private Board board;
    private BoardView boardView;
    private BoardConstructor boardConstructor;
    public enum Commands {quit, roll, passTurn, help, purchaseProperty, purchaseHouse}
    private int diceValue1,diceValue2;

    /**
     * Constructor for Game
     */
    public BoardModel(String fileName){
        this.players = new ArrayList<>();
        this.currentPlayer = null;
        this.winnersList = new ArrayList<>();
        this.losersList = new ArrayList<>();
        this.nextRoll = true;
        this.numPropertiesLeft = 26; // total number of purchasable properties = 22 streets + 4 railroads = 26
        this.board = new Board("src/"+fileName);
        this.boardConstructor = new BoardConstructor(board);
        //creates the board
        this.board = boardConstructor.loadBoardFromMapFile(fileName,true);
        //boardConstructor.validateXMLSchema("board.xsd", "board.xml");
        board.setIsValid(true);
        boardView = null;
        diceValue1 = -1;
        diceValue2 = -2;
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
        String currency = board.getCurrency();
        if (command.equals(Commands.quit)) {
            boardView.setEventLabel3Text("Game has ended.");
            boardView.resultsMessageWindow();
            return;
        }
        if (command.equals(Commands.roll)) {
            if(nextRoll && (currentPlayer.getInJail() == false)) {
                nextRoll = roll();
                Property propertyOn = board.getProperty(currentPlayer.getPositon());
                Player owner = propertyOn.getOwner();
                //if current player lands on a property it does not own, pay rent (which will pay if owner is not bank or is a tax property)
                if (!owner.equals(currentPlayer)) {
                    propertyOn.payRent(currentPlayer);
                    int taxPropertyLocation1 = 4;
                    int taxPropertyLocation2 = 38;
                    //if owner is not bank or Property is a Tax Property, then set label text to show player paying rent amount.
                    if(!owner.getName().equals("bank")||(propertyOn.getLocation() == taxPropertyLocation1)||(propertyOn.getLocation() == taxPropertyLocation2)) {
                        boardView.setEventLabel3Text(playerName + " pays "+currency + propertyOn.getRent() + " to " + owner.getName() + " on " + propertyOn.getName());
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
                        boardView.setEventLabel3Text(playerName + " Payed "+currency+"50 to get out of jail.");
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
            boardView.updateAllHousesIcons();
            passPlayerTurn();
        }

        if (gameHasEnded().equals(true)) {
            operateCommand(Commands.quit);
        }

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
        String currency = board.getCurrency();

        //generate 2 random integer numbers between 1 and 6
        int randomRoll1 = ((int)((Math.random()*totalNumOfSpaces)%numberOfSidesOnDice) + 1);
        int randomRoll2 = ((int)((Math.random()*totalNumOfSpaces)%numberOfSidesOnDice) + 1);
        this.diceValue1 = randomRoll1;
        this.diceValue2 = randomRoll2;

        int nextPlayerPosition = (randomRoll1 + randomRoll2 + playerPosition);

        if(!jailStatus){
            if(randomRoll1 == randomRoll2){
                currentPlayer.setNumOfDoubleRolls(currentPlayer.getNumOfDoubleRolls() + 1);
            }
            if((nextPlayerPosition == goToJailPosition)||(currentPlayer.getNumOfDoubleRolls() == 3)){
                //Send player to Jail
                boardView.getPlayerLists().get(playerIndex)[playerPosition].setVisible(false);
                currentPlayer.setNumOfDoubleRolls(0);
                currentPlayer.setTurnsInJail(0);
                currentPlayer.setInJail(true);
                jailStatus = true;
                currentPlayer.setPosition(jailPosition);
                boardView.getPlayerLists().get(playerIndex)[currentPlayer.getPositon()].setVisible(true);
                boardView.setEventLabel3Text(playerName+" has been set to Jail, roll a double to get out of Jail next turn.");
            }
            else{
                //Remove current player's icon from previous location
                boardView.getPlayerLists().get(playerIndex)[currentPlayer.getPositon()].setVisible(false);
                //For moving player
                if (nextPlayerPosition >= totalNumOfSpaces) { // if player passes Go
                    currentPlayer.setPosition(nextPlayerPosition - totalNumOfSpaces);
                    currentPlayer.addToBalance(passingGoAmount);
                    boardView.setEventLabel3Text(playerName+" has passed Go, Balance is now "+currency+currentPlayer.getBalance());
                    playerPosition = (nextPlayerPosition - totalNumOfSpaces);
                } else {
                    currentPlayer.setPosition(nextPlayerPosition);
                    playerPosition = nextPlayerPosition;
                }
                //Make current player's icon visible at new location
                boardView.getPlayerLists().get(playerIndex)[currentPlayer.getPositon()].setVisible(true);
            }
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
     * After each purchase, decrements numPropertiesLeft.
     */
    public void purchaseProperty(){
        int playerPosition = currentPlayer.getPositon();
        Property landedOnProperty = board.getProperty(playerPosition);
        String propertyName = landedOnProperty.getName();
        String playerName = currentPlayer.getName();
        int[] nonPurchasablePropertyLocations = {0,2,4,7,10,17,20,22,30,33,36,38};
        Color railroadPropertyColor = new Color(102,98,95);
        String currency = board.getCurrency();

        //Check property is not a non-Purchasable property location
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
            numPropertiesLeft--;
            currentPlayer.gainProperty(landedOnProperty);
            boardView.setEventLabelText(playerName + " purchased "+propertyName, "Remaining Balance: "+currency+currentPlayer.getBalance());

            //If after buying this property and it completes a colorSet, set hasAColorSet to true for the current player if not railroad color.
            Color colorOfProperty = landedOnProperty.getColor();
            Boolean ownsColorSet = true;
            for(int i =0; i<board.getColorPropertyArrayList().get(colorOfProperty).size(); i++){
                if(!board.getColorPropertyArrayList().get(colorOfProperty).get(i).getOwner().equals(currentPlayer)){
                    ownsColorSet = false;
                }
            }
            if( ownsColorSet && (!colorOfProperty.equals(railroadPropertyColor)) ){
                currentPlayer.setHasAColorSet(true);
            }
            //If purchased property is railroad, update owndXtrains for current player.
            if(colorOfProperty.equals(railroadPropertyColor)){
                currentPlayer.setOwnsXtrains( (currentPlayer.getOwnsXtrains()+1) );
            }
        }}}
    }

    /**
     * Returns void. Purchases house or hotel, depends on what the player chooses to purchase.
     * @param property param that is used to
     */
    public void purchaseHouseOrHotel(Property property){
        Boolean owningColorSet = true;
        Boolean owningEqualHouses = true;
        int numOfHouseCurrent = property.getNumHouses();
        Color colorOfProperty = property.getColor();
        String propertyName = property.getName();
        String playerName = currentPlayer.getName();
        String currency = board.getCurrency();
        int sizeOfColorSet = board.getColorPropertyArrayList().get(colorOfProperty).size();
        //Check if player owns the color set and houses numbers are correct
        for(int i = 0; i < sizeOfColorSet; i++){
            Property propertySameColor = board.getColorPropertyArrayList().get(colorOfProperty).get(i);
            if(!propertySameColor.getOwner().equals(currentPlayer)){
                owningColorSet = false;
            }
            if(!((propertySameColor.getNumHouses() >= numOfHouseCurrent)||(propertySameColor.getNumHotels() == 1))){
                owningEqualHouses = false;
            }
        }
        //Check if property is Not a nonHousesProperty
        int[] listOfNonHousesProperty= {5,12,15,25,28,35};
        for(int i = 0; i<listOfNonHousesProperty.length;i++){
            if(property.getLocation() == listOfNonHousesProperty[i]){
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
            String missingProperty = "";
            for(int i =0; i<sizeOfColorSet;i++){
                if(!board.getColorPropertyArrayList().get(colorOfProperty).get(i).equals(property)){
                    missingProperty += "- "+board.getColorPropertyArrayList().get(colorOfProperty).get(i).getName()+" ";
                }
            }
            boardView.setEventLabelText(playerName+" does NOT own the color set of this property", "Missing Property: ");
            boardView.setEventLabel3Text(missingProperty);
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
            boardView.setEventLabelText(playerName + " purchased Hotel on: "+propertyName, "Remaining Balance: "+currency+currentPlayer.getBalance());
            }
            else{
            property.setNumHouses((1+numOfHouseCurrent));
            boardView.setEventLabelText(playerName + " purchased House on: "+propertyName, "Remaining Balance: "+currency+currentPlayer.getBalance());
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
        for (int i = 0; i < totalNumPlayers; i++) {
            Player player = players.get(i);
            if (player.getBankruptStatus()) {
                totalBankruptPlayers++;
            }
        }
        //if 1 player left that is not bankrupt return false, else return true.
        if((totalNumPlayers-totalBankruptPlayers)==1){
            return false;
        }
        return true;
    }

    /**
     * Getter method for board of the game.
     * @return Board
     */
    public Board getBoard() {return board;}

    public void setBoard(Board boardSet){this.board = boardSet;}

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
     * todo add to uml
     * Returns true if the game has ended. Updates winnersList.
     */
    public Boolean gameHasEnded() {
        //The game ends if the last active player (not bankrupt)
        // or
        // if there are no properties left for purchase. In this case the game ends in a tie between non-bankrupt Players (winnersList).
        if (!checkNumOfActivePlayers() || (numPropertiesLeft == 0)) {
            boardView.setEventLabel3Text("Game has ended.");
            updateWinnersList();
            return true;
        }
        else { // else the game has not ended yet
            return false;
        }
    }

    /**
     * todo add to uml
     * Returns total number of players.
     */
    public int getNumTotalPlayers() {return players.size();}

    /**
     * todo add to uml
     * Returns winnerList.
     */
    public ArrayList<Player> getWinnersList() {return this.winnersList;}

    /**
     * todo add to uml
     * Updates winnersList and losersList
     */
    public void updateWinnersList() {
        int numTotalPlayers = players.size();
        for (int i = 0; i < numTotalPlayers; i++) {
            Player player = players.get(i);
            if (!player.getBankruptStatus()) {
                winnersList.add(player);
            } else {
                losersList.add(player);
            }
        }
    }


    /**
     * todo add to uml
     * Returns losersList.
     */
    public ArrayList<Player> getLosersList() {return losersList;}


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

        BoardModel boardModel = new BoardModel("board.xml");
        BoardView boardView = new BoardView(boardModel);
        boardModel.setBoardView(boardView);
        boardView.displayGUI();
    }

    public String toXML(){
        String s = new String();
        String stringIndent = "    ";

        s += stringIndent + "<Monopoly>\n";
        s+= stringIndent + stringIndent + "<CurrentPlayer>" + this.getCurrentPlayer().getName() + "</CurrentPlayer>\n";
        s+= stringIndent + stringIndent + "<CurrentPlayerIndex>" + this.getCurrentPlayerIndex() + "</CurrentPlayerIndex>\n";
        s+= stringIndent + stringIndent + "<Currency>" + this.getBoard().getCurrency() + "</Currency>\n";

        ;
        for (int i = 0; i < players.size() ; i++) {
            s+= stringIndent + stringIndent + "<Player>\n";

                s+= stringIndent + stringIndent + stringIndent + "<name>" + players.get(i).getName() + "</name>\n";
                s+= stringIndent + stringIndent + stringIndent + "<balance>" + players.get(i).getBalance() + "</balance>\n";
                s+= stringIndent + stringIndent + stringIndent + "<position>" + players.get(i).getPositon() + "</position>\n";

                //Jail
                s+= stringIndent + stringIndent + stringIndent + "<inJail>" + players.get(i).getInJail() + "</inJail>\n";
                s+= stringIndent + stringIndent + stringIndent + "<turnsInJail>" + players.get(i).getTurnsInJail() + "</turnsInJail>\n";


                //Color as R G B
                s+= stringIndent + stringIndent + stringIndent + "<r>" + players.get(i).getColor().getRed() + "</r>\n";
                s+= stringIndent + stringIndent + stringIndent + "<g>" + players.get(i).getColor().getGreen() + "</g>\n";
                s+= stringIndent + stringIndent + stringIndent + "<b>" + players.get(i).getColor().getBlue()+ "</b>\n";

                //Owns ___
                s+= stringIndent + stringIndent + stringIndent + "<ownsXtrains>" + players.get(i).getOwnsXtrains() + "</ownsXtrains>\n";
                s+= stringIndent + stringIndent + stringIndent + "<ownsBothUtil>" + players.get(i).getOwnsBothUtil() + "</ownsBothUtil>\n";


                s+= stringIndent + stringIndent + stringIndent + "<ai>" + players.get(i).getAi() + "</ai>\n";
                s+= stringIndent + stringIndent + stringIndent + "<numOfDoubleRolls>" + players.get(i).getNumOfDoubleRolls() + "</numOfDoubleRolls>\n";
                s+= stringIndent + stringIndent + stringIndent + "<hasAColorSet>" + players.get(i).getHasAColorSet() + "</hasAColorSet>\n";
                s+= stringIndent + stringIndent + stringIndent + "<bankruptStatus>" + players.get(i).getBankruptStatus() + "</bankruptStatus>\n";

            s+= stringIndent + stringIndent + "</Player>\n";
        }

        int[] railroadsPositions = {5,15,25,35};
        for (int i = 0; i < this.getBoard().getPropertyArrayList().size(); i++) {
            Boolean isRailroad = false;
            for (int j = 0; j < railroadsPositions.length; j++) {
                if(i == railroadsPositions[j]){
                    isRailroad = true;
                    s+= stringIndent + stringIndent + "<Railroad>\n";
                    s+= stringIndent + stringIndent + stringIndent + "<name>" + this.getBoard().getProperty(i).getName() + "</name>\n";
                    s+= stringIndent + stringIndent + stringIndent + "<rent>" + this.getBoard().getProperty(i).getRent() + "</rent>\n";
                    s+= stringIndent + stringIndent + stringIndent + "<owner>" + this.getBoard().getProperty(i).getOwner().getName() + "</owner>\n";
                    s+= stringIndent + stringIndent + stringIndent + "<price>" + this.getBoard().getProperty(i).getPrice() + "</price>\n";
                    s+= stringIndent + stringIndent + stringIndent + "<index>" + this.getBoard().getProperty(i).getLocation() + "</location>\n";

                    //Color as R G B
                    s+= stringIndent + stringIndent + stringIndent + "<r>" + this.getBoard().getProperty(i).getColor().getRed() + "</r>\n";
                    s+= stringIndent + stringIndent + stringIndent + "<g>" + this.getBoard().getProperty(i).getColor().getGreen() + "</g>\n";
                    s+= stringIndent + stringIndent + stringIndent + "<b>" + this.getBoard().getProperty(i).getColor().getBlue() + "</b>\n";

                    s+= stringIndent + stringIndent + "</Railroad>\n";
                }
            }
            if(!isRailroad) {
                s += stringIndent + stringIndent + "<Property>\n";
                s += stringIndent + stringIndent + stringIndent + "<name>" + this.getBoard().getProperty(i).getName() + "</name>\n";
                s += stringIndent + stringIndent + stringIndent + "<rent>" + this.getBoard().getProperty(i).getRent() + "</rent>\n";
                s += stringIndent + stringIndent + stringIndent + "<owner>" + this.getBoard().getProperty(i).getOwner().getName() + "</owner>\n";
                s += stringIndent + stringIndent + stringIndent + "<price>" + this.getBoard().getProperty(i).getPrice() + "</price>\n";
                s += stringIndent + stringIndent + stringIndent + "<index>" + this.getBoard().getProperty(i).getLocation() + "</location>\n";

                //Color as R G B
                s += stringIndent + stringIndent + stringIndent + "<r>" + this.getBoard().getProperty(i).getColor().getRed() + "</r>\n";
                s += stringIndent + stringIndent + stringIndent + "<g>" + this.getBoard().getProperty(i).getColor().getGreen() + "</g>\n";
                s += stringIndent + stringIndent + stringIndent + "<b>" + this.getBoard().getProperty(i).getColor().getBlue() + "</b>\n";


                s += stringIndent + stringIndent + stringIndent + "<numHouses>" + this.getBoard().getProperty(i).getNumHouses() + "</numHouses>\n";
                s += stringIndent + stringIndent + stringIndent + "<numHotels>" + this.getBoard().getProperty(i).getNumHotels() + "</numHotels>\n";

                s += stringIndent + stringIndent + "</Property>\n";
            }
        }
        s+= stringIndent + stringIndent  + "<maxPlayers>" + boardView.getMaxPlayers() + "</maxPlayers>\n";
        s+= stringIndent + stringIndent  + "<currentPlayerIndex>" + this.getCurrentPlayerIndex() + "</currentPlayerIndex>\n";
        s+= stringIndent + stringIndent  + "<nextRoll>" + nextRoll + "</nextRoll>\n";
        s+= stringIndent + stringIndent  + "<numPropertiesLeft>" + numPropertiesLeft + "</numPropertiesLeft>\n";


        s += stringIndent + "</Monopoly>\n";

        return s;
    }
    public void save(String name){
        File file = new File ("Save Files/" + name + ".xml");
        try {
            FileWriter writer = new FileWriter(file);
            writer.write(toXML());
            writer.close();
        }
        catch (IOException e){
            System.out.println(e.getMessage());
        }
    }
    public void load(String fileName){
        loadBoard(fileName);
        loadPlayers(fileName);
        loadBoardModelAttributes(fileName);
        loadPropertyOwnersAndPlayerControlled(fileName);
        boardView.updateAllHousesIcons();
    }

    public void loadPlayers(String fileName){
        int maxPlayers = 0;
        try{
            File file = new File("src/"+fileName);
            //an instance of factory that gives a document builder
            DocumentBuilderFactory dbf = DocumentBuilderFactory.newInstance();
            //an instance of builder to parse the specified xml file
            DocumentBuilder db = dbf.newDocumentBuilder();
            Document doc = db.parse(file);
            doc.getDocumentElement().normalize();
            NodeList loadPlayersList = doc.getElementsByTagName("Player");
            NodeList maxPlayersList = doc.getElementsByTagName("maxPlayers");

            for (int i = 0; i <loadPlayersList.getLength() ; i++) {
                Node playerNode = loadPlayersList.item(i);
                if (playerNode.getNodeType() == Node.ELEMENT_NODE) {
                    Element playerElement = (Element) playerNode;
                    Boolean isAi = Boolean.parseBoolean(playerElement.getElementsByTagName("ai").item(0).getTextContent());
                    String playerName = playerElement.getElementsByTagName("name").item(0).getTextContent();
                    Color playerColor = new Color( Integer.parseInt(playerElement.getElementsByTagName("r").item(0).getTextContent()),  //R
                            Integer.parseInt(playerElement.getElementsByTagName("g").item(0).getTextContent()),  //G
                            Integer.parseInt(playerElement.getElementsByTagName("b").item(0).getTextContent())); //B
                    int money = Integer.parseInt(playerElement.getElementsByTagName("balance").item(0).getTextContent());
                    int playerPosition = Integer.parseInt(playerElement.getElementsByTagName("position").item(0).getTextContent());
                    Boolean inJail = Boolean.parseBoolean(playerElement.getElementsByTagName("inJail").item(0).getTextContent());
                    int turnsInJail = Integer.parseInt(playerElement.getElementsByTagName("turnsInJail").item(0).getTextContent());
                    int ownsXtrains = Integer.parseInt(playerElement.getElementsByTagName("ownsXtrains").item(0).getTextContent());
                    Boolean ownsBothUtil = Boolean.parseBoolean(playerElement.getElementsByTagName("ownsBothUtil").item(0).getTextContent());
                    int numDoubleRolls = Integer.parseInt(playerElement.getElementsByTagName("numOfDoubleRolls").item(0).getTextContent());
                    Boolean bankruptStatus = Boolean.parseBoolean(playerElement.getElementsByTagName("bankruptStatus").item(0).getTextContent());
                    Boolean hasAColorSet = Boolean.parseBoolean(playerElement.getElementsByTagName("hasAColorSet").item(0).getTextContent());

                    if(isAi){
                        AIPlayer aiPlayer = new AIPlayer(playerName,playerColor,money,maxPlayers,board,this,boardView);
                        aiPlayer.setPosition(playerPosition);
                        aiPlayer.setInJail(inJail);
                        aiPlayer.setTurnsInJail(turnsInJail);
                        aiPlayer.setOwnsXtrains(ownsXtrains);
                        aiPlayer.setOwnsBothUtil(ownsBothUtil);
                        aiPlayer.setNumOfDoubleRolls(numDoubleRolls);
                        aiPlayer.setBankruptStatus(bankruptStatus);
                        aiPlayer.setHasAColorSet(hasAColorSet);
                        players.add(aiPlayer);
                    }
                    else{
                        Player player = new Player(playerName,playerColor,money,false);
                        player.setPosition(playerPosition);
                        player.setInJail(inJail);
                        player.setTurnsInJail(turnsInJail);
                        player.setOwnsXtrains(ownsXtrains);
                        player.setOwnsBothUtil(ownsBothUtil);
                        player.setNumOfDoubleRolls(numDoubleRolls);
                        player.setBankruptStatus(bankruptStatus);
                        player.setHasAColorSet(hasAColorSet);
                        players.add(player);
                    }
                    //Make current player's icon visible at new location
                    boardView.getPlayerLists().get(i)[playerPosition].setVisible(true);
                }
            }
            for (int i = 0; i <maxPlayersList.getLength() ; i++) {
                Node maxplayerNode = maxPlayersList.item(i);
                if (maxplayerNode.getNodeType() == Node.ELEMENT_NODE) {
                    Element maxplayerElement = (Element) maxplayerNode;
                    maxPlayers = Integer.parseInt(maxplayerElement.getTextContent());
                }
            }
            boardView.updateAllPlayersStatus(maxPlayers);
            boardView.setPlayerPanelHoldersVisibility(maxPlayers,true);
            boardView.setControllerInitializing();
            boardView.setUserInputVisibility(false);
        }
        catch (FileNotFoundException | ParserConfigurationException f) {
            f.printStackTrace();
            //loadBoardFromMapFile("board.xml");
        }   catch (SAXException e) {
            e.printStackTrace();
        }   catch (IOException e) {
            e.printStackTrace();
        }
    }
    public void loadBoard(String fileName){
        Board loadedBoard = new Board(fileName);
        BoardConstructor loadBoardConstructor = new BoardConstructor(loadedBoard);
        loadedBoard = loadBoardConstructor.loadBoardFromMapFile(fileName, false);
        board = loadedBoard;
        boardView.setAllPropertys();
    }

    public void loadPropertyOwnersAndPlayerControlled(String fileName){
        try {
            File file = new File("src/" + fileName);
            //an instance of factory that gives a document builder
            DocumentBuilderFactory dbf = DocumentBuilderFactory.newInstance();
            //an instance of builder to parse the specified xml file
            DocumentBuilder db = dbf.newDocumentBuilder();
            Document doc = db.parse(file);
            doc.getDocumentElement().normalize();
            NodeList loadPropertyList = doc.getElementsByTagName("Property");
            NodeList railroadsList = doc.getElementsByTagName("railroad");
            for (int i = 0; i <loadPropertyList.getLength() ; i++) {
                Node propertyNode = loadPropertyList.item(i);
                if (propertyNode.getNodeType() == Node.ELEMENT_NODE) {
                    Element propertyElement = (Element) propertyNode;
                    String ownerName = propertyElement.getElementsByTagName("owner").item(0).getTextContent();
                    int propertyLocation = Integer.parseInt(propertyElement.getElementsByTagName("location").item(0).getTextContent());
                    for (int j = 0; j < players.size(); j++) {
                        if(players.get(j).getName().equals(ownerName)){
                            board.getProperty(propertyLocation).setOwner(players.get(j));
                            players.get(j).gainProperty(board.getProperty(propertyLocation));
                        }
                    }
                }
            }

            for (int i = 0; i <railroadsList.getLength() ; i++) {
                Node railroadNode = railroadsList.item(i);
                if (railroadNode.getNodeType() == Node.ELEMENT_NODE) {
                    Element railroadElement = (Element) railroadNode;
                    String ownerName = railroadElement.getElementsByTagName("owner").item(0).getTextContent();
                    int propertyLocation = Integer.parseInt(railroadElement.getElementsByTagName("location").item(0).getTextContent());
                    for (int j = 0; j < players.size(); j++) {
                        if(players.get(j).getName().equals(ownerName)){
                            board.getProperty(propertyLocation).setOwner(players.get(j));
                            players.get(j).gainProperty(board.getProperty(propertyLocation));
                        }
                    }
                }
            }
        }
        catch (FileNotFoundException | ParserConfigurationException f) {
            f.printStackTrace();
            //loadBoardFromMapFile("board.xml");
        }   catch (SAXException e) {
            e.printStackTrace();
        }   catch (IOException e) {
            e.printStackTrace();
        }

    }

    public void loadBoardModelAttributes(String fileName){
        try {
            File file = new File("src/" + fileName);
            //an instance of factory that gives a document builder
            DocumentBuilderFactory dbf = DocumentBuilderFactory.newInstance();
            //an instance of builder to parse the specified xml file
            DocumentBuilder db = dbf.newDocumentBuilder();
            Document doc = db.parse(file);
            doc.getDocumentElement().normalize();
            NodeList nextRollList = doc.getElementsByTagName("nextRoll");
            NodeList currentPlayerIndexList = doc.getElementsByTagName("currentPlayerIndex");
            NodeList numPropertiesLeftList = doc.getElementsByTagName("numPropertiesLeft");

            for (int i = 0; i <nextRollList.getLength() ; i++) {
                Node nextRollNode = nextRollList.item(i);
                if (nextRollNode.getNodeType() == Node.ELEMENT_NODE) {
                    Element nextRollElement = (Element) nextRollNode;
                    nextRoll = Boolean.parseBoolean(nextRollElement.getTextContent());
                }
            }
            for (int i = 0; i <currentPlayerIndexList.getLength() ; i++) {
                Node currentPlayerIndexNode = currentPlayerIndexList.item(i);
                if (currentPlayerIndexNode.getNodeType() == Node.ELEMENT_NODE) {
                    Element currentPlayerIndexElement = (Element) currentPlayerIndexNode;
                    currentPlayer = players.get(Integer.parseInt(currentPlayerIndexElement.getTextContent()));
                }
            }
            for (int i = 0; i <numPropertiesLeftList.getLength() ; i++) {
                Node numPropertiesNode = numPropertiesLeftList.item(i);
                if (numPropertiesNode.getNodeType() == Node.ELEMENT_NODE) {
                    Element numPropetiesElement = (Element) numPropertiesNode;
                    numPropertiesLeft = Integer.parseInt(numPropetiesElement.getTextContent());
                }
            }
        }
        catch (FileNotFoundException | ParserConfigurationException f) {
            f.printStackTrace();
            //loadBoardFromMapFile("board.xml");
        }   catch (SAXException e) {
            e.printStackTrace();
        }   catch (IOException e) {
            e.printStackTrace();
        }
    }
}


