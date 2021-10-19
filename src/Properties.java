import java.awt.*;

public class Properties {
    private String name;
    private int rent; //To account for houses / hotels
    private Player owner;
    private int price;
    private int numHouses;
    private int numHotels;
    private Color color;
    private int location; //Location on the board

    public Properties( String name, int rent, int price, Color color, int location){
        this.name = name;
        this.rent = rent;
        this.owner = null;
        this.price = price;
        numHouses = 0;
        numHotels = 0;
        this.color = color;
        this.location = location;
    }

    public int getrentVar(){
        return rent;
    }
    public int getRent() {
        if (numHouses == 1) { return (int)(rent*1.1); }
        else if (numHouses == 2) { return (int)(rent*1.2); }
        else if (numHouses == 3) { return (int)(rent*1.3); }
        else if (numHouses == 4) { return (int)(rent*1.4); }
        else if (numHotels == 1) { return (int)(rent*1.5); }
        else { return rent; }
    }

    public String getName() {
        return name;
    }

    public int getLocation(){return location;}

    public Player getOwner() {
        return owner;
    }

    public void setOwner(Player owner) {
        this.owner = owner;
    }

    public int getPrice() {
        return price;
    }

    public Color getColor(){return color;}

    public void setName(String name) {
        this.name = name;
    }

    public int getNumHouses() {
        return numHouses;
    }

    public void setNumHouses(int numHouses) {
        this.numHouses = numHouses;
    }

    public int getNumHotels() {
        return numHotels;
    }

    public void setNumHotels(int numHotels) {
        this.numHotels = numHotels;
    }

    public int getHousePrice(){return ((price*numHouses)/10);}
}
