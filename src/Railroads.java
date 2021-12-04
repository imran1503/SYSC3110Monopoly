import java.awt.*;

/**
 * Class Railroad, is a special Property.
 */
public class Railroads extends Property {

    /**
     * Railroads Constructor. Outlines all railroad Property like name, rent, owner,
     * price, color, location.
     * @param name the name of the railroad
     * @param rent the rent of the railroad
     * @param price the price of the railroad
     * @param color the color of the railroad
     * @param location the location of the railroad
     */
    public Railroads(String name, int price, int rent, Color color, int location) {
        super(name, price, rent,color,location);
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
