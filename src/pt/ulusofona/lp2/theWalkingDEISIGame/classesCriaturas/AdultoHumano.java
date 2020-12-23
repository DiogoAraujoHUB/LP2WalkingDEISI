package pt.ulusofona.lp2.theWalkingDEISIGame.classesCriaturas;

import pt.ulusofona.lp2.theWalkingDEISIGame.Humano;

public class AdultoHumano extends Humano {

    public AdultoHumano(int id, int tipo, String nome, int x, int y) {
        super(id, tipo, nome, x, y);

        this.deslocamentoMaximo = 2;
    }

    public String toString() {
        String texto = id + " | Adulto (Vivo) | Os Vivos |" + nome + "";
        texto += numEquipamentos + " @ (" + x + ", " + y + ")";

        return texto;
    }

    public String getImagePNG() {
        return "AdultoVivo.png";
    }
}

