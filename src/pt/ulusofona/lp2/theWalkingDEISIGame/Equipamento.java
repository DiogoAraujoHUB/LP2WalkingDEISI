package pt.ulusofona.lp2.theWalkingDEISIGame;

public class Equipamento {
    static final int imageType = -1;
    private int id;
    private int tipo;
    private int x, y;

    Equipamento( int id, int tipo, int x, int y ) {
        this.id = id;
        this.tipo = tipo;
        this.x = x;
        this.y = y;
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
}
