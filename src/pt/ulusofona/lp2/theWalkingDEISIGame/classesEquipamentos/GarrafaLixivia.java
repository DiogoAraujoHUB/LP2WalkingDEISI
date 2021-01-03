package pt.ulusofona.lp2.theWalkingDEISIGame.classesEquipamentos;

import pt.ulusofona.lp2.theWalkingDEISIGame.Defensivo;

public class GarrafaLixivia extends Defensivo {

    public GarrafaLixivia(int id, int tipo, int x, int y) {
        super(id, tipo, x, y);

        this.numUses = 3;
    }
}
