package pt.ulusofona.lp2.theWalkingDEISIGame.classesCriaturas;

import pt.ulusofona.lp2.theWalkingDEISIGame.Zombie;

public class VampiroZombie extends Zombie {

    public VampiroZombie(int id, String nome, int x, int y) {
        super(id, nome, x, y);

        this.tipo = 4;
        this.deslocamentoMaximo = 2;
    }

    public String toString() {
        String texto = id + " | Zombie Vampiro | Os Outros |" + nome + "";
        texto += numEquipamentos + " @ (" + x + ", " + y + ")";

        return texto;
    }

    public String getImagePNG() {
        return "VampiroZombie.png";
    }
}
