package pt.ulusofona.lp2.theWalkingDEISIGame.classesCriaturas;

import pt.ulusofona.lp2.theWalkingDEISIGame.Zombie;

public class IdosoZombie extends Zombie {

    public IdosoZombie(int id, String nome, int x, int y) {
        super(id, nome, x, y);

        this.tipo = 3;
        this.deslocamentoMaximo = 1;

        this.ableToMoveFreely = false;
        this.ableToMoveLinearly = true;
    }

    public String toString() {
        String texto = id + " | Idoso (Zombie) | Os Outros |" + nome + "";

        if ( hasDied ) {
            texto += numEquipamentos + " @ RIP";
        } else {
            texto += numEquipamentos + " @ (" + x + ", " + y + ")";
        }

        return texto;
    }

    public String getImagePNG() {
        return "IdosoZombie.png";
    }
}
