public class Properties {
    private String name;
    private int[] rent; //To account for houses / hotels
    private Player owner;
    private int price;
    private int numHouses;
    private int numHotels;

    public Properties( String name, int[] rentArray, Player owner, int price){
        this.name = name;
        this.rent = rentArray;
        this.owner = owner;
        this.price = price;
        numHouses = 0;
        numHotels = 0;
    }

    public String getName() {
        return name;
    }

    public int[] getRentArray(){
        return rent;
    }

    public int getRent() {
        if (numHouses == 1) {
            return rent[1];
        }
        else if (numHouses == 2) {
            return rent[2];
        }
        else if (numHouses == 3) {
            return rent[3];
        }
        else if (numHouses == 0) {
            return rent[4];
        }
        else if (numHotels == 1){
            return rent[5];
        }
        return rent[0];
    }

    public Player getOwner() {
        return owner;
    }

    public void setOwner(Player owner) {
        this.owner = owner;
    }

    public int getPrice() {
        return price;
    }

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
}
