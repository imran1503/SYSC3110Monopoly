public class Properties {
    private String name;
    private int[] rent; //To account for houses / hotels
    private Player owner;
    private int price;

    public Properties( String name, int[] rentArray, Player owner, int price){
        this.name = name;
        this.rent = rentArray;
        this.owner = owner;
        this.price = price;
    }

    public String getName() {
        return name;
    }

    public int[] getRent() {
        return rent;
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
}
