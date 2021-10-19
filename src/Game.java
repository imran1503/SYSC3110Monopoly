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

    public Game( ){
        this.players = new ArrayList<Player>();
        this.propertiesArrayList = new ArrayList<Properties>();

    }

    public void addPlayer(Player player){players.add(player);}

    public void addProperty(Properties property){propertiesArrayList.add(property);}

    public void roll(Player player){
        int totalNumOfSpaces = 25;
        int randomRoll1 = ((int)((Math.random()*60)%6) + 1);
        int randomRoll2 = ((int)((Math.random()*77)%6) + 1);
        int playerPosition = player.getPositon();
        if((playerPosition + randomRoll1 + randomRoll2) >= totalNumOfSpaces){
            player.setPosition((randomRoll1+randomRoll2+ playerPosition) - totalNumOfSpaces);
            player.addToBalance(200); //Passing Go
        }
        else{
            player.setPosition(randomRoll1 + randomRoll2+ playerPosition);
        }
    }

    public void purchaseProperty(Player player){
        int playerPosition = player.getPositon();
        Properties landedOnProperty = propertiesArrayList.get(playerPosition);
        if(landedOnProperty.getPrice() == 0){
            System.out.println("This property can not be purchased, Property Name: "+landedOnProperty.getName());
        }
        else{ if(landedOnProperty.getOwner().equals(player)){
            System.out.println("This property belongs to you already, Property Name: "+landedOnProperty.getName());
        }
        else{ if(landedOnProperty.getOwner().getName() != null){
            System.out.println("This property belongs to someone else, Property Name: "+landedOnProperty.getName());
        }
        else{ if(player.getBalance() < landedOnProperty.getPrice()){
            System.out.println(player.getName()+" does Not have enough money to purchase this property, Property Name: "+landedOnProperty.getName());
        }
        else {
            player.removefromBalance(landedOnProperty.getPrice());
            landedOnProperty.setOwner(player);
        }}}}
    }

    public void purchaseHouseOrHotel(Player player,Properties property){
        Boolean owningColorSet = true;
        Boolean owningEqualHouses = true;
        int numOfHouseCurrent = property.getNumHouses();
        Color colorOfProperty = property.getColor();
        int sizeOfColorSet = colorPropertiesArrayList.get(colorOfProperty).size();
        for(int i = 0; i < sizeOfColorSet; i++){
            if(!colorPropertiesArrayList.get(colorOfProperty).get(i).getOwner().equals(player)){
                owningColorSet = false;
            }
            if(!((colorPropertiesArrayList.get(colorOfProperty).get(i).getNumHouses() >= numOfHouseCurrent)||(colorPropertiesArrayList.get(colorOfProperty).get(i).getNumHotels() == 1))){
                owningEqualHouses = false;
            }
        }
        if(!owningColorSet){
            System.out.println(player.getName()+" does NOT own the color set of this property, Property Name: "+property.getName());
        }
        else{ if(!owningEqualHouses){
            System.out.println(player.getName()+" does NOT own enough of houses for the color set to buy more, Property Name: "+property.getName());
        }
        else{ if(player.getBalance() < property.getHousePrice()){
            System.out.println(player.getName()+" does Not have enough money to purchase this property, Property Name: "+property.getName());
        }
        else{if(property.getNumHotels() == 1){
            System.out.println("This property already has a hotel, property Name: "+property.getName());
        }
        else{ if(numOfHouseCurrent == 4) {
            player.removefromBalance(property.getHousePrice());
            property.setNumHotels(1);
            property.setNumHouses(0);
        }
        else{
            player.removefromBalance(property.getHousePrice());
            property.setNumHouses((1+numOfHouseCurrent));
        }}}}}
    }

}
