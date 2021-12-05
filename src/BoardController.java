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

    //todo add to uml
    public enum Stages {languageSelect, numPlayersSelect, AIPlayerSelect, AIPlayerInit, gameInit, housingPurchase}

    private Boolean playersInitialized;
    private Stages playerInitializeStage;
    private int playerInitializing;
    private int Max_players;
    private Boolean waitForNextButton;

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
        this.playerInitializeStage = Stages.languageSelect;
        this.Max_players = 0;
        this.playerInitializing = 0;
        this.colorList = new ArrayList<>();
        colorList.add(new Color(100,0,0));
        colorList.add(new Color(0,100,0));
        colorList.add(new Color(0,0,100));
        colorList.add(new Color(150,100,50));
        this.waitForNextButton = false;

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
        int saveButton = 7;
        int loadButton = 8;

        //start button
        if(actionEvent.getSource().equals(buttons.get(startGameButton))){
            startButtonAction();
        }

        if(playersInitialized) {
            //roll button
            if (actionEvent.getSource().equals(buttons.get(rollButton))) {
                rollButtonAction();
            }
            //purchase property button
            if (actionEvent.getSource().equals(buttons.get(purchasePropertyButton))) {
                purchasePropertyButtonAction();
            }
            //pass player turn button
            if (actionEvent.getSource().equals(buttons.get(passTurnButton))) {
                passTurnButtonAction();
            }
            //purchase house or hotel button
            if (actionEvent.getSource().equals(buttons.get(purchaseHouseHotelButton))) {
                purchaseHouseButtonAction();
            }
        }
        //submit button has been pressed
        if (actionEvent.getSource().equals(buttons.get(submitButton))) {
            submitButtonAction();
        }
        if (actionEvent.getSource().equals(buttons.get(saveButton))) {
            //TODO save game
            bm.save("MonopolySaveTest");
        }
        if (actionEvent.getSource().equals(buttons.get(loadButton))) {
            //TODO load game
            bm.load("MonopolySaveTest.xml");
        }
    }

    public void startButtonAction(){
        ArrayList<JButton> buttons = bv.getButtonList();
        int startButton = 0; int loadButton = 8;
        if(!playersInitialized) {
            buttons.get(startButton).setVisible(false);
            buttons.get(loadButton).setVisible(false);
            bv.setUserInputVisibility(true);  //submit button & text field visible, start button not visible
            bv.hideUserInputBox(); // hide only the userInputBox
            bv.setLanguageOptionsPanelVisibility(true); // make board language options visible for user to choose from
            bv.setEventLabelText("Select the board's language and press the submit button.", "");
        }
    }

    public void rollButtonAction(){
        bm.operateCommand(BoardModel.Commands.roll);
        bv.updateAllPlayersStatus(Max_players);
    }

    public void purchasePropertyButtonAction(){
        bm.operateCommand(BoardModel.Commands.purchaseProperty);
        bv.updateAllPlayersStatus(Max_players);
    }

    public void passTurnButtonAction(){
        bm.operateCommand(BoardModel.Commands.passTurn);
        bv.updateAllPlayersStatus(Max_players);
    }

    public void purchaseHouseButtonAction(){
        bm.operateCommand(BoardModel.Commands.purchaseHouse);
        bv.setUserInputVisibility(true);
    }

    public void submitButtonAction(){
        waitForNextButton = false;
        if(playerInitializeStage.equals(Stages.languageSelect)){
            languageSelect();
        }
        if(playerInitializeStage.equals(Stages.numPlayersSelect)){
            numPlayersSelect();
        }
        if((playerInitializeStage.equals(Stages.AIPlayerSelect)) && !waitForNextButton) {
            AIPlayerSelect();
        }
        if(playerInitializeStage.equals(Stages.AIPlayerInit) && !waitForNextButton) {
            AIPlayerSelectHelper();
        }
        if(playerInitializeStage.equals(Stages.gameInit)){
            initializeGame();
        }
        if((playerInitializeStage.equals(Stages.housingPurchase))&&!waitForNextButton){
            purchaseHouseHelper();
        }
    }

    /** Stage 0 (languageSelect): Ask user to select the board's language from available options.*/
    public void languageSelect(){
        String languageSelected = "English"; // Default language is English
        ArrayList<JRadioButton> radioButtons = bv.getRadioButtonList();
        if (radioButtons.get(1).isSelected()) { // if the French radio button is selected
            languageSelected = "French";
        }
        if (radioButtons.get(2).isSelected()) { // if the Arabic radio button is selected
            languageSelected = "Arabic";
        }
        if (radioButtons.get(3).isSelected()) { // if the Persian radio button is selected
            languageSelected = "Persian";
        }
        String fileName = "";
        if(languageSelected.equals("French")) {//French Button
            fileName = "board-fr.xml";
        }
        else if (languageSelected.equals("Arabic")) {
            fileName = "board-ar.xml";
        }
        else if (languageSelected.equals("Persian")) {
            fileName  = "board-pe.xml";
        }
        else if (languageSelected.equals("English")){
            fileName = "board.xml";
        }
        Board board = new Board("src/"+fileName);
        BoardConstructor boardConstructor = new BoardConstructor(board);
        board =  boardConstructor.loadBoardFromMapFile(fileName,true);
        bm.setBoard(board);
        bv.setAllPropertys();
        playerInitializeStage = Stages.numPlayersSelect; //set next stage
    }

    /**Stage 1 (numPlayersSelect): Ask user an integer total number of players that want to play game between 2 and 4 inclusive.*/
    public void numPlayersSelect(){
        bv.setLanguageOptionsPanelVisibility(false); // hide language options
        bv.setUserInputVisibility(true);
        bv.setEventLabelText("Enter the total number of players.","Press the submit button when done.");
        bv.setEventLabel3Text("");

        Boolean isInt;
        Boolean inputIsEmpty;

        isInt = bm.isInteger(bv.getUserInput());
        inputIsEmpty= bv.getUserInput().isEmpty();

        if(!inputIsEmpty && isInt){
            Max_players = Integer.parseInt(bv.getUserInput());
            if((Max_players > 4)||(Max_players < 2)){
                bv.setEventLabel3Text("Number of players can only be 2, 3, or 4.");
            }
            else{
                playerInitializeStage = Stages.AIPlayerSelect;
                bv.setEventLabelText("Do you want player " + (playerInitializing+1) + " to be an AI Player? ('yes' if AI)?","Press the submit button when done.");
                bv.setEventLabel3Text("");
                waitForNextButton = true;
            }
        }

        else if (isInt) {
            bv.setEventLabel3Text("The number of players must be typed as a number like '4', not 'four'.");
        }
        bv.clearTextField();
    }

    /**Stage 2 (AIPlayerSelect): Ask User if each player is AI or human, if human ask for a player name.*/
    public void AIPlayerSelect(){
        boolean AIPlayer = bv.getUserInput().equals("yes");

        if (AIPlayer) {
            bm.addPlayer(new AIPlayer("AI Player" + (playerInitializing+1), colorList.get(playerInitializing), 1500,Max_players,bm.getBoard(),bm,bv));
            playerInitializing++;
            bv.setEventLabelText("Do you want player " + (playerInitializing+1) + " to be an AI Player? ('yes' if AI)?","Press the submit button when done");

        }
        else {
            bv.setEventLabelText("Enter the name of Player "+(playerInitializing+1),"Press the submit button when done");
            playerInitializeStage = Stages.AIPlayerInit;
            waitForNextButton = true;
        }

        if(playerInitializing == Max_players){
            playerInitializeStage = Stages.gameInit;
        }
        bv.clearTextField();
    }

    /**Stage 3 (AIPlayerInit): helping stage with Stage 2*/
    public void AIPlayerSelectHelper(){
        playerInitializing++;
        bv.setEventLabelText("Do you want player " + (playerInitializing+1) + " to be an AI Player? ('yes' if AI)?","Press the submit button when done");
        String playerName = bv.getUserInput();
        Player newPlayer = new Player(playerName, new Color(10*playerInitializing,10*playerInitializing,10*playerInitializing), 1500, false);
        bm.addPlayer(newPlayer);
        playerInitializeStage = Stages.AIPlayerSelect;
        bv.clearTextField();

        if(playerInitializing == Max_players){
            playerInitializeStage = Stages.gameInit;
        }
    }

    /**Stage 4 (gameInit): Initilize the game with user inputs and select a first player randomly*/
    public void initializeGame(){
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
        playerInitializeStage = Stages.housingPurchase;
        waitForNextButton = true;
        //if first player is AI, then playAITurn for first player
        if(firstPlayer.getAi()){
            firstPlayer.playAITurn();
        }
    }

    /**Stage 5 (housingPurchase): Check what property name the User typed to buy a house on*/
    public void purchaseHouseHelper(){
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

    public void setPlayersInitialized(){
        playersInitialized = true;
        playerInitializeStage = Stages.housingPurchase;
    }
}
