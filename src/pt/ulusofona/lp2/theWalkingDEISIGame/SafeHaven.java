package pt.ulusofona.lp2.theWalkingDEISIGame;

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
    //and add him to the list of survivors
    public void letIn( Humano humano, List<Creature> survivors ) {

    }
}
