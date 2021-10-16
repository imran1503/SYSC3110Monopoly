public class GO extends Properties{
    public GO(String name, int[] rentArray, Player owner, int price) {
        super(name, null, null, 0);
    }

    public void giveMoney(Player player) {
        player.addToBalance(200);
    }
}
