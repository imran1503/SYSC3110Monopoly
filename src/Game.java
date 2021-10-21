import java.awt.*;
import java.util.ArrayList;
import java.util.Enumeration;
import java.lang.Math;
import java.util.HashMap;
import java.util.Scanner;

public class Game {
    private ArrayList<Player> players;
    private ArrayList<Properties> propertiesArrayList;
    private Enumeration gameState; //todo roll & move, purchase, house/hotel, end turn.
    private HashMap<Color, ArrayList<Properties>> colorPropertiesArrayList;
    private Player currentPlayer;
    private Scanner reader; // for taking command input
    private Boolean nextRoll;

    public Game( ){
        this.players = new ArrayList<Player>();
        this.propertiesArrayList = new ArrayList<Properties>();
        this.currentPlayer = null;
        this.nextRoll = null;
    }

    /**
     * play start while loops until game is not in progress.
     */
    public void play() {
        welcomeMessage();
        boolean gameInProgress = true;
        while (gameInProgress) {
            String command = reader.nextLine();
            gameInProgress = operateCommand(command);
            if(gameInProgress) {
                gameInProgress = checkNumOfActivePlayers();
            }
        }
    }

    /**
     * Print out the opening message for the players on game start.
     */
    private void welcomeMessage() {
        System.out.println("Welcome to the game of Monopoly!");
        System.out.println();
        System.out.println("Player 1 goes first, begin by typing roll command");
    }

    private boolean operateCommand(String command) {
        if (command.equals("quit")) {
            System.out.println("Player has quit the game.");
            return false;
        } if (command.equals("roll")) {
            nextRoll = roll();
            Properties propertyOn = propertiesArrayList.get(currentPlayer.getPositon());
            if(!propertyOn.getOwner().equals(currentPlayer)){
                propertyOn.payRent(currentPlayer);
            }
        } if (command.equals("purchase property")){
            purchaseProperty();
        } if (command.equals("purchase house") || command.equals("purchase hotel")){
            System.out.println("Type in the property name on which you would like to purchase a house/hotel on.");
            String propertyName = reader.nextLine();
            Boolean propertyExists = false;
            int propertyIndex = -1;
            for(int i =0; i<propertiesArrayList.size();i++){
                if(propertiesArrayList.get(i).getName().equals(propertyName)){
                    propertyExists = true;
                    propertyIndex = i;
                }
            }
            if(propertyExists){
                purchaseHouseOrHotel(propertiesArrayList.get(propertyIndex));
            }
            else{
                System.out.println("Property: "+propertyName+", Does not exists");
            }
          // Need to find out which player is currently playing and roll for that player
        }
        if(command.equals("pass turn")){
            if(nextRoll == null){
                System.out.println(currentPlayer.getName()+" still did not roll yet. Roll first before passing.");
            }
            if(nextRoll == true){
                System.out.println(currentPlayer.getName()+" needs to roll again before passing turn. Rolled a double previously.");
            }
            else{
                passPlayerTurn();
                nextRoll = null;
            }
        }
        if(command.equals("check game state")){
            printCurrentState();
        }
        else {
            System.out.println("No such command exists!");
        }
        if(currentPlayer.getBankruptStatus()){
            passPlayerTurn();
        }
        return true;
    }

    public void addPlayer(Player player){players.add(player);}

    public void setCurrentPlayer(Player player){
        currentPlayer = player;
    }

    public void printCurrentState(){
        for(int i=0; i<players.size();i++){
            Player player = players.get(i);
            System.out.println("******\n"+player.getName() + " position is currently at "+propertiesArrayList.get(player.getPositon()).getName());
            System.out.println("Balance is "+ player.getBalance());
            System.out.println("Bankrupt Status = "+player.getBankruptStatus());
            int getNumOfProperties = player.getControlledProperties().size();
            System.out.println("List of Owned Properties:");
            for(int j=0; j<getNumOfProperties; j++){
                System.out.println("- "+player.getControlledProperties().get(j).getName());
            }
            System.out.println("*****");
        }
    }

    public void passPlayerTurn(){
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
        System.out.println("It's Now "+currentPlayer.getName()+" turn.");
    }

    public void addProperty(Properties property){propertiesArrayList.add(property);}

    public boolean roll(){
        int totalNumOfSpaces = 40;
        int randomRoll1 = ((int)((Math.random()*60)%6) + 1);
        int randomRoll2 = ((int)((Math.random()*77)%6) + 1);
        int playerPosition = currentPlayer.getPositon();
        if((playerPosition + randomRoll1 + randomRoll2) >= totalNumOfSpaces){
            currentPlayer.setPosition((randomRoll1+randomRoll2+ playerPosition) - totalNumOfSpaces);
            currentPlayer.addToBalance(200); //Passing Go
        }
        else{
            currentPlayer.setPosition(randomRoll1 + randomRoll2+ playerPosition);
        }
        if(randomRoll1 == randomRoll2){
            return true;
        }
        return false;
    }

    public void purchaseProperty(){
        int playerPosition = currentPlayer.getPositon();
        Properties landedOnProperty = propertiesArrayList.get(playerPosition);
        if(landedOnProperty.getPrice() == 0){
            System.out.println("This property can not be purchased, Property Name: "+landedOnProperty.getName());
        }
        else{ if(landedOnProperty.getOwner().equals(currentPlayer)){
            System.out.println("This property belongs to you already, Property Name: "+landedOnProperty.getName());
        }
        else{ if(landedOnProperty.getOwner().getName() != null){
            System.out.println("This property belongs to someone else, Property Name: "+landedOnProperty.getName());
        }
        else{ if(currentPlayer.getBalance() < landedOnProperty.getPrice()){
            System.out.println(currentPlayer.getName()+" does Not have enough money to purchase this house/hotel, Property Name: "+landedOnProperty.getName());
        }
        else {
            currentPlayer.removefromBalance(landedOnProperty.getPrice());
            landedOnProperty.setOwner(currentPlayer);
            currentPlayer.gainTerritory(landedOnProperty);
        }}}}
    }

    public void purchaseHouseOrHotel(Properties property){
        Boolean owningColorSet = true;
        Boolean owningEqualHouses = true;
        int numOfHouseCurrent = property.getNumHouses();
        Color colorOfProperty = property.getColor();
        int sizeOfColorSet = colorPropertiesArrayList.get(colorOfProperty).size();
        for(int i = 0; i < sizeOfColorSet; i++){
            if(!colorPropertiesArrayList.get(colorOfProperty).get(i).getOwner().equals(currentPlayer)){
                owningColorSet = false;
            }
            if(!((colorPropertiesArrayList.get(colorOfProperty).get(i).getNumHouses() >= numOfHouseCurrent)||(colorPropertiesArrayList.get(colorOfProperty).get(i).getNumHotels() == 1))){
                owningEqualHouses = false;
            }
        }
        if(!owningColorSet){
            System.out.println(currentPlayer.getName()+" does NOT own the color set of this property, Property Name: "+property.getName());
        }
        else{ if(!owningEqualHouses){
            System.out.println(currentPlayer.getName()+" does NOT own enough of houses for the color set to buy more, Property Name: "+property.getName());
        }
        else{ if(currentPlayer.getBalance() < property.getHousePrice()){
            System.out.println(currentPlayer.getName()+" does Not have enough money to purchase this property, Property Name: "+property.getName());
        }
        else{if(property.getNumHotels() == 1){
            System.out.println("This property already has a hotel, property Name: "+property.getName());
        }
        else{ if(numOfHouseCurrent == 4) {
            currentPlayer.removefromBalance(property.getHousePrice());
            property.setNumHotels(1);
            property.setNumHouses(0);
        }
        else{
            currentPlayer.removefromBalance(property.getHousePrice());
            property.setNumHouses((1+numOfHouseCurrent));
        }}}}}
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
            System.out.println("The Game has ended. "+currentPlayer.getName()+"wins!");
            return false;
        }
        return true;
    }

}
