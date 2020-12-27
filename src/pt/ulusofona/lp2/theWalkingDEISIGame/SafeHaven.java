package pt.ulusofona.lp2.theWalkingDEISIGame;

import java.util.ArrayList;
import java.util.List;

public class SafeHaven {

    private int x;
    private int y;

    public SafeHaven(int x, int y) {
        this.x = x;
        this.y = y;
    }

    public int getX() {
        return this.x;
    }

    public int getY() {
        return this.y;
    }

    //push a human into the safe haven
    public boolean moveIntoSafeHaven(Mapa map, Creature creatureFound) {
        int xO = creatureFound.getX();
        int yO = creatureFound.getY();

        //so movemos para dentro do safe haven se for humano
        if (creatureFound instanceof Humano) {
            ((Humano) creatureFound).goInsideSafeHaven();

            //Fazer o humano desaparecer
            map.setPositionType(xO, yO, 0);
            map.getPosition(xO,yO).setCreature(null);
            return true;
        }

        return false;
    }

    public String toString() {
        return "Safe Haven | @ (" + x + ", " + y + ")";
    }
}
