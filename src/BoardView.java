import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.util.ArrayList;

/**
 * class BoardView implements the graphical user interface's view for the board game Monopoly.
 *
 * @author Mahtab Ameli, Imran Latif, Muhammad Furqan
 * @version 1.0
 */
public class BoardView {

    private BoardModel boardModel;


    private JLabel[][] playerLabelList;
    // scrollPanes for list of properties owned by each player
    private JScrollPane[] scrollPanes;
    private JTextArea[] propertiesTextAreas;

    private JLabel eventLabel;

    private JLabel eventLabel2;

    private JLabel eventLabel3;

    private JButton startButton;

    private int MAX_PLAYERS;

    private JPanel mainPanel;

    private JPanel[][] panelHolder;

    private JPanel buttonPanel;

    private JTextField userInputBox;

    private JButton submitButton;


    private ArrayList<JLabel []> playerLists;

    private JLabel [] player1Labels;
    private JLabel [] player2Labels;
    private JLabel [] player3Labels;
    private JLabel [] player4Labels;

    private JLabel [] housing1Labels;
    private JLabel [] housing2Labels;
    private JLabel [] housing3Labels;
    private JLabel [] housing4Labels;
    private JLabel [] housing5Labels;


    /**
     * main frame for the GUI's View.
     */
    private final JFrame frame;

    /**
     * an array of JPanels, one for each Property (40 in total).
     */
    private JPanel [] propertyPanels;

    /**
     * an array of JPanels for each Player (4 total).
     */
    private JPanel[] playerPanels;

    /**
     * this panel contains the board's propertyPanels.
     */
    private JPanel gamePanel;

    /**
     * this panel contains all control buttons and the message windows that appear by pressing them.
     */
    private JPanel controlPanel;


    /**
     * user can press this button to roll dice.
     */
    private JButton rollButton;

    /**
     * user can press this button to purchase a property if applicable.
     */
    private JButton purchaseButton;
    /**
     * USer can press this button to see the rules and user guide.
     */
    private JButton helpButton;

    /**
     * user can press this button to start a new game.
     */
    private JButton newGameButton;

    /**
     * user can press this button to quit the game.
     */
    private JButton quitButton;

    /**
     * User can press this to buy a house on a property
     */
    private JButton purchaseHouseHotel;

    /**
     * user can press this button to pass the turn to the next player.
     */
    private JButton passButton;

    /**
     * Panels within the gamePanel.
     */
    private JPanel north, east, west, south, center;

    /**
     * List of all the buttons in boardView.
     */
    private ArrayList<JButton> buttonList;


    /**
     * constructor for the class.
     *
     */
    public BoardView(BoardModel boardModel) {
        this.frame = new JFrame("Welcome to G28's Monopoly!");
        this.frame.setResizable(true);
        this.boardModel = boardModel;
        this.MAX_PLAYERS = 4;
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
     * Creates the mainPanel of the GUI frame. Adds gamePanel and controlPanel to the mainPanel.
     *
     */
    private void addBasePanels() {
         //mainPanel placed on the frame. Contains gamePanel and controlPanel.
        mainPanel = new JPanel(new BorderLayout());
        createGamePanel();
        mainPanel.add(gamePanel, BorderLayout.WEST);
        frame.add(mainPanel);
    }

    /**
     *   //TODO
     * @return
     */
    public ArrayList<JLabel[]> getPlayerLists(){return playerLists;}

    /**
     * This helper function is used to create all the labels and properties on the grid.
     * @param direction The edge of the board we are creating labels on.
     * @param i Which property i we are adding.
     * @param propertyIndexLabels The list we are adding the labels to.
     */
    private void createPropertyPanelHelper(JPanel direction, int i, JLabel [] propertyIndexLabels){
        propertyPanels[i] = new JPanel(new BorderLayout());
        //setting a different background color for each propertyPanel to visually set them apart from each other for now
        propertyPanels[i].setBackground(boardModel.getBoard().getProperty(i).getColor());
        //creating and adding an index label to each propertyPanel to see their order
        propertyIndexLabels[i] = new JLabel();
        propertyIndexLabels[i].setText(boardModel.getBoard().getPropertiesArrayList().get(i).getName());
        propertyIndexLabels[i].setHorizontalAlignment(SwingConstants.CENTER);
        propertyIndexLabels[i].setVerticalAlignment(SwingConstants.CENTER);
        propertyPanels[i].add(propertyIndexLabels[i],BorderLayout.PAGE_START);


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
        playerPanel.setBackground(boardModel.getBoard().getProperty(i).getColor());



        JPanel housingPanel = new JPanel(new GridLayout(1,4));
        housing1Labels[i] = new JLabel(new PlayerIcon(10,10,new Color(108, 222, 27)));
        housing2Labels[i] = new JLabel(new PlayerIcon(10,10,new Color(108, 222, 27)));
        housing3Labels[i] = new JLabel(new PlayerIcon(10,10,new Color(108, 222, 27)));
        housing4Labels[i] = new JLabel(new PlayerIcon(10,10,new Color(108, 222, 27)));
        housing5Labels[i] = new JLabel(new PlayerIcon(10,10,new Color(213, 6, 6, 255)));
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
        housingPanel.setBackground(boardModel.getBoard().getProperty(i).getColor());

        propertyPanels[i].add(housingPanel, BorderLayout.CENTER);
        propertyPanels[i].add(playerPanel, BorderLayout.PAGE_END);


        direction.add(propertyPanels[i]);
    }



    /**
     * Creates 40 propertyPanels and adds them to gamePanel.
     *
     */
    private void createPropertyPanels() {
        // Create 40 panels, one for each property
        this.propertyPanels = new JPanel[40];

        //Create 40 JLabels, one for each propertyPanel
        JLabel [] propertyIndexLabels = new JLabel[40];

        for (int i = 0; i < 40; i++) {
            // Properties 0 to 10 are added to the south panel
            if (i < 11) {
                createPropertyPanelHelper(south, i, propertyIndexLabels);
            }
            // Properties 11 to 19 are added to the west panel.
            else if (i < 20) {
                createPropertyPanelHelper(west, 30 - i, propertyIndexLabels);
            }
            // Properties 20 to 30 are added to the north panel.
            else if (i < 31) {
                createPropertyPanelHelper(north, i, propertyIndexLabels);
            }
            // Properties 31 to 39 are added to the east panel.
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
        north.setPreferredSize(new Dimension(800,150));
        north.setComponentOrientation(ComponentOrientation.LEFT_TO_RIGHT);

        east.setBackground(new Color(100,140,10));
        east.setPreferredSize(new Dimension(150, 500));
        east.setComponentOrientation(ComponentOrientation.LEFT_TO_RIGHT);

        west.setBackground(new Color(0,200,150));
        west.setPreferredSize(new Dimension(150, 500));
        west.setComponentOrientation(ComponentOrientation.RIGHT_TO_LEFT);

        south.setBackground(new Color(250,120,200));
        south.setPreferredSize(new Dimension(800,150));
        south.setComponentOrientation(ComponentOrientation.RIGHT_TO_LEFT);

        center.setBackground(new Color(190,250,250));
        int numOfLabels = 6;
        this.playerLabelList = new JLabel[numOfLabels][6];
        // TODO CHANGE SIZE LATER
        this.scrollPanes = new JScrollPane[4];
        this.propertiesTextAreas = new JTextArea[4];


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
        controlPanelSize.setSize(controlPanelSize.getWidth()*0.8,controlPanelSize.getHeight()*0.5);
        controlPanel.setPreferredSize(controlPanelSize);
        controlPanel.setBackground(new Color(215, 200, 131, 255));

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

        /** BoardController initialization and actionListener for butttons */
        this.buttonList = new ArrayList<>();
        buttonList.add(startButton);     //Index 0
        buttonList.add(rollButton);      //Index 1
        buttonList.add(purchaseButton);  //Index 2
        buttonList.add(passButton);      //Index 3
        buttonList.add(quitButton);      //Index 4
        buttonList.add(purchaseHouseHotel);//Index 5
        buttonList.add(submitButton);    //Index 6

        BoardController bc = new BoardController(this,this.boardModel);

        //Button Handlers
        newGameButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                //Ask how many players?

                //for amount of players, ask name

                //Show what player starts first    show P.x = {boat, car,  dog, boot, penguin, hat}

                //Set middle board of player info V(player)
            }
        });

        purchaseButton.addActionListener(bc);

        quitButton.addActionListener(e -> System.exit(0));

        passButton.addActionListener(bc);

        helpButton.addActionListener(bc);

        purchaseHouseHotel.addActionListener(bc);

        rollButton.addActionListener(bc);

        startButton.addActionListener(bc);

        submitButton.addActionListener(bc);

        int totalNumPlayers = 4;
        this.playerPanels = new JPanel[totalNumPlayers];
        for (int i=0; i<totalNumPlayers; i++){
            createNewPlayerPanel(i);
        }
        panelHolder[0][0].add(playerPanels[0]);
        panelHolder[1][0].add(playerPanels[1]);
        panelHolder[0][2].add(playerPanels[2]);
        panelHolder[1][2].add(playerPanels[3]);

        buttonPanel.add(startButton);
        //buttonPanel.add(newGameButton);
        buttonPanel.add(rollButton);
        buttonPanel.add(purchaseButton);
        buttonPanel.add(passButton);
        buttonPanel.add(quitButton);
        //buttonPanel.add(helpButton);
        buttonPanel.add(purchaseHouseHotel);   //To do, not implemented
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

        userPanel.add(headerLabel);
        userPanel.add(eventLabel);
        userPanel.add(eventLabel2);
        userPanel.add(eventLabel3);
        userPanel.add(userInputBox);
        userPanel.add(submitButton);   //To do, not implemented
        panelHolder[1][1].add(userPanel);

    }


    /**
     * Closes the main frame and prompts the user with a confirmation message beforehand.
     */
    private void closeFrame() {

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
        int numOfLabels = 6;
        playerLabelList[playerIndex][0] = new JLabel("Name: ");
        playerLabelList[playerIndex][1] = new JLabel("Balance: $");
        playerLabelList[playerIndex][2] = new JLabel("Current location: ");
        playerLabelList[playerIndex][3] = new JLabel("In Jail Status = ");
        playerLabelList[playerIndex][4] = new JLabel("Bankrupt Status = ");
        playerLabelList[playerIndex][5] = new JLabel("<html>Owned Properties:</html>");

        // scrollPanes for list of properties owned by each player
        propertiesTextAreas[playerIndex] = new JTextArea(4,1);
        propertiesTextAreas[playerIndex].setEditable(false); //make propertiesTextAreas uneditable by the user
        scrollPanes[playerIndex] = new JScrollPane(propertiesTextAreas[playerIndex]);

        playerPanels[playerIndex] = new JPanel();
        playerPanels[playerIndex].setLayout(new BoxLayout(playerPanels[playerIndex], BoxLayout.Y_AXIS));

        //todo finish sccrollpane
        // todo move later?

        for (int k = 0; k < numOfLabels; k++) {
            playerPanels[playerIndex].add(playerLabelList[playerIndex][k]);
        }

        playerPanels[playerIndex].add(scrollPanes[playerIndex]);
    }

    /**
     * Update All Players Status
     */
    public void updateAllPlayersStatus(int numOfPlayers){
            for(int i =0; i < numOfPlayers; i++){
                Player currentPlayer = boardModel.getPlayer(i);
                playerLabelList[i][0].setText("Name: "+currentPlayer.getName());
                playerLabelList[i][1].setText("Balance: $"+currentPlayer.getBalance());
                playerLabelList[i][2].setText("Current location: "+ boardModel.getBoard().getProperty(currentPlayer.getPositon()).getName());
                playerLabelList[i][3].setText("In Jail Status = "+currentPlayer.getInJail());
                playerLabelList[i][4].setText("Bankrupt Status = "+currentPlayer.getBankruptStatus());
                String controledProperties = "";
                for(int j = 0 ; j < currentPlayer.getControlledProperties().size(); j++){
                    //controledProperties += ("- "+ currentPlayer.getControlledProperties().get(j).getName() + "<br>");
                    controledProperties += ("- "+ currentPlayer.getControlledProperties().get(j).getName() + "\n");

                }

                //todo get rid of playerLabelList[i][5] later?
                //playerLabelList[i][5].setText("<html> Owned Properties: "+controledProperties+"</html>");
                propertiesTextAreas[i].setText(controledProperties);

            }

    }

    /**
     * Update Houses icon for a specific property
     * @param propertyLocation
     */
    public void updateHousesIcons(Integer propertyLocation){
        Properties property = boardModel.getBoard().getProperty(propertyLocation);
        int propertyNumHouses = property.getNumHouses();
        int propertyNumHotels = property.getNumHotels();
        if(propertyNumHotels == 1){
            housing1Labels[propertyLocation].setVisible(false);
            housing2Labels[propertyLocation].setVisible(false);
            housing3Labels[propertyLocation].setVisible(false);
            housing4Labels[propertyLocation].setVisible(false);
            housing5Labels[propertyLocation].setVisible(true);
        }
        else{
            if(propertyNumHouses == 1){
                housing1Labels[propertyLocation].setVisible(true);
            }
            else if(propertyNumHouses == 2){
                housing2Labels[propertyLocation].setVisible(true);
            }
            else if(propertyNumHouses == 3){
                housing3Labels[propertyLocation].setVisible(true);
            }
            else if(propertyNumHouses == 4){
                housing4Labels[propertyLocation].setVisible(true);
            }
        }
    }

    /**
     * displays the GUI of the game.
     */
    public void displayGUI(){
        Dimension size = Toolkit.getDefaultToolkit().getScreenSize();
        frame.setSize(size);
        this.frame.setVisible(true);
        closeFrame();
    }

    public void setEventLabelText(String eventText, String eventText2) {
        eventLabel.setText(eventText);
        eventLabel2.setText(eventText2);
    }

    public void setEventLabel3Text(String eventText) {
        eventLabel3.setText(eventText);
    }

    public ArrayList<JButton> getButtonList(){return buttonList;}

    public String getUserInput(){return userInputBox.getText();}

    public void setTextFieldVisibility(Boolean visible){userInputBox.setVisible(visible);}

    public void clearTextField(){userInputBox.setText("");}

    public void setUserInputVisibility(Boolean condition){
        buttonList.get(1).setVisible(!condition);
        buttonList.get(2).setVisible(!condition);
        buttonList.get(3).setVisible(!condition);
        buttonList.get(5).setVisible(!condition);
        buttonList.get(6).setVisible(condition);
        userInputBox.setVisible(condition);
    }

}
