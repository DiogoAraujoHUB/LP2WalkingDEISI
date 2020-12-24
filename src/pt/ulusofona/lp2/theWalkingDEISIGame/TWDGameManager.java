package pt.ulusofona.lp2.theWalkingDEISIGame;

import pt.ulusofona.lp2.theWalkingDEISIGame.classesCriaturas.*;
import pt.ulusofona.lp2.theWalkingDEISIGame.classesEquipamentos.*;

import java.io.*;
import java.sql.SQLOutput;
import java.util.*;

public class TWDGameManager {

    //0 -> day and 1 -> night
    private int dayNightCycle;
    private int initialTeamId;
    private int currentTeamId;
    private int numberOfTurns;
    private int numberOfTurnsToPassDays;

    private List<Creature> creatures;
    private List<Equipamento> equipment;
    private List<SafeHaven> safeHavens;

    private Mapa gameMap;

    public TWDGameManager() {
        dayNightCycle = 0;
        initialTeamId = 0;
        currentTeamId = 0;
        numberOfTurns = 0;
        numberOfTurnsToPassDays = 0;

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

                                if ( !createCreatureWithParameters(creatureID, typeID, creatureName, spawnX, spawnY) ) {
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

                                if ( !createEquipmentWithParameters(equipmentID, typeID, spawnX, spawnY) ) {
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
                                String[] splitSafeHaven = lineRead.split(":" );
                                int spawnX = Integer.parseInt( splitSafeHaven[0].trim() );
                                int spawnY = Integer.parseInt( splitSafeHaven[1].trim() );

                                SafeHaven safeHavenDoor = new SafeHaven( spawnX, spawnY );
                                safeHavens.add( safeHavenDoor );

                                if ( pos == numSafeHavens - 1 ) {
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

    public boolean createEquipmentWithParameters(int id, int typeID, int spawnX, int spawnY) {
        Equipamento equipmentFound = null;

        switch ( typeID ) {
            case 0: //Escudo de Madeira
                equipmentFound = new EscudoMadeira(id,typeID,spawnX,spawnY);
                equipment.add(equipmentFound);
                break;

            case 1: //Espada Hattori Hanzo
                equipmentFound = new EspadaHattoriHanzo(id,typeID,spawnX,spawnY);
                equipment.add(equipmentFound);
                break;

            case 2: //Pistola Walther PPK
                equipmentFound = new PistolaWaltherPPK(id,typeID,spawnX,spawnY);
                equipment.add(equipmentFound);
                break;

            case 3: //Escudo Táctico
                equipmentFound = new EscudoTactico(id,typeID,spawnX,spawnY);
                equipment.add(equipmentFound);
                break;

            case 4: //Revista Maria
                //So funciona contra ataques de zombies idosos
                equipmentFound = new RevistaMaria(id,typeID,spawnX,spawnY);
                equipment.add(equipmentFound);
                break;

            case 5: //Cabeça de Alho
                //So funciona contra ataques de zombies vampiros
                equipmentFound = new CabecaAlho(id,typeID,spawnX,spawnY);
                equipment.add(equipmentFound);
                break;

            case 6: //Estaca de Madeira
                //Consegue matar zombies vampiros e zombies normais
                equipmentFound = new EstacaMadeira(id,typeID,spawnX,spawnY);
                equipment.add(equipmentFound);
                break;

            case 7: //Garrafa de Lixivía (1 litro)
                equipmentFound = new GarrafaLixivia(id,typeID,spawnX,spawnY);
                equipment.add(equipmentFound);
                break;

            case 8: //Veneno
                equipmentFound = new Veneno(id,typeID,spawnX,spawnY);
                equipment.add(equipmentFound);
                break;

            case 9: //Antidoto (para os envenenados)
                equipmentFound = new Antidoto(id,typeID,spawnX,spawnY);
                equipment.add(equipmentFound);
                break;

            case 10: //Beskar Helmet
                equipmentFound = new BeskarHelmet(id,typeID,spawnX,spawnY);
                equipment.add(equipmentFound);
                break;

            default:
                return false;
        }

        return true;
    }

    public boolean createCreatureWithParameters(int id, int typeID, String name, int spawnX, int spawnY) {
        Creature creatureFound = null;

        switch ( typeID ) {
            case 0: //Criança Zombie
                creatureFound = new CriancaZombie(id, typeID, name, spawnX, spawnY);
                creatures.add(creatureFound);
                break;

            case 1: //Adulto Zombie
                creatureFound = new AdultoZombie(id, typeID, name, spawnX, spawnY);
                creatures.add(creatureFound);
                break;

            case 2: //Militar Zombie
                creatureFound = new MilitarZombie(id, typeID, name, spawnX, spawnY);
                creatures.add(creatureFound);
                break;

            case 3: //Idoso Zombie
                creatureFound = new IdosoZombie(id, typeID, name, spawnX, spawnY);
                creatures.add(creatureFound);
                break;

            case 4: //Zombie Vampiro
                creatureFound = new VampiroZombie(id, typeID, name, spawnX, spawnY);
                creatures.add(creatureFound);
                break;

            case 5: //Criança Vivo
                creatureFound = new CriancaHumano(id, typeID, name, spawnX, spawnY);
                creatures.add(creatureFound);
                break;

            case 6: //Adulto Vivo
                creatureFound = new AdultoHumano(id, typeID, name, spawnX, spawnY);
                creatures.add(creatureFound);
                break;

            case 7: //Militar Vivo
                creatureFound = new MilitarHumano(id, typeID, name, spawnX, spawnY);
                creatures.add(creatureFound);
                break;

            case 8: //Idoso Vivo
                creatureFound = new IdosoHumano(id, typeID, name, spawnX, spawnY);
                creatures.add(creatureFound);
                break;

            case 9: //Cão
                creatureFound = new Cao(id, typeID, name, spawnX, spawnY);
                creatures.add(creatureFound);
                break;

            default:
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

    //Devolve uma lista com todos os objetos "SafeHaven" no jogo
    public List<SafeHaven> getSafeHavens() {
        return this.safeHavens;
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

    public boolean isDoorToSafeHaven(int x, int y) {
        if ( gameMap.getPosition(x, y).getSafeHaven() != null ) {
            return true;
        }

        return false;
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
            case 0:
                mapId = 0;
                break;

            case -1:
                Equipamento equipamento = gameMap.getPosition(x,y).getEquipamento();
                mapId = equipamento.getId();
                break;


            case 1:
                Creature creature = gameMap.getPosition(x,y).getCreature();
                mapId = creature.getId();
                break;

            default:
                mapId = 0;
        }

        return mapId;
    }

    public boolean attack(int xO, int yO, int xD, int yD) {
        if ( currentTeamId == 20 ) {
            return attackZombie(xO, yO, xD, yD);
        }

        //verifica se tentamos atacar com um zombie
        if ( gameMap.getPosition(xO,yO).getCreature() instanceof Zombie ) {
            return false;
        }

        Creature creatureFound = gameMap.getPosition(xO,yO).getCreature();
        if ( creatureFound == null ) {

        }

        return true;
    }

    public boolean attackZombie(int xO, int yO, int xD, int yD) {
        //verifica se tentamos atacar com um humano
        if ( gameMap.getPosition(xO,yO).getCreature() instanceof Humano ) {
            return false;
        }

        return false;
    }

    //deve tentar executar uma jogada
    //xO, yO é uma origem
    //xD, yD é o destino
    public boolean move(int xO, int yO, int xD, int yD) {
        Creature creatureFound = null;

        //Verifica se a equipa atual é a de zombies
        if ( currentTeamId == 20 ) {
            return moveZombie(xO, yO, xD, yD);
        }
        //verifica se tentamos mover um zombie
        if ( gameMap.getPosition(xO,yO).getCreature() instanceof Zombie ) {
            return false;
        }

        //Temos que mover um humano ou um animal!
        if ( gameMap.getPosition(xO,yO).getCreature() instanceof Humano
                || gameMap.getPosition(xO,yO).getCreature() instanceof Animal ) {
            creatureFound = gameMap.getPosition(xO, yO).getCreature();
        }

        if ( creatureFound == null ) {
            return false;
        }
        if ( !verificaCondicoes(xO, yO, xD, yD, creatureFound) ) {
            return false;
        }

        //Move creature onto a creature, attacking eachother
        if ( gameMap.getMapId(xO,yO) == 1 && gameMap.getMapId(xD, yD) == 1 ) {
            return attack(xO,yO,xD,yD);
        }

        //verifica se tamos a tentar mover para cima de uma criatura
        if ( gameMap.getMapId(xD,yD) == 1) {
            return false;
        }

        //ocorre o movimento
        int tipoMovido = gameMap.getMapId( xO, yO );

        //Verificar se o humano está a andar para um safe haven
        if ( gameMap.getPosition(xD, yD).getSafeHaven() != null ) {
            gameMap.getPosition(xD, yD).getSafeHaven()
                    .moveIntoSafeHaven(gameMap, creatureFound, creatures);
            incrementaTempo();
            return true;
        }

        //ve se estamos a mover para cima de uma arma
        if ( gameMap.getMapId( xD, yD ) == -1 ) {
            tipoMovido = 1;
        }

        //move normalmente
        creatureFound.move( gameMap, xD, yD, tipoMovido );
        incrementaTempo();
        return true;
    }

    public boolean moveZombie( int xO, int yO, int xD, int yD ) {
        Creature zombieFound = null;

        //Temos que mover um zombie!
        if ( gameMap.getPosition(xO,yO).getCreature() instanceof Zombie ) {
            zombieFound = gameMap.getPosition(xO, yO).getCreature();
        }

        if ( zombieFound == null ) {
            return false;
        }
        if ( !verificaCondicoes(xO, yO, xD, yD, zombieFound) ) {
            return false;
        }

        //Move creature onto a creature, attacking eachother
        if ( gameMap.getMapId(xO,yO) == 1 && gameMap.getMapId(xD, yD) == 1 ) {
            return attackZombie(xO,yO,xD,yD);
        }

        //verifica se tamos a tentar mover para cima de uma criatura
        if ( gameMap.getMapId(xD,yD) == 1) {
            return false;
        }

        //verifica se tentamos mover um humano
        if ( gameMap.getPosition(xO,yO).getCreature() instanceof Humano ) {
            return false;
        }
        //Verifica se estamos a tentar mover para um safe haven
        if ( gameMap.getPosition(xD, yD).getSafeHaven() != null ) {
            return false;
        }

        //Zombie moves onto equipment
        if ( gameMap.getPosition(xD, yD).getTipo() == -1 ) {
            retiraEquipamento( xD, yD );
        }

        incrementaTempo();
        int tipoMovido = gameMap.getMapId( xO, yO );

        gameMap.getPosition(xO, yO).getCreature().move( gameMap, xD, yD, tipoMovido );
        return true;
    }

    public boolean verificaCondicoes( int xO, int yO, int xD, int yD, Creature creatureMoved ) {

        //verifica se os parametros introduzidos estáo corretos para o mapa
        if ( xD >= gameMap.getSizeX() || yD >= gameMap.getSizeY() || xD < 0 || yD < 0 ) {
            return false;
        }
        if ( xO >= gameMap.getSizeX() || yO >= gameMap.getSizeY() || xO < 0 || yO < 0 ) {
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

        int deslocamento = creatureMoved.getDeslocamentoMaximo();
        return verificaMovimento(deslocamento, xO, yO, xD, yD);
    }

    //verifica se tenta mover mais que conseguimos
    public boolean verificaMovimento(int deslocamento, int xO, int yO, int xD, int yD) {
        for ( int pos = 1; pos <= deslocamento; pos++ ) {

            if ( xO + pos == xD && yO == yD ) {
                return verificaPassagem(xO, yO, pos, true, 1);
            }
            if( xO - pos == xD && yO == yD ) {
                if ( !verificaPassagem(xO, yO, pos, true, -1) ) {
                    return false;
                }

                return true;
            }
            if ( xO == xD && yO + pos == yD ) {
                if ( !verificaPassagem(xO, yO, pos, false, 1) ) {
                    return false;
                }

                return true;
            }
            if ( xO == xD && yO - pos == yD ) {
                if ( !verificaPassagem(xO, yO, pos, false, -1) ) {
                    return false;
                }

                return true;
            }
        }

        return false;
    }

    //Escolha Direcao == true implica que estamos a andar no x, e igual a false no y
    //Movimento so pode ser -1 e 1, e vai escolher o que fazemos á posição
    public boolean verificaPassagem(int xO, int yO, int tamanhoPassagem, boolean escolhaDirecao, int movimento) {

        for ( int pos = 1; pos <= tamanhoPassagem; pos++ ) {
            if ( escolhaDirecao ) {
                /*
                if ( gameMap.getMapId(xO + ( movimento * pos ), yO ) == -1 ) {
                    return true;
                }
                 */

                if ( gameMap.getMapId(xO + ( movimento * pos ), yO ) == 1 ) {
                    return false;
                }
            } else {
                /*
                if ( gameMap.getMapId(xO, yO + ( movimento * pos ) ) == -1 ) {
                    return true;
                }
                 */

                if ( gameMap.getMapId(xO, yO + ( movimento * pos ) ) == 1 ) {
                    return false;
                }
            }
        }

        return true;
    }

    public boolean removeCreature( Creature creatureFound ) {
        if ( creatureFound == null ) {
            return false;
        }

        int pos = 0;
        for ( Creature creature : creatures ) {
            if ( creatureFound.getId() == creature.getId() ) {
                creatures.remove(pos);
                return true;
            }

            pos++;
        }

        return false;
    }

    /*
    //este movimento é automático e feito para o zombie
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
        numberOfTurnsToPassDays++;

        if ( numberOfTurnsToPassDays % 2 == 0 ) {
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

    public int getEquipmentId( int creatureId ) {
        if ( creatures == null || creatures.size() == 0 ) {
            return 0;
        }

        //Percorrer a lista de criaturas á procura da criatura que é a que procuramos
        for ( Creature creature : creatures ) {
            if ( creature.getId() == creatureId ) {

                //Se for humano, então consegue aguentar uma arma
                //Logo vamos verificar se a criatura é um humano
                if ( creature instanceof Humano ) {
                    Equipamento equipamentoHumano = ((Humano) creature).getEquipamentoApanhado();

                    //Vamos verificar se o humano tem equipamento
                    if ( equipamentoHumano == null ) {
                        return 0;
                    }
                    return equipamentoHumano.getId();
                }

                return 0;
            }
        }

        return 0;
    }

    public int getEquipmentTypeId(int equipmentId) {
        if ( equipment == null || equipment.size() == 0 ) {
            return 0;
        }

        for (Equipamento equipamento: equipment) {
            if ( equipamento.getId() == equipmentId ) {
                return equipamento.getTipo();
            }
        }

        return 0;
    }

    public List<Integer> getIdsInSafeHaven() {
        if ( safeHavens == null ) {
            return null;
        }

        List<Integer> idsInSafeHaven = new ArrayList<>();
        for ( SafeHaven safeHaven: safeHavens ) {
            if ( safeHaven == null ) {
                continue;
            }

            for ( Creature creature: safeHaven.getHumansInSafeHaven() ) {
                if ( creature == null ) {
                    continue;
                }

                idsInSafeHaven.add( creature.getId() );
            }
        }

        return idsInSafeHaven;
    }

    public String getEquipmentInfo(int equipmentId) {
        if ( equipment == null || equipment.size() == 0 ) {
            return null;
        }

        for ( Equipamento equipamento: equipment ) {
            if ( equipamento.getId() == equipmentId ) {
                int tipo = equipamento.getTipo();
                String nomeTipo = getEquipmentName(tipo);
                String text = "";

                switch (tipo) {
                    case 0:
                    case 2:
                    case 7:
                        text = nomeTipo + " | " + equipamento.getNumUses();
                        break;

                    default:
                        text = nomeTipo;
                }

                return text;
            }
        }

        return null;
    }

    public String getEquipmentName(int tipo) {
        String name = "";

        switch (tipo) {
            case 0:
                name = "Escudo de Madeira";
                break;

            case 1:
                name = "Espada Hattori Hanzo";
                break;

            case 2:
                name = "Pistola Walther PPK";
                break;

            case 3:
                name = "Escudo Táctico";
                break;

            case 4:
                name = "Revista Maria";
                break;

            case 5:
                name = "Cabeça de Alho";
                break;

            case 6:
                name = "Estaca de Madeira";
                break;

            case 7:
                name = "Garrafa de lixívia";
                break;

            case 8:
                name = "Veneno";
                break;

            case 9:
                name = "Antidoto";
                break;

            case 10:
                name = "Beskar helmet";
                break;

            default:
                name = "Invalido";
        }

        return name;
    }

    //Save current state of game onto file
    public boolean saveGame(File fich) {
        String text = "";

        try {
            FileWriter fileWriter = new FileWriter(fich);
            fileWriter.flush();

            PrintWriter writer = new PrintWriter(fileWriter);

            writer.print(gameMap.getSizeX() + " " + gameMap.getSizeY());
            writer.print(initialTeamId);
            writer.print(creatures.size());

            for ( Creature creature: creatures ) {
                if ( creature == null ) {
                    continue;
                }

                writer.print(creature.getId() + " : " + creature.getTipo() + " : " + creature.getNome() +
                        " : " + creature.getX() + " : " + creature.getY() );
            }

            writer.print(equipment.size());

            for ( Equipamento equipamento: equipment ) {
                if ( equipamento == null ) {
                    continue;
                }

                writer.print(equipamento.getId() + " : " + equipamento.getTipo() + " : "
                        + equipamento.getX() + " : " + equipamento.getY() );
            }

            writer.print(safeHavens.size());

            for ( SafeHaven safeHaven: safeHavens ) {
                if ( safeHaven == null ) {
                    continue;
                }

                writer.print(safeHaven.getX() + " : " + safeHaven.getY() );
            }

            writer.close();
        } catch ( IOException e ) {
            System.out.println("Exception IO on Save Game");
            return false;
        }

        return true;
    }

    public boolean loadGame(File fich) {
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
            BufferedReader reader = new BufferedReader( new FileReader(fich) );
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

                                if ( !createCreatureWithParameters(creatureID, typeID, creatureName, spawnX, spawnY) ) {
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

                                if ( !createEquipmentWithParameters(equipmentID, typeID, spawnX, spawnY) ) {
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
                                String[] splitSafeHaven = lineRead.split(":" );
                                int spawnX = Integer.parseInt( splitSafeHaven[0].trim() );
                                int spawnY = Integer.parseInt( splitSafeHaven[1].trim() );

                                SafeHaven safeHavenDoor = new SafeHaven( spawnX, spawnY );
                                safeHavens.add( safeHavenDoor );

                                if ( pos == numSafeHavens - 1 ) {
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

    public String[] popCultureExtravaganza() {
        String[] popCulture = new String[11];

        return popCulture;
    }
}
