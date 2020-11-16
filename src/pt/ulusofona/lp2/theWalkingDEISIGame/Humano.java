package pt.ulusofona.lp2.theWalkingDEISIGame;

public class Humano {
    int id;
    String nome;
    int x, y;
    boolean alive;

    Humano( int id, String nome, int x, int y ) {
        this.id = id;
        this.nome = nome;
        this.x = x;
        this.y = y;
        this.alive = true;
    }
}
