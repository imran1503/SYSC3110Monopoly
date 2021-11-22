import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;

public class BoardController implements ActionListener {

    private BoardView bv;

    private BoardModel bm;

    private Boolean playersInitialized;

    private int playerInitializeStage;

    private int playerInitializing;

    private int Max_players;

    private ArrayList<Color> colorList;

    public BoardController (BoardView bv,BoardModel bm){
        this.bv=bv;
        this.bm=bm;
        this.playersInitialized = false;
        this.playerInitializeStage = 1;
        this.Max_players = 0;
        this.playerInitializing = 0;
        this.colorList = new ArrayList<>();
        colorList.add(new Color(100,0,0));
        colorList.add(new Color(0,100,0));
        colorList.add(new Color(0,0,100));
        colorList.add(new Color(150,100,50));

    }

    @Override
    public void actionPerformed(ActionEvent actionEvent){
        ArrayList<JButton> buttons = bv.getButtonList();
        //start button
        if(actionEvent.getSource().equals(buttons.get(0))){
            if(!playersInitialized) {
                buttons.get(0).setVisible(false);
                bv.setUserInputVisibility(true);  //submit button & text field visible, start button not visible
                bv.setEventLabelText("Enter the total number of players.","Press the submit button when done");

            }
        }
        if(playersInitialized) {
            //roll button
            if (actionEvent.getSource().equals(buttons.get(1))) {
                bm.operateCommand(BoardModel.Commands.roll);
                bv.updateAllPlayersStatus(Max_players);
            }
            //purchase property button
            if (actionEvent.getSource().equals(buttons.get(2))) {
                bm.operateCommand(BoardModel.Commands.purchaseProperty);
                bv.updateAllPlayersStatus(Max_players);
            }
            //pass player turn button
            if (actionEvent.getSource().equals(buttons.get(3))) {
                bm.operateCommand(BoardModel.Commands.passTurn);
                bv.updateAllPlayersStatus(Max_players);
            }
            //purchase house or hotel button
            if (actionEvent.getSource().equals(buttons.get(5))) {
                bm.operateCommand(BoardModel.Commands.purchaseHouse);
                bv.setUserInputVisibility(true);
            }
        }
        //submit button
        if (actionEvent.getSource().equals(buttons.get(6))) {
            Boolean waitForNextButton = false;

            if((playerInitializeStage == 1)&&!waitForNextButton){
                Boolean isInt;
                isInt = bm.isInteger(bv.getUserInput());
                if(isInt){
                    Max_players = Integer.parseInt(bv.getUserInput());
                    if((Max_players > 4)||(Max_players < 2)){
                        bv.setEventLabel3Text("Total number entered is greater than 4 or less than 2");
                    }
                    else{
                        playerInitializeStage++;
                        bv.setEventLabelText("Do you want player " + (playerInitializing+1) + " to be an AI Player? ('yes' if AI)?","Press the submit button when done");
                        bv.setEventLabel3Text("");
                        waitForNextButton = true;
                    }
                }
                else{
                    bv.setEventLabel3Text("Enter an integer number like '4' , not a word.");
                }
                bv.clearTextField();
            }
            if((playerInitializeStage == 2)&&!waitForNextButton){

                boolean AIPlayer = bv.getUserInput().equals("yes");

                if (AIPlayer) {
                    bm.addPlayer(new AIPlayer("AI Player" + (playerInitializing + 1), colorList.get(playerInitializing), 1500,Max_players,bm.getBoard(),bm,bv));
                    playerInitializing++;
                    bv.setEventLabelText("Do you want player " + (playerInitializing+1) + " to be an AI Player? ('yes' if AI)?","Press the submit button when done");

                }
                else {
                    bv.setEventLabelText("Enter the name of Player "+(playerInitializing+1),"Press the submit button when done");
                    playerInitializeStage = 3;
                    waitForNextButton = true;
                }

                if(playerInitializing == Max_players){
                    playerInitializeStage = 4;
                }

                bv.clearTextField();
            }

            if(playerInitializeStage == 3 && !waitForNextButton) {
                playerInitializing++;
                bv.setEventLabelText("Do you want player " + (playerInitializing+1) + " to be an AI Player? ('yes' if AI)?","Press the submit button when done");
                String playerName = bv.getUserInput();
                Player newPlayer = new Player(playerName, new Color(10*playerInitializing,10*playerInitializing,10*playerInitializing), 1500, false);
                bm.addPlayer(newPlayer);
                playerInitializeStage = 2;
                bv.clearTextField();

                if(playerInitializing == Max_players){
                    playerInitializeStage = 4;
                }
            }

            if(playerInitializeStage == 4){
                bv.updateAllPlayersStatus(Max_players);
                int firstPlayerIndex = bm.determineFirstPlayer();
                Player firstPlayer = bm.getPlayer(firstPlayerIndex);
                bm.setCurrentPlayer(firstPlayer);
                playersInitialized = true;
                bv.setEventLabelText(firstPlayer.getName() + " has been randomly chosen to go first!", "Start your turn by pressing the roll button.");
                bv.setUserInputVisibility(false);

                for (int i = 0; i < Max_players; i++) {  //For all 4 players
                    bv.getPlayerLists().get(i)[0].setVisible(true); //set icons to visible on GO
                }
                playerInitializeStage++;
                waitForNextButton = true;
                //if first player is AI, then playAITurn for first player
                if(firstPlayer.getAi()){
                    firstPlayer.playAITurn();
                }
            }
            if((playerInitializeStage == 5)&&!waitForNextButton){ //Buying a house
                String propertyName = bv.getUserInput();
                Boolean propertyExists = false;
                int propertyIndex = -1;
                Board board = bm.getBoard();
                for (int i = 0; i < board.getPropertiesArrayList().size(); i++) {
                    if (board.getProperty(i).getName().equals(propertyName)) {
                        propertyExists = true;
                        propertyIndex = i;
                    }
                }
                if (propertyExists) {
                    bm.purchaseHouseOrHotel(board.getProperty(propertyIndex));
                } else {
                    bv.setEventLabelText("Property: " + propertyName + ", Does not exists","");
                }
                bv.setUserInputVisibility(false);
                bv.clearTextField();
            }



        }

    }

}
