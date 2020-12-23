package pt.ulusofona.lp2.theWalkingDEISIGame.classesEquipamentos;

import pt.ulusofona.lp2.theWalkingDEISIGame.Ofensivo;

public class EstacaMadeira extends Ofensivo {

    public EstacaMadeira( int id, int tipo, int x, int y) {
        super(id, tipo, x, y);

        this.range = 1;
    }
}
