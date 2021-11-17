import java.awt.*;

public class AIPlayer extends Player{
    /**
     * @param name          sets the players name to this parameter
     * @param color         player's color set to this parameter
     * @param startingMoney starting money of player is set to this parameter
     * @param isAi
     */
    public AIPlayer(String name, Color color, int startingMoney, boolean isAi) {
        super(name, color, startingMoney, isAi);
    }
}
