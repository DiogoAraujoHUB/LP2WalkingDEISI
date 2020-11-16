package pt.ulusofona.lp2.theWalkingDEISIGame;

import java.util.ArrayList;
import java.util.List;

public class Zombie {
    int id;
    int tipo;
    String nome;
    List<Equipamento> equipamentosDestruidos;
    int x, y;
    boolean alive;

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
        String texto = id + " | " + tipo + " | Zombies | " + nome + " ";
        if ( equipamentosDestruidos == null || equipamentosDestruidos.size() == 0 ) {
            texto += "@ (" + x + "," + y + ")";
        } else {
            texto += equipamentosDestruidos.size() + " @ (" + x + "," + y + ")";
        }

        return texto;
    }

}
