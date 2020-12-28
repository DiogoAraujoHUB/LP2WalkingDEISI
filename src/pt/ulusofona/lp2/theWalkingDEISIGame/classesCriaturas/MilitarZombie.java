package pt.ulusofona.lp2.theWalkingDEISIGame.classesCriaturas;

import pt.ulusofona.lp2.theWalkingDEISIGame.Zombie;

public class MilitarZombie extends Zombie {

    public MilitarZombie(int id, String nome, int x, int y) {
        super(id, nome, x, y);

        this.tipo = 2;
        this.deslocamentoMaximo = 3;

        this.ableToMoveFreely = true;
        this.ableToMoveLinearly = true;
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
