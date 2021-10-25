import java.awt.*;

/**
 * Class Utilities, is a special Property.
 */
public class Utilities extends Properties{

    /**
     * Utilities constructor
     * @param name name of the utilities
     * @param rent rent variable of the utilities
     * @param owner owner of the utilities
     * @param price the price of the the utilities
     * @param colour the colour of the utilities
     * @param location the location of the utilities
     */
    public Utilities(String name, int rent, Player owner, int price, Color colour, int location) {
        super(name, rent, price, colour, location);
    }


    @Override
    /**
     * get and return rent variable.
     * if owner owns both utilities, return rentVar times 2.5
     * else return rentVar
     * @returns calculated rentVar() value
     */
    public int getRent() {
        if (getOwner().getOwnsBothUtil()) {
            return (int)(this.getrentVar()*2.5);
        }
        else{
            return this.getrentVar();
        }
    }
}
