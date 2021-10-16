public class GO extends Properties{
    public GO(String name, int rent, Player owner, int price) {
        super(name, 0, null, 0);
    }

    public void giveMoney(Player player) {
        player.addToBalance(200);
    }
}
