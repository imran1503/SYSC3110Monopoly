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
            for (int i = 0; i < board.getPropertiesArrayList().size(); i++) {
                if (!board.getProperty(i).getColor().equals(new Color(255, 255, 255))) {         //
                    if (!board.getProperty(i).getColor().equals(new Color(250, 140, 0))) {

                        //IF Bal <400                           set cbv > 1.00 , if prop(cbv)bigger, buy it


                        //ELSE IF Bal >400 and Bal <750         set cbv > 0.75 , if prop(cbv)bigger, buy it

                        //else if bal > 750 and bal < 1000      set cbv > 0.70 , if prop(cbv)bigger, buy it

                        board.getProperty(i).getCostBenfitRatio(this.getBalance(), board.getProperty(i).getHousePrice(), board.getProperty(i).getPrice());
                    }
                }
            }

            return choice;
        }
        return choice;

    }
}


