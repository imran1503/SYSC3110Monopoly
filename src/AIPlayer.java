import java.awt.*;

/**
 * Class AIPlayer, an extension to Player. This player will be able to make automatic game decisions about it's turn.
 * board, bm, boardView variables to store a reference to the Board, BoardModel, BoardView of the game.
 * numPlayers is store the max number of players playing the game.
 */
public class AIPlayer extends Player {
    private Board board;
    private BoardModel bm;
    private BoardView boardView;
    private int numPlayers;

    /**
     * @param name          sets the players name to this parameter
     * @param color         player's color set to this parameter
     * @param startingMoney starting money of player is set to this parameter
     * @param maxPlayers    number of players playing the game
     * @param board         Board of player is set to this parameter
     * @param bm            BoardModel of player is set to this parameter
     * @param boardView     BoardView of player is set to this parameter
     */
    public AIPlayer(String name, Color color, int startingMoney,int maxPlayers, Board board, BoardModel bm, BoardView boardView) {
        super(name, color, startingMoney, true);
        this.board = board;
        this.bm = bm;
        this.boardView = boardView;
        this.numPlayers = maxPlayers;
    }

    /**
     * Method for AI to make decision to buy something or not.
     * @param Stage String for what to buy (currently just Buy Property)
     */
    public void aiLogic(String Stage) {
        int pos = this.getPositon();
        if (Stage.compareToIgnoreCase("Buy Property") == 0) {
            Color propertyColor = board.getProperty(pos).getColor();
            Color jailColor = new Color(250, 140, 0);
            Color nonPurchasableColor = new Color(255, 255, 255);

            //If its not a chance/Go/Tax/non-purchasable space/Jail
            if ( (!propertyColor.equals(nonPurchasableColor)) && (!propertyColor.equals(jailColor)) ){
                // check if a decision to purchase would complete a color set
                boolean purchaseCompletesColorset = false;
                Color currPropertyColor = board.getProperty(pos).getColor();

                int matchingColorCount = 0;

                for (int i = 0; i < this.getControlledProperties().size(); i++) {
                    if (currPropertyColor.equals(this.getControlledProperties().get(i).getColor())) {
                        matchingColorCount++;
                    }
                }

                // if matching color is brown or dark blue
                if (currPropertyColor.equals(new Color (136, 69, 19)) || currPropertyColor.equals(new Color (0, 0, 128))) {
                    if (matchingColorCount == 1) {
                        purchaseCompletesColorset = true;
                    }
                }
                else if (matchingColorCount == 2) {
                    purchaseCompletesColorset = true;
                }

                // give a priority boost to decision to purchase if it will complete colorset
                double priorityBoost = 0;
                if(purchaseCompletesColorset) {
                    priorityBoost = 0.5;
                }

                // check costBenifitRatio of buying property with different Balance ranges
                double costBenifitRatio = board.getProperty(pos).getCostBenfitRatio(this.getBalance(), board.getProperty(pos).getHousePrice(), board.getProperty(pos).getPrice()) + priorityBoost;
                Boolean buyProperty = false;
                if (this.getBalance() < 400){
                    if (costBenifitRatio>= 1.0){
                        buyProperty = true;
                    }
                }
                else if ((this.getBalance() > 400)  && (this.getBalance() < 750) ){
                    if (costBenifitRatio >= 0.75){
                        buyProperty = true;
                    }
                }
                else if (this.getBalance() > 750){
                    if (costBenifitRatio >= 0.70){
                        buyProperty = true;
                    }
                }
                //if true, operate buy property command
                if(buyProperty){
                    bm.operateCommand(BoardModel.Commands.purchaseProperty);
                }
            }
        }
    }

    /**
     * Method to play an AI player's turn
     */
    @Override
    public void playAITurn(){
        //While AIPlayer can roll again, roll again and make decision to buy property or not
        while(bm.getNextRoll()){
            bm.operateCommand(BoardModel.Commands.roll);
            this.aiLogic("Buy Property");
        }
        //Update GUI and pass turn to next player
        boardView.updateAllPlayersStatus(numPlayers);
        bm.operateCommand(BoardModel.Commands.passTurn);
    }
}
