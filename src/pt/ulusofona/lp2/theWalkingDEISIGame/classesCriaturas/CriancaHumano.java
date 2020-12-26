package pt.ulusofona.lp2.theWalkingDEISIGame.classesCriaturas;

import pt.ulusofona.lp2.theWalkingDEISIGame.Humano;

public class CriancaHumano extends Humano {

    public CriancaHumano(int id, String nome, int x, int y) {
        super(id, nome, x, y);

        this.tipo = 5;
        this.deslocamentoMaximo = 1;

        this.ableToMoveFreely = false;
    }

    public String toString() {
        String texto = id + " | Criança (Vivo) | Os Vivos |" + nome + "";
        texto += numEquipamentos + " @ (" + x + ", " + y + ")";

        return texto;
    }

    public String getImagePNG() {
        return "CriancaVivo.png";
    }
}
