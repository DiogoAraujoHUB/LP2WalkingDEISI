package pt.ulusofona.lp2.theWalkingDEISIGame.classesEquipamentos;

import pt.ulusofona.lp2.theWalkingDEISIGame.Defensivo;

public class Veneno extends Defensivo {

    private boolean isFull;

    public Veneno(int id, int tipo, int x, int y) {
        super(id, tipo, x, y);

        this.isFull = true;
    }

    public boolean getIsFull() {
        return this.isFull;
    }

    public void esvaziarVeneno() {
        this.isFull = false;
    }
}
