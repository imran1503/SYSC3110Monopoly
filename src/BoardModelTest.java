
import org.junit.Test;
import static org.junit.Assert.*;
import java.awt.*;



public class BoardModelTest {
    BoardModel boardModel;
    Player p1;

    @Test
    public void testAddPlayer() {
        boardModel = new BoardModel("Save Files/board.xml");
        BoardView boardView = new BoardView(boardModel);
        boardModel.setBoardView(boardView);
        for (int i = 0; i < 5; i++) {
            Player p = new Player("P" + (i+1) , new Color(10,10,10),1500, false);
            boardModel.addPlayer(p);
        }
        assertEquals(5, boardModel.players.size());
    }

    // No player is bankrupt. CheckNumActivePlayers must return true.
    @Test
    public void testCheckNumOfActivePlayers1() {
        boardModel = new BoardModel("Save Files/board.xml");
        BoardView boardView = new BoardView(boardModel);
        boardModel.setBoardView(boardView);
        for (int i = 0; i < 5; i++) {
            Player p = new Player("P" + (i+1) , new Color(10,10,10),1500, false);
            boardModel.addPlayer(p);
            boardModel.setCurrentPlayer(p);
        }
        assertEquals(true, boardModel.checkNumOfActivePlayers());
    }

    // 1 player is bankrupt. CheckNumActivePlayers must return true.
    @Test
    public void testCheckNumOfActivePlayers2() {
        boardModel = new BoardModel("Save Files/board.xml");
        BoardView boardView = new BoardView(boardModel);
        boardModel.setBoardView(boardView);
        for (int i = 0; i < 5; i++) {
            Player p = new Player("P" + (i+1) , new Color(10,10,10),1500, false);
            boardModel.addPlayer(p);
            boardModel.setCurrentPlayer(p);
        }
        Player pBankrupt  = new Player("PBankrupt", new Color(10,10,10), 1500, false);
        pBankrupt.setBankruptStatus(true);
        boardModel.addPlayer(pBankrupt);
        boardModel.setCurrentPlayer(pBankrupt);
        assertEquals(true, boardModel.checkNumOfActivePlayers());
    }


    // All but one players (the winner) are bankrupt. CheckNumActivePlayers must return false.
    @Test
    public void testCheckNumOfActivePlayers3() {
        boardModel = new BoardModel("Save Files/board.xml");
        BoardView boardView = new BoardView(boardModel);
        boardModel.setBoardView(boardView);
        for (int i = 0; i < 5; i++) {
            Player p = new Player("P" + (i+1) , new Color(10,10,10),1500, false);
            p.setBankruptStatus(true);
            boardModel.addPlayer(p);
            boardModel.setCurrentPlayer(p);
        }
        Player pNotBankrupt  = new Player("PNotBankrupt", new Color(10,10,10),1500, false);
        boardModel.addPlayer(pNotBankrupt);
        boardModel.setCurrentPlayer(pNotBankrupt);
        pNotBankrupt.setBankruptStatus(false);
        assertEquals(false, boardModel.checkNumOfActivePlayers());
    }


    // testing if player moves forward on the board after rolling
    @Test
    public void testRoll1(){
        boardModel = new BoardModel("Save Files/board.xml");
        BoardView boardView = new BoardView(boardModel);
        boardModel.setBoardView(boardView);
        p1 = new Player("P1", new Color(10,10,10),1500, false);
        p1.setInJail(false);
        boardModel.addPlayer(p1);
        boardModel.setCurrentPlayer(p1);
        boardModel.roll();
        assertNotEquals( 0,p1.getPositon());
    }

    //test case for when player is not in jail and rolls a double. roll() must return true.
    @Test
    public void testRollCase2(){
        boardModel = new BoardModel("Save Files/board.xml");
        BoardView boardView = new BoardView(boardModel);
        boardModel.setBoardView(boardView);
        p1 = new Player("P1", new Color(10,10,10),1500, false);
        boardModel.setCurrentPlayer(p1);
        p1.setInJail(false);
        boardModel.addPlayer(p1);

        Boolean actualValue = boardModel.roll();
        while(boardModel.getDiceValue1() != boardModel.getDiceValue2()){
            actualValue = boardModel.roll();
        }
        assertEquals( true, actualValue);
    }

    //test case for when player is in jail and rolls. Player should not move on the board and stay at Jail (position 10)
    @Test
    public void testRoll3(){
        boardModel = new BoardModel("Save Files/board.xml");
        BoardView boardView = new BoardView(boardModel);
        boardModel.setBoardView(boardView);
        p1 = new Player("P1", new Color(10,10,10),1500, false);
        boardModel.setCurrentPlayer(p1);
        p1.setPosition(10);
        p1.setInJail(true);
        boardModel.addPlayer(p1);
        boardModel.roll();

        assertEquals( 10, p1.getPositon());
    }


    @Test
    public void testOperateCommandRoll(){
        boardModel = new BoardModel("Save Files/board.xml");
        BoardView boardView = new BoardView(boardModel);
        boardModel.setBoardView(boardView);
        p1 = new Player("P1", new Color(10,10,10),1500, false);
        boardModel.addPlayer(p1);
        boardModel.setCurrentPlayer(p1);

        boardModel.operateCommand(BoardModel.Commands.roll);
        assertNotEquals( 0,p1.getPositon());
    }

    // Test operateCommand pass turn when next player (p2) is Not Bankrupt
    @Test
    public void testOperateCommandPassTurn1(){
        boardModel = new BoardModel("Save Files/board.xml");
        BoardView boardView = new BoardView(boardModel);
        boardModel.setBoardView(boardView);
        p1 = new Player("P1", new Color(10,10,10),1500, false);
        boardModel.addPlayer(p1);
        boardModel.setCurrentPlayer(p1);

        Player p2 = new Player("P2", new Color(20,20,20),1500, false);
        boardModel.operateCommand(BoardModel.Commands.roll);
        boardModel.addPlayer(p2);
        boardModel.operateCommand(BoardModel.Commands.passTurn);
        assertEquals(p2, boardModel.getCurrentPlayer());
    }

    // Test operateCommand pass turn when next player (p2) is Bankrupt
    @Test
    public void testOperateCommandPassTurn2(){
        boardModel = new BoardModel("Save Files/board.xml");
        BoardView boardView = new BoardView(boardModel);
        boardModel.setBoardView(boardView);
        p1 = new Player("P1", new Color(10,10,10),1500, false);
        boardModel.addPlayer(p1);
        boardModel.setCurrentPlayer(p1);

        Player p2 = new Player("P2", new Color(20,20,20),1500, false);
        p2.setBankruptStatus(true);
        boardModel.addPlayer(p2);
        Player p3 = new Player("P3", new Color(10,10,10),1500, false);
        boardModel.addPlayer(p3);

        // p1 rolls , passes turn, p2 is bankrupt so p3 is next current player.
        boardModel.operateCommand(BoardModel.Commands.roll);
        boardModel.operateCommand(BoardModel.Commands.passTurn);
        assertEquals(p3, boardModel.getCurrentPlayer());
    }

    @Test
    public void testPurchaseProperty(){
        boardModel = new BoardModel("Save Files/board.xml");
        BoardView boardView = new BoardView(boardModel);
        boardModel.setBoardView(boardView);
        p1 = new Player("P1", new Color(10,10,10),1500, false);
        boardModel.setCurrentPlayer(p1);
        p1.setPosition(6);
        boardModel.purchaseProperty();
        Boolean actualValue = (p1.getControlledProperty().get(0).getName().equals("Oriental Avenue")) &&(p1.getBalance() == 1400);
        assertEquals(true,actualValue);
    }

    @Test
    public void testOperateCommandPurchaseProperty(){
        boardModel = new BoardModel("Save Files/board.xml");
        BoardView boardView = new BoardView(boardModel);
        boardModel.setBoardView(boardView);
        p1 = new Player("P1", new Color(10,10,10),1500, false);
        boardModel.setCurrentPlayer(p1);
        p1.setPosition(6);
        boardModel.operateCommand(BoardModel.Commands.purchaseProperty);
        Boolean actualValue = (p1.getControlledProperty().get(0).getName().equals("Oriental Avenue")) &&(p1.getBalance() == 1400);
        assertEquals(true,actualValue);
    }

    // Test purchaseHouseOrHotel command when player does not have color set (so no houses bought)
    @Test
    public void testPurchaseHouseOrHotel1(){
        boardModel = new BoardModel("Save Files/board.xml");
        BoardView boardView = new BoardView(boardModel);
        boardModel.setBoardView(boardView);
        p1 = new Player("P1", new Color(10,10,10),1500, false);
        boardModel.setCurrentPlayer(p1);
        p1.setPosition(1);
        boardModel.purchaseProperty();
        boardModel.purchaseHouseOrHotel(boardModel.getBoard().getProperty(1));
        assertEquals(0, boardModel.getBoard().getProperty(1).getNumHouses());
    }

    // Test purchaseHouseOrHotel command when player does have color set (so 1 house bought)
    @Test
    public void testPurchaseHouseOrHotel2(){
        boardModel = new BoardModel("Save Files/board.xml");
        BoardView boardView = new BoardView(boardModel);
        boardModel.setBoardView(boardView);
        p1 = new Player("P1", new Color(10,10,10),1500, false);
        boardModel.setCurrentPlayer(p1);
        p1.setPosition(1);
        boardModel.purchaseProperty();
        p1.setPosition(3);
        boardModel.purchaseProperty();
        boardModel.purchaseHouseOrHotel(boardModel.getBoard().getProperty(1));
        assertEquals(1, boardModel.getBoard().getProperty(1).getNumHouses());
    }

    // Tests if adding house to a property works correctly
    @Test
    public void testCheckAddHouse(){
        boardModel = new BoardModel("Save Files/board.xml");
        p1 = new Player("P1", new Color(10,10,10),1500, false);
        Property prop1 = new Property("Oriental", new Color(135,206,250), 6);
        Property prop2 = new Property("Vermont", new Color(135,206,250), 8);
        Property prop3 = new Property("Connecticut", new Color(135,206,250), 9);

        prop1.setOwner(p1);
        prop2.setOwner(p1);
        prop3.setOwner(p1);

        p1.setHasAColorSet(true);

        prop1.setNumHouses(1);
        assertEquals(1, prop1.getNumHouses());
        assertEquals(0, prop1.getNumHotels());
    }

    // Tests if adding a hotel to a property works correctly
    @Test
    public void testCheckAddHotel(){
        boardModel = new BoardModel("Save Files/board.xml");
        p1 = new Player("P1", new Color(10,10,10),1500, false);
        Property prop1 = new Property("Oriental", new Color(135,206,250), 6);
        Property prop2 = new Property("Vermont", new Color(135,206,250), 8);
        Property prop3 = new Property("Connecticut", new Color(135,206,250), 9);

        prop1.setOwner(p1);
        prop2.setOwner(p1);
        prop3.setOwner(p1);

        p1.setHasAColorSet(true);

        prop1.setNumHotels(1);

        assertEquals(0, prop1.getNumHouses());
        assertEquals(1, prop1.getNumHotels());
    }

    // Test if AI players are correctly added
    @Test
    public void testAddAiPlayer() {
        boardModel = new BoardModel("Save Files/board.xml");
        BoardView boardView = new BoardView(boardModel);
        boardModel.setBoardView(boardView);
        Board board = new Board("Save Files/board.xml");
        for (int i = 0; i < 5; i++) {
            AIPlayer p = new AIPlayer("P" + (i+1) , new Color(10,10,10),1500, 5,board,  boardModel, boardView);
            boardModel.addPlayer(p);
        }
        assertEquals(5, boardModel.players.size());
    }

}
