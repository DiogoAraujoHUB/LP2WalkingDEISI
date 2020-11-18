package pt.ulusofona.lp2.theWalkingDEISIGame;


import org.junit.jupiter.api.*;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;
import java.io.File;

public class Testes {

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
        game.move( 3, 3, 2, 3 );
        game.move(0,0,0,0); //zombie move
        assertEquals( game.getElementId(4, 3), 3 );
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
        assertEquals( game.verificaCondicoes(2,2, 5, 5), false );
    }
}