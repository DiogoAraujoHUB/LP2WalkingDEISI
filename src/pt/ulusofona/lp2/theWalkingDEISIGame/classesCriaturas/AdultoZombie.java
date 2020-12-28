package pt.ulusofona.lp2.theWalkingDEISIGame.classesCriaturas;

import pt.ulusofona.lp2.theWalkingDEISIGame.Zombie;

public class AdultoZombie extends Zombie {

    public AdultoZombie(int id, String nome, int x, int y) {
        super(id, nome, x, y);

        this.tipo = 1;
        this.deslocamentoMaximo = 2;

        this.ableToMoveFreely = true;
        this.ableToMoveLinearly = true;
    }

    public String toString() {
        String texto = id + " | Adulto (Zombie) | Os Outros |" + nome + "";
        texto += numEquipamentos + " @ (" + x + ", " + y + ")";

        return texto;
    }

    public String getImagePNG() {
        return "AdultoZombie.png";
    }
}
