package pt.ulusofona.lp2.theWalkingDEISIGame;

import java.util.ArrayList;
import java.util.List;

public class Zombie {
    private int id;
    private int tipo;
    private int x, y;
    private boolean alive;

    private String nome;
    private List<Equipamento> equipamentosDestruidos;

    Zombie( int id, int tipo, String nome, int x, int y ) {
        this.id = id;
        this.tipo = tipo;
        this.nome = nome;
        this.x = x;
        this.y = y;
        this.alive = true;
        equipamentosDestruidos = new ArrayList<>();
    }

    public int getId() {
        return this.id;
    }

    public String getImagePNG() {
        return "Zombie.png";
    }

    public int getX() {
        return this.x;
    }

    public int getY() {
        return this.y;
    }

    public int getTipo() {
        return this.tipo;
    }

    public String getNome() {
        return this.nome;
    }

    public String toString() {
        String texto = id + " | Zombie | Zombies |" + nome + "";
        if ( equipamentosDestruidos == null ) {
            texto += "@ (" + x + ", " + y + ")";
        } else {
            texto += equipamentosDestruidos.size() + " @ (" + x + ", " + y + ")";
        }

        return texto;
    }

}
