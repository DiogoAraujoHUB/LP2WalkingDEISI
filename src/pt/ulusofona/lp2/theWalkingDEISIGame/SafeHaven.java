package pt.ulusofona.lp2.theWalkingDEISIGame;

import java.util.ArrayList;
import java.util.List;

public class SafeHaven {

    private int x;
    private int y;

    private List<Creature> humansInSafeHaven;

    public SafeHaven(int x, int y) {
        this.x = x;
        this.y = y;

        this.humansInSafeHaven = new ArrayList<>();
    }

    public int getX() {
        return this.x;
    }

    public int getY() {
        return this.y;
    }

    public List<Creature> getHumansInSafeHaven() {
        return this.humansInSafeHaven;
    }

    //push a human into the safe haven
    //and add him to the list of survivors
    public boolean moveIntoSafeHaven(Mapa map, Creature humanFound) {
        int xO = humanFound.getX();
        int yO = humanFound.getY();

        //mover para dentro do safeHaven
        humansInSafeHaven.add(humanFound);

        //Fazer o humano desaparecer
        map.setPositionType( xO, yO, 0 );
        map.getPosition(xO,yO).setCreature(null);

        return true;
    }

    public String toString() {
        return "Safe Haven | @ (" + x + ", " + y + ")";
    }
}
