package pt.ulusofona.lp2.theWalkingDEISIGame.classesEquipamentos;

import pt.ulusofona.lp2.theWalkingDEISIGame.Defensivo;

public class Veneno extends Defensivo {

    private int numTurnosProtecao;
    private boolean isEmpty;

    public Veneno(int id, int tipo, int x, int y) {
        super(id, tipo, x, y);

        this.numTurnosProtecao = 2;
        this.isEmpty = false;
    }

    public int getNumTurnosProtecao() {
        return this.numTurnosProtecao;
    }

    public boolean getIsEmpty() {
        return this.isEmpty;
    }

    public void beberVeneno() {
        this.isEmpty = true;
    }
}
