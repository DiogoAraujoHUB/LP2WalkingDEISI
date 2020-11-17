package pt.ulusofona.lp2.theWalkingDEISIGame;

import java.util.ArrayList;
import java.util.List;

public class Humano {
    private int id;
    private int tipo;
    private int x, y;
    private boolean alive;

    private String nome;
    private List<Equipamento> equipamentosApanhados;

    Humano(int id, int tipo, String nome, int x, int y) {
        this.id = id;
        this.tipo = tipo;
        this.nome = nome;
        this.x = x;
        this.y = y;
        this.alive = true;
        equipamentosApanhados = new ArrayList<>();
    }

    public String getImagePNG() {
        return "Human.png";
    }

    public int getId() {
        return this.id;
    }

    public int getTipo() {
        return this.tipo;
    }

    public int getX() {
        return this.x;
    }

    public int getY() {
        return this.y;
    }

    public String getNome() {
        return this.nome;
    }

    public String toString() {
        String texto = id + " | Humano | Os Vivos | " + nome + " ";
        if ( equipamentosApanhados == null || equipamentosApanhados.size() == 0 ) {
            texto += "@ (" + x + ", " + y + ")";
        } else {
            texto += equipamentosApanhados.size() + " @ (" + x + ", " + y + ")";
        }

        return texto;
    }
}
