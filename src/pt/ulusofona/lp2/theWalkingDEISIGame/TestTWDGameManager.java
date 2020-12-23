package pt.ulusofona.lp2.theWalkingDEISIGame;

import org.junit.Test;
import pt.ulusofona.lp2.theWalkingDEISIGame.classesCriaturas.*;

import java.io.File;

import static org.junit.Assert.assertEquals;

public class TestTWDGameManager {
    @Test
    public void testMove() {
        TWDGameManager game = new TWDGameManager();

        game.startGame( new File("valoresWalkingDEISI.txt") );
        game.move( 3, 3, 4, 3 );
        assertEquals( game.getElementId(4, 3), 0 );
    }

    @Test
    public void testZombieMove() {
        TWDGameManager game = new TWDGameManager();

        game.startGame( new File("valoresWalkingDEISI.txt") );
        game.move( 5, 4, 4, 4 );
        game.move(3,3,3,4); //zombie move
        assertEquals( game.getElementId(3, 4), 0 );
    }

    @Test
    public void testGetElementId() {
        TWDGameManager game = new TWDGameManager();

        game.startGame( new File("valoresWalkingDEISI.txt") );
        assertEquals( game.getElementId(2,2), 0 );
    }

    @Test
    public void testVerificaCondicoes() {
        TWDGameManager game = new TWDGameManager();

        game.startGame( new File("valoresWalkingDEISI.txt") );
        Creature creatureTested = new AdultoHumano(3, 2, "Diogo Brando", 2, 2);
        assertEquals( game.verificaCondicoes(2,2, 5, 5, creatureTested), false );
    }

    @Test
    public void testIncrementaTempo() {
        TWDGameManager game = new TWDGameManager();

        game.startGame( new File("valoresWalkingDEISI.txt") );
        game.incrementaTempo();
        assertEquals( game.isDay(), true );
    }

}
