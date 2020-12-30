package pt.ulusofona.lp2.theWalkingDEISIGame.classesEquipamentos;

import pt.ulusofona.lp2.theWalkingDEISIGame.Defensivo;

public class Antidoto extends Defensivo {

    private boolean isFull;

    public Antidoto(int id, int tipo, int x, int y) {
        super(id, tipo, x, y);

        this.isFull = true;
    }

    public boolean getIsFull() {
        return this.isFull;
    }

    public void beberAntidoto() {
        this.isFull = false;
    }
}
