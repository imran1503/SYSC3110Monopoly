import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;

/**
 * Class BoardController. Controls the implementations of the buttons pressed in BoardView of the game.
 * bv to store the BoardView of the game.
 * bm to store the BoardModel of the game.
 * playersInitialized: true if players are initialized, else false.
 * playerInitializeStage: int to store which stage of the initializing process the game is in.
 * Max_players: to store the total number of players the user entered that will play the game.
 * colorList: Stores an ArrayList of colors for Players.
 *
 */
public class BoardController implements ActionListener {
    private BoardView bv;
    private BoardModel bm;

    private Boolean playersInitialized;
    private int playerInitializeStage;
    private int playerInitializing;
    private int Max_players;

    private ArrayList<Color> colorList;

    /**
     * BoardController Constructor
     * @param bv BoardView of game
     * @param bm BoardModel of game
     */
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

    /**
     * Method to check which button sent an Action Event and then perform the operations related to the button pressed.
     * @param actionEvent Action event performed
     */
    @Override
    public void actionPerformed(ActionEvent actionEvent){
        ArrayList<JButton> buttons = bv.getButtonList();
        int startGameButton = 0;
        int rollButton = 1;
        int purchasePropertyButton = 2;
        int passTurnButton = 3;
        int purchaseHouseHotelButton = 5;
        int submitButton = 6;

        //start button
        if(actionEvent.getSource().equals(buttons.get(startGameButton))){
            if(!playersInitialized) {
                buttons.get(0).setVisible(false);
                bv.setUserInputVisibility(true);  //submit button & text field visible, start button not visible
                bv.setEventLabelText("Enter the total number of players.","Press the submit button when done");

            }
        }

        if(playersInitialized) {

            //roll button
            if (actionEvent.getSource().equals(buttons.get(rollButton))) {
                bm.operateCommand(BoardModel.Commands.roll);
                bv.updateAllPlayersStatus(Max_players);
            }
            //purchase property button
            if (actionEvent.getSource().equals(buttons.get(purchasePropertyButton))) {
                bm.operateCommand(BoardModel.Commands.purchaseProperty);
                bv.updateAllPlayersStatus(Max_players);
            }
            //pass player turn button
            if (actionEvent.getSource().equals(buttons.get(passTurnButton))) {
                bm.operateCommand(BoardModel.Commands.passTurn);
                bv.updateAllPlayersStatus(Max_players);
            }
            //purchase house or hotel button
            if (actionEvent.getSource().equals(buttons.get(purchaseHouseHotelButton))) {
                bm.operateCommand(BoardModel.Commands.purchaseHouse);
                bv.setUserInputVisibility(true);
            }
        }
        //submit button
        if (actionEvent.getSource().equals(buttons.get(submitButton))) {
            Boolean waitForNextButton = false;

            //Stage 1: Ask user an integer total number of players that want to play game between 2 and 4 inclusive.
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
            //Stage 2: Ask User if each player is AI or human, if human ask for a player name.
            if((playerInitializeStage == 2)&&!waitForNextButton){
                boolean AIPlayer = bv.getUserInput().equals("yes");

                if (AIPlayer) {
                    bm.addPlayer(new AIPlayer("AI Player" + (playerInitializing+1), colorList.get(playerInitializing), 1500,Max_players,bm.getBoard(),bm,bv));
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
            //Stage 3: helping stage with Stage 2
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
            //Stage 4: Initilize the game with user inputs and select a first player randomly
            if(playerInitializeStage == 4){
                bv.updateAllPlayersStatus(Max_players);
                bv.setPlayerPanelHoldersVisibility(Max_players,true);
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
            //Stage 5: Check what property name the User typed to buy a house on
            if((playerInitializeStage == 5)&&!waitForNextButton){
                String propertyName = bv.getUserInput();
                Boolean propertyExists = false;
                int propertyIndex = -1;
                Board board = bm.getBoard();
                for (int i = 0; i < board.getPropertyArrayList().size(); i++) {
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
