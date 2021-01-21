package pt.ulusofona.lp2.theWalkingDEISIGame;

import org.junit.Test;
import pt.ulusofona.lp2.theWalkingDEISIGame.classesCriaturas.*;
import java.io.File;
import java.util.List;
import java.util.Map;

import static org.junit.Assert.assertEquals;

public class TestTWDGameManager {
    @Test
    public void testHumanoMove() {
        TWDGameManager game = new TWDGameManager();

        try {
            game.startGame( new File("./test-files/valoresWalkingDEISI.txt") );
            game.move( 3, 3, 4, 3 );
            assertEquals( 1 , game.getElementId(4, 3) );
        } catch ( Exception e ) {
            System.out.println("File contained error -> " + e.getMessage() );
        }
    }

    @Test
    public void testHumanoAttackHumano() {
        TWDGameManager game = new TWDGameManager();

        try {
            game.startGame(new File("./test-files/valoresWalkingDEISI.txt"));
            game.move(2, 2, 3, 2);    //Humano apanha arma
            game.move(3, 4, 4, 4);
            assertEquals(false, game.move(3, 2, 3, 3));   //Humano tenta atacar humano(false)
        } catch ( Exception e ) {
            System.out.println("File contained error -> " + e.getMessage() );
        }
    }

    @Test
    public void testHumanoAttackZombieWithWeapon() {
        TWDGameManager game = new TWDGameManager();

        try {
            game.startGame(new File("./test-files/valoresWalkingDEISI.txt"));
            game.move(3, 3, 3, 2);    //Humano apanha arma
            game.move(0, 1, 0, 0);
            game.move(3, 2, 3, 4);     //Humano ataca zombie
            assertEquals(1, game.getElementId(3, 4)); //(Move para la)
        } catch ( Exception e ) {
            System.out.println("File contained error -> " + e.getMessage() );
        }
    }

    @Test
    public void testZombieMove() {
        TWDGameManager game = new TWDGameManager();

        try {
            game.startGame(new File("./test-files/valoresWalkingDEISI.txt"));
            game.move(6, 3, 4, 3);
            game.move(3, 4, 0, 4); //zombie move
            assertEquals(2, game.getElementId(0, 4));
        } catch ( Exception e ) {
            System.out.println("File contained error -> " + e.getMessage() );
        }
    }

    @Test
    public void testZombieDestroyEquipment() {
        TWDGameManager game = new TWDGameManager();

        try {
            game.startGame(new File("./test-files/valoresWalkingDEISI.txt"));
            game.move(3, 3, 4, 3);
            game.move(3, 4, 2, 3); //Zombie move to equipment
            game.move(4, 3, 4, 2);
            game.move(2, 3, 1, 3); //Zombie move off equipment
            assertEquals(0, game.getElementId(2, 3));
        } catch ( Exception e ) {
            System.out.println("File contained error -> " + e.getMessage() );
        }
    }

    @Test
    public void testZombieAttackHumanThatHasOffensiveWeapon() {
        TWDGameManager game = new TWDGameManager();

        try {
            game.startGame(new File("./test-files/valoresWalkingDEISI.txt"));
            game.move(3, 3, 3, 2);    //Humano apanha arma
            game.move(3, 4, 3, 2);      //Zombie ataca humano
            assertEquals(0, game.getElementId(3, 4)); //Zombie morre e desaparece
        } catch ( Exception e ) {
            System.out.println("File contained error -> " + e.getMessage() );
        }
    }

    @Test
    public void testVampireZombieAttackDog() {
        TWDGameManager game = new TWDGameManager();

        try {
            game.startGame(new File("./test-files/valoresWalkingDEISI.txt"));
            game.move(7, 1, 6, 2);
            game.move(3, 4, 5, 2);
            game.move(3, 3, 3, 2);
            assertEquals(false, game.move(5, 2, 6, 2)); //Zombie tenta mover para cao
        } catch ( Exception e ) {
            System.out.println("File contained error -> " + e.getMessage() );
        }
    }

    @Test
    public void testZombieConvert() {
        TWDGameManager game = new TWDGameManager();

        try {
            game.startGame(new File("./test-files/valoresWalkingDEISI.txt"));
            game.move(2, 2, 2, 1);
            game.move(3, 4, 3, 3);  //Zombie ataca um humano e passa o seu tipo para 1 (convert)
            assertEquals(1, game.getGameMap().getPosition(3, 3).getCreature().getTipo());
        } catch ( Exception e ) {
            System.out.println("File contained error -> " + e.getMessage() );
        }
    }


    @Test
    public void testGetElementId() {
        TWDGameManager game = new TWDGameManager();

        try {
            game.startGame(new File("./test-files/valoresWalkingDEISI.txt"));
            assertEquals(4, game.getElementId(2, 2));
        } catch ( Exception e ) {
            System.out.println("File contained error -> " + e.getMessage() );
        }
    }

    @Test
    public void testVerificaCondicoes() {
        TWDGameManager game = new TWDGameManager();

        try {
            game.startGame(new File("./test-files/valoresWalkingDEISI.txt"));
            Creature creatureTested = new AdultoHumano(3, "Diogo Brando", 2, 2);
            assertEquals(false, game.verificaCondicoes(2, 2, 5, 5, creatureTested));
        } catch ( Exception e ) {
            System.out.println("File contained error -> " + e.getMessage() );
        }
    }

    @Test
    public void testIncrementaTempo() {
        TWDGameManager game = new TWDGameManager();

        try {
            game.startGame(new File("./test-files/valoresWalkingDEISI.txt"));
            game.incrementaTempo();
            game.incrementaTempo();
            game.incrementaTempo();
            assertEquals(3, game.getNumberOfTurnsTotal());
        } catch ( Exception e ) {
            System.out.println("File contained error -> " + e.getMessage() );
        }
    }

    @Test
    public void testIsDay() {
        TWDGameManager game = new TWDGameManager();

        try {
            game.startGame(new File("./test-files/valoresWalkingDEISI.txt"));
            game.incrementaTempo();
            game.incrementaTempo();
            assertEquals(false, game.isDay());
        } catch ( Exception e ) {
            System.out.println("File contained error -> " + e.getMessage() );
        }
    }

    @Test
    public void testMoveIntoSafeHaven() {
        TWDGameManager game = new TWDGameManager();

        try {
            game.startGame(new File("./test-files/valoresWalkingDEISI.txt"));
            game.move(6, 3, 7, 4);

            int numCreaturesInSafeHaven = 0;
            for (Creature creatureFound : game.getCreatures()) {
                if (creatureFound instanceof Humano) {
                    if (((Humano) creatureFound).getInsideSafeHaven()) {
                        numCreaturesInSafeHaven++;
                    }
                }
            }
            assertEquals(1, numCreaturesInSafeHaven);
        } catch ( Exception e ) {
            System.out.println("File contained error -> " + e.getMessage() );
        }
    }

    @Test
    public void testMoveIntoSafeHavenWithZombie() {
        TWDGameManager game = new TWDGameManager();

        try {
            game.startGame(new File("./test-files/valoresWalkingDEISITestSafeHavens.txt"));
            //Não conseguimos mover zombie para safe haven
            assertEquals(false, game.move(2, 2, 2, 1));   //Zombie tenta mover para safe haven
        } catch ( Exception e ) {
            System.out.println("File contained error -> " + e.getMessage() );
        }
    }

    @Test
    public void testCaoMoveLinearly() {
        TWDGameManager game = new TWDGameManager();

        try {
            game.startGame(new File("./test-files/valoresWalkingDEISI.txt"));
            game.move(7, 1, 6, 1);
            assertEquals(0, game.getElementId(6, 1));
        } catch ( Exception e ) {
            System.out.println("File contained error -> " + e.getMessage() );
        }
    }

    @Test
    public void testCaoMoveDiagonally() {
        TWDGameManager game = new TWDGameManager();

        try {
            game.startGame(new File("./test-files/valoresWalkingDEISI.txt"));
            game.move(7, 1, 6, 2);
            assertEquals(5, game.getElementId(6, 2));
        } catch ( Exception e ) {
            System.out.println("File contained error -> " + e.getMessage() );
        }
    }

    @Test
    public void testCaoApanhaEquipamento() {
        TWDGameManager game = new TWDGameManager();

        try {
            game.startGame(new File("./test-files/valoresWalkingDEISITestCaoApanhaEquipamento.txt"));
            game.move(0, 0, 1, 1);  //Cao apanha equipamento

            Creature creatureFound = game.getGameMap().getPosition(1, 1).getCreature();

            if (creatureFound instanceof Cao) {
                assertEquals(-1, ((Cao) creatureFound).getEquipamentoApanhado().getId());
            }
        } catch ( Exception e ) {
            System.out.println("File contained error -> " + e.getMessage() );
        }
    }

    @Test
    public void testCriancaComEspadaAttackCriancaZombie() {
        TWDGameManager game = new TWDGameManager();

        try {
            game.startGame(new File("./test-files/valoresWalkingDEISITestCriancaAttack.txt"));
            game.move(0, 1, 0, 0);  //Criança apanha espada
            game.move(1, 1, 2, 1);
            game.move(0, 0, 1, 0);
            assertEquals(1, game.getElementId(1, 0));
        } catch ( Exception e ) {
            System.out.println("File contained error -> " + e.getMessage() );
        }
    }

    @Test
    public void testCriancaComEspadaDefendeCriancaZombie() {
        TWDGameManager game = new TWDGameManager();

        try {
            game.startGame(new File("./test-files/valoresWalkingDEISITestCriancaAttack.txt"));
            game.move(0, 1, 0, 0);  //Criança apanha espada
            game.move(1, 0, 0, 0);  //Criança zombie ataca criança
            assertEquals(0, game.getElementId(1, 0));
        } catch ( Exception e ) {
            System.out.println("File contained error -> " + e.getMessage() );
        }
    }

    @Test
    public void testCriancaComEspadaAtacaOutroTipoZombie() {
        TWDGameManager game = new TWDGameManager();

        try {
            game.startGame(new File("./test-files/valoresWalkingDEISITestCriancaAttack.txt"));
            game.move(0, 1, 0, 0);  //Criança apanha espada
            game.move(1, 1, 0, 1);
            game.move(0, 0, 0, 1);  //Criança ataca adulto zombie
            //Tipo 1 é igual a crianca zombie, tipo 5 é igual a crianca humana (logo foi convertida)
            assertEquals(1, game.getGameMap().getPosition(0, 0).getTipo());
        } catch ( Exception e ) {
            System.out.println("File contained error -> " + e.getMessage() );
        }
    }

    @Test
    public void testCriancaComEspadaDefendeOutroTipoZombie() {
        TWDGameManager game = new TWDGameManager();

        try {
            game.startGame(new File("./test-files/valoresWalkingDEISITestCriancaAttack.txt"));
            game.move(0, 1, 0, 0);  //Criança apanha espada
            game.move(1, 1, 0, 0);  //Adulto Zombie ataca criança
            //Tipo 1 é igual a crianca zombie, tipo 5 é igual a crianca humana (logo foi convertida)
            assertEquals(1, game.getGameMap().getPosition(0, 0).getTipo());
        } catch ( Exception e ) {
            System.out.println("File contained error -> " + e.getMessage() );
        }
    }

    @Test
    public void testSabemApanharEquipamento() {
        TWDGameManager game = new TWDGameManager();

        try {
            game.startGame(new File("./test-files/valoresWalkingDEISITestOutrosApanharEquipamento.txt"));
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
            if (game.getElementId(1, 1) != 1) {
                deuFalseAqui = 1;
                apanharamTudo = false;
            }
            if (game.getElementId(2, 1) != 2) {
                deuFalseAqui = 2;
                apanharamTudo = false;
            }
            if (game.getElementId(3, 1) != 3) {
                deuFalseAqui = 3;
                apanharamTudo = false;
            }
            if (game.getElementId(4, 1) != 4) {
                deuFalseAqui = 4;
                apanharamTudo = false;
            }
            if (game.getElementId(6, 1) != 5) {
                deuFalseAqui = 5;
                apanharamTudo = false;
            }

            //System.out.println(deuFalseAqui);
            //Tipo 1 é igual a crianca zombie, tipo 5 é igual a crianca humana (logo foi convertida)
            assertEquals(true, apanharamTudo);
        } catch ( Exception e ) {
            System.out.println("File contained error -> " + e.getMessage() );
        }
    }

    @Test
    public void testDefendBeskarHelmet() {
        TWDGameManager game = new TWDGameManager();

        try {
            game.startGame(new File("./test-files/valoresWalkingDEISITestBeskarHelmet.txt"));
            game.move(0, 1, 0, 0);  //Adulto apanha Beskar Helmet
            game.move(1, 0, 0, 0);  //Adulto Zombie ataca adulto com helmet
            assertEquals(0, game.getElementId(1, 0));  //Zombie morre a atacar
        } catch ( Exception e ) {
            System.out.println("File contained error -> " + e.getMessage() );
        }
    }

    @Test
    public void testAttackBeskarHelmet() {
        TWDGameManager game = new TWDGameManager();

        try {
            game.startGame(new File("./test-files/valoresWalkingDEISITestBeskarHelmet.txt"));
            game.move(0, 1, 0, 0);  //Adulto apanha Beskar Helmet
            game.move(1, 0, 0, 0);  //Adulto Zombie ataca adulto com helmet
            game.move(0, 0, 1, 1); //Humano ataca zombie adulto
            assertEquals(1, game.getElementId(1, 1));  //Humano mata zombie
        } catch ( Exception e ) {
            System.out.println("File contained error -> " + e.getMessage() );
        }
    }

    @Test
    public void testApanhaVenenoEZombieAtaca() {
        TWDGameManager game = new TWDGameManager();

        try {
            game.startGame(new File("./test-files/valoresWalkingDEISITestVeneno.txt"));
            game.move(0, 1, 0, 0);  //Adulto apanha veneno
            game.move(1, 0, 0, 0); //Zombie ataca humano
            assertEquals(1, game.getElementId(0, 0)); //Humano continua la
        } catch ( Exception e ) {
            System.out.println("File contained error -> " + e.getMessage() );
        }
    }

    @Test
    public void testApanhaVenenoENaoChegaAntidotoATempo() {
        TWDGameManager game = new TWDGameManager();

        try {
            game.startGame(new File("./test-files/valoresWalkingDEISITestVeneno.txt"));
            game.move(0, 1, 0, 0);  //Adulto apanha veneno
            game.move(1, 0, 0, 0);  //Zombie ataca humano (veneno defende)
            game.move(0, 0, 0, 1); //Humano tenta mover não para cima de antidoto
            assertEquals(0, game.getElementId(0, 1));  //Humano morre envenenado
        } catch ( Exception e ) {
            System.out.println("File contained error -> " + e.getMessage() );
        }
    }

    @Test
    public void testApanhaVenenoEChegaAntidotoATempo() {
        TWDGameManager game = new TWDGameManager();

        try {
            game.startGame(new File("./test-files/valoresWalkingDEISITestVeneno.txt"));
            game.move(0, 1, 0, 0);  //Adulto apanha veneno
            game.move(1, 0, 0, 0);  //Zombie ataca humano (veneno defende)
            game.move(0, 0, 1, 1); //Humano move para cima de antidoto
            assertEquals(1, game.getElementId(1, 1));  //Humano não morre envenenado
        } catch ( Exception e ) {
            System.out.println("File contained error -> " + e.getMessage() );
        }
    }

    @Test
    public void testHumanoUsaGarrafaLixiviaParaDefender3Vezes() {
        TWDGameManager game = new TWDGameManager();

        try {
            game.startGame(new File("./test-files/valoresWalkingDEISITestGarrafaLixivia.txt"));
            game.move(0, 1, 0, 0);  //Humano apanha Lixivia
            game.move(1, 0, 0, 0);  //Zombie ataca humano (lixiva defende)
            game.move(0, 0, 0, 1); //Humano move para cima
            game.move(1, 1, 0, 1);  //Zombie ataca humano (lixivia defende)
            game.move(0, 1, 0, 0);  //Humano move para baixo
            game.move(1, 0, 0, 0);  //Zombie ataca humano (lixivia defende)
            assertEquals(5, game.getGameMap().getPosition(0, 0).getCreature().getTipo());  //Tipo 5 é criança humana
        } catch ( Exception e ) {
            System.out.println("File contained error -> " + e.getMessage() );
        }
    }

    @Test
    public void testHumanoUsaGarrafaLixiviaParaDefender4Vezes() {
        TWDGameManager game = new TWDGameManager();

        try {
            game.startGame(new File("./test-files/valoresWalkingDEISITestGarrafaLixivia.txt"));
            game.move(0, 1, 0, 0);  //Humano apanha Lixivia
            game.move(1, 0, 0, 0);  //Zombie ataca humano (lixiva defende)
            game.move(0, 0, 0, 1); //Humano move para cima
            game.move(1, 1, 0, 1);  //Zombie ataca humano (lixivia defende)
            game.move(0, 1, 0, 0);  //Humano move para baixo
            game.move(1, 0, 0, 0);  //Zombie ataca humano (lixivia defende)
            game.move(0, 0, 0, 1); //Humano move para cima
            game.move(1, 1, 0, 1);  //Zombie ataca humano (lixivia nao defende)
            //Tipo 0 é criança zombie
            assertEquals(0, game.getGameMap().getPosition(0, 1).getCreature().getTipo());
        } catch ( Exception e ) {
            System.out.println("File contained error -> " + e.getMessage() );
        }
    }

    @Test
    public void testMapInputsCreatureWithEquipment() {
        TWDGameManager game = new TWDGameManager();

        try {
            game.startGame(new File("./test-files/valoresWalkingDEISITestZombieDoFilme.txt"));
            Creature creatureFound = game.getGameMap().getPosition(3, 3).getCreature();
            Equipamento equipamentFound = null;
            if (creatureFound instanceof Humano) {
                equipamentFound = ((Humano) creatureFound).getEquipamentoApanhado();
            }

            assertEquals(true, equipamentFound != null);  //O mapa põe o equipamento na criatura
        } catch ( Exception e ) {
            System.out.println("File contained error -> " + e.getMessage() );
        }
    }

    @Test
    public void testSmokerZombiePullsHumanoAndKills() {
        TWDGameManager game = new TWDGameManager();

        try {
            game.startGame(new File("./test-files/valoresWalkingDEISITestZombieDoFilme.txt"));
            game.move(6, 2, 6, 1);  //Humano mata zombie
            game.move(12, 3, 3, 3); //Smoker apanha survivor (survivor é pulled - 4 3)
            game.move(4, 0, 6, 0);  //Humano move para o lado (survivor é pulled - 5 3)
            game.move(6, 5, 6, 4);  //Zombie move para escudo (survivor é pulled - 6 3)
            game.move(1, 2, 3, 0);  //Humano troca de arma (survivor é pulled - 7 3)
            game.move(10, 0, 7, 0);  //Zombie move contra humano (survivor é pulled - 8 3)
            game.move(6, 0, 7, 0);  //Humano ataca zombie (survivor é pulled - 9 3)
            game.move(1, 4, 1, 3);  //Zombie move para cima (survivor é pulled - 10 3)
            game.move(7, 0, 9, 0);  //Humano move para lado (survivor é pulled - 11 3)
            game.move(9, 4, 10, 4);  //Zombie move para lado (survivor é atacado e morre)
            assertEquals(0, game.getElementId(11, 3));
        } catch ( Exception e ) {
            System.out.println("File contained error -> " + e.getMessage() );
        }
    }

    @Test
    public void testSmokerZombiePullsHumanoButGetsStoppedByHumano() {
        TWDGameManager game = new TWDGameManager();

        try {
            game.startGame(new File("./test-files/valoresWalkingDEISITestZombieDoFilme.txt"));
            game.move(6, 2, 6, 1);  //Humano mata zombie
            game.move(12, 3, 3, 3); //Smoker apanha survivor (survivor é pulled - 4 3)
            game.move(4, 0, 3, 0);  //Humano apanha arma (survivor é pulled - 5 3)
            game.move(6, 5, 6, 4);  //Zombie move para escudo (survivor é pulled - 6 3)
            game.move(6, 1, 6, 3);  //Humano ataca survivor pulled (survivor é released)
            assertEquals(4, game.getElementId(6, 3));  //Foi released, logo não foi mexido para o lado
        } catch ( Exception e ) {
            System.out.println("File contained error -> " + e.getMessage() );
        }
    }

    @Test
    public void testSmokerZombiePullsHumanoButHumanoGetsAttackedByZombieAndConverted() {
        TWDGameManager game = new TWDGameManager();

        try {
            game.startGame(new File("./test-files/valoresWalkingDEISITestZombieDoFilme.txt"));
            game.move(6, 2, 6, 1);  //Humano mata zombie
            game.move(12, 3, 3, 3); //Smoker apanha survivor (survivor é pulled - 4 3)
            game.move(4, 0, 3, 0);  //Humano apanha arma (survivor é pulled - 5 3)
            game.move(6, 5, 7, 4);  //Zombie move para preparar attack (survivor é pulled - 6 3)
            game.move(1, 2, 1, 3);  //Humano move contra zombie (survivor é pulled - 7 3)
            game.move(7, 4, 7, 3);  //Zombie ataca survivor (survivor para de ser pulled e é convertido)
            assertEquals(4, game.getElementId(7, 3));  //Foi convertido, logo não foi mexido para o lado
        } catch ( Exception e ) {
            System.out.println("File contained error -> " + e.getMessage() );
        }
    }

    @Test
    public void testSmokerZombieMovesLinearlyToValidPosition() {
        TWDGameManager game = new TWDGameManager();

        try {
            game.startGame(new File("./test-files/valoresWalkingDEISITestZombieDoFilme.txt"));
            game.move(6, 2, 6, 1);  //Humano mata zombie
            game.move(12, 3, 11, 3); //Smoker move para posição
            assertEquals(1, game.getElementId(11, 3));  //Foi mexido
        } catch ( Exception e ) {
            System.out.println("File contained error -> " + e.getMessage() );
        }
    }

    @Test
    public void testSmokerZombieMovesLinearyToInvalidPosition() {
        TWDGameManager game = new TWDGameManager();

        try {
            game.startGame(new File("./test-files/valoresWalkingDEISITestZombieDoFilme.txt"));
            game.move(6, 2, 6, 1);  //Humano mata zombie
            game.move(12, 3, 10, 3); //Smoker move para posição demasiado longe
            assertEquals(0, game.getElementId(10, 3));  //Não foi mexido
        } catch ( Exception e ) {
            System.out.println("File contained error -> " + e.getMessage() );
        }
    }

    @Test
    public void testSmokerZombieMovesDiagonallyToInvalidPosition() {
        TWDGameManager game = new TWDGameManager();

        try {
            game.startGame(new File("./test-files/valoresWalkingDEISITestZombieDoFilme.txt"));
            game.move(6, 2, 6, 1);  //Humano mata zombie
            game.move(12, 3, 11, 2); //Smoker move para diagonalmente
            assertEquals(0, game.getElementId(10, 3));  //Não foi mexido
        } catch ( Exception e ) {
            System.out.println("File contained error -> " + e.getMessage() );
        }
    }

    @Test
    public void testSmokerZombieTakesHit() {
        TWDGameManager game = new TWDGameManager();

        try {
            game.startGame(new File("./test-files/valoresWalkingDEISITestZombieDoFilme.txt"));
            Creature creatureFound = game.getGameMap().getPosition(12, 3).getCreature();

            if (creatureFound instanceof SmokerZombie) {
                ((SmokerZombie) creatureFound).takeHit();
                assertEquals(1, ((SmokerZombie) creatureFound).getAmountOfLifeLeft());  //Perdeu vida
            }
        } catch ( Exception e ) {
            System.out.println("File contained error -> " + e.getMessage() );
        }
    }

    @Test
    public void testSmokerZombieTakesHitAt0() {
        TWDGameManager game = new TWDGameManager();

        try {
            game.startGame(new File("./test-files/valoresWalkingDEISITestZombieDoFilme.txt"));
            Creature creatureFound = game.getGameMap().getPosition(12, 3).getCreature();

            if (creatureFound instanceof SmokerZombie) {
                ((SmokerZombie) creatureFound).takeHit();
                ((SmokerZombie) creatureFound).takeHit();
                ((SmokerZombie) creatureFound).takeHit();
                assertEquals(0, ((SmokerZombie) creatureFound).getAmountOfLifeLeft());  //não passou zero
            }
        } catch ( Exception e ) {
            System.out.println("File contained error -> " + e.getMessage() );
        }
    }

    @Test
    public void testSmokerZombieGrabsCreature() {
        TWDGameManager game = new TWDGameManager();

        try {
            game.startGame(new File("./test-files/valoresWalkingDEISITestZombieDoFilme.txt"));
            Creature creatureFound = game.getGameMap().getPosition(12, 3).getCreature();

            if (creatureFound instanceof SmokerZombie) {
                Creature creatureAttacked = game.getGameMap().getPosition(3, 3).getCreature();

                ((SmokerZombie) creatureFound).grabCreature(game.getGameMap(), creatureAttacked);
                assertEquals(true, ((SmokerZombie) creatureFound).getCurrentlyPulling());  //não passou zero
            }
        } catch ( Exception e ) {
            System.out.println("File contained error -> " + e.getMessage() );
        }
    }

    @Test
    public void testSmokerZombieGrabsCreatureAndThenStopsPulling() {
        TWDGameManager game = new TWDGameManager();

        try {
            game.startGame(new File("./test-files/valoresWalkingDEISITestZombieDoFilme.txt"));
            Creature creatureFound = game.getGameMap().getPosition(12, 3).getCreature();

            if (creatureFound instanceof SmokerZombie) {
                Creature creatureAttacked = game.getGameMap().getPosition(3, 3).getCreature();

                ((SmokerZombie) creatureFound).grabCreature(game.getGameMap(), creatureAttacked);
                ((SmokerZombie) creatureFound).stopPulling();
                assertEquals(false, ((SmokerZombie) creatureFound).getCurrentlyPulling());  //não passou zero
            }
        } catch ( Exception e ) {
            System.out.println("File contained error -> " + e.getMessage() );
        }
    }

    @Test
    public void testGameStatistics3ZombiesTramados() {
        TWDGameManager game = new TWDGameManager();
        String key = "os3ZombiesMaisTramados";

        try {
            game.startGame(new File("./test-files/valoresWalkingDEISI.txt"));
            game.move(2, 2, 2, 1);  //Human moves to position
            game.move(0, 1, 2, 1);  //Zombie attacks human
            Map<String, List<String>> gameStatistics = game.getGameStatistics();
            List<String> zombiesTramados = gameStatistics.get(key);

            assertEquals(1, zombiesTramados.size());  //Só um zombie é que matou, deve so aparecer esse zombie
        } catch ( Exception e ) {
            System.out.println("File contained error -> " + e.getMessage() );
        }
    }

    @Test
    public void testGameStatistics3VivosDuros() {
        TWDGameManager game = new TWDGameManager();
        String key = "os3VivosMaisDuros";

        try {
            game.startGame(new File("./test-files/valoresWalkingDEISI.txt"));
            game.move(2, 2, 3, 2);  //Human moves to equipment
            game.move(0, 1, 2, 1);  //Zombie moves to position
            game.move(3, 3, 2, 3);  //Human moves to equipment
            game.move(2, 1, 3, 2);  //Zombie attacks human
            game.move(6, 3, 7, 4);  //Human moves to safe haven
            game.move(3, 4, 2, 3);  //Zombie attacks human
            Map<String, List<String>> gameStatistics = game.getGameStatistics();
            List<String> vivosDuros = gameStatistics.get(key);
            for ( String line: vivosDuros ) {
                System.out.println(line);
            }

            assertEquals(2, vivosDuros.size());  //Dois vivos mataram zombies
        } catch ( Exception e ) {
            System.out.println("File contained error -> " + e.getMessage() );
        }
    }

    @Test
    public void testGameStatisticsTiposEquipamentosMaisDuros() {
        TWDGameManager game = new TWDGameManager();
        String key = "tiposDeEquipamentoMaisUteis";

        try {
            game.startGame(new File("./test-files/valoresWalkingDEISI.txt"));
            game.move(2, 2, 3, 2);  //Human moves to equipment
            game.move(0, 1, 2, 1);  //Zombie moves to position
            game.move(3, 3, 2, 3);  //Human moves to equipment
            game.move(2, 1, 3, 2);  //Zombie attacks human
            game.move(6, 3, 7, 4);  //Human moves to safe haven
            game.move(3, 4, 2, 3);  //Zombie attacks human
            Map<String, List<String>> gameStatistics = game.getGameStatistics();
            List<String> equipamentosUteis = gameStatistics.get(key);

            assertEquals(2, equipamentosUteis.size());  //Dois equipamentos defenderam dos zombies
        } catch ( Exception e ) {
            System.out.println("File contained error -> " + e.getMessage() );
        }
    }
}
