package pt.ulusofona.lp2.theWalkingDEISIGame.classesEquipamentos;

import pt.ulusofona.lp2.theWalkingDEISIGame.Defensivo;

public class EscudoMadeira extends Defensivo {

    private boolean utilizadoPorMilitar;

    public EscudoMadeira(int id, int tipo, int x, int y) {
        super(id, tipo, x, y);

        this.utilizadoPorMilitar = true;
        this.numUses = 1;
    }

    public boolean getUtilizadoPorMilitar() {
        return this.utilizadoPorMilitar;
    }

    public void setUtilizadoPorMilitar(boolean utilizado) {
        this.utilizadoPorMilitar = utilizado;
    }
}
