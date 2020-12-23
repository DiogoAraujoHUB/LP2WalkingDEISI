package pt.ulusofona.lp2.theWalkingDEISIGame.classesCriaturas;

import pt.ulusofona.lp2.theWalkingDEISIGame.Zombie;

public class MilitarZombie extends Zombie {

    public MilitarZombie(int id, int tipo, String nome, int x, int y) {
        super(id, tipo, nome, x, y);

        this.deslocamentoMaximo = 3;
    }

    public String toString() {
        String texto = id + " | Militar (Zombie) | Os Outros |" + nome + "";
        texto += numEquipamentos + " @ (" + x + ", " + y + ")";

        return texto;
    }

    public String getImagePNG() {
        return "MilitarZombie.png";
    }
}
