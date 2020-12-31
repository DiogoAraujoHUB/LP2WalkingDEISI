package pt.ulusofona.lp2.theWalkingDEISIGame;

import pt.ulusofona.lp2.theWalkingDEISIGame.classesCriaturas.*;
import pt.ulusofona.lp2.theWalkingDEISIGame.classesEquipamentos.*;

import java.io.*;
import java.util.*;

//Falta-me fazer com que o veneno funcione

public class TWDGameManager {

    //0 -> day and 1 -> night
    private int dayNightCycle;
    private int initialTeamId;
    private int currentTeamId;
    private int numberOfTurns;
    private int numberOfTurnsTotal;

    private List<Creature> creatures;
    private List<Equipamento> equipment;
    private List<SafeHaven> safeHavens;

    private Mapa gameMap;

    public TWDGameManager() {
        dayNightCycle = 0;
        initialTeamId = 0;
        currentTeamId = 0;
        numberOfTurns = 0;
        numberOfTurnsTotal = 0;

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
        numberOfTurnsTotal = 0;
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

                            gameMap.setSizeX( Integer.parseInt( splitNumLinesColumns[0].trim() ) );
                            gameMap.setSizeY( Integer.parseInt( splitNumLinesColumns[1].trim() ) );
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
                            //Skip over creatures
                            if ( numCreatures == 0 ) {
                                numFileLine = 4;
                            }
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
                            //Skip over equipment
                            if ( numEquipment == 0 ) {
                                numFileLine = 6;
                            }
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
                            //Skip over safe havens
                            if ( numSafeHavens == 0 ) {
                                numFileLine = 8;
                            }
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

        //Adiciona o que fomos buscar ao ficheiro para o mapa
        gameMap.addCreatures( creatures );
        gameMap.addEquipment( equipment );
        gameMap.addSafeHavens( safeHavens );
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

        //Making sure the last character in a name is a space
        if ( !Character.isWhitespace(name.charAt(name.length() - 1) ) ) {
            name += " ";
        }

        switch ( typeID ) {
            case 0: //Criança Zombie
                creatureFound = new CriancaZombie(id, name, spawnX, spawnY);
                creatures.add(creatureFound);
                break;

            case 1: //Adulto Zombie
                creatureFound = new AdultoZombie(id, name, spawnX, spawnY);
                creatures.add(creatureFound);
                break;

            case 2: //Militar Zombie
                creatureFound = new MilitarZombie(id, name, spawnX, spawnY);
                creatures.add(creatureFound);
                break;

            case 3: //Idoso Zombie
                creatureFound = new IdosoZombie(id, name, spawnX, spawnY);
                creatures.add(creatureFound);
                break;

            case 4: //Zombie Vampiro
                creatureFound = new VampiroZombie(id, name, spawnX, spawnY);
                creatures.add(creatureFound);
                break;

            case 5: //Criança Vivo
                creatureFound = new CriancaHumano(id, name, spawnX, spawnY);
                creatures.add(creatureFound);
                break;

            case 6: //Adulto Vivo
                creatureFound = new AdultoHumano(id, name, spawnX, spawnY);
                creatures.add(creatureFound);
                break;

            case 7: //Militar Vivo
                creatureFound = new MilitarHumano(id, name, spawnX, spawnY);
                creatures.add(creatureFound);
                break;

            case 8: //Idoso Vivo
                creatureFound = new IdosoHumano(id, name, spawnX, spawnY);
                creatures.add(creatureFound);
                break;

            case 9: //Cão
                creatureFound = new Cao(id, name, spawnX, spawnY);
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

        worldSize[0] = gameMap.getSizeX();
        worldSize[1] = gameMap.getSizeY();
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

    //O safe haven for null indica que não é uma porta
    //Ver se esta maneira passou o teste 10
    public boolean isDoorToSafeHaven(int x, int y) {
        return (gameMap.getPosition(x, y).getSafeHaven() != null);
    }

    //devolve o id do objeto/elemento que se encontra na posição indicada pelas
    //coordenadas (x,y) passadas no argumento
    public int getElementId(int x, int y) {
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

            case 2:
                mapId = 0;
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
        Creature creatureBeingAttacked = null;
        Creature creatureAttacking = null;

        //Apanhar o humano que está a tentar atacar
        if ( gameMap.getPosition(xO,yO).getCreature() instanceof Humano ) {
            creatureAttacking = gameMap.getPosition(xO,yO).getCreature();
        }
        //Se a criatura não for humano então não vamos atacar
        if ( creatureAttacking == null ) {
            return false;
        }

        //Verifica se tentamos atacar algo diferente de um zombie
        if ( gameMap.getPosition(xD,yD).getCreature() instanceof Zombie ) {
            creatureBeingAttacked = gameMap.getPosition(xD,yD).getCreature();
        }
        //Só vamos atacar um zombie, e nada mais
        if ( creatureBeingAttacked == null ) {
            return false;
        }

        //Vamos ver se o humano tem um equipamento para atacar com
        if ( creatureAttacking instanceof Humano ) {
            Equipamento equipamentoUtilizado = ((Humano) creatureAttacking).getEquipamentoApanhado();

            //Não conseguimos atacar se não tivermos um equipamento na mão
            if ( equipamentoUtilizado == null ) {
                return false;
            }

            //Não conseguimos atacar com um equipamento defensivo, logo tem que ser ofensivo
            if ( equipamentoUtilizado instanceof Ofensivo ) {
                //Kill zombie being attacked
                if ( ((Humano) creatureAttacking).attack(gameMap, creatureBeingAttacked, xD, yD) ) {
                    incrementaTempo();
                    creatureBeingAttacked.beDestroyed();

                    return true;
                }

                return false;
            }
        }

        return false;
    }

    public boolean attackZombie(int xO, int yO, int xD, int yD) {
        Creature creatureAttacking = null;
        Creature creatureBeingAttacked = null;

        //Verifica se tentamos atacar com um Zombie
        if ( gameMap.getPosition(xO,yO).getCreature() instanceof Zombie ) {
            creatureAttacking = gameMap.getPosition(xO,yO).getCreature();
        }
        //Estamos a tentar atacar com algo diferente de um zombie
        if ( creatureAttacking == null ) {
            return false;
        }

        //Verifica se estamos a tentar atacar um humano
        if ( gameMap.getPosition(xD,yD).getCreature() instanceof Humano ) {
            creatureBeingAttacked = gameMap.getPosition(xD,yD).getCreature();
        }
        //Só queremos atacar um humano
        if ( creatureBeingAttacked == null ) {
            return false;
        }

        //Vamos começar o ataque no humano!
        if ( creatureAttacking instanceof Zombie ) {

            //Vamos verificar se o humano tem algum equipamento para se defender com
            if ( creatureBeingAttacked instanceof Humano ) {
                //Um humano envenenado defende contra tudo
                if ( ((Humano) creatureBeingAttacked).getEnvenenado() ) {
                    incrementaTempo();
                    return true;
                }

                //Vamos verificar se o humano tem um equipamento equipado!
                Equipamento equipamentoApanhado = ((Humano) creatureBeingAttacked).getEquipamentoApanhado();
                if ( equipamentoApanhado != null ) {

                    //Como o humano tem um equipamento ofensivo, vai atacar o zombie para se defender
                    if ( equipamentoApanhado instanceof Ofensivo ) {
                        //xO and yO are the position of the zombie attacking
                        if ( ((Humano) creatureBeingAttacked).defendWithAttack(gameMap, creatureAttacking, xO, yO) ) {
                            incrementaTempo();
                            //Kill the zombie attacking
                            gameMap.setPositionType(xO,yO,0);
                            creatureAttacking.beDestroyed();

                            return true;
                        }
                    }

                    //Como o humano tem um equipamento defensivo, vai se defender de o zombie
                    if (equipamentoApanhado instanceof Defensivo) {
                        /*
                        if ( equipamentoApanhado instanceof Veneno || equipamentoApanhado instanceof Antidoto ) {
                            Creature zombieMade = ((Zombie) creatureAttacking).convert(gameMap, creatureBeingAttacked, xD, yD);
                            removeCreature(creatureBeingAttacked);
                            creatures.add(zombieMade);

                            //Como um humano foi convertido, então o número de turnos volta a zero
                            //Não tenho a certeza se sou suposto o numberOfTurns = 0 antes ou depois do incrementa tempo
                            incrementaTempo();
                            numberOfTurns = 0;
                            return true;
                        }
                         */

                        /*
                        //Defesa através da garrafa de lixivia
                        if (equipamentoApanhado instanceof GarrafaLixivia) {
                            if ( ((Humano) creatureBeingAttacked).defendWithAttack(gameMap, creatureAttacking, xO, yO) ) {
                                //Kill the zombie attacking
                                gameMap.setPositionType(xO,yO,0);
                                creatureAttacking.beDestroyed();

                                incrementaTempo();
                                return true;
                            }
                        }
                         */

                        if ( ((Humano) creatureBeingAttacked).defend(gameMap, creatureAttacking) ) {
                            incrementaTempo();

                            return true;
                        }
                    }
                }
            }

            //Se o humano tiver um equipamento que já não o protega, temos que o retirar da lista (partir)
            //(Não sei se temos de incrementar como ele tivesse partido o equipamento)
            if ( creatureBeingAttacked instanceof Humano ) {
                if ( ((Humano) creatureBeingAttacked).getEquipamentoApanhado() != null ) {
                    removeEquipment(((Humano) creatureBeingAttacked).getEquipamentoApanhado());
                    ((Humano) creatureBeingAttacked).setEquipamentoApanhado(null);
                }
            }

            //Vamos atacar com o zombie e converter o humano
            Creature zombieMade = ((Zombie) creatureAttacking).convert(gameMap, creatureBeingAttacked, xD, yD);
            removeCreature(creatureBeingAttacked);
            creatures.add(zombieMade);

            //Como um humano foi convertido, então o número de turnos volta a zero
            //Não tenho a certeza se sou suposto o numberOfTurns = 0 antes ou depois do incrementa tempo
            incrementaTempo();
            numberOfTurns = 0;
            return true;
        }

        return false;
    }

    //deve tentar executar uma jogada
    //xO, yO é uma origem
    //xD, yD é o destino
    public boolean move(int xO, int yO, int xD, int yD) {
        Creature creatureFound = null;

        //Não conseguimos mover para (ou atacar) um cão!
        if (gameMap.getPosition(xD,yD).getCreature() instanceof Cao) {
            return false;
        }

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

        //Os idosos humanos não se conseguem mover á noite!
        if (creatureFound instanceof IdosoHumano) {
            if ( !isDay() ) {
                return false;
            }
        }

        //Move creature onto a creature, attacking eachother
        if ( gameMap.getMapId(xO,yO) == 1 && gameMap.getMapId(xD, yD) == 1 ) {
            return attack(xO,yO,xD,yD);
        }

        //verifica se tamos a tentar mover para cima de uma criatura
        if ( gameMap.getMapId(xD,yD) == 1) {
            return false;
        }

        //Ocorre o movimento
        //Verificar se o humano está a andar para um safe haven
        if ( gameMap.getPosition(xD, yD).getSafeHaven() != null ) {
            if ( creatureFound instanceof Humano ) {

                gameMap.getPosition(xD, yD).getSafeHaven().moveIntoSafeHaven(gameMap, creatureFound);
                incrementaTempo();
                return true;
            }

            return false;
        }

        if ( gameMap.getMapId(xD, yD) == -1 ) {
            if ( gameMap.getPosition(xD,yD).getEquipamento() instanceof Antidoto ) {

                //Se a criatura for um animal, então nao pode mover-se para o antidoto
                if ( creatureFound instanceof Animal ) {
                    return false;
                }

                if (  creatureFound instanceof Humano ) {
                    //Verificar se estamos a mover para um antidoto sem estar envenenado
                    if ( !((Humano) creatureFound).getEnvenenado() ) {
                        return false;
                    }

                    //Se houver antidoto, ele vai beber
                    if ( ((Antidoto) gameMap.getPosition(xD,yD).getEquipamento()).getIsFull() ) {
                        ((Humano) creatureFound).beberAntidoto();
                        ((Antidoto) gameMap.getPosition(xD,yD).getEquipamento()).esvaziarAntidoto();
                    }
                }
            }

            //Vamos tentar apanhar um veneno
            if ( gameMap.getPosition(xD,yD).getEquipamento() instanceof Veneno ) {
                //Um animal não pode apanhar o veneno
                if ( creatureFound instanceof Animal ) {
                    return false;
                }

                //Se for um humano, então vamos tentar beber o veneno
                if ( creatureFound instanceof Humano ) {
                    //Já está envenenado, logo não pode apanhar o veneno
                    if ( ((Humano) creatureFound).getEnvenenado() ) {
                        return false;
                    }

                    //O humano vai ficar envenenado e vai esvaziar o veneno
                    if ( ((Veneno) gameMap.getPosition(xD,yD).getEquipamento()).getIsFull() ) {
                        ((Humano) creatureFound).beberVeneno();
                        ((Veneno) gameMap.getPosition(xD,yD).getEquipamento()).esvaziarVeneno();
                    }
                }
            }
        }

        //move normalmente
        creatureFound.move(gameMap, xD, yD);
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

        //Os vampiros só conseguem mover-se á noite! (Se for dia não se consegue mover)
        if ( zombieFound instanceof VampiroZombie ) {
            if ( isDay() ) {
                return false;
            }
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
            //O zombie não se pode mover para cima de veneno!
            if ( gameMap.getPosition(xD,yD).getEquipamento() instanceof Veneno ) {
                //O zombie so quer saber do veneno se ainda houver algum la dentro
                if ( ((Veneno) gameMap.getPosition(xD,yD).getEquipamento()).getIsFull() ) {
                    return false;
                }
            }

            //Se o equipamento for uma cabeca de alho, um zombie vampiro não pode destruir!
            if ( gameMap.getPosition(xD, yD).getEquipamento() instanceof CabecaAlho ) {
                if (gameMap.getPosition(xO,yO).getCreature() instanceof VampiroZombie) {
                    return false;
                }
            }

            //Vamos retirar o equipamento da lista
            removeEquipment(gameMap.getPosition(xD,yD).getEquipamento());
        }

        incrementaTempo();
        gameMap.getPosition(xO, yO).getCreature().move( gameMap, xD, yD );
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
        return verificaMovimento(deslocamento, xO, yO, xD, yD, creatureMoved);
    }

    //verifica se tenta mover mais que conseguimos
    public boolean verificaMovimento(int deslocamento, int xO, int yO, int xD, int yD, Creature creatureMoved) {
        for ( int pos = 1; pos <= deslocamento; pos++ ) {

            //A criatura consegue-se mover nas 4 direções principais
            if ( creatureMoved.getAbleToMoveLinearly() ) {
                //Direita
                if ( xO + pos == xD && yO == yD ) {
                    return verificaPassagem(xO, yO, pos, true, 1);
                }
                //Esquerda
                if( xO - pos == xD && yO == yD ) {
                    return verificaPassagem(xO, yO, pos, true, -1);
                }
                //Para baixo
                if ( xO == xD && yO + pos == yD ) {
                    return verificaPassagem(xO, yO, pos, false, 1);
                }
                //Para cima
                if ( xO == xD && yO - pos == yD ) {
                    return verificaPassagem(xO, yO, pos, false, -1);
                }
            }

            //A criatura consegue mover-se nas direções colatorais
            if ( creatureMoved.getAbleToMoveFreely() ) {
                //Canto inferior direito
                if ( xO + pos == xD && yO + pos == yD ) {
                    return verificaPassagemObliqua(xO, yO, pos, 1, 1);
                }
                //Canto superior direito
                if( xO + pos == xD && yO - pos == yD ) {
                    return verificaPassagemObliqua(xO, yO, pos, 1, -1);
                }
                //Canto inferior esquerdo
                if ( xO - pos == xD && yO + pos == yD ) {
                    return verificaPassagemObliqua(xO, yO, pos, -1, 1);
                }
                //Canto superior esquerdo
                if ( xO - pos == xD && yO - pos == yD ) {
                    return verificaPassagemObliqua(xO, yO, pos, -1, -1);
                }
            }
        }

        return false;
    }

    //Escolha Direcao == true implica que estamos a andar no x, e igual a false no y
    //Movimento so pode ser -1 e 1, e vai escolher o que fazemos á posição
    //Ou seja, se incrementamos ou se decrementamos
    public boolean verificaPassagem(int xO, int yO, int tamanhoPassagem, boolean escolhaDirecao, int movimento) {
        if ( tamanhoPassagem == 1 ) {
            return true;
        }

        for ( int pos = 1; pos < tamanhoPassagem; pos++ ) {
            if ( escolhaDirecao ) {
                if ( gameMap.getPosition(xO + ( movimento * pos ), yO).getSafeHaven() != null ) {
                    return false;
                }

                if ( gameMap.getMapId(xO + ( movimento * pos ), yO ) != 0 ) {
                    return false;
                }
            } else {
                if ( gameMap.getPosition(xO, yO + ( movimento * pos )).getSafeHaven() != null ) {
                    return false;
                }

                if ( gameMap.getMapId(xO, yO + ( movimento * pos ) ) != 0 ) {
                    return false;
                }
            }
        }

        return true;
    }

    //O movX e movY escolhem em que direção vamos em relação ao x e ao y so podem ser 1 e -1
    //Ou seja, se incrementamos ou se decrementamos
    public boolean verificaPassagemObliqua(int xO, int yO, int tamanhoPassagem, int movX, int movY) {
        if ( tamanhoPassagem == 1 ) {
            return true;
        }

        for ( int pos = 1; pos < tamanhoPassagem; pos++ ) {
            if ( gameMap.getPosition(xO + ( movX * pos ), yO + ( movY * pos) ).getSafeHaven() != null ) {
                return false;
            }

            if ( gameMap.getMapId(xO + ( movX * pos ), yO + ( movY * pos) ) != 0 ) {
                return false;
            }
        }

        return true;
    }

    public boolean removeCreature( Creature creatureFound ) {
        int pos = 0;
        for (Creature creature : creatures) {
            if (creature.getId() == creatureFound.getId()) {
                break;
            }

            pos++;
        }

        if (pos == creatures.size()) {
            return false;
        }

        creatures.remove(pos);
        return true;
    }

    public boolean removeEquipment(Equipamento equipamentFound) {
        int pos = 0;
        for (Equipamento equipamento : equipment) {
            if (equipamento.getId() == equipamentFound.getId()) {
                break;
            }

            pos++;
        }

        if (pos == equipment.size()) {
            return false;
        }

        equipment.remove(pos);
        return true;
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
        numberOfTurnsTotal++;
        incrementaTempoVeneno();

        if ( numberOfTurnsTotal % 2 == 0 ) {
            if ( dayNightCycle == 0 ) {
                dayNightCycle = 1;
            } else {
                dayNightCycle = 0;
            }
        }
    }

    //Vamos percorrer a lista de criaturas á procura de humanos que estejam envenenados
    //Para reduzir o número de turnos restantes para eles viverem
    public void incrementaTempoVeneno() {
        for (Creature creature: creatures) {
            if ( creature instanceof Humano ) {
                if ( ((Humano) creature).getEnvenenado() ) {
                    ((Humano) creature).decrementarTurnosRestantes();

                    if ( ((Humano) creature).getTurnosRestantes() == 0 ) {
                        creature.beDestroyed();
                        ((Humano) creature).beberAntidoto();
                        gameMap.setPositionType(creature.getX(),creature.getY(),0);
                    }
                }
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
        if ( checkNumberOfHumansOnMap() == 0 ) {
            return true;
        }
        if ( checkNumberOfZombiesOnMap() == 0 ) {
            return true;
        }

        return false;
    }

    public int checkNumberOfHumansOnMap(){
        int numHumans = 0;
        for (Creature creature: creatures) {
            if (creature instanceof Humano) {
                if ( ((Humano) creature).getInsideSafeHaven() ) {
                    continue;
                }
                if ( creature.getHasDied() ) {
                    continue;
                }

                numHumans++;
            }
        }

        return numHumans;
    }

    public int checkNumberOfZombiesOnMap(){
        int numZombies = 0;
        for (Creature creature: creatures) {
            if (creature instanceof Zombie) {
                if ( creature.getHasDied() ) {
                    continue;
                }

                numZombies++;
            }
        }

        return numZombies;
    }

    //Se der true quer dizer que é dia
    public boolean isDay() {
        return (dayNightCycle == 0);
    }

    public List<String> getGameResults() {
        List<String> gameResults = new ArrayList<>();
        String text = "Nr. de turnos terminados:";
        gameResults.add(text);
        text = "" + numberOfTurnsTotal;
        gameResults.add(text);
        gameResults.add("");

        text = "Ainda pelo bairro:";
        gameResults.add(text);

        text = "OS VIVOS";
        gameResults.add( text );
        for ( Creature creature: creatures ) {
            if ( creature instanceof Zombie ) {
                continue;   //Se estivermos a ver um zombie ou um cao
            }

            if ( creature.getHasDied() ) {
                continue;
            }
            if ( creature instanceof Humano ) {
                if ( ((Humano) creature).getInsideSafeHaven() ) {
                    continue;
                }
            }
            text = "" + creature.getId() + " " + creature.getNome().trim();
            gameResults.add(text);
        }
        gameResults.add("");

        text = "OS OUTROS";
        gameResults.add( text );
        for ( Creature creature: creatures ) {
            if ( creature instanceof Humano || creature instanceof Animal ) {
                continue;   //Se estivermos a ver um humano ou um animal vivo
            }
            if ( creature.getHasDied() ) {
                continue;
            }

            text = creature.getId() + " (antigamente conhecido como " + creature.getNome().trim() + ")";
            gameResults.add(text);
        }

        text = "Num safe haven:";
        gameResults.add(text);
        gameResults.add("");

        text = "OS VIVOS";
        gameResults.add( text );
        for ( Creature creature: creatures ) {
            if ( creature instanceof Humano ) {
                if ( ((Humano) creature).getInsideSafeHaven() ) {
                    text = "" + creature.getId() + " " + creature.getNome().trim();
                    gameResults.add(text);
                }
            }
        }
        gameResults.add("");

        text = "Envenenados / Destruídos";
        gameResults.add(text);

        text = "OS VIVOS";
        gameResults.add( text );
        for ( Creature creature: creatures ) {
            //Não queremos ver os zombies
            if ( creature instanceof Zombie ) {
                  continue;
            }
            if ( creature instanceof Humano ) {
                if ( ((Humano) creature).getEnvenenado() ) {
                    text = "" + creature.getId() + " " + creature.getNome().trim();
                    gameResults.add(text);
                }

                if ( creature.getHasDied() ) {
                    text = "" + creature.getId() + " " + creature.getNome().trim();
                    gameResults.add(text);
                }
            } else {
                if ( creature.getHasDied() ) {
                    text = "" + creature.getId() + " " + creature.getNome().trim();
                    gameResults.add(text);
                }
            }
        }
        gameResults.add("");

        text = "OS OUTROS";
        gameResults.add( text );
        for ( Creature creature: creatures ) {
            if ( creature instanceof Humano || creature instanceof Animal ) {
                continue;   //Se estivermos a ver um humano ou um animal vivo
            }

            if ( creature.getHasDied() ) {
                text = creature.getId() + " (antigamente conhecido como " + creature.getNome().trim() + ")";
                gameResults.add(text);
            }
        }
        gameResults.add("");

        return gameResults;
    }

    public int getEquipmentId(int creatureId) {
        if ( creatures == null || creatures.size() == 0 ) {
            return 0;
        }

        //Percorrer a lista de criaturas á procura da criatura que é a que procuramos
        for ( Creature creature : creatures ) {

            //Encontramos o id que procuravamos
            if ( creature.getId() == creatureId ) {

                //Se for humano, então consegue aguentar um equipamento
                if ( creature instanceof Humano ) {
                    Equipamento equipamentoHumano = ((Humano) creature).getEquipamentoApanhado();

                    //Vamos verificar se o humano tem equipamento
                    if ( equipamentoHumano == null ) {
                        return 0;
                    }

                    return equipamentoHumano.getId();
                }

                //Se for cão, então consegue aguentar um equipamento
                if ( creature instanceof Cao) {
                    Equipamento equipamentoCao = ((Cao) creature).getEquipamentoApanhado();

                    //Vamos verificar se o humano tem equipamento
                    if ( equipamentoCao == null ) {
                        return 0;
                    }

                    return equipamentoCao.getId();
                }
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
        for ( Creature creature: creatures ) {
            if ( creature == null ) {
                continue;
            }

            if ( creature instanceof Humano ) {
                if (((Humano) creature).getInsideSafeHaven()) {
                    idsInSafeHaven.add(creature.getId());
                }
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
                name = "Garrafa de Lixívia (1 litro)";
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
        BufferedWriter writer = null;

        try {
            if ( !fich.exists() ) {
                fich.createNewFile();
            }
            FileWriter fileWriter = new FileWriter(fich);
            writer = new BufferedWriter(fileWriter);

            writer.write(gameMap.getSizeX() + " " + gameMap.getSizeY());
            writer.flush();
            writer.write(initialTeamId);
            writer.flush();
            writer.write(creatures.size());
            writer.flush();

            for ( Creature creature: creatures ) {
                if ( creature == null ) {
                    continue;
                }

                writer.write(creature.getId() + " : " + creature.getTipo() + " : " + creature.getNome() +
                        " : " + creature.getX() + " : " + creature.getY() );
                writer.flush();
            }

            writer.write(equipment.size());

            for ( Equipamento equipamento: equipment ) {
                if ( equipamento == null ) {
                    continue;
                }

                writer.write(equipamento.getId() + " : " + equipamento.getTipo() + " : "
                        + equipamento.getX() + " : " + equipamento.getY() );
                writer.flush();
            }

            writer.write(safeHavens.size());
            writer.flush();

            for ( SafeHaven safeHaven: safeHavens ) {
                if ( safeHaven == null ) {
                    continue;
                }

                writer.write(safeHaven.getX() + " : " + safeHaven.getY() );
                writer.flush();
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
        numberOfTurnsTotal = 0;
        dayNightCycle = 0;

        int numFileLine = 1;
        int numCreatures = 0;
        int numEquipment = 0;
        int numSafeHavens = 0;

        try {
            BufferedReader reader = new BufferedReader( new FileReader( fich ) );
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
                            if ( initialTeamId == 10 || initialTeamId == 20 ) {
                                currentTeamId = initialTeamId;
                            } else {
                                return false;
                            }
                            break;

                        case 3:
                            numCreatures = Integer.parseInt( lineRead.trim() );
                            //Skip over creatures
                            if ( numCreatures == 0 ) {
                                numFileLine = 4;
                            }
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
                            //Skip over equipment
                            if ( numEquipment == 0 ) {
                                numFileLine = 6;
                            }
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
                            //Skip over safe havens
                            if ( numSafeHavens == 0 ) {
                                numFileLine = 8;
                            }
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

        //Adiciona o que fomos buscar ao ficheiro para o mapa
        gameMap.addCreatures( creatures );
        gameMap.addEquipment( equipment );
        gameMap.addSafeHavens( safeHavens );
        return true;
    }

    public String[] popCultureExtravaganza() {
        String[] popCulture = new String[14];

        popCulture[0] = "Resident Evil";
        popCulture[1] = "Ash vs. Evil Dead";
        popCulture[2] = "The Night Eats the World";
        popCulture[3] = "Gremlins";
        popCulture[4] = "";
        popCulture[5] = "";
        popCulture[6] = "";
        popCulture[7] = "";
        popCulture[8] = "";
        popCulture[9] = "";
        popCulture[10] = "";
        popCulture[11] = "";
        popCulture[12] = "";
        popCulture[13] = "";


        return popCulture;
    }
}



