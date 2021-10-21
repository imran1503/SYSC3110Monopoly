import java.awt.*;

public class Jail extends Properties {
    private int enteredJailTurn;
    private int timeInJail = 3;
    public Jail(String name, int rent, int price, Color color, int location ) {
        super(name, 0, 0, color, location);
    }

    //TODO
    public void timeInJail(Player player, int turncount){
        enteredJailTurn = turncount;

    }
    public void timePass(Player player){
        timeInJail -= 1;
        if (timeInJail == 0){
            player.setInJail(false);
        }
    }


}
