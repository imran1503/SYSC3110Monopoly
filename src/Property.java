import java.awt.*;

/**
 * Class Property. String name for name of property.
 * Integer rent for rent to pay for the property if a non-owner player lands on it.
 * owner variable is a Player who is the owner of the property.
 * Integer price is the cost to purchase the property.
 * Integer numHouses is number of houses on the property.
 * Integer numHotels is number of hotels on the property.
 * color is the color group of the property.
 * Integer location is the location of the property on the board.
 */
public class Property {
    private String name;
    private int rent; //To account for  houses / hotels
    private Player owner;
    private int price;
    private int numHouses;
    private int numHotels;
    private int housePrice;
    private Color color;
    private int location; //Location on the board

    /**
     * Property Constructor. Outlines property information like name, price, rent,
     * color, location.
     * @param name name of property
     * @param price price of property
     * @param rent rent of property
     * @param color color of property
     * @param location location of property
     */
    public Property(String name, int price, int rent, Color color, int location){
        Player bank = new Player("bank",Color.white,1000000, false);
        this.name = name;
        this.rent = rent;
        this.owner = bank;
        this.price = price;
        numHouses = 0;
        numHotels = 0;
        this.color = color;
        this.location = location;
        this.housePrice = 0;
    }

    /**
     * Second Property constructor. This is like the first but without price and
     * rent of the property.
     * @param name name of property
     * @param color color of property
     * @param location location of property
     */
    public Property(String name, Color color, int location) {
        Player bank = new Player("bank",Color.white,1000000, false);
        this.name = name;
        this.owner = bank;
        numHouses = 0;
        numHotels = 0;
        this.color = color;
        this.location = location;
    }

    /**
     * gets and returns rent variable
     * @return rent variable
     */
    public int getrentVar(){
        return rent;
    }

    /**
     * gets and returns rent variable
     * @return rent variable
     */
    public int getRent() {
        if (numHouses == 1) { return (int)(rent*1.1); }
        else if (numHouses == 2) { return (int)(rent*1.2); }
        else if (numHouses == 3) { return (int)(rent*1.3); }
        else if (numHouses == 4) { return (int)(rent*1.4); }
        else if (numHotels == 1) { return (int)(rent*1.5); }
        else { return rent; }
    }

    /**
     * Pays rent for player given payingPlayer parameter of class Player
     * Removes respective money from balance.
     * @param payingPlayer the player paying the rent
     */
    public void payRent(Player payingPlayer){
        if(!owner.getName().equals("bank")||(location == 4)||(location == 38)){
            payingPlayer.removefromBalance(this.getRent());
            if (owner != null) {
                owner.addToBalance(this.getRent());
            }
        }
    }

    /**
     * gets and returns name variable
     * @return name variable
     */
    public String getName() {
        return name;
    }

    /**
     * gets and returns location variable
     * @return location variable
     */
    public int getLocation(){return location;}

    /**
     * gets and returns owner variable
     * @return owner variable
     */
    public Player getOwner() {
        return owner;
    }

    /**
     * assigns given parameter to the owner variable
     * @param owner of class Player
     */
    public void setOwner(Player owner) {
        this.owner = owner;
    }

    /**
     * gets and returns price variable.
     * @return price variable
     */
    public int getPrice() {
        return price;
    }

    /**
     * gets and returns color variable.
     * @return color variable.
     */
    public Color getColor(){return color;}

    /**
     * assigns given parameter to the name variable
     * @param name variable
     */
    public void setName(String name) {
        this.name = name;
    }

    /**
     * gets and returns numHouses variable
     * @return numHouses variable
     */
    public int getNumHouses() {
        return numHouses;
    }

    /**
     * assigns given parameter to the numhouses Variable
     * @param numHouses variable
     */
    public void setNumHouses(int numHouses) {
        this.numHouses = numHouses;
    }

    /**
     * gets and returns numHotels variable
     * @return numHotels variable
     */
    public int getNumHotels() {
        return numHotels;
    }

    /**
     * assigns given parameter to numHotels variable
     * @param numHotels variable
     */
    public void setNumHotels(int numHotels) {
        this.numHotels = numHotels;
    }

    /**
     * calculates and returns housePrice                   (price*(numHouses+1))/10)
     * @return housePrice
     */
    public int getHousePrice(){return housePrice;}

    /**
     * Set house price of Property
     * @param i
     */
    public void setHousePrice(int i ){housePrice = i;}

    /**
     * Get cost benefit ratio for property
     * @param currPlayerBalance balance of player
     * @param housePriceCustom house price
     * @param propertyPriceCustom property price
     * @return double ratio of benefit
     */
    public double getCostBenefitRatio(double currPlayerBalance, double housePriceCustom, double propertyPriceCustom){
        double costPrice = (housePriceCustom * 5) + propertyPriceCustom;
        return ( currPlayerBalance/ costPrice  );
    }

    @Override
    public String toString() {
        return "Property{" +
                "name='" + name + '\'' +
                ", rent=" + rent +
                ", owner=" + owner +
                ", price=" + price +
                ", numHouses=" + numHouses +
                ", numHotels=" + numHotels +
                ", housePrice=" + housePrice +
                ", location=" + location +
                '}';
    }
}
