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

    public String toString() {
        String texto = id + " | Zombie | Zombies | " + nome + " ";
        if ( equipamentosDestruidos == null || equipamentosDestruidos.size() == 0 ) {
            texto += "@ (" + x + ", " + y + ")";
        } else {
            texto += equipamentosDestruidos.size() + " @ (" + x + ", " + y + ")";
        }

        return texto;
    }

}
