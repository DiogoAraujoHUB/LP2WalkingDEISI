package pt.ulusofona.lp2.theWalkingDEISIGame;

import java.util.Arrays;
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

    public void addHumans( List<Humano> humans ) {
        for ( Humano human: humans ) {
            int xFound = human.getX();
            int yFound = human.getY();

            if ( map[xFound][yFound].getTipo() == 1 || map[xFound][yFound].getTipo() == 2 ) {
                continue;
            }

            if ( map[xFound][yFound].getTipo() == 0 ) {
                map[xFound][yFound].setTipo(2);    //1 is human with equipment //2 is human without equipment
                map[xFound][yFound].setHuman(human);
            } else if ( map[xFound][yFound].getTipo() == -1 ) {
                human.getEquipamentoApanhado().add( map[xFound][yFound].getEquipamento() );
                map[xFound][yFound].setEquipamento(null);
                human.apanharEquipamento();

                map[xFound][yFound].setTipo(1);
                map[xFound][yFound].setHuman(human);
            }
        }
    }

    public void addZombies( List<Zombie> zombies ) {
        for ( Zombie zombie: zombies ) {
            int xFound = zombie.getX();
            int yFound = zombie.getY();

            if ( map[xFound][yFound].getTipo() == 1 || map[xFound][yFound].getTipo() == 2 ) {
                continue;
            }
            if ( map[xFound][yFound].getTipo() == 3 ) {
                continue;
            }

            if ( map[xFound][yFound].getTipo() == -1 ) {
                map[xFound][yFound].setEquipamento(null);
                zombie.destroiEquipamento();
            }
            map[xFound][yFound].setTipo(3); //3 is a zombie
            map[xFound][yFound].setZombie(zombie);
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

    //está correto assim
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
