package pt.ulusofona.lp2.theWalkingDEISIGame;

import java.util.List;

public class Mapa {
    private int sizeX;
    private int sizeY;
    private int[][] map;

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
        map = new int[sizeX][sizeY];
    }

    public void addHumans( List<Humano> humans ) {
        for ( Humano human: humans ) {
            int xFound = human.getX();
            int yFound = human.getY();

            if ( map[xFound][yFound] != 0 ) {
                continue;
            }
            map[xFound][yFound] = 2;    //1 is human with equipment //i think this method might be wrong
        }
    }

    public void addZombies( List<Zombie> zombies ) {
        for ( Zombie zombie: zombies ) {
            int xFound = zombie.getX();
            int yFound = zombie.getY();

            if ( map[xFound][yFound] != 0 ) {
                continue;
            }
            map[xFound][yFound] = 3;
        }
    }

    public void addEquipment( List<Equipamento> equipment ) {
        for ( Equipamento equipamento: equipment ) {
            int xFound = equipamento.getX();
            int yFound = equipamento.getY();

            if ( map[xFound][yFound] != 0 ) {
                continue;
            }
            map[xFound][yFound] = -1;   //previously -1
        }
    }

    public int getMapId( int x, int y ) {
        return map[x][y];
    }

    public void setPosition( int x, int y, int type ) {
        map[x][y] = type;
    }
}
