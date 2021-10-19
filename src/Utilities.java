import java.awt.*;

public class Utilities extends Properties{

    public Utilities(String name, int rent, Player owner, int price, Color colour, int location) {
        super(name, rent, price, colour, location);
    }


    @Override
    public int getRent() {
        if (getOwner().getOwnsBothUtil()) {
            return (int)(this.getrentVar()*2.5);
        }
        else{
            return this.getrentVar();
        }
    }
}
