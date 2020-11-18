package pt.ulusofona.lp2.theWalkingDEISIGame;

import java.util.ArrayList;
import java.util.List;

public class Zombie {
    private int id;
    private int x, y;

    private String nome;
    private List<Equipamento> equipamentosDestruidos;

    Zombie( int id, String nome, int x, int y ) {
        this.id = id;
        this.nome = nome;
        this.x = x;
        this.y = y;
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

    public String getNome() {
        return this.nome;
    }

    public String toString() {
        String texto = id + " | Zombie | Os Outros |" + nome + "";
        if ( equipamentosDestruidos == null ) {
            texto += "@ (" + x + ", " + y + ")";
        } else {
            texto += equipamentosDestruidos.size() + " @ (" + x + ", " + y + ")";
        }

        return texto;
    }

    public void setX( int x ) {
        this.x = x;
    }

    public void setY( int y ) {
        this.y = y;
    }

}
