package pt.ulusofona.lp2.theWalkingDEISIGame.classesCriaturas;

import pt.ulusofona.lp2.theWalkingDEISIGame.Humano;

public class CriancaHumano extends Humano {

    public CriancaHumano(int id, String nome, int x, int y) {
        super(id, nome, x, y);

        this.tipo = 5;
        this.nomeTipo = "Criança";

        this.deslocamentoMaximo = 1;
        this.ableToMoveFreely = false;
        this.ableToMoveLinearly = true;
    }

    public String toString() {
        String texto = id + " | Criança (Vivo) | Os Vivos | " + nome.trim() + " ";

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
        return "CriancaVivo.png";
    }
}
