package pt.ulusofona.lp2.theWalkingDEISIGame.classesCriaturas;

import pt.ulusofona.lp2.theWalkingDEISIGame.Humano;

public class MilitarHumano extends Humano {

    public MilitarHumano(int id, String nome, int x, int y) {
        super(id, nome, x, y);

        this.tipo = 7;
        this.deslocamentoMaximo = 3;

        this.ableToMoveFreely = true;
        this.ableToMoveLinearly = true;
    }

    public String toString() {
        String texto = id + " | Militar (Vivo) | Os Vivos |" + nome + "";

        if ( hasDied ) {
            texto += numEquipamentos + " @ RIP";
        } else if ( insideSafeHaven ) {
            texto += numEquipamentos + " @ A salvo";
        } else {
            texto += numEquipamentos + " @ (" + x + ", " + y + ")";
        }

        return texto;
    }

    public String getImagePNG() {
        return "MilitarVivo.png";
    }
}
