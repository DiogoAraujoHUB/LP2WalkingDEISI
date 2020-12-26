package pt.ulusofona.lp2.theWalkingDEISIGame.classesCriaturas;

import pt.ulusofona.lp2.theWalkingDEISIGame.Humano;

public class IdosoHumano extends Humano {

    public IdosoHumano(int id, String nome, int x, int y) {
        super(id, nome, x, y);

        this.tipo = 8;
        this.deslocamentoMaximo = 1;

        this.ableToMoveFreely = false;
    }

    public String toString() {
        String texto = id + " | Idoso (Vivo) | Os Vivos |" + nome + "";
        texto += numEquipamentos + " @ (" + x + ", " + y + ")";

        return texto;
    }

    public String getImagePNG() {
        return "IdosoVivo.png";
    }
}
