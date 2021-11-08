import static org.junit.jupiter.api.Assertions.*;
import org.junit.Test;
import org.junit.Assert.*;
import org.junit.Test;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.testng.annotations.BeforeTest;


import java.awt.*;
import java.util.ArrayList;
import java.util.Scanner;


public class GameTest {
    Game game;
    Player p1;

// todo this BeforeEach doesn't work. Also tried @BeforeAll. didn't work.

/*    @BeforeEach
    public void init(){
        game = new Game();
        p1 = new Player("P1", new Color(10,10,10),1500);
        game.addPlayer(p1);
        game.setCurrentPlayer(p1);
    }*/


    @Test
    public void testAddPlayer() {
        game = new Game();
        for (int i = 0; i < 5; i++) {
            Player p = new Player("P" + (i+1) , new Color(10,10,10),1500);
            game.addPlayer(p);
        }
        assertEquals(5, game.players.size());
    }

    // No player is bankrupt. CheckNumActivePlayers must return true.
    @Test
    public void testCheckNumOfActivePlayersCase2() {
        game = new Game();
        for (int i = 0; i < 5; i++) {
            Player p = new Player("P" + (i+1) , new Color(10,10,10),1500);
            game.addPlayer(p);
            game.setCurrentPlayer(p);
        }
        assertEquals(true, game.checkNumOfActivePlayers());
    }

    // 1 player is bankrupt. CheckNumActivePlayers must return true.
    @Test
    public void testCheckNumOfActivePlayersCase1() {
        game = new Game();
        for (int i = 0; i < 5; i++) {
            Player p = new Player("P" + (i+1) , new Color(10,10,10),1500);
            game.addPlayer(p);
            game.setCurrentPlayer(p);
        }
        Player pBankrupt  = new Player("PBankrupt", new Color(10,10,10), 1500);
        pBankrupt.setBankruptStatus(true);
        game.addPlayer(pBankrupt);
        game.setCurrentPlayer(pBankrupt);
        assertEquals(true, game.checkNumOfActivePlayers());
    }


    // All but one players (the winner) are bankrupt. CheckNumActivePlayers must return false.
    @Test
    public void testCheckNumOfActivePlayersCase3() {
        game = new Game();
        for (int i = 0; i < 5; i++) {
            Player p = new Player("P" + (i+1) , new Color(10,10,10),1500);
            p.setBankruptStatus(true);
            game.addPlayer(p);
            game.setCurrentPlayer(p);
        }
        Player pNotBankrupt  = new Player("PNotBankrupt", new Color(10,10,10), 1500);
        game.addPlayer(pNotBankrupt);
        game.setCurrentPlayer(pNotBankrupt);
        pNotBankrupt.setBankruptStatus(false);
        assertEquals(false, game.checkNumOfActivePlayers());
    }


    // testing if player moves forward on the board after rolling
    @Test
    public void testRollCase1(){
        game = new Game();
        p1 = new Player("P1", new Color(10,10,10),1500);
        p1.setInJail(false);
        game.addPlayer(p1);
        game.setCurrentPlayer(p1);
        game.roll();
        assertNotEquals( 0,p1.getPositon());
    }

    //test case for when player is not in jail and rolls a double. roll() must return true.
    @Test
    public void testRollCase2(){
        game = new Game();
        p1 = new Player("P1", new Color(10,10,10),1500);
        game.setCurrentPlayer(p1);
        p1.setInJail(false);
        game.addPlayer(p1);

        game.setDiceValue1(2);
        game.setDiceValue2(2);

        Boolean actualValue = game.roll();
        while(game.getDiceValue1() != game.getDiceValue2()){
            actualValue = game.roll();
        }
        assertEquals( true, actualValue);
    }

    //test case for when player is in jail and rolls a double. roll() must return false.
    @Test
    public void testRollCase3(){
        game = new Game();
        p1 = new Player("P1", new Color(10,10,10),1500);
        game.setCurrentPlayer(p1);
        p1.setInJail(true);
        game.addPlayer(p1);

        game.setDiceValue1(2);
        game.setDiceValue2(2);

        Boolean actualValue = game.roll();
        while(game.getDiceValue1() != game.getDiceValue2()){
            actualValue = game.roll();
        }
        assertEquals( false, actualValue);
    }

    @Test
    public void testOperateCommandRoll(){
        game = new Game();
        p1 = new Player("P1", new Color(10,10,10),1500);
        game.addPlayer(p1);
        game.setCurrentPlayer(p1);

        game.operateCommand(Game.Commands.roll);
        assertNotEquals( 0,p1.getPositon());
    }

    @Test
    public void testOperateCommandPassTurn(){
        game = new Game();
        p1 = new Player("P1", new Color(10,10,10),1500);
        game.addPlayer(p1);
        game.setCurrentPlayer(p1);

        Player p2 = new Player("P2", new Color(20,20,20),1500);
        game.operateCommand(Game.Commands.roll);
        game.addPlayer(p2);
        game.operateCommand(Game.Commands.passTurn);
        assertEquals(p2,game.getCurrentPlayer());
    }


    @Test
    public void testPurchaseProperty(){
        game = new Game();
        p1 = new Player("P1", new Color(10,10,10),1500);
        game.setCurrentPlayer(p1);
        p1.setPosition(6);
        game.operateCommand(Game.Commands.purchaseProperty);
        Boolean actualValue = (p1.getControlledProperties().get(0).getName().equals("Oriental Avenue")) &&(p1.getBalance() == 1400);
        assertEquals(true,actualValue);

    }

}
