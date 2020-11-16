package pt.ulusofona.lp2.theWalkingDEISIGame;

import java.util.ArrayList;
import java.util.List;

public class Humano {
    int id;
    int tipo;
    String nome;
    int x, y;
    List<Equipamento> equipamentosApanhados = new ArrayList<>();
    boolean alive;

    Humano(int id, int tipo, String nome, int x, int y) {
        this.id = id;
        this.tipo = tipo;
        this.nome = nome;
        this.x = x;
        this.y = y;
        this.alive = true;
    }

    public String getImagePNG() {
        return "Human.png";
    }

    public int getId() {
        return this.id;
    }

    public String toString() {
        String texto = id + " | " + tipo + " | Humans | " + nome + " ";
        if ( equipamentosApanhados == null || equipamentosApanhados.size() == 0 ) {
            texto += "@ (" + x + "," + y + ")";
        } else {
            texto += equipamentosApanhados.size() + " @ (" + x + "," + y + ")";
        }

        return texto;
    }
}
