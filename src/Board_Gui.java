import javax.swing.*;
import java.awt.*;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.util.concurrent.Flow;


/**
 * class Board_Gui implements the graphical user interface for the board game Monopoly.
 *
 * @author Mahtab Ameli
 * @version 1.0
 */
public class Board_Gui {

    /**
     * main frame for the GUI.
     */
    private final JFrame frame;

    /**
     * an array of JPanels, one for each Property (40 in total).
     */
    private JPanel [] propertyPanels;

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
     * user can press this button to start a new game.
     */
    private JButton newGameButton;

    /**
     * user can press this button to quit the game.
     */
    private JButton quitButton;

    /**
     * Panels within the gamePanel.
     */
    private JPanel north, east, west, south, center;


    /**
     * constructor for the class.
     *
     */
    public Board_Gui() {
        this.frame = new JFrame("Welcome to G28's Monopoly!");
        this.frame.setVisible(true);
        this.frame.setResizable(true);
        addBasePanels();
    }

    /**
     * Creates the mainPanel of the GUI frame. Adds gamePanel and controlPanel to the mainPanel.
     *
     */
    private void addBasePanels() {
         //mainPanel placed on the frame. Contains gamePanel and controlPanel.
        JPanel mainPanel = new JPanel(new BorderLayout());
        mainPanel.setSize(new Dimension(1100, 800));

        createGamePanel();
        createControlPanel();

        mainPanel.add(gamePanel, BorderLayout.WEST);
        mainPanel.add(controlPanel, BorderLayout.EAST);

        frame.add(mainPanel);
        frame.revalidate();
    }

    /**
     * Creates 40 propertyPanels and adds them to gamePanel.
     *
     */
    private void createPropertyPanels() {
        // Create 40 panels, one for each property
        // and one for the large centre panel which does not include properties
        this.propertyPanels = new JPanel[40];

        //Create 41 JLabels, one for each propertyPanel
        JLabel [] propertyIndexLabels = new JLabel[40];

        //todo: the code is heavily duplicated. Will fix later.
        for (int i = 0; i < 40;i++) {

            // Properties 0 to 10 are added to the south panel
            if (i < 11) {
                propertyPanels[i] = new JPanel(new BorderLayout());
                // todo fix later
                //setting a different background color for each propertyPanel to visually set them apart from each other for now
                propertyPanels[i].setBackground(new Color(255 - (4*i), 255 - (4*i), 255 - (4*i)));

                //creating and adding an index label to each propertyPanel to see their order
                propertyIndexLabels[i] = new JLabel();
                propertyIndexLabels[i].setText("" + i);
                propertyPanels[i].add(propertyIndexLabels[i]);
                south.add(propertyPanels[i]);

            }
            // Properties 11 to 19 are added to the west panel.
            else if (i < 20) {
                propertyPanels[i] = new JPanel(new BorderLayout());
                // todo fix later
                //setting a different background color for each propertyPanel to visually set them apart from each other for now
                propertyPanels[i].setBackground(new Color(255 - (4*i), 255 - (4*i), 255 - (4*i)));

                //creating and adding an index label to each propertyPanel to see their order
                propertyIndexLabels[i] = new JLabel();
                propertyIndexLabels[i].setText("" + i);
                propertyPanels[i].add(propertyIndexLabels[i]);
                west.add(propertyPanels[i]);

            }
            // Properties 20 to 30 are added to the north panel.
            else if (i < 31) {
                propertyPanels[i] = new JPanel(new BorderLayout());
                // todo fix later
                //setting a different background color for each propertyPanel to visually set them apart from each other for now
                propertyPanels[i].setBackground(new Color(255 - (4*i), 255 - (4*i), 255 - (4*i)));

                //creating and adding an index label to each propertyPanel to see their order
                propertyIndexLabels[i] = new JLabel();
                propertyIndexLabels[i].setText("" + i);
                propertyPanels[i].add(propertyIndexLabels[i]);
                north.add(propertyPanels[i]);

            }
            // Properties 31 to 39 are added to the east panel.
            else {
                propertyPanels[i] = new JPanel(new BorderLayout());
                // todo fix later
                //setting a different background color for each propertyPanel to visually set them apart from each other for now
                propertyPanels[i].setBackground(new Color(255 - (4*i), 255 - (4*i), 255 - (4*i)));

                //creating and adding an index label to each propertyPanel to see their order
                propertyIndexLabels[i] = new JLabel();
                propertyIndexLabels[i].setText("" + i);
                propertyPanels[i].add(propertyIndexLabels[i]);
                east.add(propertyPanels[i]);

            }

        }
    }

    /** creates gamePanel which contains all propertyPanels
     *
     */
    private void createGamePanel() {
        // gamePanel contains the board's properties

        gamePanel = new JPanel(new BorderLayout());


        //todo fix size so that gamePanel is always a square with same side length as
        // controlPanel's height
        gamePanel.setPreferredSize(new Dimension(800, 800));
        gamePanel.setBackground(new Color(255, 255, 255));

        //gamePanel is made oof three panels: top, center, and bottom
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
        // todo: fix orientation of propertyPanels in the west panel
        west.setComponentOrientation(ComponentOrientation.RIGHT_TO_LEFT);

        south.setBackground(new Color(250,120,200));
        south.setPreferredSize(new Dimension(800,150));
        south.setComponentOrientation(ComponentOrientation.RIGHT_TO_LEFT);

        center.setBackground(new Color(190,250,250));
        rollButton = new JButton("Roll Dice");
        center.add(rollButton);


        gamePanel.add(north, BorderLayout.NORTH);
        gamePanel.add(east, BorderLayout.EAST);
        gamePanel.add(west, BorderLayout.WEST);
        gamePanel.add(south, BorderLayout.SOUTH);
        gamePanel.add(center, BorderLayout.CENTER);

        createPropertyPanels();

    }


    /** creates controlPanel, which contains all controls and buttons
     * including purchase property, and quit game
     * todo: add a button that shows the players' states
     *       and other necessary buttons
     *
     */
    private void createControlPanel() {
        // controlPanel contains the buttons for purchasing property, and quiting the game.
        // The message windows that appear as a result of pressing these buttons will also appear on eastPanel
        controlPanel = new JPanel(new FlowLayout());
        //todo fix size so that controlPanel in narrower than gamePanel but has same height. size currently incorrect.
        controlPanel.setPreferredSize(new Dimension(300, 800));
        controlPanel.setBackground(new Color(215, 200, 131, 255));

        // buttons for New Game, purchase Property, and Quit Game
        // todo add more buttons later
        newGameButton = new JButton("New Game");
        purchaseButton = new JButton("Purchase Property");
        quitButton = new JButton("Quit Game");

        //todo handle buttons later
        controlPanel.add(newGameButton);
        controlPanel.add(purchaseButton);
        controlPanel.add(quitButton);


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
     * displays the GUI of the game.
     */
    public void displayGUI(){
        // todo set size so that the board is always a square and
        //  arrangement of propertyPanels remain the same regardless of size
        frame.setSize(new Dimension(1100, 800));   //set size of the main frame
        closeFrame();
    }

}
