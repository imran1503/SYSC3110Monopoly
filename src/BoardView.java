import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.util.ArrayList;

/**
 * class BoardView implements the graphical user interface's view for the Monopoly game.
 * frame variable to store the main frame of the gui.
 * boardModel to store the BoardModel of the game.
 * Alot of panels to hold the players, Property, buttons of the game
 * Alot of labels to store the players, events, player icons, houses icons for the game.
 * Alot of buttons for each important operation in the game and buttons list.
 * userInputBox to store user input on gui in the JTextField
 * scrollPanes and PropertyTextArea to hold the controlled Property in a list for each player.
 * JRadio buttons to select the language version of board to play with.
 *
 * @author Mahtab Ameli, Imran Latif, Muhammad Furqan
 * @version 1.0
 */
public class BoardView {
    private final JFrame frame;
    private BoardModel boardModel;

    private JPanel[][] panelHolder;
    private JPanel[] propertyPanels,playerPanels;
    private JPanel north, east, west, south, center,mainPanel,controlPanel,gamePanel, buttonPanel;

    private ArrayList<JLabel []> playerLists;
    private JLabel[][] playerLabelList;
    private JLabel[] player1Labels,player2Labels,player3Labels,player4Labels;
    private JLabel[] housing1Labels,housing2Labels,housing3Labels,housing4Labels,housing5Labels;
    private JLabel eventLabel,eventLabel2,eventLabel3;

    private ArrayList<JButton> buttonList;
    private JButton startButton,rollButton,purchaseButton,helpButton,newGameButton,quitButton,purchaseHouseHotel,passButton,submitButton,saveButton, loadButton;

    private JRadioButton EnglishButton, FrenchButton, ArabicButton, PersianButton;
    private ButtonGroup languageButtonGroup;
    private JPanel languageOptionsPanel;
    private ArrayList<JRadioButton> radioButtonList;

    private JTextField userInputBox;
    private JScrollPane[] scrollPanes;
    private JTextArea[] PropertyTextAreas;
    private BoardController bc;

    /**
     * Constructor for BoardView.
     */
    public BoardView(BoardModel boardModel) {
        this.frame = new JFrame("Welcome to G28's Monopoly!");
        this.frame.setResizable(true);
        this.boardModel = boardModel;
        this.player1Labels = new JLabel[40];
        this.player2Labels = new JLabel[40];
        this.player3Labels = new JLabel[40];
        this.player4Labels = new JLabel[40];
        this.housing1Labels = new JLabel[40];
        this.housing2Labels = new JLabel[40];
        this.housing3Labels = new JLabel[40];
        this.housing4Labels = new JLabel[40];
        this.housing5Labels = new JLabel[40];
        this.playerLists = new ArrayList<>();
        playerLists.add(player1Labels);
        playerLists.add(player2Labels);
        playerLists.add(player3Labels);
        playerLists.add(player4Labels);
        addBasePanels();
    }

    /**
     * Creates the mainPanel of the GUI frame. Adds gamePanel to the mainPanel.
     */
    private void addBasePanels() {
        //mainPanel placed on the frame. Contains gamePanel.
        mainPanel = new JPanel(new BorderLayout());
        createGamePanel();
        mainPanel.add(gamePanel, BorderLayout.WEST);
        frame.add(mainPanel);
    }

    /**
     * Returns the list of radio buttons.
     * @return
     */
    public ArrayList<JRadioButton> getRadioButtonList() {
        return radioButtonList;
    }


    /**
     * Getter method for playerLists
     * @return playerLists
     */
    public ArrayList<JLabel[]> getPlayerLists(){return playerLists;}

    /**
     * This helper function is used to create all the labels, player icons, houses icons and Property on the grid.
     * @param direction The edge of the board we are creating labels on.
     * @param i Which property i we are adding.
     * @param propertyIndexLabels The list we are adding the labels to.
     */
    private void createPropertyPanelHelper(JPanel direction, int i, JLabel [] propertyIndexLabels){
        propertyPanels[i] = new JPanel(new BorderLayout());
        Color propertyColor = boardModel.getBoard().getProperty(i).getColor();
        propertyPanels[i].setBackground(propertyColor);

        // Initialize Player Icons for each property, set them to Not visible and add them to playerPanel
        JPanel playerPanel = new JPanel(new GridLayout(1,4));
        player1Labels[i] = new JLabel(new PlayerIcon(10,10,new Color(100,0,0)));
        player2Labels[i] = new JLabel(new PlayerIcon(10,10,new Color(0,100,0)));
        player3Labels[i] = new JLabel(new PlayerIcon(10,10,new Color(0,0,100)));
        player4Labels[i] = new JLabel(new PlayerIcon(10,10,new Color(150,100,50)));
        player1Labels[i].setVisible(false);
        player2Labels[i].setVisible(false);
        player3Labels[i].setVisible(false);
        player4Labels[i].setVisible(false);

        playerPanel.add(player1Labels[i]);
        playerPanel.add(player2Labels[i]);
        playerPanel.add(player3Labels[i]);
        playerPanel.add(player4Labels[i]);
        playerPanel.setBackground(propertyColor);

        //Initlize housing icons for each property, set them to Not visible and add them to housingPanel
        JPanel housingPanel = new JPanel(new GridLayout(1,5));
        PlayerIcon houseIcon = new PlayerIcon(10,10,new Color(108, 222, 27));
        PlayerIcon hotelIcon = new PlayerIcon(10,10,new Color(213, 6, 6, 255));
        housing1Labels[i] = new JLabel(houseIcon);
        housing2Labels[i] = new JLabel(houseIcon);
        housing3Labels[i] = new JLabel(houseIcon);
        housing4Labels[i] = new JLabel(houseIcon);
        housing5Labels[i] = new JLabel(hotelIcon);
        housing1Labels[i].setVisible(false);
        housing2Labels[i].setVisible(false);
        housing3Labels[i].setVisible(false);
        housing4Labels[i].setVisible(false);
        housing5Labels[i].setVisible(false);

        housingPanel.add(housing1Labels[i]);
        housingPanel.add(housing2Labels[i]);
        housingPanel.add(housing3Labels[i]);
        housingPanel.add(housing4Labels[i]);
        housingPanel.add(housing5Labels[i]);
        housingPanel.setBackground(propertyColor);

        //add housingPanel + playerPanel to propertyPanels[i].
        propertyPanels[i].add(housingPanel, BorderLayout.CENTER);
        propertyPanels[i].add(playerPanel, BorderLayout.PAGE_END);
        direction.add(propertyPanels[i]);
    }

    /**
     * Creates 40 propertyPanels and adds them to gamePanel.
     *
     */
    private void createPropertyPanels() {
        // Create 40 panels, 1 for each property. Create 40 JLabels, 1 for each propertyPanel
        this.propertyPanels = new JPanel[40];
        JLabel [] propertyIndexLabels = new JLabel[40];

        for (int i = 0; i < 40; i++) {
            // Property 0 to 10 are added to the south panel
            if (i < 11) {
                createPropertyPanelHelper(south, i, propertyIndexLabels);
            }
            // Property 11 to 19 are added to the west panel.
            else if (i < 20) {
                createPropertyPanelHelper(west, 30 - i, propertyIndexLabels);
            }
            // Property 20 to 30 are added to the north panel.
            else if (i < 31) {
                createPropertyPanelHelper(north, i, propertyIndexLabels);
            }
            // Property 31 to 39 are added to the east panel.
            else {
                createPropertyPanelHelper(east, i,propertyIndexLabels);
            }
        }
    }

    /** creates gamePanel which contains all propertyPanels
     *
     */
    private void createGamePanel() {
        gamePanel = new JPanel(new BorderLayout());
        Dimension size = Toolkit. getDefaultToolkit(). getScreenSize();
        gamePanel.setPreferredSize(size);
        gamePanel.setBackground(new Color(255, 255, 255));

        //gamePanel is made of 5  other panels: north, east, west, south, center
        north = new JPanel(new GridLayout(1,11));
        east = new JPanel(new GridLayout(9,1));
        west = new JPanel(new GridLayout(9,1));
        south = new JPanel(new GridLayout(1,11));
        center = new JPanel(new FlowLayout());

        north.setBackground(new Color(90,20,200));
        north.setPreferredSize(new Dimension(800,100));
        north.setComponentOrientation(ComponentOrientation.LEFT_TO_RIGHT);

        east.setBackground(new Color(100,140,10));
        east.setPreferredSize(new Dimension(150, 500));
        east.setComponentOrientation(ComponentOrientation.LEFT_TO_RIGHT);

        west.setBackground(new Color(0,200,150));
        west.setPreferredSize(new Dimension(150, 500));
        west.setComponentOrientation(ComponentOrientation.RIGHT_TO_LEFT);

        south.setBackground(new Color(250,120,200));
        south.setPreferredSize(new Dimension(800,100));
        south.setComponentOrientation(ComponentOrientation.RIGHT_TO_LEFT);

        center.setBackground(new Color(190,250,250));
        int numOfLabels = 6;
        this.playerLabelList = new JLabel[numOfLabels][7];

        this.scrollPanes = new JScrollPane[4];
        this.PropertyTextAreas = new JTextArea[4];

        //create Control panel and add to center panel
        createControlPanel();
        center.add(controlPanel);

        createPropertyPanels();

        gamePanel.add(north, BorderLayout.NORTH);
        gamePanel.add(east, BorderLayout.EAST);
        gamePanel.add(west, BorderLayout.WEST);
        gamePanel.add(south, BorderLayout.SOUTH);
        gamePanel.add(center, BorderLayout.CENTER);
    }

    /** creates controlPanel, which contains all controls and buttons
     * including purchase property, and quit game
     *
     */
    private void createControlPanel() {
        // controlPanel contains the buttons for starting a new game, roll, purchasing property, and quiting the game.
        // The message windows that appear as a result of pressing these buttons will also appear on eastPanel
        int rows = 2;
        int columns = 3;
        controlPanel = new JPanel(new GridLayout(rows,columns));
        panelHolder = new JPanel[rows][columns];
        for(int m = 0; m < rows; m++) {
            for(int n = 0; n < columns; n++) {
                panelHolder[m][n] = new JPanel();
                controlPanel.add(panelHolder[m][n]);
            }
        }

        Dimension controlPanelSize = Toolkit.getDefaultToolkit().getScreenSize();
        controlPanelSize.setSize(controlPanelSize.getWidth()*0.8,controlPanelSize.getHeight()*0.65);
        controlPanel.setPreferredSize(controlPanelSize);

        //Initialize buttonPanel and buttons
        this.buttonPanel = new JPanel();
        buttonPanel.setLayout(new BoxLayout(buttonPanel, BoxLayout.Y_AXIS));
        startButton = new JButton("Start Game");
        rollButton = new JButton("Roll");
        newGameButton = new JButton("New Game");
        purchaseButton = new JButton("Purchase Property");
        quitButton = new JButton("Quit Game");
        passButton = new JButton("Pass turn");
        helpButton = new JButton("Help");
        purchaseHouseHotel = new JButton("Buy house/hotel");
        submitButton = new JButton("Submit");
        saveButton = new JButton("Save Game");
        loadButton = new JButton("Load Game");

        /** BoardController initialization and actionListener for butttons */
        this.buttonList = new ArrayList<>();
        buttonList.add(startButton);     //Index 0
        buttonList.add(rollButton);      //Index 1
        buttonList.add(purchaseButton);  //Index 2
        buttonList.add(passButton);      //Index 3
        buttonList.add(quitButton);      //Index 4
        buttonList.add(purchaseHouseHotel);//Index 5
        buttonList.add(submitButton);    //Index 6
        buttonList.add(saveButton);     //Index 7
        buttonList.add(loadButton);     //Index 8


        /** Initializing language radio buttons and adding them to languagesButtonGroup, and to languageOptionsPanel */
        EnglishButton = new JRadioButton("English");
        FrenchButton = new JRadioButton("French");
        ArabicButton = new JRadioButton("Arabic");
        PersianButton = new JRadioButton("Persian");
        languageButtonGroup = new ButtonGroup();
        languageButtonGroup.add(EnglishButton);
        languageButtonGroup.add(FrenchButton);
        languageButtonGroup.add(ArabicButton);
        languageButtonGroup.add(PersianButton);
        this.languageOptionsPanel = new JPanel(new FlowLayout());
        languageOptionsPanel.add(EnglishButton);
        languageOptionsPanel.add(FrenchButton);
        languageOptionsPanel.add(ArabicButton);
        languageOptionsPanel.add(PersianButton);
        languageOptionsPanel.setVisible(false);

        this.radioButtonList = new ArrayList<JRadioButton>();
        radioButtonList.add(EnglishButton);    //Index 00
        radioButtonList.add(FrenchButton);     //Index 01
        radioButtonList.add(ArabicButton);     //Index 02
        radioButtonList.add(PersianButton);    //Index 03




        bc = new BoardController(this,this.boardModel);

        //Button Handlers
        newGameButton.addActionListener(bc);
        purchaseButton.addActionListener(bc);
        quitButton.addActionListener(e -> System.exit(0));
        passButton.addActionListener(bc);
        helpButton.addActionListener(bc);
        purchaseHouseHotel.addActionListener(bc);
        rollButton.addActionListener(bc);
        startButton.addActionListener(bc);
        submitButton.addActionListener(bc);
        saveButton.addActionListener(bc);
        loadButton.addActionListener(bc);

        //radio button handlers
        EnglishButton.addActionListener(bc);
        FrenchButton.addActionListener(bc);
        ArabicButton.addActionListener(bc);
        PersianButton.addActionListener(bc);

        //create Player panels and add them top left, top right, bottom left and bottom right. Make panels not visible.
        int MaxNumPlayers = 4;
        this.playerPanels = new JPanel[MaxNumPlayers];
        for (int i=0; i<MaxNumPlayers; i++){
            createNewPlayerPanel(i);
        }
        panelHolder[0][0].add(playerPanels[0]);
        panelHolder[1][0].add(playerPanels[1]);
        panelHolder[0][2].add(playerPanels[2]);
        panelHolder[1][2].add(playerPanels[3]);
        this.setPlayerPanelHoldersVisibility(MaxNumPlayers,false);

        buttonPanel.add(startButton);
        //buttonPanel.add(newGameButton);
        buttonPanel.add(rollButton);
        buttonPanel.add(purchaseButton);
        buttonPanel.add(passButton);
        buttonPanel.add(quitButton);
        //buttonPanel.add(helpButton);
        buttonPanel.add(purchaseHouseHotel);
        buttonPanel.add(saveButton);
        buttonPanel.add(loadButton);
        panelHolder[0][1].add(buttonPanel);

        JPanel userPanel = new JPanel();
        userPanel.setLayout(new BoxLayout(userPanel, BoxLayout.Y_AXIS));
        JLabel headerLabel = new JLabel("Logs:");
        this.eventLabel = new JLabel("<html>Starts the game by pressing the Start Game button.<br>  <br> </html>");
        this.eventLabel2 = new JLabel(" ");
        this.eventLabel3 = new JLabel(" ");
        this.userInputBox = new JTextField();


        //Hiding buttons not needed at start of the game
        this.setUserInputVisibility(true);
        submitButton.setVisible(false);
        userInputBox.setVisible(false);
        purchaseHouseHotel.setVisible(false);
        saveButton.setVisible(false);


        //add event labels, submit button and user input box in userPanel and add that to bottom middle of control panel
        userPanel.add(headerLabel);
        userPanel.add(eventLabel);
        userPanel.add(eventLabel2);
        userPanel.add(eventLabel3);
        userPanel.add(userInputBox);
        userPanel.add(languageOptionsPanel);
        userPanel.add(languageOptionsPanel);
        userPanel.add(submitButton);
        panelHolder[1][1].add(userPanel);

    }


    /**
     * Closes the main frame and prompts the user with a confirmation message beforehand.
     */
    public void closeFrame() {

        frame.setDefaultCloseOperation(JFrame.DO_NOTHING_ON_CLOSE);
        frame.addWindowListener(new WindowAdapter() {
            @Override
            public void windowClosing(WindowEvent we) {
                if (JOptionPane.showConfirmDialog(frame, "Are you sure you want to quit the game?")
                        == JOptionPane.OK_OPTION) {
                    frame.setVisible(false);
                    frame.dispose();
                    System.exit(0);
                }
            }
        });
    }

    /**
     * Creates panels for player objects
     */
    private void createNewPlayerPanel(int playerIndex){
        int numOfLabels = 7;
        playerLabelList[playerIndex][0] = new JLabel("Name: ");
        playerLabelList[playerIndex][1] = new JLabel("Balance: ");
        playerLabelList[playerIndex][2] = new JLabel("Current location: ");
        playerLabelList[playerIndex][3] = new JLabel("In Jail Status = ");
        playerLabelList[playerIndex][4] = new JLabel("Bankrupt Status = ");
        playerLabelList[playerIndex][5] = new JLabel("Owns a Color set = ");
        playerLabelList[playerIndex][6] = new JLabel("<html>Owned Property:</html>");

        // scrollPanes for list of Property owned by each player
        PropertyTextAreas[playerIndex] = new JTextArea(4,1);
        PropertyTextAreas[playerIndex].setEditable(false); //make PropertyTextAreas uneditable by the user
        scrollPanes[playerIndex] = new JScrollPane(PropertyTextAreas[playerIndex]);

        playerPanels[playerIndex] = new JPanel();
        playerPanels[playerIndex].setLayout(new BoxLayout(playerPanels[playerIndex], BoxLayout.Y_AXIS));

        for (int k = 0; k < numOfLabels; k++) {
            playerPanels[playerIndex].add(playerLabelList[playerIndex][k]);
        }

        playerPanels[playerIndex].add(scrollPanes[playerIndex]);
    }

    /**
     * Update All Players Status
     */
    public void updateAllPlayersStatus(int numOfPlayers){
            String gameCurrency = boardModel.getBoard().getCurrency();
            for(int i =0; i < numOfPlayers; i++){
                Player currentPlayer = boardModel.getPlayer(i);
                playerLabelList[i][0].setText("Name: "+currentPlayer.getName());
                playerLabelList[i][1].setText("Balance: "+gameCurrency+currentPlayer.getBalance());
                playerLabelList[i][2].setText("Current location: "+ boardModel.getBoard().getProperty(currentPlayer.getPositon()).getName());
                playerLabelList[i][3].setText("In Jail Status = "+currentPlayer.getInJail());
                playerLabelList[i][4].setText("Bankrupt Status = "+currentPlayer.getBankruptStatus());
                playerLabelList[i][5].setText("Owns a color set = "+currentPlayer.getHasAColorSet());
                String controledProperty = "";
                for(int j = 0 ; j < currentPlayer.getControlledProperty().size(); j++){
                    controledProperty += ("- "+ currentPlayer.getControlledProperty().get(j).getName() + "\n");
                }
                PropertyTextAreas[i].setText(controledProperty);
            }
    }


    /**
     * Update Houses icon for a specific property
     * @param propertyLocation
     */
    public void updateHousesIcons(Integer propertyLocation){
        Property property = boardModel.getBoard().getProperty(propertyLocation);
        int propertyNumHouses = property.getNumHouses();
        int propertyNumHotels = property.getNumHotels();
        if((propertyNumHouses == 0) && (propertyNumHotels ==0)){
            housing1Labels[propertyLocation].setVisible(false);
            housing2Labels[propertyLocation].setVisible(false);
            housing3Labels[propertyLocation].setVisible(false);
            housing4Labels[propertyLocation].setVisible(false);
            housing5Labels[propertyLocation].setVisible(false);
        }
        else if(propertyNumHotels == 1){
            housing1Labels[propertyLocation].setVisible(false);
            housing2Labels[propertyLocation].setVisible(false);
            housing3Labels[propertyLocation].setVisible(false);
            housing4Labels[propertyLocation].setVisible(false);
            housing5Labels[propertyLocation].setVisible(true);
        }
        else{
            if(propertyNumHouses == 1){
                housing1Labels[propertyLocation].setVisible(true);
                housing2Labels[propertyLocation].setVisible(false);
                housing3Labels[propertyLocation].setVisible(false);
                housing4Labels[propertyLocation].setVisible(false);
                housing5Labels[propertyLocation].setVisible(false);
            }
            else if(propertyNumHouses == 2){
                housing1Labels[propertyLocation].setVisible(true);
                housing2Labels[propertyLocation].setVisible(true);
            }
            else if(propertyNumHouses == 3){
                housing1Labels[propertyLocation].setVisible(true);
                housing2Labels[propertyLocation].setVisible(true);
                housing3Labels[propertyLocation].setVisible(true);
            }
            else if(propertyNumHouses == 4){
                housing1Labels[propertyLocation].setVisible(true);
                housing2Labels[propertyLocation].setVisible(true);
                housing3Labels[propertyLocation].setVisible(true);
                housing4Labels[propertyLocation].setVisible(true);
            }
        }
    }

    /**
     * Updates all Property Houses Icons
     */
    public void updateAllHousesIcons(){
        ArrayList<Property> allProperty = boardModel.getBoard().getPropertyArrayList();
        for (int i = 0; i < allProperty.size(); i++) {
            updateHousesIcons(i);
        }
    }

    /**
     * displays the GUI of the game.
     */
    public void displayGUI(){
        Dimension size = Toolkit.getDefaultToolkit().getScreenSize();
        frame.setSize(size);
        frame.setVisible(true);
        closeFrame();
    }

    /**
     * Set the text of eventLabel and eventLabel2 JLabels to the paramters given
     * @param eventText text to set eventLabel with
     * @param eventText2 text to set eventLabel2 with
     */
    public void setEventLabelText(String eventText, String eventText2) {
        eventLabel.setText(eventText);
        eventLabel2.setText(eventText2);
    }

    /**
     * Set the text of eventLabel3 JLabel to the paramters given
     * @param eventText text to set eventLabel3 with
     */
    public void setEventLabel3Text(String eventText) {
        eventLabel3.setText(eventText);
    }

    /**
     * Getter method for buttonList
     * @return buttonList
     */
    public ArrayList<JButton> getButtonList(){return buttonList;}

    /**
     * Getter method for userInput text in the JTextField
     * @return the text the user typed in
     */
    public String getUserInput(){return userInputBox.getText();}

    public void setControllerInitializing(){
        bc.setPlayersInitialized();
    }

    /**
     * Set purchaseHouseHotel button visibility with parameter
     * @param visible true for visible, false for not visible
     */
    public void setPurchaseHouseButtonVisibility(Boolean visible){purchaseHouseHotel.setVisible(visible);}

    /**
     * Clear text in userInputBox JTextField
     */
    public void clearTextField(){userInputBox.setText("");}

    /**
     * Set user input related stuff to visible and buttons to not visible or vise versa.
     * @param condition true if userInputBox and submit visible, false if not visible.
     */
    public void setUserInputVisibility(Boolean condition){
        rollButton.setVisible(!condition);
        purchaseButton.setVisible(!condition);
        passButton.setVisible(!condition);
        saveButton.setVisible(!condition);
        submitButton.setVisible(condition);
        userInputBox.setVisible(condition);
    }

    /**
     * Sets user UserInputBox's visibility to false..
     */
    public void hideUserInputBox(){
        userInputBox.setVisible(false);
    }

    /**
     * Set visibilty of Player Panels
     * @param numOfPlayers Total number of players
     * @param visible true if visible, false if not visible.
     */
    public void setPlayerPanelHoldersVisibility(int numOfPlayers, Boolean visible){
        JPanel[] panelList = {panelHolder[0][0],panelHolder[1][0],panelHolder[0][2],panelHolder[1][2]};
        for (int i = 0; i < numOfPlayers; i++) {
            panelList[i].setVisible(visible);
        }
    }

    /**
     * Set visibility of languageOptionsPanel
     *
     * @param condition true if visible, false if not visible.
     */
    public void setLanguageOptionsPanelVisibility(boolean condition){
        if (condition) {
            languageOptionsPanel.setVisible(true);
        } else {
            languageOptionsPanel.setVisible(false);
        }
    }

    /**
     * Set all properties in the game to the latest board, boardModel is set with
     */
    public void setAllPropertys(){
        for (int i = 0; i < 40; i++) {
            Color propertyColor = boardModel.getBoard().getProperty(i).getColor();
            propertyPanels[i].setBackground(propertyColor);
            //creating and adding an index label to each propertyPanel to see their order
            JLabel propertyIndexLabel = new JLabel();
            propertyIndexLabel.setText(boardModel.getBoard().getPropertyArrayList().get(i).getName());
            propertyIndexLabel.setHorizontalAlignment(SwingConstants.CENTER);
            propertyIndexLabel.setVerticalAlignment(SwingConstants.CENTER);
            propertyPanels[i].add(propertyIndexLabel,BorderLayout.PAGE_START);
        }
    }

    /**
     * Method to change visibility of load button and start button and set max number of players in Board Controller
     * @param visible visibility to set with, true for visible
     * @param number number to set max_players in the game with
     */
    public void loadButtonSetup(Boolean visible, int number){
        startButton.setVisible(visible);
        loadButton.setVisible(visible);
        bc.setMax_players(number);
    }
    /**
     * Creates a popup window to announce the result of the game after the game ends.
     * The result can be a single winner, multiple winners in a tie, or no winners.
     */
    public void resultsMessageWindow() {
        String finalMessage = "";
        String winnersNames = "";
        String losersNames = "";
        ArrayList<Player> winners = boardModel.getWinnersList();
        ArrayList<Player> losers = boardModel.getLosersList();
        int numWinners = winners.size();
        int numLosers= losers.size();

        // if 1 or more players went bankrupt
        if (!losers.isEmpty()) {
            for (int i = 0; i < numLosers; i++) {
                losersNames = losersNames + losers.get(i).getName() + "\n";
            }
        }
        if (numWinners == 1){ // if a single winner
            finalMessage += winners.get(0).getName()
                    + " wins!\n"
                    + "\nBankrupt Players:\n";
            finalMessage += losersNames;
        }
        else if (numWinners > 1) { // if tie between more than one winner
            for (int j = 0; j < numWinners; j++) {
                winnersNames += winners.get(j).getName() + "\n";
            }
            finalMessage  = "All properties are purchased and the game ends in a tie between:\n" + winnersNames;

            if (numLosers > 0) {
                finalMessage += "\nBankrupt Players:\n" + losersNames;
            } else {
                finalMessage += "\nNo players went bankrupt!";
            }
        }
        int input = JOptionPane.showConfirmDialog(frame, finalMessage, "Game is over!", JOptionPane.DEFAULT_OPTION, JOptionPane.PLAIN_MESSAGE);
        if( input == JOptionPane.OK_OPTION) {
            frame.dispose();
            System.exit(0);
        }
    }
}
