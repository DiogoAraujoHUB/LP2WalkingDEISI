package pt.ulusofona.lp2.theWalkingDEISIGame.classesCriaturas;

import pt.ulusofona.lp2.theWalkingDEISIGame.Zombie;

public class AdultoZombie extends Zombie {

    public AdultoZombie(int id, int tipo, String nome, int x, int y) {
        super(id, tipo, nome, x, y);

        this.deslocamentoMaximo = 2;
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
