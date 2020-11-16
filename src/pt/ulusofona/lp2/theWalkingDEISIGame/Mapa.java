package pt.ulusofona.lp2.theWalkingDEISIGame;

public class Mapa {
    int sizeX;
    int sizeY;
    int[][] map;

    Mapa() {
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

    public int getMapId( int x, int y ) {
        return map[x][y];
    }
}
