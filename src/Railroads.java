import java.awt.*;

public class Railroads extends Properties{
    public Railroads(String name, int rent, Player owner, int price, Color color, int location) {
        super(name, rent, price,color,location);
    }


    @Override
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
