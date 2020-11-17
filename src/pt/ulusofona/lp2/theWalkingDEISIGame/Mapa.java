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

    public void createMap(List<Humano> humans, List<Zombie> zombies, List<Equipamento> equipment ) {
        map = new int[sizeX][sizeY];

        for ( Humano human: humans ) {
            int xFound = human.getX();
            int yFound = human.getY();

            map[xFound][yFound] = human.getTipo();
        }
        for ( Zombie zombie: zombies ) {
            int xFound = zombie.getX();
            int yFound = zombie.getY();

            map[xFound][yFound] = zombie.getTipo();
        }
        for ( Equipamento equipamento: equipment ) {
            int xFound = equipamento.getX();
            int yFound = equipamento.getY();

            map[xFound][yFound] = equipamento.getTipo();
        }
    }

    public int getMapId( int x, int y ) {
        return map[x][y];
    }
}
