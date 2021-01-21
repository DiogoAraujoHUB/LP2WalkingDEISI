package pt.ulusofona.lp2.theWalkingDEISIGame.classesCriaturas;

import pt.ulusofona.lp2.theWalkingDEISIGame.Humano;

public class AdultoHumano extends Humano {

    public AdultoHumano(int id, String nome, int x, int y) {
        super(id, nome, x, y);

        this.tipo = 6;
        this.nomeTipo = "Adulto (Vivo)";

        this.deslocamentoMaximo = 2;
        this.ableToMoveFreely = true;
        this.ableToMoveLinearly = true;
    }

    public String toString() {
        String texto = id + " | Adulto (Vivo) | Os Vivos | " + nome.trim() + " ";

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
        return "AdultoVivo.png";
    }
}

