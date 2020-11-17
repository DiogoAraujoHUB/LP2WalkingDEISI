package pt.ulusofona.lp2.theWalkingDEISIGame;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

public class TWDGameManager {

    //0 -> day and 1 -> night
    private int dayNightCycle;
    private int initialTeamId;
    private int currentTeamId;
    private boolean fairPlay;

    private List<Humano> humanos;
    private List<Zombie> zombies;
    private List<String> currentSurvivors;
    private List<Equipamento> equipment;

    private Mapa gameMap;

    public TWDGameManager() {
        dayNightCycle = 0;
        humanos = new ArrayList<>();
        zombies = new ArrayList<>();
        currentSurvivors = new ArrayList<>();
        equipment = new ArrayList<>();
        gameMap = new Mapa();
        initialTeamId = 0;
        currentTeamId = 0;
        fairPlay = false;
    }

    //passar os dois fors para uma função
    //tornar isto mais pequeno

    //leitura do ficheiro texto
    //e carregar para a memória a informação relevante
    public boolean startGame( File ficheiroInicial ) {
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
                            gameMap.setSizeY( Integer.parseInt( splitNumLinesColumns[1].trim() ) );
                            gameMap.createMap();
                            break;

                        case 2:
                            initialTeamId = Integer.parseInt( lineRead.trim() );
                            break;

                        case 3:
                            lineRead = lineRead.trim();

                            numCreatures = Integer.parseInt( lineRead );
                            break;

                        case 4:
                            for ( int pos = 0; pos < numCreatures; pos++ ) {
                                String[] splitCreatures = lineRead.split(":", 5 );

                                int creatureID = Integer.parseInt( splitCreatures[0].trim() );
                                int typeID = Integer.parseInt( splitCreatures[1].trim() );
                                String creatureName = splitCreatures[2];
                                int spawnX = Integer.parseInt( splitCreatures[3].trim() );
                                int spawnY = Integer.parseInt( splitCreatures[4].trim() );

                                if ( typeID == 0 ) {
                                    Zombie zombieCriado = new Zombie( creatureID, typeID, creatureName, spawnX, spawnY );
                                    zombies.add( zombieCriado );
                                } else if ( typeID == 1 ) {
                                    Humano humanoCriado = new Humano( creatureID, typeID, creatureName, spawnX, spawnY );
                                    humanos.add( humanoCriado );
                                    currentSurvivors.add( humanoCriado.getNome() );
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
        if ( x > gameMap.getSizeX() || y > gameMap.getSizeY() || x < 0 || y < 0 ) {
            return 0;
        }
        if (gameMap == null ) {
            return 0;
        }

        return gameMap.getMapId(x, y);
    }

    //deve tentar executar uma jogada
    //xO, yO é uma origem
    //xD, yD é o destino
    public boolean move( int xO, int yO, int xD, int yD ) {
        if ( xD > gameMap.getSizeX() || yD > gameMap.getSizeY() || xD < 0 || yD < 0 ) {
            return false;
        }
        if ( xO > gameMap.getSizeX() || yO > gameMap.getSizeY() || xO < 0 || yO < 0 ) {
            return false;
        }

        if ( dayNightCycle == 0 ) {
            dayNightCycle = 1;
        } else {
            dayNightCycle = 0;
        }
        fairPlay = true;
        return true;
    }

    //se uma das condições de paragem ja tenha sido alcançada
    //então retorna true
    public boolean gameIsOver() {
        if ( fairPlay ) {
            return true;
        }

        return false;
    }

    public List<String> getSurvivors() {
        return this.currentSurvivors;
    }

    public boolean isDay() {
        if ( dayNightCycle == 0 ) {
            return true;
        }

        return false;
    }

    public boolean hasEquipment( int creatureId, int equipmentTypeId ) {
        if ( creatureId == 0 ) {
            return true;
        } else if ( creatureId == 1 ) {
            return true;
        }

        return false;
    }
}
