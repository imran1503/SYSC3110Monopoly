import java.awt.*;

public class AIPlayer extends Player {
    private Board board;

    /**
     * @param name          sets the players name to this parameter
     * @param color         player's color set to this parameter
     * @param startingMoney starting money of player is set to this parameter
     * @param isAi
     */
    public AIPlayer(String name, Color color, int startingMoney, boolean isAi, Board board) {
        super(name, color, startingMoney, isAi);
        this.board = board;
    }


    public String aiLogic(String Stage) {
        String choice = "";

        int pos = this.getPositon();
        if (Stage.compareToIgnoreCase("Buy Property") == 0) {
            //TODO Calc(Cost (property)) So final value +5 x house price

            int i = pos;
            System.out.println(board.getProperty(i).getName() + " "
                    + board.getProperty(i).getCostBenfitRatio(this.getBalance(),board.getProperty(i).getHousePrice(), board.getProperty(i).getPrice())
                    + " HP >" + board.getProperty(i).getHousePrice());
            //TODO if value < x, buy it, then pass, else pass
        }

        return choice;
    }



}


