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

    private List<Humano> humanos;
    private List<Zombie> zombies;
    private List<Equipamento> equipment;

    private Mapa gameMap;

    public TWDGameManager() {
        dayNightCycle = 0;
        humanos = new ArrayList<>();
        zombies = new ArrayList<>();
        equipment = new ArrayList<>();
        gameMap = new Mapa();
        initialTeamId = 0;
        currentTeamId = 0;
        numberOfTurns = 0;
    }

    //passar os dois fors para uma função
    //tornar isto mais pequeno

    //leitura do ficheiro texto
    //e carregar para a memória a informação relevante
    public boolean startGame( File ficheiroInicial ) {
        humanos.clear();
        zombies.clear();
        equipment.clear();
        initialTeamId = 0;
        currentTeamId = 0;
        numberOfTurns = 0;
        gameMap = new Mapa();
        dayNightCycle = 0;

        int numFileLine = 1;
        int numCreatures = 0;
        int numEquipment = 0;

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

                            gameMap.setSizeX( Integer.parseInt( splitNumLinesColumns[0].trim() ) );
                            gameMap.setSizeY( Integer.parseInt( splitNumLinesColumns[1].trim()   ) );
                            gameMap.createMap();
                            break;

                        case 2:
                            initialTeamId = Integer.parseInt( lineRead.trim() );
                            if ( initialTeamId == 0 || initialTeamId == 1 ) {
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

                                if ( typeID == 0 ) {
                                    Zombie zombieCriado = new Zombie( creatureID, creatureName, spawnX, spawnY);
                                    zombies.add( zombieCriado );
                                } else if ( typeID == 1 ) {
                                    Humano humanoCriado = new Humano( creatureID, creatureName, spawnX, spawnY);
                                    humanos.add( humanoCriado );
                                } else {
                                    System.out.println("Erro no tipo de criatura");
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

                            gameMap.addHumans( humanos );
                            gameMap.addZombies( zombies );
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

                                Equipamento currentEquipment = new Equipamento( equipmentID, typeID, spawnX, spawnY );
                                equipment.add( currentEquipment );

                                if ( pos == numEquipment - 1 ) {
                                    break;
                                }
                                lineRead = reader.readLine();
                                if ( lineRead == null ) {
                                    return false;
                                }
                            }

                            gameMap.addEquipment( equipment );
                            break;

                        default:
                            return true;
                    }
                    numFileLine++;

                } catch ( Exception e ) {
                    System.out.println("Error -> " + e.getMessage() );
                }

            } while ( lineRead != null );

        } catch ( FileNotFoundException e ) {
            System.out.println("No file was found with that name");
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

        worldSize[0] = gameMap.getSizeX();
        worldSize[1] = gameMap.getSizeY();
        return worldSize;
    }

    //Devolve o ID da equipa que vai jogar no primeiro turno
    public int getInitialTeam() {
        return this.initialTeamId;
    }

    //Devolve uma lista com todos os objetos "Humano" no jogo
    public List<Humano> getHumans() {
        return this.humanos;
    }

    //Devolve uma lista com todos os objetos "Zombie" no jogo
    public List<Zombie> getZombies() {
        return this.zombies;
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
            case -1:
                Equipamento equipamento = gameMap.getPosition(x,y).getEquipamento();
                mapId = equipamento.getId();
                break;

            case 1:
            case 2:
                Humano human = gameMap.getPosition(x,y).getHuman();
                mapId = human.getId();
                break;

            case 3:
                Zombie zombie = gameMap.getPosition(x,y).getZombie();
                mapId = zombie.getId();
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
        if ( xD >= gameMap.getSizeX() || yD >= gameMap.getSizeY() || xD < 0 || yD < 0 ) {
            return false;
        }
        if ( xO >= gameMap.getSizeX() || yO >= gameMap.getSizeY() || xO < 0 || yO < 0 ) {
            return false;
        }

        if ( currentTeamId == 1 ) {
            return moveZombie();
        }

        if ( verificaCondicoes( xO, yO, xD, yD ) == false ) {
            return false;
        }

        //ocorre o movimento
        //tenho que fazer para a arma
        Humano humanFound = gameMap.getPosition(xO,yO).getHuman();
        int tipoMovido = gameMap.getMapId( xO, yO );

        if ( humanFound.getTwoHanded() == 1 ) {
            gameMap.getPosition(xO,yO).setEquipamento(humanFound.getEquipamentoApanhado().get(0));
            humanFound.getEquipamentoApanhado().remove(0);
            humanFound.setTwoHanded(0);
            gameMap.setPositionType( xO, yO, -1 );
            gameMap.setPositionType( xD, yD, tipoMovido );
            gameMap.getPosition(xD,yD).setHuman( humanFound );
            gameMap.getPosition(xO,yO).setHuman(null);

            incrementaTempo();
            return true;
        }

        if ( gameMap.getMapId( xD, yD ) == -1 ) {
            tipoMovido = 1;
            if ( humanFound.getEquipamentoApanhado().size() == 0 ) {
                humanFound.getEquipamentoApanhado().add( gameMap.getPosition(xD,yD).getEquipamento() );
                gameMap.getPosition(xD,yD).setEquipamento(null);
                humanFound.apanharEquipamento();
                gameMap.setPositionType( xO, yO, 0 );
                gameMap.setPositionType( xD, yD, tipoMovido );
                gameMap.getPosition(xD,yD).setHuman( humanFound );
                gameMap.getPosition(xO,yO).setHuman(null);

                incrementaTempo();
                return true;
            }

            humanFound.getEquipamentoApanhado().add( gameMap.getPosition(xD,yD).getEquipamento() );
            gameMap.getPosition(xD,yD).setEquipamento(null);
            humanFound.apanharEquipamento();
            humanFound.setTwoHanded(1);
            gameMap.setPositionType( xO, yO, 0 );
            gameMap.setPositionType( xD, yD, tipoMovido );
            gameMap.getPosition(xD,yD).setHuman( humanFound );
            gameMap.getPosition(xO,yO).setHuman(null);

            incrementaTempo();
            return true;
        }

        gameMap.setPositionType( xO, yO, 0 );
        gameMap.setPositionType( xD, yD, tipoMovido );
        gameMap.getPosition(xD,yD).setHuman( gameMap.getPosition(xO,yO).getHuman() );
        gameMap.getPosition(xO,yO).setHuman(null);

        incrementaTempo();
        return true;
    }

    public boolean verificaCondicoes( int xO, int yO, int xD, int yD ) {
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
        //verifica se tamos a tentar mover um zombie, ou um equipamento
        if ( gameMap.getMapId(xO, yO) == 3 || gameMap.getMapId(xO, yO) == -1 ) {
            return false;
        }
        //verifica se tentamos mover um zombie
        if ( gameMap.getMapId(xO,yO) == 3 ) {
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
                case 0:
                    if ( zombie.getY() - 1 < 0 ) {
                        break;
                    }
                    if ( gameMap.getMapId( zombie.getX(), zombie.getY() - 1 ) == 2  ) {
                        break;
                    }
                    if ( gameMap.getMapId( zombie.getX(), zombie.getY() - 1 ) == 1 ) {
                        break;
                    }
                    if ( gameMap.getMapId( zombie.getX(), zombie.getY() - 1 ) == 3 ) {
                        break;
                    }

                    if ( gameMap.getMapId( zombie.getX(), zombie.getY() - 1 )  == -1 ) {
                        zombie.destroiEquipamento();
                        gameMap.getPosition(zombie.getX(),zombie.getY() - 1).setEquipamento(null);
                        gameMap.setPositionType(zombie.getX(), zombie.getY() - 1, 0);
                    }
                    incrementaTempo();
                    gameMap.setPositionType( zombie.getX(), zombie.getY(), 0 );
                    gameMap.setPositionType( zombie.getX(), zombie.getY() - 1, 3 );
                    gameMap.getPosition(zombie.getX(), zombie.getY() - 1)
                            .setZombie( gameMap.getPosition(zombie.getX(),zombie.getY() ).getZombie() );
                    gameMap.getPosition(zombie.getX(), zombie.getY() ).setZombie( null );
                    zombie.setX( zombie.getX() );
                    zombie.setY( zombie.getY() - 1);
                    return true;

                case 1:
                    if ( zombie.getY() + 1 > gameMap.getSizeY() - 1 ) {
                        break;
                    }
                    if ( gameMap.getMapId( zombie.getX(), zombie.getY() + 1 ) == 2 ) {
                        break;
                    }
                    if ( gameMap.getMapId( zombie.getX(), zombie.getY() + 1 ) == 1 ) {
                        break;
                    }
                    if ( gameMap.getMapId( zombie.getX(), zombie.getY() + 1) == 3 ) {
                        break;
                    }

                    if ( gameMap.getMapId( zombie.getX(), zombie.getY() + 1 )  == -1 ) {
                        zombie.destroiEquipamento();
                        gameMap.getPosition(zombie.getX(),zombie.getY() + 1).setEquipamento(null);
                        gameMap.setPositionType(zombie.getX(), zombie.getY() + 1, 0);
                    }

                    incrementaTempo();
                    gameMap.setPositionType( zombie.getX(), zombie.getY(), 0 );
                    gameMap.setPositionType( zombie.getX(), zombie.getY() + 1, 3 );
                    gameMap.getPosition(zombie.getX(), zombie.getY() + 1)
                            .setZombie( gameMap.getPosition(zombie.getX(),zombie.getY() ).getZombie() );
                    gameMap.getPosition(zombie.getX(), zombie.getY() ).setZombie( null );
                    zombie.setX( zombie.getX() );
                    zombie.setY( zombie.getY() + 1);
                    return true;

                case 2:
                    if ( zombie.getX() - 1 < 0 ) {
                        break;
                    }
                    if ( gameMap.getMapId( zombie.getX() - 1, zombie.getY() ) == 2 ) {
                        break;
                    }
                    if ( gameMap.getMapId( zombie.getX() - 1, zombie.getY()  ) == 1 ) {
                        break;
                    }
                    if ( gameMap.getMapId( zombie.getX()  - 1, zombie.getY() ) == 3 ) {
                        break;
                    }

                    if ( gameMap.getMapId( zombie.getX() - 1, zombie.getY() )  == -1 ) {
                        zombie.destroiEquipamento();
                        gameMap.getPosition(zombie.getX() - 1,zombie.getY() ).setEquipamento(null);
                        gameMap.setPositionType(zombie.getX() - 1, zombie.getY(), 0);
                    }

                    incrementaTempo();
                    gameMap.setPositionType( zombie.getX(), zombie.getY(), 0 );
                    gameMap.setPositionType( zombie.getX() - 1, zombie.getY(), 3 );
                    gameMap.getPosition(zombie.getX() - 1, zombie.getY())
                            .setZombie( gameMap.getPosition(zombie.getX(),zombie.getY() ).getZombie() );
                    gameMap.getPosition(zombie.getX(), zombie.getY() ).setZombie( null );
                    zombie.setX( zombie.getX() - 1 );
                    zombie.setY( zombie.getY() );
                    return true;

                case 3:
                    if ( zombie.getX() + 1 > gameMap.getSizeX() - 1 ) {
                        break;
                    }
                    if ( gameMap.getMapId( zombie.getX() + 1, zombie.getY() ) == 2 ) {
                        break;
                    }
                    if ( gameMap.getMapId( zombie.getX() + 1, zombie.getY() ) == 1 ) {
                        break;
                    }
                    if ( gameMap.getMapId( zombie.getX() + 1, zombie.getY() ) == 3 ) {
                        break;
                    }

                    if ( gameMap.getMapId( zombie.getX() + 1, zombie.getY() )  == -1 ) {
                        zombie.destroiEquipamento();
                        gameMap.getPosition(zombie.getX() + 1,zombie.getY() ).setEquipamento(null);
                        gameMap.setPositionType(zombie.getX() + 1, zombie.getY(), 0);
                    }

                    incrementaTempo();
                    gameMap.setPositionType( zombie.getX(), zombie.getY(), 0 );
                    gameMap.setPositionType( zombie.getX() + 1, zombie.getY(), 3 );
                    gameMap.getPosition(zombie.getX() + 1, zombie.getY())
                            .setZombie( gameMap.getPosition(zombie.getX(),zombie.getY() ).getZombie() );
                    gameMap.getPosition(zombie.getX(), zombie.getY() ).setZombie( null );
                    zombie.setX( zombie.getX() + 1 );
                    zombie.setY( zombie.getY() );
                    return true;

                default:
            }

        }

        return false;
    }

    public void incrementaTempo() {
        if ( currentTeamId == initialTeamId ) {
            currentTeamId++;
        } else {
            currentTeamId--;
        }
        numberOfTurns++;
        if ( dayNightCycle == 0 && numberOfTurns % 2 == 0 ) {
            dayNightCycle = 1;
        } else {
            dayNightCycle = 0;
        }
    }

    //se uma das condições de paragem ja tenha sido alcançada
    //então retorna true
    public boolean gameIsOver() {
        if ( numberOfTurns == 12 ) {    //é suposto ser 12
            return true;
        }
        if ( humanos.size() == 0 ) {
            return true;
        }
        if ( zombies.size() == 0 ) {
            return true;
        }

        return false;
    }

    public List<String> getSurvivors() {
        List<String> listOfSurvivors = new ArrayList<>();
        String text = "Nr. de turnos terminados:\n";
        listOfSurvivors.add( text );
        text = "" + numberOfTurns + "\n\n\n";
        listOfSurvivors.add( text );

        text = "OS VIVOS\n";
        listOfSurvivors.add( text );
        text = "";
        for ( Humano humano: humanos ) {
            text += "" + humano.getId() + " " + humano.getNome() + "\n";
        }
        text += "\n";
        listOfSurvivors.add( text );

        text = "OS OUTROS\n";
        listOfSurvivors.add( text );
        for ( Zombie zombie: zombies ) {
            text += zombie.getId() + " " + zombie.getNome() + "\n";
        }
        listOfSurvivors.add( text );

        return listOfSurvivors;
    }

    public boolean isDay() {
        if ( dayNightCycle == 0 ) {
            return true;
        }

        return false;
    }

    public boolean hasEquipment( int creatureId, int equipmentTypeId ) {
        for ( Humano humano: humanos ) {
            if ( humano.getId() == creatureId ) {
                if ( humano.getEquipamentoApanhado() == null ) {
                    return false;
                }
                if ( humano.getEquipamentoApanhado().size() == 0 ) {
                    return false;
                }

                if ( humano.getEquipamentoApanhado().get(0).getTipo() == equipmentTypeId ) {
                    return true;
                } else {
                    return false;
                }
            }
        }

        return false;
    }
}
