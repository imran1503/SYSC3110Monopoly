import java.awt.*;

public class GO extends Properties{
    public GO() {
        super("Go", 0, 0, null,0);
    }

    public void giveMoney(Player player) {
        player.addToBalance(200);
    }
}
