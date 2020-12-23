package pt.ulusofona.lp2.theWalkingDEISIGame.classesCriaturas;

import pt.ulusofona.lp2.theWalkingDEISIGame.Humano;

public class CriancaHumano extends Humano {

    public CriancaHumano(int id, int tipo, String nome, int x, int y) {
        super(id, tipo, nome, x, y);

        this.deslocamentoMaximo = 1;
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
