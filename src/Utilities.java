public class Utilities extends Properties{

    public Utilities(String name, int[] rentArray, Player owner, int price) {
        super(name, rentArray, owner, price);
    }


    @Override
    public int getRent() {
        if (getOwner().getOwnsBothUtil() == true) {
            return getRentArray()[1];
        }
        else{
            return getRentArray()[0];
        }
    }
}
