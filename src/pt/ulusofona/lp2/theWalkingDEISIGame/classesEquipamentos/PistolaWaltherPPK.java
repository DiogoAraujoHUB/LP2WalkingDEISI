package pt.ulusofona.lp2.theWalkingDEISIGame.classesEquipamentos;

import pt.ulusofona.lp2.theWalkingDEISIGame.Ofensivo;

public class PistolaWaltherPPK extends Ofensivo {

    public PistolaWaltherPPK(int id, int tipo, int x, int y) {
        super(id, tipo, x, y);

        this.numUses = 3;
    }
}
