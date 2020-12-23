package pt.ulusofona.lp2.theWalkingDEISIGame.classesEquipamentos;

import pt.ulusofona.lp2.theWalkingDEISIGame.Defensivo;

public class Veneno extends Defensivo {

    private int numTurnosProtecao;

    public Veneno(int id, int tipo, int x, int y) {
        super(id, tipo, x, y);

        this.numTurnosProtecao = 2;
    }

    public int getNumTurnosProtecao() {
        return this.numTurnosProtecao;
    }
}
