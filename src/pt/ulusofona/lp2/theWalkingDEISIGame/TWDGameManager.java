package pt.ulusofona.lp2.theWalkingDEISIGame;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

public class TWDGameManager {
    List<Humano> humanos = new ArrayList<>();
    List<Zombie> zombies = new ArrayList<>();
    List<Equipamento> equipment = new ArrayList<>();
    int linesSize = 0;
    int columnsSize = 0;
    int initialTeam = 0;

    //Construtor vazio
    public TWDGameManager() {
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

                            linesSize = Integer.parseInt( splitNumLinesColumns[0].trim() );
                            columnsSize = Integer.parseInt( splitNumLinesColumns[1].trim() );
                            break;

                        case 2:
                            initialTeam = Integer.parseInt( lineRead.trim() );
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
                                    Zombie zombieCriado = new Zombie( creatureID, creatureName, spawnX, spawnY );
                                    zombies.add( zombieCriado );
                                } else if ( typeID == 1 ) {
                                    Humano humanoCriado = new Humano( creatureID, creatureName, spawnX, spawnY );
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
        if ( linesSize == 0 || columnsSize == 0 ) {
            return null;
        }

        worldSize[0] = linesSize;
        worldSize[1] = columnsSize;
        return worldSize;
    }

    //Devolve o ID da equipa que vai jogar no primeiro turno
    public int getInitialTeam() {
        return this.initialTeam;
    }

    //Devolve uma lista com todos os objetos "Humano" no jogo
    public List<Humano> getHumans() {
        return this.humanos;
    }

    //Devolve uma lista com todos os objetos "Zombie" no jogo
    public List<Zombie> getZombies() {
        return this.zombies;
    }

    //deve tentar executar uma jogada
    //xO, yO é uma origem
    //xD, yD é o destino
    public boolean move( int xO, int yO, int xD, int yD ) {
        if ( xD > linesSize || yD > columnsSize || xD < 0 || yD < 0 ) {
            return false;
        }
        if ( xO > linesSize || yO > columnsSize || xO < 0 || yO < 0 ) {
            return false;
        }

        return true;
    }

    //se uma das condições de paragem ja tenha sido alcançada
    //então retorna true
    public boolean gameIsOver() {
        return true;
    }
}
