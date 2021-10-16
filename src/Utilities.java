public class Utilities extends Properties{

    public Utilities(String name, int rent, Player owner, int price) {
        super(name, rent, owner, price);
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
