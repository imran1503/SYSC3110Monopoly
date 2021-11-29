import java.awt.*;

/**
 * Class Railroad, is a special Property.
 */
public class Railroads extends Property {

    /**
     * Railroads Constructor. Outlines all railroad properties like name, rent, owner,
     * price, color, location.
     * @param name the name of the railroad
     * @param rent the rent of the railroad
     * @param owner the owner of railroad, of class Player
     * @param price the price of the railroad
     * @param color the color of the railroad
     * @param location the location of the railroad
     */
    public Railroads(String name, int rent, Player owner, int price, Color color, int location) {
        super(name, rent, price,color,location);
    }


    @Override
    /**
     * get rent of railroad, calculate it using if statements, and return the rent.
     */
    public int getRent() {
        if (this.getOwner().getOwnsXtrains() == 2){
            return getrentVar()*2; //50
        }
        else if (this.getOwner().getOwnsXtrains() == 3){
            return getrentVar()*4; //100
        }
        else if(this.getOwner().getOwnsXtrains() == 4){
            return getrentVar()*8; //200
        }
        else{
            return getrentVar();
        }
    }
}
