import static org.junit.jupiter.api.Assertions.*;
import org.junit.Test;
import org.junit.Assert.*;
import org.junit.Test;

import java.awt.*;
import java.security.spec.PKCS8EncodedKeySpec;

public class GameTest {
    Game game;

    @Test
    public void testRollCase1(){
        game = new Game();
        Player p1 = new Player("P1", new Color(10,10,10),1500);
        game.addPlayer(p1);
        game.setCurrentPlayer(p1);
        game.roll();
        assertNotEquals( 0,p1.getPositon());
    }

    @Test
    public void testOperateCommandRoll(){
        game = new Game();
        Player p1 = new Player("P1", new Color(10,10,10),1500);
        game.addPlayer(p1);
        game.setCurrentPlayer(p1);
        game.operateCommand(Game.Commands.roll);
        assertNotEquals( 0,p1.getPositon());
    }

}
