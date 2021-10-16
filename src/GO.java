import java.awt.*;

public class GO extends Properties{
    public GO(String name, int rent, Player owner, int price, Color color) {
        super(name, 0, null, 0, color);
    }

    public void giveMoney(Player player) {
        player.addToBalance(200);
    }
}
