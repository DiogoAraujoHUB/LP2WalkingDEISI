package pt.ulusofona.lp2.theWalkingDEISIGame.classesCriaturas;

import pt.ulusofona.lp2.theWalkingDEISIGame.Zombie;

public class CriancaZombie extends Zombie {

    public CriancaZombie(int id, String nome, int x, int y) {
        super(id, nome, x, y);

        this.tipo = 0;
        this.deslocamentoMaximo = 1;
    }

    public String toString() {
        String texto = id + " | Criança (Zombie) | Os Outros |" + nome + "";
        texto += numEquipamentos + " @ (" + x + ", " + y + ")";

        return texto;
    }

    public String getImagePNG() {
        return "CriancaZombie.png";
    }
}
