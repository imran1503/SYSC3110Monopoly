import java.awt.*;

public class Utilities extends Properties{

    public Utilities(String name, int rent, Player owner, int price, Color colour) {
        super(name, rent, owner, price, colour);
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
