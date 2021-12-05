import java.awt.*;
import java.util.ArrayList;

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
        //if Buy Property Stage
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
                for (int i = 0; i < this.getControlledProperty().size(); i++) {
                    if (currPropertyColor.equals(this.getControlledProperty().get(i).getColor())) {
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
                double costBenifitRatio = board.getProperty(pos).getCostBenefitRatio(this.getBalance(), board.getProperty(pos).getHousePrice(), board.getProperty(pos).getPrice()) + priorityBoost;
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
        //if Buy House Stage
        if (Stage.compareToIgnoreCase("Buy House") == 0) {
            //if AI has colorSet
            if(this.getHasAColorSet()){
                ArrayList<Color> colorSetList = new ArrayList<>();
                ArrayList<Property> propertyList = new ArrayList<>();
                Color railroadColor =  new Color(102,98,95);
                //Get Color Set Property owned by AI
                for (int i = 0; i < board.getAllColorsList().size(); i++) {
                    Color color = board.getAllColorsList().get(i);
                    ArrayList<Property> tempPropetyList =board.getColorPropertyArrayList().get(color);
                    Boolean ownsColorSet = true;
                    for(int j = 0; j<tempPropetyList.size(); j++){
                        if(!tempPropetyList.get(j).getOwner().equals(this)){
                            ownsColorSet = false;
                        }
                    }
                    if(ownsColorSet && (!color.equals(railroadColor))){
                        colorSetList.add(color);
                        for (int k = 0; k < tempPropetyList.size(); k++) {
                            propertyList.add(tempPropetyList.get(k));
                        }
                    }
                }
                //Check value of each property option in propertyList and get color of top priority
                ArrayList<Double> costBenitfitList = new ArrayList<>();
                double topBenifitRatio = 0.0;
                int topBenifitIndex = 0;
                for (int i = 0; i < propertyList.size(); i++) {
                    double costBenifitRatio = propertyList.get(i).getCostBenefitRatio(this.getBalance(), propertyList.get(i).getRent(), propertyList.get(i).getHousePrice());
                    costBenitfitList.add(costBenifitRatio);
                    if(costBenifitRatio > topBenifitRatio){
                        topBenifitRatio = costBenifitRatio;
                        topBenifitIndex = i;
                    }
                }

                Color colorOfTopProperty = propertyList.get(topBenifitIndex).getColor();
                int colorIndex = colorSetList.indexOf(colorOfTopProperty);
                //if topPriority Color's Property already have Hotels, change the color.
                if(board.getColorPropertyArrayList().get(colorOfTopProperty).get(0).getNumHotels() ==1){
                    colorOfTopProperty = colorSetList.get((colorIndex +1)%colorSetList.size());
                }
                ArrayList<Property> topPropertyList = board.getColorPropertyArrayList().get(colorOfTopProperty);
                for(int i = 0; i < topPropertyList.size(); i++){
                    Property propertySameColor = topPropertyList.get(i);
                    //if balance atleast triple cost of buying house, buy houses on top priority color set.
                    if((this.getBalance()*3) > propertySameColor.getHousePrice()) {
                        bm.purchaseHouseOrHotel(propertySameColor);
                    }
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
            this.aiLogic("Buy House");
        }
        //Update GUI and pass turn to next player
        boardView.updateAllPlayersStatus(numPlayers);
        if(this.getBankruptStatus()){
            boardView.getPlayerLists().get(bm.getCurrentPlayerIndex())[this.getPositon()].setVisible(false);
        }
        bm.operateCommand(BoardModel.Commands.passTurn);
    }
}
