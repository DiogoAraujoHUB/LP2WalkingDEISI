package pt.ulusofona.lp2.theWalkingDEISIGame;

import java.util.List;

public class Mapa {
    private int sizeX;
    private int sizeY;
    private MapPosition[][] map;

    Mapa() {
        this.sizeX = 0;
        this.sizeY = 0;
    }

    public void setSizeX( int sizeX ) {
        this.sizeX = sizeX;
    }

    public int getSizeX() {
        return this.sizeX;
    }

    public void setSizeY( int sizeY ) {
        this.sizeY = sizeY;
    }

    public int getSizeY() {
        return this.sizeY;
    }

    public void createMap() {
        map = new MapPosition[sizeX][sizeY];

        for ( int posX = 0; posX < sizeX; posX++ ) {
            for ( int posY = 0; posY < sizeY; posY++ ) {
                map[posX][posY] = new MapPosition();
            }
        }
    }

    public void addCreatures( List<Creature> creatures ) {
        for ( Creature creature: creatures ) {
            int xFound = creature.getX();
            int yFound = creature.getY();

            //Já está um humano na posicão?
            if ( map[xFound][yFound].getTipo() == 1 || map[xFound][yFound].getTipo() == 2 ) {
                continue;
            }
            //Já está um zombie na posição?
            if ( map[xFound][yFound].getTipo() == 3 ) {
                continue;
            }

            map[xFound][yFound].setTipo(2);    //1 is human with equipment //2 is human without equipment
            map[xFound][yFound].setCreature(creature);
        }
    }

    public void addEquipment( List<Equipamento> equipment ) {
        for ( Equipamento equipamento: equipment ) {
            int xFound = equipamento.getX();
            int yFound = equipamento.getY();

            if ( map[xFound][yFound].getTipo() != 0 ) {
                continue;
            }
            map[xFound][yFound].setTipo(-1);    //everything below 0 is equipment
            map[xFound][yFound].setEquipamento(equipamento);
        }
    }

    public void addSafeHavens( List<SafeHaven> safeHavens ) {
        for ( SafeHaven safeHaven: safeHavens ) {
            int xFound = safeHaven.getX();
            int yFound = safeHaven.getY();

            if ( map[xFound][yFound].getTipo() != 0 ) {
                continue;
            }

            map[xFound][yFound].setSafeHaven(safeHaven);
            map[xFound][yFound].setTipo(0);    //O tipo 0 é um SafeHaven
        }
    }

    public int getMapId( int x, int y ) {
        return map[x][y].getTipo();
    }

    public void setPositionType( int x, int y, int type ) {
        map[x][y].setTipo(type);
    }

    public MapPosition getPosition( int x, int y ) {
        return map[x][y];
    }
}
