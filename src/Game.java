import java.awt.*;
import java.util.ArrayList;
import java.util.Enumeration;
import java.lang.Math;
import java.util.HashMap;

public class Game {
    private ArrayList<Player> players;
    private ArrayList<Properties> propertiesArrayList;
    private Enumeration gameState; //todo roll & move, purchase, house/hotel, end turn.
    private HashMap<Color, ArrayList<Properties>> colorPropertiesArrayList;
    private Player currentPlayer;

    public Game( ){
        this.players = new ArrayList<Player>();
        this.propertiesArrayList = new ArrayList<Properties>();
        this.currentPlayer = null;
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
    }

    public void addProperty(Properties property){propertiesArrayList.add(property);}

    public void roll(){
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

}
