package pt.ulusofona.lp2.theWalkingDEISIGame;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.sql.SQLOutput;
import java.util.*;

public class TWDGameManager {

    //0 -> day and 1 -> night
    private int dayNightCycle;
    private int initialTeamId;
    private int currentTeamId;
    private int numberOfTurns;

    private List<Creature> creatures;
    private List<Equipamento> equipment;
    private List<SafeHaven> safeHavens;

    private Mapa gameMap;

    public TWDGameManager() {
        dayNightCycle = 0;
        initialTeamId = 0;
        currentTeamId = 0;
        numberOfTurns = 0;

        creatures = new ArrayList<>();
        equipment = new ArrayList<>();
        safeHavens = new ArrayList<>();

        gameMap = new Mapa();
    }

    //leitura do ficheiro texto
    //e carregar para a memória a informação relevante
    public boolean startGame( File ficheiroInicial ) {
        creatures.clear();
        equipment.clear();
        safeHavens.clear();

        gameMap = new Mapa();

        initialTeamId = 0;
        currentTeamId = 0;
        numberOfTurns = 0;
        dayNightCycle = 0;

        int numFileLine = 1;
        int numCreatures = 0;
        int numEquipment = 0;
        int numSafeHavens = 0;

        try {
            BufferedReader reader = new BufferedReader( new FileReader( ficheiroInicial ) );
            String lineRead = null;

            do {
                try {
                    lineRead = reader.readLine();
                    if ( lineRead == null ) {
                        break;
                    }

                    switch ( numFileLine ) {
                        case 1:
                            String[] splitNumLinesColumns = lineRead.split(" ", 2);

                            gameMap.setSizeX( Integer.parseInt( splitNumLinesColumns[0].trim() ) + 1 );
                            gameMap.setSizeY( Integer.parseInt( splitNumLinesColumns[1].trim() ) + 1 );
                            gameMap.createMap();
                            break;

                        case 2:
                            initialTeamId = Integer.parseInt( lineRead.trim() );
                            if ( initialTeamId == 10 || initialTeamId == 20 ) {
                                currentTeamId = initialTeamId;
                            } else {
                                return false;
                            }
                            break;

                        case 3:
                            numCreatures = Integer.parseInt( lineRead.trim() );
                            break;

                        case 4:
                            for ( int pos = 0; pos < numCreatures; pos++ ) {
                                String[] splitCreatures = lineRead.split(":" );

                                int creatureID = Integer.parseInt( splitCreatures[0].trim() );
                                int typeID = Integer.parseInt( splitCreatures[1].trim() );
                                String creatureName = splitCreatures[2];
                                int spawnX = Integer.parseInt( splitCreatures[3].trim() );
                                int spawnY = Integer.parseInt( splitCreatures[4].trim() );

                                switch ( typeID ) {
                                    case 0: //Criança Zombie
                                        Creature criancaZombie = new Zombie(creatureID, creatureName, spawnX, spawnY,
                                                1, true);
                                        creatures.add(criancaZombie);
                                        break;

                                    case 1: //Adulto Zombie
                                        Creature adultoZombie = new Zombie(creatureID, creatureName, spawnX, spawnY,
                                                2, true);
                                        creatures.add(adultoZombie);
                                        break;

                                    case 2: //Militar Zombie
                                        Creature militarZombie = new Zombie(creatureID, creatureName, spawnX, spawnY,
                                                3, true);
                                        creatures.add(militarZombie);
                                        break;

                                    case 3: //Idoso Zombie
                                        Creature idosoZombie = new Zombie(creatureID, creatureName, spawnX, spawnY,
                                                1, false);
                                        creatures.add(idosoZombie);
                                        break;

                                    case 4: //Zombie Vampiro
                                        Creature vampiroZombie = new Zombie(creatureID, creatureName, spawnX, spawnY,
                                                2, true);
                                        creatures.add(vampiroZombie);
                                        break;

                                    case 5: //Criança Vivo
                                        Creature criancaVivo = new Humano(creatureID, creatureName, spawnX, spawnY,
                                                1, true);
                                        creatures.add(criancaVivo);
                                        break;

                                    case 6: //Adulto Vivo
                                        Creature adultoVivo = new Humano(creatureID, creatureName, spawnX, spawnY,
                                                2, true);
                                        creatures.add(adultoVivo);
                                        break;

                                    case 7: //Militar Vivo
                                        Creature militarVivo = new Humano(creatureID, creatureName, spawnX, spawnY,
                                                3, true);
                                        creatures.add(militarVivo);
                                        break;

                                    case 8: //Idoso Vivo
                                        Creature idosoVivo = new Humano(creatureID, creatureName, spawnX, spawnY,
                                                1, false);
                                        creatures.add(idosoVivo);
                                        break;

                                    case 9: //Cão
                                        Creature cao = new Cao(creatureID, creatureName, spawnX, spawnY,
                                                2, false);
                                        break;

                                    default:
                                        return false;
                                }
                                
                                if ( pos == numCreatures - 1 ) {
                                    break;
                                }
                                lineRead = reader.readLine();
                                if ( lineRead == null ) {
                                    return false;
                                }
                            }

                            break;

                        case 5:
                            numEquipment = Integer.parseInt( lineRead.trim() );
                            break;

                        case 6:
                            for ( int pos = 0; pos < numEquipment; pos++ ) {
                                String[] splitEquipment = lineRead.split(":", 4 );
                                int equipmentID = Integer.parseInt( splitEquipment[0].trim() );
                                int typeID = Integer.parseInt( splitEquipment[1].trim() );
                                int spawnX = Integer.parseInt( splitEquipment[2].trim() );
                                int spawnY = Integer.parseInt( splitEquipment[3].trim() );

                                if ( typeID == 0 || typeID == 1 ) {
                                    Equipamento currentEquipment = new Equipamento(equipmentID,typeID,spawnX,spawnY);
                                    equipment.add( currentEquipment );
                                } else {
                                    return false;
                                }

                                if ( pos == numEquipment - 1 ) {
                                    break;
                                }
                                lineRead = reader.readLine();
                                if ( lineRead == null ) {
                                    return false;
                                }
                            }
                            break;

                        case 7:
                            numSafeHavens = Integer.parseInt( lineRead );
                            break;

                        case 8:
                            for ( int pos = 0; pos < numSafeHavens; pos++ ) {
                                String[] splitEquipment = lineRead.split(":", 2 );
                                int spawnX = Integer.parseInt( splitEquipment[2].trim() );
                                int spawnY = Integer.parseInt( splitEquipment[3].trim() );

                                SafeHaven safeHavenDoor = new SafeHaven( spawnX, spawnY );
                                safeHavens.add( safeHavenDoor );

                                if ( pos == numEquipment - 1 ) {
                                    break;
                                }
                                lineRead = reader.readLine();
                                if ( lineRead == null ) {
                                    return false;
                                }
                            }

                            gameMap.addCreatures( creatures );
                            gameMap.addEquipment( equipment );
                            gameMap.addSafeHavens( safeHavens );
                            break;

                        default:
                            return true;
                    }
                    numFileLine++;

                } catch ( Exception e ) {
                    System.out.println("Error -> " + e.getMessage() );
                    //return false;
                }

            } while ( lineRead != null );

        } catch ( FileNotFoundException e ) {
            System.out.println("No file was found with that name");
            return false;
        }

        return true;
    }

    //Tem que devolver o tamanho do bairro
    //Conforme lido do ficheiro respetivo
    //na posição 0 tem que ser lido o número de linhas
    //e na posição 1 o número de colunas
    public int[] getWorldSize() {
        int[] worldSize = new int[2];
        if ( gameMap.getSizeX() == 0 || gameMap.getSizeY() == 0 ) {
            return null;
        }

        worldSize[0] = gameMap.getSizeX() - 1;
        worldSize[1] = gameMap.getSizeY() - 1;
        return worldSize;
    }

    //Devolve o ID da equipa que vai jogar no primeiro turno
    public int getInitialTeam() {
        return this.initialTeamId;
    }

    //Devolve uma lista com todos os objetos "Creature" no jogo
    public List<Creature> getCreatures() {
        return this.creatures;
    }

    public List<String> getAuthors() {
        List<String> authors = new ArrayList<>();

        String primeiroAutor = "Diogo Araújo - a21905661";
        String segundoAutor = "Miguel Pereira - a21902726";

        authors.add( primeiroAutor );
        authors.add( segundoAutor );
        return authors;
    }

    //devolve o id da equipa que está ativa no turno atual
    public int getCurrentTeamId() {
        return this.currentTeamId;
    }

    //devolve o id do objeto/elemento que se encontra na posição indicada pelas
    //coordenadas (x,y) passadas no argumento
    public int getElementId( int x, int y ) {
        if ( x >= gameMap.getSizeX() || y >= gameMap.getSizeY() || x < 0 || y < 0 ) {
            return 0;
        }
        if ( gameMap == null ) {
            return 0;
        }
        int tipo = gameMap.getMapId(x,y);
        int mapId;

        switch( tipo ) {
            case -2:
            case -1:
                Equipamento equipamento = gameMap.getPosition(x,y).getEquipamento();
                mapId = equipamento.getId();
                break;

            case 1:
            case 2:
            case 3:
                Creature creature = gameMap.getPosition(x,y).getCreature();
                mapId = creature.getId();
                break;

            default:
                mapId = 0;
        }

        return mapId;
    }

    //deve tentar executar uma jogada
    //xO, yO é uma origem
    //xD, yD é o destino
    public boolean move( int xO, int yO, int xD, int yD ) {
        if ( !verificaCondicoes(xO, yO, xD, yD) ) {
            return false;
        }
        if ( currentTeamId == 1 ) {
            return moveZombie(xO, yO, xD, yD);
        }

        //verifica se tentamos mover um zombie
        if ( gameMap.getMapId(xO,yO) == 3 ) {
            return false;
        }

        //ocorre o movimento
        Creature humanFound = gameMap.getPosition(xO,yO).getCreature();
        int tipoMovido = gameMap.getMapId( xO, yO );

        //ve se estamos a mover para cima de uma arma
        if ( gameMap.getMapId( xD, yD ) == -1 ) {
            tipoMovido = 1;
        }

        //move normalmente
        humanFound.move( gameMap, xD, yD, tipoMovido );
        incrementaTempo();
        return true;
    }

    public boolean moveZombie( int xO, int yO, int xD, int yD ) {
        //verifica se tentamos mover um humano
        if ( gameMap.getMapId(xO,yO) == 2 || gameMap.getMapId(xO,yO) == 1 ) {
            return false;
        }

        if ( gameMap.getPosition(xD, yD).getTipo() == -1 ) {
            retiraEquipamento( xD, yD );
        }

        incrementaTempo();
        int tipoMovido = gameMap.getMapId( xO, yO );

        gameMap.getPosition(xO, yO).getCreature().move( gameMap, xD, yD, tipoMovido );
        return true;
    }

    public boolean verificaCondicoes( int xO, int yO, int xD, int yD ) {
        //verifica se os parametros introduzidos estáo corretos para o mapa
        if ( xD >= gameMap.getSizeX() || yD >= gameMap.getSizeY() || xD < 0 || yD < 0 ) {
            return false;
        }
        if ( xO >= gameMap.getSizeX() || yO >= gameMap.getSizeY() || xO < 0 || yO < 0 ) {
            return false;
        }

        //verifica se tamos a tentar mover para cima de um humano
        if ( gameMap.getMapId(xD,yD) == 2 || gameMap.getMapId(xD,yD) == 1) {
            return false;
        }
        //verifica se tentamos mover para cima de um zombie
        if ( gameMap.getMapId(xD,yD) == 3 ) {
            return false;
        }

        //verifica se tamos a tentar mover nada
        if ( gameMap.getMapId(xO,yO) == 0 ) {
            return false;
        }
        //verifica se tamos a tentar mover um equipamento
        if ( gameMap.getMapId(xO, yO) == -1 ) {
            return false;
        }

        //verifica se tenta mover mais que uma posição
        if ( xO + 1 == xD && yO == yD ) {
            return true;
        }
        if( xO - 1 == xD && yO == yD ) {
            return true;
        }
        if ( xO == xD && yO + 1 == yD ) {
            return true;
        }
        if ( xO == xD && yO - 1 == yD ) {
            return true;
        }

        return false;
    }

    /*
    //este movimento é automático
    public boolean moveZombie() {
        Random randomNum = new Random();
        if ( zombies == null ) {
            return false;
        }
        int zombieSize = zombies.size();
        if ( zombieSize == 0 ) {
            return false;
        }

        Zombie zombie;
        while ( true ) {
            int randomZombie = randomNum.nextInt( zombieSize );
            if ( randomZombie == zombieSize ) {
                continue;
            }

            zombie = zombies.get( randomZombie );
            break;
        }

        int count = 0;
        while ( count <= 16 ) {
            count++;
            int random = randomNum.nextInt( 4 );

            switch ( random ) {
                //move para cima (y - 1)
                case 0:
                    if ( zombie.getY() - 1 < 0 ) {
                        break;
                    }
                    if ( !verificaCondicoes(zombie.getX(), zombie.getY() - 1 ) ) {
                        break;
                    }

                    //se for para uma posição do tipo -1, então retira o equipamento da lista
                    if ( gameMap.getPosition(zombie.getX(), zombie.getY() - 1).getTipo() == -1 ) {
                        retiraEquipamento( zombie.getX(), zombie.getY() - 1 );
                    }

                    incrementaTempo();
                    zombie.move( zombie.getX(), zombie.getY() - 1, gameMap );
                    return true;

                //move para baixo (y + 1)
                case 1:
                    if ( zombie.getY() + 1 > gameMap.getSizeY() - 1 ) {
                        break;
                    }
                    if ( !verificaCondicoes(zombie.getX(), zombie.getY() + 1 ) ) {
                        break;
                    }

                    //se for para uma posição do tipo -1, então retira o equipamento da lista
                    if ( gameMap.getPosition(zombie.getX(), zombie.getY() + 1).getTipo() == -1 ) {
                        retiraEquipamento( zombie.getX(), zombie.getY() + 1 );
                    }

                    incrementaTempo();
                    zombie.move( zombie.getX(), zombie.getY() + 1, gameMap );
                    return true;

                case 2:
                    if ( zombie.getX() - 1 < 0 ) {
                        break;
                    }
                    if ( !verificaCondicoes(zombie.getX() - 1, zombie.getY() ) ) {
                        break;
                    }

                    //se for para uma posição do tipo -1, então retira o equipamento da lista
                    if ( gameMap.getPosition(zombie.getX() - 1, zombie.getY()).getTipo() == -1 ) {
                        retiraEquipamento( zombie.getX() - 1, zombie.getY() );
                    }

                    incrementaTempo();
                    zombie.move( zombie.getX() - 1, zombie.getY(), gameMap );
                    return true;

                case 3:
                    if ( zombie.getX() + 1 > gameMap.getSizeX() - 1 ) {
                        break;
                    }
                    if ( !verificaCondicoes(zombie.getX() + 1, zombie.getY() ) ) {
                        break;
                    }

                    //se for para uma posição do tipo -1, então retira o equipamento da lista
                    if ( gameMap.getPosition(zombie.getX() + 1, zombie.getY()).getTipo() == -1 ) {
                        retiraEquipamento( zombie.getX() + 1, zombie.getY());
                    }

                    incrementaTempo();
                    zombie.move(zombie.getX() + 1, zombie.getY(), gameMap );
                    return true;

                default:
            }

        }

        incrementaTempo();
        return true;   //se true, quando o zombie esta preso, não se move
    }
     */

    public void retiraEquipamento( int destinoX, int destinoY ) {
        Equipamento equipamentoDestruido = gameMap.getPosition(destinoX, destinoY).getEquipamento();
        int idEquipamento = equipamentoDestruido.getId();

        int pos = 0;
        for ( Equipamento equipamento: equipment ) {
            if ( equipamento.getId() == idEquipamento ) {
                break;
            }
            pos++;
        }

        equipment.remove(pos);
    }

    /*
    //este verifica condicoes foi feito para funcionar
    //com o movimento automático do zombie
    public boolean verificaCondicoes( int destinoX, int destinoY ) {
        if ( destinoX >= gameMap.getSizeX() || destinoY >= gameMap.getSizeY() || destinoX < 0 || destinoY < 0 ) {
            return false;
        }

        if ( gameMap.getMapId( destinoX, destinoY ) == 2 ) {
            return false;
        }
        if ( gameMap.getMapId( destinoX, destinoY ) == 1 ) {
            return false;
        }
        if ( gameMap.getMapId( destinoX, destinoY ) == 3 ) {
            return false;
        }

        return true;
    }
     */

    public void incrementaTempo() {
        if ( currentTeamId == 10 ) {
            currentTeamId += 10;
        } else {
            currentTeamId -= 10;
        }
        numberOfTurns++;
        if ( numberOfTurns % 2 == 0 ) {
            if ( dayNightCycle == 0 ) {
                dayNightCycle = 1;
            } else {
                dayNightCycle = 0;
            }
        }
    }

    //se uma das condições de paragem ja tenha sido alcançada
    //então retorna true
    public boolean gameIsOver() {
        if ( numberOfTurns == 12 ) {
            return true;
        }
        if ( creatures.size() == 0 ) {
            return true;
        }

        return false;
    }

    /*
    public List<String> getSurvivors() {
        List<String> listOfSurvivors = new ArrayList<>();
        String text = "Nr. de turnos terminados:";
        listOfSurvivors.add( text );
        text = "" + numberOfTurns ;
        listOfSurvivors.add( text );
        listOfSurvivors.add(" ");

        text = "OS VIVOS";
        listOfSurvivors.add( text );
        for ( Creature creature: creatures ) {
            if ( creature.getTipo() == 3  ) {
                continue;   //Se estivermos a ver um zombie
            }

            text = "" + creature.getId() + " " + creature.getNome();
            listOfSurvivors.add(text);
        }
        listOfSurvivors.add( " " );

        text = "OS OUTROS";
        listOfSurvivors.add( text );
        for ( Creature creature: creatures ) {
            if ( creature.getTipo() == 1 || creature.getTipo() == 2 ) {
                continue;   //Se estivermos a ver um humano
            }

            text = creature.getId() + " " + creature.getNome();
            listOfSurvivors.add(text);
        }

        return listOfSurvivors;
    }
     */

    public boolean isDay() {
        return (dayNightCycle == 0);
    }

    public List<String> getGameResults() {
        List<String> gameResults = new ArrayList<>();
        String text = "Nr. de turnos terminados:";
        gameResults.add( text );
        text = "" + numberOfTurns ;
        gameResults.add( text );
        gameResults.add(" ");

        text = "OS VIVOS";
        gameResults.add( text );
        for ( Creature creature: creatures ) {
            if ( creature instanceof Zombie || creature instanceof Cao ) {
                continue;   //Se estivermos a ver um zombie ou um cao
            }

            text = "" + creature.getId() + " " + creature.getNome();
            gameResults.add(text);
        }
        gameResults.add( " " );

        text = "OS OUTROS";
        gameResults.add( text );
        for ( Creature creature: creatures ) {
            if ( creature instanceof Humano || creature instanceof Cao ) {
                continue;   //Se estivermos a ver um humano ou um cao
            }

            text = creature.getId() + " " + creature.getNome();
            gameResults.add(text);
        }

        return gameResults;
    }

    /*
    public boolean hasEquipment( int creatureId, int equipmentTypeId ) {
        for ( Humano humano: humanos ) {
            if ( humano.getId() == creatureId ) {
                if ( humano.getEquipamentoApanhado() == null ) {
                    return false;
                }

                return (humano.getEquipamentoApanhado().getTipo() == equipmentTypeId);
            }
        }

        return false;
    }
     */

    /*
    public int getEquipmentId( int creatureId ) {
        if ( creatures == null || creatures.size() == 0 ) {
            return 0;
        }

        for ( Creature creature : creatures ) {
            if ( creature.getId() == creatureId ) {
                //Se for humano, então consegue aguentar uma arma
            }
        }
    }
     */
}
