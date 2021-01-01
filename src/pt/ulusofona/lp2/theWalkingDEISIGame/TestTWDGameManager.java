package pt.ulusofona.lp2.theWalkingDEISIGame;

import org.junit.Test;
import pt.ulusofona.lp2.theWalkingDEISIGame.classesCriaturas.*;
import org.junit.jupiter.api.*;
import java.io.File;
import org.junit.jupiter.*;

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
    public void testHumanoAttackZombieWithWeapon() {
        TWDGameManager game = new TWDGameManager();

        game.startGame( new File("./test-files/valoresWalkingDEISI.txt") );
        game.move( 3, 3, 3, 2 );    //Humano apanha arma
        game.move(0, 1, 0, 0);
        game.move(3, 2, 3, 4);     //Humano ataca zombie
        assertEquals( 1, game.getElementId(3, 4) ); //(Move para la)
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
    public void testZombieAttackHumanThatHasOffensiveWeapon() {
        TWDGameManager game = new TWDGameManager();

        game.startGame( new File("./test-files/valoresWalkingDEISI.txt") );
        game.move( 3, 3, 3, 2 );    //Humano apanha arma
        game.move(3, 4, 3, 2);      //Zombie ataca humano
        assertEquals( 0, game.getElementId(3, 4) ); //Zombie morre e desaparece
    }

    @Test
    public void testVampireZombieAttackDog() {
        TWDGameManager game = new TWDGameManager();

        game.startGame( new File("./test-files/valoresWalkingDEISI.txt") );
        game.move( 7, 1, 6, 2 );
        game.move(3, 4, 5, 2);
        game.move(3, 3, 3, 2);
        assertEquals( false, game.move(5, 2, 6, 2) ); //Zombie tenta mover para cao
    }

    @Test
    public void testZombieConvert() {
        TWDGameManager game = new TWDGameManager();

        game.startGame( new File("./test-files/valoresWalkingDEISI.txt") );
        game.move(2, 2, 2, 1);
        game.move(3, 4, 3, 3);  //Zombie ataca um humano e passa o seu tipo para 1 (convert)
        assertEquals( 1, game.getGameMap().getPosition(3,3).getCreature().getTipo() );
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

    @Test
    public void testCaoMoveLinearly() {
        TWDGameManager game = new TWDGameManager();

        game.startGame( new File("./test-files/valoresWalkingDEISI.txt") );
        game.move(7, 1, 6, 1);
        assertEquals( 0, game.getElementId(6,1) );
    }

    @Test
    public void testCaoMoveDiagonally() {
        TWDGameManager game = new TWDGameManager();

        game.startGame( new File("./test-files/valoresWalkingDEISI.txt") );
        game.move(7, 1, 6, 2);
        assertEquals( 5, game.getElementId(6,2) );
    }

    @Test
    public void testCaoApanhaEquipamento() {
        TWDGameManager game = new TWDGameManager();

        game.startGame( new File("./test-files/valoresWalkingDEISITestCaoApanhaEquipamento.txt") );
        game.move(0, 0, 1, 1);  //Cao apanha equipamento

        Creature creatureFound = game.getGameMap().getPosition(1, 1).getCreature();

        if ( creatureFound instanceof Cao) {
            assertEquals( -1, ((Cao) creatureFound).getEquipamentoApanhado().getId() );
        }
    }

    @Test
    public void testCriancaComEspadaAttackCriancaZombie() {
        TWDGameManager game = new TWDGameManager();

        game.startGame( new File("./test-files/valoresWalkingDEISITestCriancaAttack.txt") );
        game.move(0, 1, 0, 0);  //Criança apanha espada
        game.move(1, 1, 2, 1);
        game.move(0, 0, 1, 0);
        assertEquals( 1, game.getElementId(1,0) );
    }

    @Test
    public void testCriancaComEspadaDefendeCriancaZombie() {
        TWDGameManager game = new TWDGameManager();

        game.startGame( new File("./test-files/valoresWalkingDEISITestCriancaAttack.txt") );
        game.move(0, 1, 0, 0);  //Criança apanha espada
        game.move(1, 0, 0, 0);  //Criança zombie ataca criança
        assertEquals( 0, game.getElementId(1,0) );
    }

    @Test
    public void testCriancaComEspadaAtacaOutroTipoZombie() {
        TWDGameManager game = new TWDGameManager();

        game.startGame( new File("./test-files/valoresWalkingDEISITestCriancaAttack.txt") );
        game.move(0, 1, 0, 0);  //Criança apanha espada
        game.move(1, 1, 0, 1);
        game.move(0, 0, 0, 1);  //Criança ataca adulto zombie
        //Tipo 1 é igual a crianca zombie, tipo 5 é igual a crianca humana (logo foi convertida)
        assertEquals( 1, game.getGameMap().getPosition(0,0).getTipo() );
    }

    @Test
    public void testCriancaComEspadaDefendeOutroTipoZombie() {
        TWDGameManager game = new TWDGameManager();

        game.startGame( new File("./test-files/valoresWalkingDEISITestCriancaAttack.txt") );
        game.move(0, 1, 0, 0);  //Criança apanha espada
        game.move(1, 1, 0, 0);  //Adulto Zombie ataca criança
        //Tipo 1 é igual a crianca zombie, tipo 5 é igual a crianca humana (logo foi convertida)
        assertEquals( 1, game.getGameMap().getPosition(0,0).getTipo() );
    }

    @Test
    public void testSabemApanharEquipamento() {
        TWDGameManager game = new TWDGameManager();

        game.startGame( new File("./test-files/valoresWalkingDEISITestOutrosApanharEquipamento.txt") );
        game.move(1, 0, 1, 1);  //Criança apanha escudo madeira
        game.move(6, 6, 5, 6);  //Zombie serve como buffer
        game.move(2, 0, 2, 1);  //Adulto apanha pistola
        game.move(5, 6, 6, 6);
        game.move(4, 0, 4, 1);  //Idoso apanha revista maria
        game.move(6, 6, 5, 6);
        game.move(3, 0, 3, 1);  //Militar apanha Escudo Policial
        game.move(5, 6, 6, 6);
        game.move(5, 0, 6, 1);  //Cão apanha cabeça alho

        //Percorrer as posições e ter a certeza que apanharam
        boolean apanharamTudo = true;
        int deuFalseAqui = 0;
        if ( game.getElementId(1, 1) != 1) {
            deuFalseAqui = 1;
            apanharamTudo = false;
        }
        if ( game.getElementId(2, 1) != 2) {
            deuFalseAqui = 2;
            apanharamTudo = false;
        }
        if ( game.getElementId(3, 1) != 3) {
            deuFalseAqui = 3;
            apanharamTudo = false;
        }
        if ( game.getElementId(4, 1) != 4) {
            deuFalseAqui = 4;
            apanharamTudo = false;
        }
        if ( game.getElementId(6, 1) != 5) {
            deuFalseAqui = 5;
            apanharamTudo = false;
        }

        //System.out.println(deuFalseAqui);
        //Tipo 1 é igual a crianca zombie, tipo 5 é igual a crianca humana (logo foi convertida)
        assertEquals( true, apanharamTudo );
    }

    @Test
    public void testDefendBeskarHelmet() {
        TWDGameManager game = new TWDGameManager();

        game.startGame( new File("./test-files/valoresWalkingDEISITestBeskarHelmet.txt") );
        game.move(0, 1, 0, 0);  //Adulto apanha Beskar Helmet
        game.move(1, 0, 0, 0);  //Adulto Zombie ataca adulto com helmet
        assertEquals( 0, game.getElementId(1, 0));  //Zombie morre a atacar
    }

    @Test
    public void testAttackBeskarHelmet() {
        TWDGameManager game = new TWDGameManager();

        game.startGame( new File("./test-files/valoresWalkingDEISITestBeskarHelmet.txt") );
        game.move(0, 1, 0, 0);  //Adulto apanha Beskar Helmet
        game.move(1, 0, 0, 0);  //Adulto Zombie ataca adulto com helmet
        game.move(0, 0, 1, 1); //Humano ataca zombie adulto
        assertEquals( 1, game.getElementId(1, 1));  //Humano mata zombie
    }

    @Test
    public void testApanhaVenenoEZombieAtaca() {
        TWDGameManager game = new TWDGameManager();

        game.startGame( new File("./test-files/valoresWalkingDEISITestVeneno.txt") );
        game.move(0, 1, 0, 0);  //Adulto apanha veneno
        game.move(1, 0, 0, 0); //Zombie ataca humano
        assertEquals( 1, game.getElementId(0, 0) ); //Humano continua la
    }

    @Test
    public void testApanhaVenenoENaoChegaAntidotoATempo() {
        TWDGameManager game = new TWDGameManager();

        game.startGame( new File("./test-files/valoresWalkingDEISITestVeneno.txt") );
        game.move(0, 1, 0, 0);  //Adulto apanha veneno
        game.move(1, 0, 0, 0);  //Zombie ataca humano (veneno defende)
        game.move(0, 0, 0, 1); //Humano tenta mover não para cima de antidoto
        assertEquals( 0, game.getElementId(0, 1));  //Humano morre envenenado
    }

    @Test
    public void testApanhaVenenoEChegaAntidotoATempo() {
        TWDGameManager game = new TWDGameManager();

        game.startGame( new File("./test-files/valoresWalkingDEISITestVeneno.txt") );
        game.move(0, 1, 0, 0);  //Adulto apanha veneno
        game.move(1, 0, 0, 0);  //Zombie ataca humano (veneno defende)
        game.move(0, 0, 1, 1); //Humano move para cima de antidoto
        assertEquals( 1, game.getElementId(1, 1));  //Humano não morre envenenado
    }

    @Test
    public void testHumanoUsaGarrafaLixiviaParaDefender3Vezes() {
        TWDGameManager game = new TWDGameManager();

        game.startGame( new File("./test-files/valoresWalkingDEISITestGarrafaLixivia.txt") );
        game.move(0, 1, 0, 0);  //Humano apanha Lixivia
        game.move(1, 0, 0, 0);  //Zombie ataca humano (lixiva defende)
        game.move(0, 0, 0, 1); //Humano move para cima
        game.move(1, 1, 0, 1);  //Zombie ataca humano (lixivia defende)
        game.move(0, 1, 0, 0);  //Humano move para baixo
        game.move(1, 0, 0, 0);  //Zombie ataca humano (lixivia defende)
        assertEquals( 5,game.getGameMap().getPosition(0,0).getCreature().getTipo());  //Tipo 5 é criança humana
    }

    public void testHumanoUsaGarrafaLixiviaParaDefender4Vezes() {
        TWDGameManager game = new TWDGameManager();

        game.startGame( new File("./test-files/valoresWalkingDEISITestGarrafaLixivia.txt") );
        game.move(0, 1, 0, 0);  //Humano apanha Lixivia
        game.move(1, 0, 0, 0);  //Zombie ataca humano (lixiva defende)
        game.move(0, 0, 0, 1); //Humano move para cima
        game.move(1, 1, 0, 1);  //Zombie ataca humano (lixivia defende)
        game.move(0, 1, 0, 0);  //Humano move para baixo
        game.move(1, 0, 0, 0);  //Zombie ataca humano (lixivia defende)
        game.move(0, 0, 0, 1); //Humano move para cima
        game.move(1, 1, 0, 1);  //Zombie ataca humano (lixivia nao defende)
        assertEquals( 1, game.getGameMap().getPosition(0,1).getCreature().getTipo());  //Tipo 5 é criança humana
    }
}
