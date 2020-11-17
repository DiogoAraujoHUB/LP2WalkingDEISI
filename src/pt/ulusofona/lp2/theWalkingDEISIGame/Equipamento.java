package pt.ulusofona.lp2.theWalkingDEISIGame;

public class Equipamento {
    private int id;
    private int tipo;
    private int x, y;

    Equipamento( int id, int tipo, int x, int y ) {
        this.id = id;
        this.tipo = tipo;
        this.x = x;
        this.y = y;
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
}
