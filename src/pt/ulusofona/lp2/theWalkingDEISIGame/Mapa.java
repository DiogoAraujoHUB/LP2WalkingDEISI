package pt.ulusofona.lp2.theWalkingDEISIGame;

import java.util.List;

public class Mapa {
    private int sizeX;
    private int sizeY;
    private MapPosition[][] map;

    public Mapa() {
        this.sizeX = 0;
        this.sizeY = 0;

        map = null;
    }

    public void setSizeY(int sizeY) {
        this.sizeY = sizeY;
    }

    public void setSizeX(int sizeX) {
        this.sizeX = sizeX;
    }

    public int getSizeY() {
        return this.sizeY;
    }

    public int getSizeX() {
        return this.sizeX;
    }

    public void createMap() {
        //System.out.println(" in create - size of map (x,y) = (" + x + ", " + y + ")");

        map = new MapPosition[sizeX][sizeY];

        for (int posY = 0; posY < sizeY; posY++ ) {
            for (int posX = 0; posX < sizeX; posX++ ) {
                map[posX][posY] = new MapPosition();
            }
        }

        //drawMap();
    }

    public void drawMap() {
        for (int posY = 0; posY < sizeY; posY++ ) {
            for (int posX = 0; posX < sizeX; posX++ ) {
                System.out.print(map[posX][posY].getTipo() + " - ");
            }
            System.out.println();
        }
    }

    public void addCreatures( List<Creature> creatures ) {

        for ( Creature creature: creatures ) {
            int xFound = creature.getX();
            int yFound = creature.getY();

            //Já está uma criatura na posicão?
            if ( map[xFound][yFound].getTipo() != 0 ) {
                continue;
            }

            if ( creature.getHasDied() ) {
                map[xFound][yFound].setTipo(0);
                map[xFound][yFound].setCreature(null);
                continue;
            }

            if ( creature instanceof Humano ) {
                if ( ((Humano) creature).getInsideSafeHaven() ) {
                    map[xFound][yFound].setTipo(0);
                    map[xFound][yFound].setCreature(null);
                    continue;
                }
            }

            map[xFound][yFound].setTipo(1);
            map[xFound][yFound].setCreature(creature);
        }
    }

    public void addEquipment( List<Equipamento> equipment ) {
        for ( Equipamento equipamento: equipment ) {
            int xFound = equipamento.getX();
            int yFound = equipamento.getY();

            //Já está um equipamento na posição?
            if ( map[xFound][yFound].getTipo() == -1 ) {
                continue;
            }
            //Já está uma creatura na posição?
            //Se ja estiver, e a criatura não for um zombie, então damos o equipamento
            if ( map[xFound][yFound].getTipo() == 1 ) {
                Creature creatureFound = map[xFound][yFound].getCreature();

                //Se for um zombie, então destroi o equipamento
                if ( creatureFound instanceof Zombie ) {
                    creatureFound.pickEquipment();
                }
                //Se for um humano ou um cão, então apanha o equipamento
                if ( creatureFound instanceof Humano ) {
                    ((Humano) creatureFound).setEquipamentoApanhado(equipamento);
                    creatureFound.pickEquipment();
                }
                if ( creatureFound instanceof Cao ) {
                    ((Cao) creatureFound).setEquipamentoApanhado(equipamento);
                    creatureFound.pickEquipment();
                }

                continue;
            }

            //Na posição não está nada, logo pusemos o equipamento no chão
            map[xFound][yFound].setTipo(-1);    //Tipo -1 é equipamento
            map[xFound][yFound].setEquipamento(equipamento);
        }
    }

    public void addSafeHavens( List<SafeHaven> safeHavens ) {
        for ( SafeHaven safeHaven: safeHavens ) {
            int xFound = safeHaven.getX();
            int yFound = safeHaven.getY();

            //Verificar se podemos por a porta no chão
            if ( map[xFound][yFound].getTipo() != 0 ) {
                continue;
            }

            map[xFound][yFound].setSafeHaven(safeHaven);
            map[xFound][yFound].setTipo(2); //O tipo 2 é um SafeHaven
        }
    }

    public int getMapId(int x, int y) {
        return map[x][y].getTipo();
    }

    public void setPositionType(int x, int y, int type) {
        map[x][y].setTipo(type);
    }

    public MapPosition getPosition( int x, int y ) {
        return map[x][y];
    }
}
