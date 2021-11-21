import java.awt.*;

public class AIPlayer extends Player {
    private Board board;
    private BoardModel bm;
    private BoardView boardView;
    private int numPlayers;

    /**
     * @param name          sets the players name to this parameter
     * @param color         player's color set to this parameter
     * @param startingMoney starting money of player is set to this parameter
     */
    public AIPlayer(String name, Color color, int startingMoney,int maxPlayers, Board board, BoardModel bm, BoardView boardView) {
        super(name, color, startingMoney, true);
        this.board = board;
        this.bm = bm;
        this.boardView = boardView;
        this.numPlayers = maxPlayers;
    }


    public String aiLogic(String Stage) {
        String choice = "";

        int pos = this.getPositon();
        if (Stage.compareToIgnoreCase("Buy Property") == 0) {
            if (!board.getProperty(pos).getColor().equals(new Color(255, 255, 255))) {         //If its not a chance/Go/Tax/    uninteractible/purchasable space
                if (!board.getProperty(pos).getColor().equals(new Color(250, 140, 0))) {       //If not Jail

                    if (this.getBalance() < 400){
                        if (
                        board.getProperty(pos).getCostBenfitRatio(this.getBalance(), board.getProperty(pos).getHousePrice(), board.getProperty(pos).getPrice()) >= 1.0){
                            bm.operateCommand(BoardModel.Commands.purchaseProperty);
                        }
                    }

                    else if ((this.getBalance() > 400)  && (this.getBalance() < 750) ){
                        if (board.getProperty(pos).getCostBenfitRatio(this.getBalance(), board.getProperty(pos).getHousePrice(), board.getProperty(pos).getPrice()) >= 0.75){
                            bm.operateCommand(BoardModel.Commands.purchaseProperty);
                        }
                    }

                    else if (this.getBalance() > 750){
                        if (board.getProperty(pos).getCostBenfitRatio(this.getBalance(), board.getProperty(pos).getHousePrice(), board.getProperty(pos).getPrice()) >= 0.70){
                            bm.operateCommand(BoardModel.Commands.purchaseProperty);
                        }
                    }

                    //Color set properties priority

                }
            }
            return choice;
        }
        return choice;
    }

    /**
     * Method to play an AI player's turn
     */
    @Override
    public void playAITurn(){
        while(bm.getNextRoll()){
            bm.operateCommand(BoardModel.Commands.roll);
            this.aiLogic("Buy Property");
        }
        boardView.updateAllPlayersStatus(numPlayers);
        bm.operateCommand(BoardModel.Commands.passTurn);
    }
}


