package pt.ulusofona.lp2.theWalkingDEISIGame;

import org.junit.Test;
import pt.ulusofona.lp2.theWalkingDEISIGame.classesCriaturas.*;

import java.io.File;

import static org.junit.Assert.assertEquals;

public class TestTWDGameManager {
    @Test
    public void testHumanoMove() {
        TWDGameManager game = new TWDGameManager();

        game.startGame( new File("./test-files/valoresWalkingDEISI.txt") );
        game.move( 3, 3, 4, 3 );
        assertEquals( 1 , game.getElementId(4, 3) );
    }

    @Test
    public void testHumanoAttackHumano() {
        TWDGameManager game = new TWDGameManager();

        game.startGame( new File("./test-files/valoresWalkingDEISI.txt") );
        game.move( 2, 2, 3, 2 );    //Humano apanha arma
        game.move(3, 4, 4, 4);
        assertEquals( false, game.move(3, 2, 3, 3) );   //Humano tenta atacar humano(false)
    }

    @Test
    public void testZombieMove() {
        TWDGameManager game = new TWDGameManager();

        game.startGame( new File("./test-files/valoresWalkingDEISI.txt") );
        game.move( 6, 3, 4, 3 );
        game.move(3,4,0,4); //zombie move
        assertEquals( 2,  game.getElementId(0, 4) );
    }

    @Test
    public void testZombieDestroyEquipment() {
        TWDGameManager game = new TWDGameManager();

        game.startGame( new File("./test-files/valoresWalkingDEISI.txt") );
        game.move( 3, 3, 4, 3 );
        game.move(3,4,2,3); //Zombie move to equipment
        game.move(4, 3, 4, 2);
        game.move(2, 3, 1, 3); //Zombie move off equipment
        assertEquals( 0,  game.getElementId(2, 3) );
    }

    @Test
    public void testZombieConvert() {
        TWDGameManager game = new TWDGameManager();

        game.startGame( new File("./test-files/valoresWalkingDEISI.txt") );
        game.move(2, 2, 2, 1);
        game.move(3, 4, 3, 3);  //Zombie ataca um humano e passa o seu tipo para 1 (convert)
        assertEquals( 1, game.getGameMap().getPosition(3, 3).getTipo() );
    }


    @Test
    public void testGetElementId() {
        TWDGameManager game = new TWDGameManager();

        game.startGame( new File("./test-files/valoresWalkingDEISI.txt") );
        assertEquals( 4, game.getElementId(2,2) );
    }

    @Test
    public void testVerificaCondicoes() {
        TWDGameManager game = new TWDGameManager();

        game.startGame( new File("./test-files/valoresWalkingDEISI.txt") );
        Creature creatureTested = new AdultoHumano(3,"Diogo Brando", 2, 2);
        assertEquals( false, game.verificaCondicoes(2,2, 5, 5, creatureTested) );
    }

    @Test
    public void testIncrementaTempo() {
        TWDGameManager game = new TWDGameManager();

        game.startGame( new File("./test-files/valoresWalkingDEISI.txt") );
        game.incrementaTempo();
        game.incrementaTempo();
        game.incrementaTempo();
        assertEquals( 3, game.getNumberOfTurnsTotal() );
    }

    @Test
    public void testIsDay() {
        TWDGameManager game = new TWDGameManager();

        game.startGame( new File("./test-files/valoresWalkingDEISI.txt") );
        game.incrementaTempo();
        game.incrementaTempo();
        assertEquals( false, game.isDay() );
    }

    @Test
    public void testMoveIntoSafeHaven() {
        TWDGameManager game = new TWDGameManager();

        game.startGame( new File("./test-files/valoresWalkingDEISI.txt") );
        game.move(6, 3, 7, 4);

        int numCreaturesInSafeHaven = 0;
        for (Creature creatureFound: game.getCreatures()) {
            if ( creatureFound instanceof Humano ) {
                if ( ((Humano) creatureFound).getInsideSafeHaven() ) {
                    numCreaturesInSafeHaven++;
                }
            }
        }
        assertEquals( 1, numCreaturesInSafeHaven );
    }

}
