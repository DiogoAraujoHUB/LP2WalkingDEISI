package pt.ulusofona.lp2.theWalkingDEISIGame.classesEquipamentos;

import pt.ulusofona.lp2.theWalkingDEISIGame.Defensivo;

public class Veneno extends Defensivo {

    private int numTurnosProtecao;
    private boolean isFull;

    public Veneno(int id, int tipo, int x, int y) {
        super(id, tipo, x, y);

        this.numTurnosProtecao = 2;
        this.isFull = true;
    }

    public int getNumTurnosProtecao() {
        return this.numTurnosProtecao;
    }

    public boolean getIsFull() {
        return this.isFull;
    }

    public void beberVeneno() {
        this.isFull = false;
    }
}
