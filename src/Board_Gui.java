import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;


/**
 * class Board_View models the graphical user interface for the board game Monopoly.
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
     * an array of JPanels, one for each Property.
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
     * constructor for the class.
     *
     */
    public Board_Gui() {
        this.frame = new JFrame("Welcome to G28's Monopoly!");
        this.frame.setVisible(true);
        addBasePanels();
    }

    /**
     * Creates the mainPanel of the GUI frame. Adds gamePanel and controlPanel to the mainPanel.
     *
     */
    private void addBasePanels() {
        // mainPanel placed on the frame. Contains gamePanel and controlPanel.
        JPanel mainPanel = new JPanel(new GridLayout());

        createGamePanel();
        createControlPanel();

        mainPanel.add(gamePanel);
        mainPanel.add(controlPanel);

        frame.add(mainPanel);
        frame.revalidate();
    }

    /**
     * Creates 41 propertyPanels and adds them to gamePanel.
     *
     */
    private void createPropertyPanels() {
        // Create 41 panels, one for each property
        // and one for the large centre panel which does not include properties
        this.propertyPanels = new JPanel[41];

        //Create 41 JLabels, one for each propertyPanel
        JLabel [] propertyIndexLabels = new JLabel[41];

        for (int i = 0; i < 41;i++) {

            propertyPanels[i] = new JPanel(new BorderLayout());

            // todo fix later
            //setting a different background color for each propertyPanel to visually set them apart from each other for now
            propertyPanels[i].setBackground(new Color(255 - (4*i), 255 - (4*i), 255 - (4*i)));

            //creating and adding an index label to each propertyPanel to see their order
            propertyIndexLabels[i] = new JLabel();
            propertyIndexLabels[i].setText("" + i);
            propertyPanels[i].add(propertyIndexLabels[i]);

            gamePanel.add(propertyPanels[i]);
        }
    }

    /** creates gamePanel which contains all propertyPanels
     *
     */
    private void createGamePanel() {
        // westPanel contains the board's properties
        gamePanel = new JPanel(new GridLayout(8,5));
        //todo fix size so that gamePanel is always a square with same side length as
        // controlPanel's height
        gamePanel.setSize(new Dimension(1000, 1000));
        gamePanel.setBackground(new Color(255, 255, 255));
        createPropertyPanels();
    }



    /** creates controlPanel, which contains all controls and buttons
     * including roll dice, purchase property, and quit game
     * todo: add a button that shows the players' states
     *       and other necessary buttons
     *
     */
    private void createControlPanel() {
        // controlPanel contains the buttons for rolling dice, purchasing property, and quiting the game.
        // The message windows that appear as a result of pressing these buttons will also appear on eastPanel
        controlPanel = new JPanel(new FlowLayout());
        //todo fix size so that controlPanel in narrower than gamePanel but has same height. size currently incorrect.
        controlPanel.setSize(new Dimension(200, 400));
        controlPanel.setBackground(new Color(161, 210, 208, 255));

        // buttons for Roll, purchase Property, and Quit Game
        // todo add more buttons later
        newGameButton = new JButton("New Game");
        rollButton = new JButton("Roll Dice");
        purchaseButton = new JButton("Purchase Property");
        quitButton = new JButton("Quit Game");

        //todo handle buttons later
        controlPanel.add(newGameButton);
        controlPanel.add(rollButton);
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
        frame.setSize(new Dimension(800, 400));   //set size of the main frame
        closeFrame();
    }

}
