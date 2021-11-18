import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;

public class BoardController implements ActionListener {

    private BoardView bv;

    private BoardModel bm;

    private Boolean playersInitialized;

    public BoardController (BoardView bv,BoardModel bm){
        this.bv=bv;
        this.bm=bm;
        this.playersInitialized = false;
    }

    @Override
    public void actionPerformed(ActionEvent actionEvent){
        ArrayList<JButton> buttons = bv.getButtonList();
        //start button
        if(actionEvent.getSource().equals(buttons.get(0))){
            if(!playersInitialized) {
                //TODO Implement option for AI Player
                String player1 = JOptionPane.showInputDialog("Enter the name of Player 1");
                String player2 = JOptionPane.showInputDialog("Enter the name of Player 2");
                String player3 = JOptionPane.showInputDialog("Enter the name of Player 3");
                String player4 = JOptionPane.showInputDialog("Enter the name of Player 4");

                Player newPlayer1 = new Player(player1, new Color(20, 20, 100), 1500, false);
                bm.addPlayer(newPlayer1);
                Player newPlayer2 = new Player(player2, new Color(100, 20, 20), 1500, false);
                bm.addPlayer(newPlayer2);
                Player newPlayer3 = new Player(player3, new Color(20, 100, 20), 1500, false);
                bm.addPlayer(newPlayer3);
                Player newPlayer4 = new Player(player4, new Color(100, 100, 0), 1500, false);
                bm.addPlayer(newPlayer4);
                bv.updateAllPlayersStatus(4);
                bm.setCurrentPlayer(newPlayer1);
                playersInitialized = true;
                bv.setEventLabelText(newPlayer1.getName() + " Goes first! Start your turn by pressing the roll button.","");
                buttons.get(1).setVisible(true);
                buttons.get(2).setVisible(true);
                buttons.get(3).setVisible(true);
                buttons.get(0).setVisible(false);

                for(int i=0 ; i<4; i++){
                    bv.getPlayerLists().get(i)[0].setVisible(true);
                }
            }
        }
        //roll button
        if(actionEvent.getSource().equals(buttons.get(1))){
            if(playersInitialized) {
                bm.operateCommand(BoardModel.Commands.roll);
                bv.updateAllPlayersStatus(4);
            }
        }
        //purchase property button
        if(actionEvent.getSource().equals(buttons.get(2))){
            if(playersInitialized) {
                bm.operateCommand(BoardModel.Commands.purchaseProperty);
                bv.updateAllPlayersStatus(4);
            }
        }
        //pass player turn button
        if(actionEvent.getSource().equals(buttons.get(3))){
            if(playersInitialized) {
                bm.operateCommand(BoardModel.Commands.passTurn);
                bv.updateAllPlayersStatus(4);
            }
        }
    }

}
