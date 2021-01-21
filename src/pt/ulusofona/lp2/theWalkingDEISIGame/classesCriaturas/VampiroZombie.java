package pt.ulusofona.lp2.theWalkingDEISIGame.classesCriaturas;

import pt.ulusofona.lp2.theWalkingDEISIGame.Zombie;

public class VampiroZombie extends Zombie {

    public VampiroZombie(int id, String nome, int x, int y) {
        super(id, nome, x, y);

        this.tipo = 4;
        this.nomeTipo = "Zombie Vampiro";

        this.deslocamentoMaximo = 2;
        this.ableToMoveFreely = true;
        this.ableToMoveLinearly = true;
    }

    public String toString() {
        String texto = id + " | Zombie Vampiro | Os Outros | " + nome.trim() + " ";

        if ( hasDied ) {
            texto += numEquipamentos + " @ RIP";
        } else {
            texto += numEquipamentos + " @ (" + x + ", " + y + ")";
        }

        return texto;
    }

    public String getImagePNG() {
        return "VampiroZombie.png";
    }
}
